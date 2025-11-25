package com.gym.workload.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TransactionFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TransactionFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String txId = request.getHeader("X-Transaction-Id");
        if (txId == null || txId.trim().isEmpty()) {
            txId = UUID.randomUUID().toString();
        }

        MDC.put("transactionId", txId);
        response.setHeader("X-Transaction-Id", txId);

        long start = System.currentTimeMillis();
        try {
            log.info("txId={} START {} {}", txId, request.getMethod(), request.getRequestURI());
            filterChain.doFilter(request, response);
            long duration = System.currentTimeMillis() - start;
            log.info("txId={} END {} {} -> HTTP {} ({} ms)",
                    txId, request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
        } finally {
            MDC.remove("transactionId");
        }
    }
}
