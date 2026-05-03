import com.santhosh.pipeline.SafePipeline;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SafePipeline Feature Demonstration ===\n");

        // FEATURE 1 & 5: Pipeline Architecture & Fluent Builder Pattern
        // We create a generic pipeline and chain our validators and steps fluently.
        SafePipeline<String> processPipeline = new SafePipeline<>();

        processPipeline
            // FEATURE 3: Pre-Execution Validation (Validator)
            // These run FIRST to ensure data integrity before any processing starts.
            .addValidator(input -> {
                System.out.println("[Validator] Checking for null...");
                if (input == null) throw new IllegalArgumentException("Input cannot be null");
            })
            .addValidator(input -> {
                System.out.println("[Validator] Checking for empty string...");
                if (input.trim().isEmpty()) throw new IllegalArgumentException("Input cannot be empty");
            })
            
            // FEATURE 2: Data Processing Steps (Step)
            // Data flows sequentially through these functional interfaces.
            .addStep(input -> {
                System.out.println("[Step 1] Trimming whitespace...");
                return input.trim();
            })
            .addStep(input -> {
                System.out.println("[Step 2] Formatting text...");
                return "Processed: " + input.toUpperCase();
            })
            .addStep(input -> {
                System.out.println("[Step 3] Finalizing...");
                // FEATURE 4: Safe Step Execution (SafeExecutor)
                // If a step crashes, SafeExecutor handles the exception gracefully.
                if (input.contains("CRASH")) {
                    throw new RuntimeException("Unexpected processing error occurred!");
                }
                return input + " (Done)";
            });

        // SCENARIO A: Valid Input
     
        System.out.println("--- Scenario A: Valid Input ---");
        try {
            String result = processPipeline.execute("   safe pipeline   ");
            System.out.println("SUCCESS: " + result);
        } catch (Exception e) {
            System.err.println("FAILED: " + e.getMessage());
        }

        // SCENARIO B: Validation Failure
        
        System.out.println("\n--- Scenario B: Validation Failure ---");
        try {
            processPipeline.execute("   "); // Triggers the empty string validator
        } catch (Exception e) {
            System.err.println("CAUGHT VALIDATION ERROR: " + e.getMessage());
        }

        
        // SCENARIO C: Step Execution Failure
        
        System.out.println("\n--- Scenario C: Step Execution Failure ---");
        try {
            processPipeline.execute("Trigger Crash"); // Triggers the runtime exception in Step 3
        } catch (Exception e) {
            System.err.println("CAUGHT SAFE-EXECUTOR ERROR: " + e.getMessage());
        }
    }
}