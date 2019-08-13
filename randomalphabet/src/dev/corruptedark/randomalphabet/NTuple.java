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
public class NTuple {
    private int[] values;
    private int index;
    private int location;

    public NTuple(int[] values, int penultimate, int ultimate)
    {
        this.values = values;
        index = penultimate;
        location = ultimate;
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
