package home.lang.concurrency;

import home.lang.PlainTuple;
import home.lang.functional.Lambda1A;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MVar<T extends Object> {
    protected volatile T obj = null;
    protected final Object takeMonitor = new Object();
    protected final Object putMonitor = new Object();
    protected List<AtomicBoolean> takersTokensFIFO = new LinkedList<AtomicBoolean>();
    protected List<AtomicBoolean> puttersTokensFIFO = new LinkedList<AtomicBoolean>();

    // Constructors
    public MVar() {}
    public MVar(T t) { this.obj = t; }

    // getters/setters
    public T take() throws InterruptedException {
        T z = null;
        AtomicBoolean b = null;

        synchronized(takeMonitor) {
            if(takersTokensFIFO.isEmpty()) {
                z = this.obj;
                if(z == null) b = initTakerToken();
                else this.obj = null;                
            } else b = initTakerToken();
        }
        
        if (z == null) z = waitForTakerToken(b);
        
        notifyPutter();

        assert z != null;
        
        return z;
    }

    public void put(T t) throws InterruptedException {
        if(t == null) throw new NullPointerException();

        AtomicBoolean b = null;
        boolean done = false;

        synchronized(putMonitor) {
            if(puttersTokensFIFO.isEmpty()) {
                if(this.obj != null) b = initPutterToken();
                else {
                    this.obj = t;
                    done = true;
                }
            } else b = initPutterToken();
        }
        
        if (!done) waitForPutterToken(b, t);

        notifyTaker();
    }

    public PlainTuple<T, T> modify(Lambda1A<T, T> la) throws InterruptedException {
        T oldVal = null;
        T newVal = null;
        PlainTuple<T, T> r = null;
        AtomicBoolean b = null;

        synchronized(takeMonitor) {
            if(takersTokensFIFO.isEmpty()) {
                oldVal = this.obj;
                if(oldVal == null) b = initTakerToken();
                else {
                    newVal = la.reduceLambda(oldVal);
                    this.obj = newVal;
                    r = new PlainTuple(oldVal, newVal);
                }
            } else b = initTakerToken();
        }

        if (oldVal == null) r = waitForModifierToken(b, la);

        notifyTaker();

        assert (r.getFirst() != null && r.getSecond() != null);

        return r;
    }

    public T modify_(T t) throws InterruptedException {
        Lambda1A<T,T> la = new Lambda1A<T,T>() {
            @Override
            public T reduceLambda(T tt) {
                return tt;
            }
        };
        PlainTuple<T, T> r = modify(la);
        return r.getFirst();
    }

    public T peek() {
        return this.obj;
    }

    public boolean isEmpty() {
        return (this.obj == null);
    }

    // --------------------------
    private AtomicBoolean initTakerToken() {
        AtomicBoolean b = new AtomicBoolean(false);
        takersTokensFIFO.add(b);
        return b;
    }

    private AtomicBoolean initPutterToken() {
        AtomicBoolean b = new AtomicBoolean(false);
        puttersTokensFIFO.add(b);
        return b;
    }

    private T waitForTakerToken(AtomicBoolean b) throws InterruptedException {
        T z;
        synchronized(b){
            while(! b.get()) b.wait();
        }
        synchronized(takeMonitor) {
            takersTokensFIFO.remove(0);
            z = this.obj;
            this.obj = null;
        }
        return z;
    }
    
    private void waitForPutterToken(AtomicBoolean b, T t) throws InterruptedException {
        synchronized(b){
            while(! b.get()) b.wait();
        }
        synchronized(putMonitor) {
            puttersTokensFIFO.remove(0);
            
            assert (this.obj == null);

            this.obj = t;
        }
    }
    
    private PlainTuple<T, T> waitForModifierToken(AtomicBoolean b, Lambda1A<T, T> la) throws InterruptedException {
        T oldVal;
        T newVal;
        PlainTuple<T, T> r;
        synchronized(b){
            while(! b.get()) b.wait();
        }
        synchronized(takeMonitor) {
            takersTokensFIFO.remove(0);
            oldVal = this.obj;
            newVal = la.reduceLambda(oldVal);
            this.obj = newVal;
        }
        return new PlainTuple(oldVal, newVal);
    }

    private void notifyTaker() {
        synchronized(takeMonitor) {
            if(!takersTokensFIFO.isEmpty()) {
                AtomicBoolean b = takersTokensFIFO.get(0);
                b.set(true);
                synchronized(b){
                    b.notify();
                }
            }
        }
    }

    private void notifyPutter() {
        synchronized(putMonitor) {
            if(!puttersTokensFIFO.isEmpty()) {
                AtomicBoolean b = puttersTokensFIFO.get(0);
                b.set(true);
                synchronized(b){
                    b.notify();
                }
            }
        }
    }
}
