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
    
    public RandomTranslator(String key, int bucketSize, int decoyCount)
    {
        this.decoyCount = decoyCount;
        rand = new SecureRandom();
        handler = new AlphabetHandler();
        handler.generateAlphabet(key, bucketSize);
    }
    
    public String encodeText(String text)
    {
        List<NTuple> tupleList = new ArrayList<>();
        int[] tempArray = new int[decoyCount + 1];
        int tempIndex;
        String encodedText;

        try {
            for (int i = 0; i < text.length(); i++) {
                tempIndex = rand.nextInt(decoyCount + 1);

                for (int j = 0; j < decoyCount + 1; j++) {
                    tempArray[j] = handler.randomValue();
                }

                tempArray[tempIndex] = handler.getValueForLetter(text.charAt(i));

                tempIndex = handler.getValueForLetter((char) (tempIndex + '0'));

                tupleList.add(new NTuple(tempArray.clone(), tempIndex, i));
            }

            for (int i = 0; i < tupleList.size(); i++) {
                int index = rand.nextInt(tupleList.size());
                NTuple temp = tupleList.get(index);
                tupleList.set(index, tupleList.get(i));
                tupleList.set(i, temp);
            }

            encodedText = "";
            for (int i = 0; i < tupleList.size(); i++)
            {
                tempArray = tupleList.get(i).getValues();
                tempIndex = tupleList.get(i).getIndex();
                int tempLocation = tupleList.get(i).getLocation();

                for(int j = 0; j < tempArray.length; j++)
                {
                    encodedText += String.valueOf(tempArray[j]) + ",";
                }

                encodedText += String.valueOf(tempIndex) + "," + String.valueOf(tempLocation) + ";";
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
        String[] tupleSplit = text.split(";");
        char[] decoded = new char[tupleSplit.length];
        String[] tempSplit;
        int tempIndex;
        char tempLetter;
        int tempLocation;

        int penultimate;
        int ultimate;

        try
        {
            for(int i = 0; i < tupleSplit.length; i++)
            {
                tempSplit = tupleSplit[i].split(",");

                penultimate = tempSplit.length - 2;
                ultimate = tempSplit.length - 1;

                tempIndex = Integer.parseInt(String.valueOf(handler.getCharFromValue(Integer.parseInt(tempSplit[penultimate]))));
                tempLetter = handler.getCharFromValue(Integer.parseInt(tempSplit[tempIndex]));
                tempLocation = Integer.parseInt(tempSplit[ultimate]);
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
