package home.log;

import home.lang.Either;
import home.lang.Tuple;
import home.lang.functional.Lambda0A;
import home.lang.functional.Lambda1A;
import home.lang.functional.StdLambdas;
import home.lang.functional.concurrent.LazyConcurrentHashMapOfImmutables;
import home.utils.reflection.ObjectsHierarchy;
import org.slf4j.Logger;

public class LoggersByOH {
    
    private static class ComplexMap<V> {
        private LazyConcurrentHashMapOfImmutables<Tuple<ObjectsHierarchy, Class<V>>, V> complexMap;
        private ComplexMap() { complexMap = new LazyConcurrentHashMapOfImmutables<Tuple<ObjectsHierarchy, Class<V>>, V>(); }
    }

    private static final ComplexMap<Logger> LOGS_BY_OH = new ComplexMap<Logger>();

    public static Logger get(ObjectsHierarchy oh) {
        return LOGS_BY_OH.complexMap.get(new Tuple(oh, Logger.class));
    }

    public static void strictize(ObjectsHierarchy oh) {
        LOGS_BY_OH.complexMap.strictize(new Tuple(oh, Logger.class));
    }
    
    public static Either<Logger, Lambda0A<Logger>> putIfAbsent(ObjectsHierarchy oh) {
        Tuple<ObjectsHierarchy, Class<Logger>> tu = new Tuple<ObjectsHierarchy, Class<Logger>>(oh, Logger.class);
        Lambda1A<String, Logger> val_la = StdLambdas.LA_loggerByStr;
        return LOGS_BY_OH.complexMap.putIfAbsent(tu, val_la.reduceTo0A(oh.getSig()));
    }

}
