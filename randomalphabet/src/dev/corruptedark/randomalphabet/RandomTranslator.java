/*
 * RandomAlphabet is a Java GUI tool to encode and decode text strings using pseudorandomly generated ciphers from string keys.
 *     Copyright (C) 2019  Noah Stanford <noahstandingford@gmail.com>
 *
 *     RandomAlphabet is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     RandomAlphabet is distributed in the hope that it will be fun, interesting, and useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.corruptedark.randomalphabet;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class RandomTranslator {

    private final SecureRandom rand;
    private final AlphabetHandler handler;
    private final int decoyCount;
    private final int tupleSize;


    public RandomTranslator(String key, int bucketSize, int decoyCount) {
        this.decoyCount = decoyCount;
        this.tupleSize = decoyCount + 3;
        rand = new SecureRandom();
        handler = new AlphabetHandler();
        handler.generateAlphabet(key, bucketSize);
    }

    public String encodeText(String text) throws NumberFormatException {
        List<NTuple> tupleList = new ArrayList<>();
        int[] tempArray = new int[decoyCount + 1];
        int tempRealCharIndex;
        int tempLocation;
        StringBuilder encodedText;

        for (int i = 0; i < text.length(); i++) {
            tempRealCharIndex = rand.nextInt(decoyCount + 1);

            for (int j = 0; j < decoyCount + 1; j++) {
                tempArray[j] = handler.randomValue();
            }

            tempArray[tempRealCharIndex] = handler.getValueForLetter(text.charAt(i));

            tempRealCharIndex = handler.encodeNumber(tempRealCharIndex, decoyCount + 1);

            tempLocation = handler.encodeNumber(i, text.length());

            tupleList.add(new NTuple(tempArray.clone(), tempRealCharIndex, tempLocation));
        }

        for (int i = 0; i < tupleList.size(); i++) {
            int index = rand.nextInt(tupleList.size());
            NTuple temp = tupleList.get(index);
            tupleList.set(index, tupleList.get(i));
            tupleList.set(i, temp);
        }

        encodedText = new StringBuilder();
        for (NTuple nTuple : tupleList) {
            tempArray = nTuple.getValues();
            tempRealCharIndex = nTuple.getIndex();
            tempLocation = nTuple.getLocation();

            for (int i : tempArray) {
                encodedText.append(i).append(",");
            }

            encodedText.append(tempRealCharIndex).append(",").append(tempLocation).append(",");
        }

        return encodedText.toString();
    }

    public String decodeText(String text) throws NumberFormatException {
        String[] pieces = text.split(",");
        int tupleCount = pieces.length / tupleSize;
        char[] decoded = new char[tupleCount];

        String[] tempSplit = new String[tupleSize];
        int tempIndex;
        char tempLetter;
        int tempLocation;

        int penultimate;
        int ultimate;

        for (int i = 0; i < pieces.length; ) {
            for (int j = 0; j < tempSplit.length; j++, i++) {
                tempSplit[j] = pieces[i];
            }
            penultimate = tempSplit.length - 2;
            ultimate = tempSplit.length - 1;

            tempIndex = handler.decodeNumberValue(Integer.parseInt(tempSplit[penultimate]), decoyCount + 1);
            tempLetter = handler.getCharFromValue(Integer.parseInt(tempSplit[tempIndex]));
            tempLocation = handler.decodeNumberValue(Integer.parseInt(tempSplit[ultimate]), tupleCount);
            decoded[tempLocation] = tempLetter;
        }

        return new String(decoded);
    }
}
