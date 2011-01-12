/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.home.myutils;

/**
 *
 * @author Master
 */
public interface Tuple<T1, T2> {
    public T1 getFirst();
    public T2 getSecond();
    public void setFirst(T1 t1);
    public void setSecond(T2 t2);
}
