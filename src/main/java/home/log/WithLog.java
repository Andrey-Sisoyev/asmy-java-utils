package home.log;

import home.lang.Either;
import home.lang.functional.Lambda0A;
import home.utils.reflection.ObjectsHierarchy;
import org.slf4j.Logger;

public class WithLog {
    private Either<Logger, Lambda0A<Logger>> lazyLogger;

    private void initLogger(ObjectsHierarchy oh) {
        lazyLogger = LoggersByOH.putIfAbsent(oh);
    }

    public WithLog(ObjectsHierarchy oh) {
        initLogger(oh);
    }

    public WithLog() {
        initLogger(new ObjectsHierarchy(this.getClass()));
    }
    
    protected Logger getLogger() {
        if(lazyLogger.isLeft()) return lazyLogger.getLeft();
        else {
            Logger l = lazyLogger.getRight().reduceLambda();
            lazyLogger.setLeft(l);
            return l;
        }
    }

    protected void errorP(RuntimeException t) {
        this.getLogger().error(t.getMessage(), t);
        throw t;
    }

    protected void errorPt(Throwable t) throws Throwable {
        this.getLogger().error(t.getMessage(), t);
        throw t;
    }
}
