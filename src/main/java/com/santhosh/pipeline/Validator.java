package com.santhosh.pipeline;

public interface Validator<T> {
    void validate(T input) throws Exception;
}