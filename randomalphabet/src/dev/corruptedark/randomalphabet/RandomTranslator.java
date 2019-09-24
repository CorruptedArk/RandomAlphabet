/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.corruptedark.randomalphabet;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Noah
 */
public class RandomTranslator {
    
    private SecureRandom rand;
    private AlphabetHandler handler;
    private int decoyCount;
    private int tupleSize;

    
    public RandomTranslator(String key, int bucketSize, int decoyCount)
    {
        this.decoyCount = decoyCount;
        this.tupleSize = decoyCount + 3;
        rand = new SecureRandom();
        handler = new AlphabetHandler();
        handler.generateAlphabet(key, bucketSize);
    }
    
    public String encodeText(String text)
    {
        List<NTuple> tupleList = new ArrayList<>();
        int[] tempArray = new int[decoyCount + 1];
        int tempRealCharIndex;
        int tempLocation;
        String encodedText;

        try {
            for (int i = 0; i < text.length(); i++) {
                tempRealCharIndex = rand.nextInt(decoyCount + 1);

                for (int j = 0; j < decoyCount + 1; j++) {
                    tempArray[j] = handler.randomValue();
                }

                tempArray[tempRealCharIndex] = handler.getValueForLetter(text.charAt(i));

                tempRealCharIndex = handler.getValueForNumber(tempRealCharIndex);

                tempLocation = handler.getValueForNumber(i);

                tupleList.add(new NTuple(tempArray.clone(), tempRealCharIndex, tempLocation));
            }

            for (int i = 0; i < tupleList.size(); i++) {
                int index = rand.nextInt(tupleList.size());
                NTuple temp = tupleList.get(index);
                tupleList.set(index, tupleList.get(i));
                tupleList.set(i, temp);
            }

            encodedText = "";
            for (NTuple nTuple : tupleList) {
                tempArray = nTuple.getValues();
                tempRealCharIndex = nTuple.getIndex();
                tempLocation = nTuple.getLocation();

                for (int i : tempArray) {
                    encodedText += String.valueOf(i) + ",";
                }

                encodedText += String.valueOf(tempRealCharIndex) + "," + String.valueOf(tempLocation) + ",";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        
        return encodedText;
    }
    
    public String decodeText(String text)
    {
        String[] pieces = text.split(",");
        char[] decoded = new char[pieces.length / (tupleSize)];

        String[] tempSplit = new String[tupleSize];
        int tempIndex;
        char tempLetter;
        int tempLocation;

        int penultimate;
        int ultimate;

        try
        {
            for(int i = 0; i < pieces.length;)
            {
                for(int j = 0; j < tempSplit.length; j++, i++)
                {
                    tempSplit[j] = pieces[i];
                }
                penultimate = tempSplit.length - 2;
                ultimate = tempSplit.length - 1;

                tempIndex = handler.getNumberForValue(Integer.parseInt(tempSplit[penultimate]));
                tempLetter = handler.getCharFromValue(Integer.parseInt(tempSplit[tempIndex]));
                tempLocation = handler.getNumberForValue(Integer.parseInt(tempSplit[ultimate]));
                decoded[tempLocation] = tempLetter;

            }
        }
        catch (Exception e)
        {
            throw e;
        }

        return new String(decoded);
    }
}
