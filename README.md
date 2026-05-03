# SafePipeline

**SafePipeline** is a generic framework that allows you to construct and execute a sequence of processing steps on data of a specific type. It features pre-execution validation, sequential data processing, and built-in safe execution wrappers.

## Features & Syntax

### 1. Installation (Maven / JitPack)
To add SafePipeline to your project, add the JitPack repository and the dependency to your `pom.xml`.

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.Santhosh200429</groupId>
        <artifactId>safe-pipeline</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### 2. Pipeline Architecture (SafePipeline)
Initialize a pipeline by specifying the type of data it will process using Java Generics.

```java
import com.santhosh.pipeline.SafePipeline;

// Creating a pipeline that processes String data
SafePipeline<String> pipeline = new SafePipeline<>();

// Creating a pipeline that processes Integer data
SafePipeline<Integer> intPipeline = new SafePipeline<>();
```

### 3. Fluent Builder Pattern
The library uses a fluent builder pattern, meaning you can easily chain `.addValidator()` and `.addStep()` methods together without having to reference the pipeline variable repeatedly.

```java
pipeline
    .addValidator(...)
    .addStep(...)
    .addStep(...);
```

### 4. Pre-Execution Validation (Validator)
You can add one or more validators to ensure the initial input is sound before any processing begins. The pipeline has a dedicated "Validation phase" that runs first. If validation fails, an exception is thrown immediately.

```java
pipeline.addValidator(input -> {
    if (input == null || input.trim().isEmpty()) {
        throw new IllegalArgumentException("Input cannot be empty");
    }
});
```

### 5. Data Processing Steps (Step)
Steps are functional interfaces representing single, transformational operations. The output of one step is automatically passed as the input to the next step.

```java
pipeline
    .addStep(input -> input.trim())                 // Step 1: Trim spaces
    .addStep(input -> input.toUpperCase())          // Step 2: Make uppercase
    .addStep(input -> "Result: " + input);          // Step 3: Append prefix
```

### 6. Pipeline Execution & Safe Step Execution
The `SafeExecutor` securely runs each step. If any step throws an unexpected exception during execution, it catches it and wraps it safely, ensuring predictable failure behavior.

```java
try {
    // Pass the initial input into the pipeline
    String result = pipeline.execute("   my raw data   ");
    
    System.out.println(result); 
} catch (Exception e) {
    // Handles both Validation errors and Step Execution errors safely
    System.err.println("Pipeline failed: " + e.getMessage());
}
```

---

### Complete Example
Here is a complete, working example using the library:

```java
import com.santhosh.pipeline.SafePipeline;

public class Example {
    public static void main(String[] args) {
        SafePipeline<Integer> mathPipeline = new SafePipeline<>();

        mathPipeline
            .addValidator(val -> {
                if (val < 0) throw new IllegalArgumentException("Must be non-negative");
            })
            .addStep(val -> val + 10)
            .addStep(val -> val * 2);

        try {
            int result = mathPipeline.execute(5);
            System.out.println("Final Result: " + result); // Prints 30
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
