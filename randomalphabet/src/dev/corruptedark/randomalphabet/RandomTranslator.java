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
    
    SecureRandom rand;
    AlphabetHandler handler;
    
    
    public RandomTranslator(String key, int bucketSize)
    {
        rand = new SecureRandom();
        handler = new AlphabetHandler();
        handler.generateAlphabet(key, bucketSize);
    }
    
    public String encodeText(String text)
    {
        List<FiveTuple> tupleList = new ArrayList<>();
        int[] tempArray = new int[3];
        int tempIndex;
        String encodedText;

        try {
            for (int i = 0; i < text.length(); i++) {
                tempIndex = rand.nextInt(3);

                for (int j = 0; j < 3; j++) {
                    tempArray[j] = handler.randomValue();
                }

                tempArray[tempIndex] = handler.getValueForLetter(text.charAt(i));

                tempIndex = handler.getValueForLetter((char) (tempIndex + '0'));

                tupleList.add(new FiveTuple(tempArray.clone(), tempIndex, i));
            }

            for (int i = 0; i < tupleList.size(); i++) {
                int index = rand.nextInt(tupleList.size());
                FiveTuple temp = tupleList.get(index);
                tupleList.set(index, tupleList.get(i));
                tupleList.set(i, temp);
            }

            encodedText = "";
            for (int i = 0; i < tupleList.size(); i++) {
                tempArray = tupleList.get(i).getValues();
                tempIndex = tupleList.get(i).getIndex();
                int tempLocation = tupleList.get(i).getLocation();

                encodedText += String.valueOf(tempArray[0]) + "," + String.valueOf(tempArray[1]) + "," + String.valueOf(tempArray[2]) + ","
                        + String.valueOf(tempIndex) + "," + String.valueOf(tempLocation) + ";";
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

        try
        {
            for(int i = 0; i < tupleSplit.length; i++)
            {

                    tempSplit = tupleSplit[i].split(",");
                    tempIndex = Integer.parseInt(String.valueOf(handler.getCharFromValue(Integer.parseInt(tempSplit[3]))));
                    tempLetter = handler.getCharFromValue(Integer.parseInt(tempSplit[tempIndex]));
                    tempLocation = Integer.parseInt(tempSplit[4]);
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
