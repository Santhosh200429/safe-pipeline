# Safe Pipeline

A lightweight Java pipeline framework with validation and safe execution.

## Features
- Step-based processing
- Validation support
- Fail-fast execution
- Generic reusable design

## Usage

### Maven Dependency

Add to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Santhosh200429</groupId>
    <artifactId>safe-pipeline</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Example

```java
import com.santhosh.pipeline.SafePipeline;
import com.santhosh.pipeline.Step;
import com.santhosh.pipeline.Validator;

public class Example {
    public static void main(String[] args) {
        SafePipeline<Integer> pipeline = new SafePipeline<>();

        pipeline
            .addValidator(input -> {
                if (input < 0) throw new Exception("Input must be non-negative");
            })
            .addStep(input -> input + 10)
            .addStep(input -> input * 2);

        try {
            int result = pipeline.execute(5); // Result: 30
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Pipeline failed: " + e.getMessage());
        }
    }
}
```

### API

#### SafePipeline<T>
- `addStep(Step<T> step)`: Add a processing step
- `addValidator(Validator<T> validator)`: Add input validation
- `execute(T input)`: Run the pipeline

#### Step<T>
Functional interface for processing steps:
```java
@FunctionalInterface
public interface Step<T> {
    T process(T input) throws Exception;
}
```

#### Validator<T>
Functional interface for validation:
```java
@FunctionalInterface
public interface Validator<T> {
    void validate(T input) throws Exception;
}
```

## Requirements
- Java 21+
- Maven 3.6+

## License
Apache License 2.0"# safe-pipeline" 
