package home.lang;

public class Tuple<T1, T2> implements ITuple<T1, T2> {
    private T1 first;
    private T2 second;

    public Tuple () {
        first = null;
        second = null;
    }
    public Tuple (T1 t1, T2 t2) {
        first = t1;
        second = t2;
    }
    public T1 getFirst() { return first; }
    public T2 getSecond() { return second; }
    public void setFirst(T1 t1) { first = t1; }
    public void setSecond(T2 t2) { second = t2; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple<T1, T2> other = (Tuple<T1, T2>) obj;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.first != null ? this.first.hashCode() : 0);
        hash = 59 * hash + (this.second != null ? this.second.hashCode() : 0);
        return hash;
    }
}
