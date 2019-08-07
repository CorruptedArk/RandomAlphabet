/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.corruptedark.randomalphabet;

import java.util.List;
import java.util.ArrayList;
import java.security.SecureRandom;

/**
 *
 * @author Noah
 */
public class LetterBucket {
    private char letter;
    private List<Integer> values;
    private SecureRandom rand;
    
    public LetterBucket (char letter)
    {
        this.letter = letter;
        this.values = new ArrayList<>();
        rand = new SecureRandom();
    }
    
    public void addValue(int value)
    {
        values.add(value);
    }
    
    public boolean containsValue(int value)
    {
        boolean contains = false;
        
        for(int i = 0; !contains && i < values.size(); i++)
        {
            contains = value == values.get(i);
        }
        
        return contains;
    }
    
    public char getLetter()
    {
        return letter;
    }
    
    public int randomValue()
    {
        return values.get(rand.nextInt(values.size()));
    }
    
}
