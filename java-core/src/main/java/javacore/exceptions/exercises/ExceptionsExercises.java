package javacore.exceptions.exercises;

/**
 * Exceptions — exercises.
 */
public class ExceptionsExercises {

    /**
     * Exercise 1 — Design an OrderProcessingException.
     * - Inherits RuntimeException.
     * - Constructors: (String message), (String message, Throwable cause),
     *   (String orderId, String message), (String orderId, String message, Throwable cause).
     * - Stores the orderId; expose it via orderId().
     * - Message format: "{message} (orderId={orderId})" when an orderId is provided.
     */
    public static class OrderProcessingException extends RuntimeException {
        // TODO
    }

    /**
     * Exercise 2 — Wrap a checked exception into a domain one.
     * Implement loadConfig(String path) that calls readFileUnsafe(path) (which
     * declares "throws IOException"), and rethrows as OrderProcessingException
     * with the file path as the orderId and "failed to load config" as the message.
     */
    static String loadConfig(String path) {
        // TODO — try/catch, rethrow as the domain exception preserving cause
        return "";
    }

    static String readFileUnsafe(String path) throws java.io.IOException {
        if (path == null || path.isEmpty()) throw new java.io.IOException("missing path");
        return "{\"path\":\"" + path + "\"}";
    }

    /**
     * Exercise 3 — Make this an AutoCloseable resource.
     * Currently the resource leaks if foo() throws. Fix it so it can be used
     * inside try-with-resources. The release() method should be called whether
     * foo() succeeds or fails.
     */
    public static class LeakyResource {
        // TODO — implement AutoCloseable so try-with-resources works
        public String foo() { return "did work"; }
        public void release() { System.out.println("released"); }
    }

    /**
     * Exercise 4 — Restore the interrupted flag.
     * pollWithBackoff() sleeps and retries. Currently it swallows
     * InterruptedException so the caller never knows the thread was interrupted.
     * Fix it.
     */
    static String pollWithBackoff() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO — restore the flag before continuing or return
            }
        }
        return "done";
    }

    public static void main(String[] args) {
        // Exercise 1
        // try { throw new OrderProcessingException("ORD-1", "broke", new RuntimeException("root")); }
        // catch (OrderProcessingException e) { ... }

        // Exercise 2
        // try { loadConfig(""); } catch (OrderProcessingException e) { ... }

        // Exercise 3
        // try (LeakyResource r = new LeakyResource()) { r.foo(); }

        // Exercise 4
        pollWithBackoff();
    }
}
