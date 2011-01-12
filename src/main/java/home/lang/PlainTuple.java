package home.lang;

public class PlainTuple<T1, T2> implements Tuple<T1, T2> {
    protected T1 first;
    protected T2 second;

    public PlainTuple () {
        first = null;
        second = null;
    }
    public PlainTuple (T1 t1, T2 t2) {
        first = t1;
        second = t2;
    }
    public T1 getFirst() { return first; }
    public T2 getSecond() { return second; }
    public void setFirst(T1 t1) { first = t1; }
    public void setSecond(T2 t2) { second = t2; }
}
