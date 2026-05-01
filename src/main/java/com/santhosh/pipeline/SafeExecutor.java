package com.santhosh.pipeline;

public class SafeExecutor {

    public static <T> T execute(Step<T> step, T input) {
        try {
            return step.process(input);
        } catch (Exception e) {
            throw new RuntimeException("Step execution failed: " + e.getMessage(), e);
        }
    }
}