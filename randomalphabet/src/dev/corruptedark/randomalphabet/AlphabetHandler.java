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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.security.MessageDigest;


public class AlphabetHandler {
    private final Random generator;
    private final List<LetterBucket> alphabet;
    private final List<Integer> valueList;

    private final String SHA_256 = "SHA-256";

    public AlphabetHandler() {
        generator = new Random();
        alphabet = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    public void generateAlphabet(String key, int bucketSize) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update(key.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest();
            String byteString = new String(bytes);
            generator.setSeed(byteString.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        alphabet.clear();
        valueList.clear();

        for (char i = 32; i <= 126; i++) {
            alphabet.add(new LetterBucket(i));
        }
        alphabet.add(new LetterBucket((char) 9));
        alphabet.add(new LetterBucket((char) 10));
        alphabet.add(new LetterBucket((char) 11));
        alphabet.add(new LetterBucket((char) 13));

        for (int i = 0; i < alphabet.size() * bucketSize; i++) {
            valueList.add(i);
        }

        for (int i = 0; i < valueList.size(); i++) {
            int index = generator.nextInt(valueList.size());
            int temp = valueList.get(index);
            valueList.set(index, valueList.get(i));
            valueList.set(i, temp);
        }

        int valueIndex = 0;
        for (LetterBucket letterBucket : alphabet) {
            for (int i = 0; i < bucketSize; i++) {
                letterBucket.addValue(valueList.get(valueIndex));
                valueIndex++;
            }
        }
    }


    public char getCharFromValue(int value) {
        char letter = 0;
        boolean found = false;
        for (int i = 0; !found && i < alphabet.size(); i++) {
            if (alphabet.get(i).containsValue(value)) {
                letter = alphabet.get(i).getLetter();
                found = true;
            }
        }

        return letter;
    }

    public int getValueForLetter(char letter) {
        int value = -1;
        boolean letterFound = false;

        for (int i = 0; !letterFound && i < alphabet.size(); i++) {
            if (letter == alphabet.get(i).getLetter()) {
                value = alphabet.get(i).randomValue();
                letterFound = true;
            }
        }

        return value;
    }

    public int encodeNumber(int number, int numberMax) {
        int encoded;

        encoded = randomValue();

        int diff = (number - encoded) % numberMax;
        encoded += diff;

        return encoded;
    }

    public int decodeNumberValue(int value, int numberMax) {
        int number;

        number = value % numberMax;

        return number;
    }

    public int randomValue() {
        return valueList.get(generator.nextInt(valueList.size()));
    }
}
