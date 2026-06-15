package javacore.exceptions.exercises.solutions;

/**
 * Reference solutions for ExceptionsExercises.
 */
public class ExceptionsSolutions {

    // Exercise 1 — Custom domain exception with cause preservation.
    public static class OrderProcessingException extends RuntimeException {
        private final String orderId;

        public OrderProcessingException(String message) {
            this(null, message, null);
        }

        public OrderProcessingException(String message, Throwable cause) {
            this(null, message, cause);
        }

        public OrderProcessingException(String orderId, String message) {
            this(orderId, message, null);
        }

        public OrderProcessingException(String orderId, String message, Throwable cause) {
            super(orderId == null ? message : message + " (orderId=" + orderId + ")", cause);
            this.orderId = orderId;
        }

        public String orderId() { return orderId; }
    }

    // Exercise 2 — wrap the low-level IOException into the domain exception.
    static String loadConfig(String path) {
        try {
            return readFileUnsafe(path);
        } catch (java.io.IOException e) {
            throw new OrderProcessingException(path, "failed to load config", e);
        }
    }

    static String readFileUnsafe(String path) throws java.io.IOException {
        if (path == null || path.isEmpty()) throw new java.io.IOException("missing path");
        return "{\"path\":\"" + path + "\"}";
    }

    // Exercise 3 — AutoCloseable resource that releases in close().
    public static class LeakyResource implements AutoCloseable {
        public String foo() { return "did work"; }
        public void release() { System.out.println("released"); }
        @Override public void close() { release(); }
    }

    // Exercise 4 — restore the interrupt flag and bail out early.
    static String pollWithBackoff() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();          // restore the flag
                return "interrupted";                         // and bail
            }
        }
        return "done";
    }

    public static void main(String[] args) {
        // Exercise 1
        try {
            throw new OrderProcessingException("ORD-1", "broke", new RuntimeException("root"));
        } catch (OrderProcessingException e) {
            System.out.println("orderId = " + e.orderId() + ", msg = " + e.getMessage());
            System.out.println("cause = " + e.getCause());
        }

        // Exercise 2
        try {
            loadConfig("");
        } catch (OrderProcessingException e) {
            System.out.println("caught: " + e.getMessage() + " -> cause: " + e.getCause());
        }

        // Exercise 3
        try (LeakyResource r = new LeakyResource()) {
            System.out.println(r.foo());
        }
        // prints "did work" then "released" via close()

        // Exercise 4
        System.out.println("pollWithBackoff -> " + pollWithBackoff());
    }
}
