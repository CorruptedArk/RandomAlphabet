/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.corruptedark.randomalphabet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Noah
 */
public class AlphabetHandler {
    private Random generator;
    private List<LetterBucket> alphabet;
    private List<Integer> valueList;
    private int numberOffset;
    
    public AlphabetHandler()
    {
        generator = new Random();
        alphabet = new ArrayList<>();
        valueList = new ArrayList<>();
    }
    
    public void generateAlphabet(String key, int bucketSize)
    {
        generator.setSeed(key.hashCode());
        alphabet.clear();
        valueList.clear();
        
        for (char i = 32; i <= 126; i++)
        {
            alphabet.add(new LetterBucket(i));
        }
        alphabet.add(new LetterBucket((char)9));
        alphabet.add(new LetterBucket((char)10));
        alphabet.add(new LetterBucket((char)11));
        alphabet.add(new LetterBucket((char)13));
        
        for (int i = 0; i < alphabet.size() * bucketSize; i++)
        {
            valueList.add(i);
        }
        
        for(int i = 0; i < valueList.size(); i++)
        {
          int index = generator.nextInt(valueList.size());
          int temp = valueList.get(index);
          valueList.set(index, valueList.get(i));
          valueList.set(i, temp);
        }
        
        int valueIndex = 0;
        for(int i = 0; i < alphabet.size(); i++)
        {
            for(int j = 0; j < bucketSize; j++)
            {
                alphabet.get(i).addValue(valueList.get(valueIndex));
                valueIndex++;
            }
        }

        numberOffset = valueList.get(generator.nextInt(valueList.size()));
    }
    
    
    public char getCharFromValue(int value)
    {
        char letter = 0;
        boolean found = false;
        for(int i = 0; !found && i < alphabet.size(); i++)
        {
           if(alphabet.get(i).containsValue(value)) 
           {
               letter = alphabet.get(i).getLetter();
               found = true;
           }
        }
        
        return letter;
    }
    
    public int getValueForLetter(char letter)
    {
        int value = -1;
        boolean letterFound = false;
        
        for(int i = 0; !letterFound && i < alphabet.size(); i++)
        {
            if(letter == alphabet.get(i).getLetter())
            {
                value = alphabet.get(i).randomValue();
                letterFound = true;
            }
        }
        
        return value;
    }

    public int getValueForNumber(int number)
    {
        int value;

        value = number + numberOffset;

        return value;
    }

    public int getNumberForValue(int value)
    {
        int number;

        number = value - numberOffset;

        return number;
    }
    
    public int randomValue()
    {
        return valueList.get(generator.nextInt(valueList.size()));
    }
}
