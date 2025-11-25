package com.gym.gymapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Utility class for unified logging style across the application.
 */
public final class LoggingUtils {

    private LoggingUtils() {
    }

    public static void info(Class<?> source, String message) {
        getLogger(source).info(format(message));
    }

    public static void warn(Class<?> source, String message) {
        getLogger(source).warn(format(message));
    }

    public static void error(Class<?> source, String message, Throwable t) {
        getLogger(source).error(format(message), t);
    }

    public static void debug(Class<?> source, String message) {
        getLogger(source).debug(format(message));
    }

    private static Logger getLogger(Class<?> source) {
        return LoggerFactory.getLogger(source);
    }

    private static String format(String message) {
        String txId = MDC.get("transactionId");
        if (txId == null) {
            return "[GYM-APP] " + message;
        }
        return "[GYM-APP][" + txId + "] " + message;
    }
}
