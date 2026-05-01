package com.santhosh.pipeline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SafePipelineTest {

    @Test
    void testPipelineSuccess() {

        SafePipeline<Integer> pipeline = new SafePipeline<>();

        pipeline
                .addValidator(input -> {
                    if (input < 0) {
                        throw new Exception("Negative values not allowed");
                    }
                })
                .addStep(input -> input + 10)
                .addStep(input -> input * 2);

        int result = pipeline.execute(5);

        // (5 + 10) * 2 = 30
        assertEquals(30, result);
    }

    @Test
    void testValidationFailure() {

        SafePipeline<Integer> pipeline = new SafePipeline<>();

        pipeline.addValidator(input -> {
            if (input < 0) {
                throw new Exception("Invalid input");
            }
        });

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pipeline.execute(-1);
        });

        assertTrue(exception.getMessage().contains("Validation failed"));
    }

    @Test
    void testStepFailure() {

        SafePipeline<Integer> pipeline = new SafePipeline<>();

        pipeline
                .addStep(input -> {
                    if (input == 5) {
                        throw new Exception("Step error");
                    }
                    return input;
                });

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pipeline.execute(5);
        });

        assertTrue(exception.getMessage().contains("Step execution failed"));
    }

    @Test
    void testMultipleSteps() {

        SafePipeline<String> pipeline = new SafePipeline<>();

        pipeline
                .addStep(input -> input.trim())
                .addStep(input -> input.toUpperCase())
                .addStep(input -> input + " DONE");

        String result = pipeline.execute("  hello  ");

        assertEquals("HELLO DONE", result);
    }
}