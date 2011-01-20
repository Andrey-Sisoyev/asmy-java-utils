package home.lang.functional;

import java.util.Collection;
import java.util.Iterator;

public class FunctionalUtils {
    public static <AC,EL> AC foldl(AC initialAccum, Lambda2A<AC, EL, AC> la, Iterator<EL> it) {
        if(it.hasNext()) {
            AC newAccum = la.reduceLambda(initialAccum, it.next());
            return foldl(newAccum, la, it);
        } else return initialAccum;
    }

    public static <AC,EL> AC foldr(Iterator<EL> it, Lambda2A<EL, AC, AC> la, AC initialAccum) {
        if(it.hasNext()) {
            EL el = it.next();
            AC newAccum = foldr(it, la, initialAccum);
            return la.reduceLambda(el, newAccum);
        } else return initialAccum;
    }

    public static <A,B> Collection<B> map(Iterator<A> it, Lambda1A<A, B> la, Collection<B> resultAcceptor) {
        while(it.hasNext()) {
            resultAcceptor.add(la.reduceLambda(it.next()));
        }
        return resultAcceptor;
    }

    public static <A> Collection<A> filter(Iterator<A> it, Lambda1A<A, Boolean> la, Collection<A> resultAcceptor) {
        A el;
        while(it.hasNext()) {
            el = it.next();
            if(la.reduceLambda(el)) resultAcceptor.add(el);
        }
        return resultAcceptor;
    }
}
