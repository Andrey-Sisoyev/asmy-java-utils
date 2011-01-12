/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.home.myutils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Master
 */
public class Chan<T extends Object> {
    protected List<T> objList = null;
    protected final Object readMonitor = new Object();
    protected static List<Long> readersThreadsStack = new ArrayList<Long>();
    

    // Constructors
    public Chan() { this.objList = new ArrayList<T>(); }
    public Chan(List<T> objList_0) { this.objList = objList_0; }

    // getters/setters
    public synchronized T read() throws InterruptedException {
        T z;
        synchronized(readMonitor) {
            if(this.objList.isEmpty()) {
                Long myThreadID = new Long(Thread.currentThread().getId());
                readersThreadsStack.add(myThreadID);
                while(this.objList.isEmpty()) {
                    readMonitor.wait();
                    if(readersThreadsStack.get(0) == myThreadID) {
                        if(!this.objList.isEmpty()) { // protection against Spurious Wakeup
                            readersThreadsStack.remove(0);
                        }
                    }
                }
            }
            z = this.objList.get(0);
            boolean notEmpty;
            synchronized(objList) {
                this.objList.remove(0);
                notEmpty = !this.objList.isEmpty();
            }
            if(notEmpty) readMonitor.notifyAll();
        }
        return z;
    }

    public void readAll(List<T> dest) {
        synchronized(readMonitor) {
            synchronized(objList) {
                dest.addAll(this.objList);
                this.objList.clear();
            }
        }
    }

    public List<T> readAll() {
        List<T> z = new ArrayList<T>();
        this.readAll(z);
        return z;
    }

    public void write(T t) throws InterruptedException {
        synchronized(objList) { objList.add(t); }
        synchronized(readMonitor) { readMonitor.notifyAll(); }
    }

    public void writeAll(List<T> tl, int big_list_after, int sleep_milis_after) throws InterruptedException {
        int s = tl.size();
        if (s > big_list_after && big_list_after > 0) {
            ListIterator<T> it = tl.listIterator();
            synchronized(objList) {
                for (int i = 0; i < big_list_after; i++ ) objList.add(it.next());
            }
            synchronized(readMonitor) { readMonitor.notifyAll(); }
            Thread.sleep(sleep_milis_after);
            synchronized(objList) {
                while(it.hasNext()) objList.add(it.next());
            }
        } else {
            synchronized(objList) { objList.addAll(tl); }
        }
        synchronized(readMonitor) { readMonitor.notifyAll(); }
    }

    public void writeAll(List<T> tl) {
        try {
            this.writeAll(tl, 0, 0);
        } catch (InterruptedException ex) {
            ex.printStackTrace(); // this should never happen, since (big_list_after == 0)
        }
    }

    public T peek() {
        return this.objList.get(0);
    }

    public boolean isEmpty() {
        return this.objList.isEmpty();
    }
}