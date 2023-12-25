package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.SubstituteLoggerFactory;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.logging.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.security.CodeSource;
import java.security.ProtectionDomain;

public class MyLogbackLoggingSystem extends AbstractLoggingSystem implements BeanFactoryInitializationAotProcessor {

    public MyLogbackLoggingSystem(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected String[] getStandardConfigLocations() {
        return new String[0];
    }

    @Override
    protected void loadDefaults(LoggingInitializationContext initializationContext, LogFile logFile) {

    }

    @Override
    protected void loadConfiguration(LoggingInitializationContext initializationContext, String location, LogFile logFile) {

    }

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {
        return null;
    }
}
