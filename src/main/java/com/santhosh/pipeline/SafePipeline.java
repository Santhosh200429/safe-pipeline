package com.santhosh.pipeline;

import java.util.ArrayList;
import java.util.List;

public class SafePipeline<T> {

    private final List<Step<T>> steps = new ArrayList<>();
    private final List<Validator<T>> validators = new ArrayList<>();

    public SafePipeline<T> addStep(Step<T> step) {
        steps.add(step);
        return this;
    }

    public SafePipeline<T> addValidator(Validator<T> validator) {
        validators.add(validator);
        return this;
    }

    public T execute(T input) {

        // Validation phase
        for (Validator<T> validator : validators) {
            try {
                validator.validate(input);
            } catch (Exception e) {
                throw new RuntimeException("Validation failed: " + e.getMessage(), e);
            }
        }

        // Execution phase
        T result = input;
        for (Step<T> step : steps) {
            result = SafeExecutor.execute(step, result);
        }

        return result;
    }
}