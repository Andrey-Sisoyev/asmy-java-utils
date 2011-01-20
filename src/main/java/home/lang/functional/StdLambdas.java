package home.lang.functional;

import home.patterns.access.IAccessRoleAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StdLambdas {
    public static final Lambda1A<String, Logger> LA_loggerByStr =
            new Lambda1A<String, Logger>() {
                     @Override public Logger reduceLambda(String a1) {
                         return LoggerFactory.getLogger(a1);
                     }
                };

    public static final Lambda1A<Class<?>, Logger> LA_loggerByCls =
            new Lambda1A<Class<?>, Logger>() {
                     @Override public Logger reduceLambda(Class<?> a1) {
                         return LoggerFactory.getLogger(a1);
                     }
                };
}
