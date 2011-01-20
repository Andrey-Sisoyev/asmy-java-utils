package home.lang.functional.concurrent;

import home.lang.Either;
import home.lang.Nothing;
import home.lang.functional.Lambda0A;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LazyConcurrentHashMapOfImmutables<K,V> {
    private final ConcurrentMap<K, Either<V, Lambda0A<V>>> MMMMM;
    
    public LazyConcurrentHashMapOfImmutables() {
        MMMMM = new ConcurrentHashMap<K, Either<V, Lambda0A<V>>>();
    }

    public V get(K k) {
        Either<V, Lambda0A<V>> ei = MMMMM.get(k);
        V e;
        if (ei.isLeft()) {
            e = ei.getLeft();
        } else {
            synchronized (ei) {
                if (ei.isLeft()) {
                    e = ei.getLeft();
                } else {
                    e = ei.getRight().reduceLambda();
                    ei.setLeft(e);
                }
            }
        }
        return e;
    }

    public void strictize(K k) {
        Either<V, Lambda0A<V>> ei = MMMMM.get(k);
        if (ei.isRight()) {
            synchronized (ei) {
                if (ei.isRight()) {
                    ei.setLeft(ei.getRight().reduceLambda());
                }
            }
        }
    }

    public Either<V, Lambda0A<V>> putIfAbsent(K k, Lambda0A<V> val_la) {
        Either<V, Lambda0A<V>> o;
        synchronized (MMMMM) {
            o = MMMMM.putIfAbsent(k, new Either<V, Lambda0A<V>>(Nothing.IS, val_la));
        }
        return o;
    }
}
