package com.santhosh.pipeline;

@FunctionalInterface
public interface Step<T> {
    T process(T input) throws Exception;
}