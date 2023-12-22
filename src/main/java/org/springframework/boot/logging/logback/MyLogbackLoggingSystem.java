package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.SubstituteLoggerFactory;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.security.CodeSource;
import java.security.ProtectionDomain;

public class MyLogbackLoggingSystem extends LogbackLoggingSystem implements BeanFactoryInitializationAotProcessor {

    public MyLogbackLoggingSystem(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void loadDefaults(LoggingInitializationContext initializationContext, LogFile logFile) {
        LoggerContext context = getLoggerContext();
        stopAndReset(context);
        new MyLogbackConfigurator().configure(context);
    }

    private void stopAndReset(LoggerContext loggerContext) {
        loggerContext.stop();
        loggerContext.reset();
    }

    private LoggerContext getLoggerContext() {
        ILoggerFactory factory = getLoggerFactory();
        Assert.isInstanceOf(LoggerContext.class, factory,
                () -> String.format(
                        "LoggerFactory is not a Logback LoggerContext but Logback is on "
                                + "the classpath. Either remove Logback or the competing "
                                + "implementation (%s loaded from %s). If you are using "
                                + "WebLogic you will need to add 'org.slf4j' to "
                                + "prefer-application-packages in WEB-INF/weblogic.xml",
                        factory.getClass(), getLocation(factory)));
        return (LoggerContext) factory;
    }

    private ILoggerFactory getLoggerFactory() {
        ILoggerFactory factory = LoggerFactory.getILoggerFactory();
        while (factory instanceof SubstituteLoggerFactory) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for non-subtitute logger factory", ex);
            }
            factory = LoggerFactory.getILoggerFactory();
        }
        return factory;
    }

    private Object getLocation(ILoggerFactory factory) {
        try {
            ProtectionDomain protectionDomain = factory.getClass().getProtectionDomain();
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource != null) {
                return codeSource.getLocation();
            }
        } catch (SecurityException ex) {
            // Unable to determine location
        }
        return "unknown location";
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
