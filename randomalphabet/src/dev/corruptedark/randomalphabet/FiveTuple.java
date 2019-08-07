/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.corruptedark.randomalphabet;

/**
 *
 * @author Noah
 */
public class FiveTuple {
    private int[] values;
    private int index;
    private int location;
    
    public FiveTuple(int[] values, int fourth, int fifth)
    {
        this.values = values;
        index = fourth;
        location = fifth;
    }
    
    public int[] getValues()
    {
        return values;
    }
    
    public int getIndex()
    {
        return index;
    }
    
    public int getLocation()
    {
        return location;
    }
}
