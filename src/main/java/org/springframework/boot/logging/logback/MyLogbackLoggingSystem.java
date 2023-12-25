package org.springframework.boot.logging.logback;

import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;

public class MyLogbackLoggingSystem extends LogbackLoggingSystem implements BeanFactoryInitializationAotProcessor {

    public MyLogbackLoggingSystem(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void loadDefaults(LoggingInitializationContext initializationContext, LogFile logFile) {
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    public static class Factory implements LoggingSystemFactory {
        private static final boolean PRESENT = ClassUtils.isPresent("ch.qos.logback.classic.LoggerContext",
                LogbackLoggingSystem.Factory.class.getClassLoader());
        @Override
        public LoggingSystem getLoggingSystem(ClassLoader classLoader) {
            if (PRESENT) {
                return new MyLogbackLoggingSystem(classLoader);
            }
            return null;
        }
    }
}
