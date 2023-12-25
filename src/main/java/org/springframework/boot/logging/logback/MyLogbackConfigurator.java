package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.spi.ContextAwareBase;

import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MyLogbackConfigurator extends ContextAwareBase implements Configurator {

    @Override
    public ExecutionStatus configure(LoggerContext context) {
        System.out.println("================================================");
        System.out.println("You are using org.springframework.boot.logging.logback.MyLogbackConfigurator");
        System.out.println("================================================");
        /*
         * Appenders
         */
        List<Appender> appenders = new ArrayList<>();
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setName("stdout");
        appender.setContext(context);
        //
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setCharset(UTF_8);
        encoder.setPattern("%d{mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();
        //
        appender.setEncoder(encoder);
        appender.start();
        appenders.add(appender);

        // let the caller decide
        return ExecutionStatus.NEUTRAL;
    }
}
