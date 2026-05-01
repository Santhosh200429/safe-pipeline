# Safe Pipeline

A lightweight Java pipeline framework with validation and safe execution.

## Features
- Step-based processing
- Validation support
- Fail-fast execution
- Generic reusable design

## Example
SafePipeline<Integer> pipeline = new SafePipeline<>();

pipeline
    .addValidator(input -> {
        if (input < 0) throw new Exception("Invalid");
    })
    .addStep(input -> input + 10)
    .addStep(input -> input * 2);

int result = pipeline.execute(5);"# safe-pipeline" 
