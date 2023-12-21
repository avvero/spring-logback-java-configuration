import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import java.nio.charset.StandardCharsets

println "================================================"
println " You are using logback.groovy"
println "================================================"

appender("console", ConsoleAppender) {
    target = "System.out"
    encoder(PatternLayoutEncoder) {
        charset = StandardCharsets.UTF_8
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

logger("org.springframework", INFO, ["console"], false)
logger("pw.avvero", TRACE, ["console"], false)
root(WARN, ["console"])