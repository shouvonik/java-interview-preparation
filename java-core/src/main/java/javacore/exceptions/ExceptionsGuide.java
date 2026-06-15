package javacore.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Exceptions — checked vs unchecked, resources, custom types, best practices.
 *
 * Run: java javacore.exceptions.ExceptionsGuide
 *
 * The interviewer wants to see:
 *   - You know the hierarchy (Throwable -> Error / Exception -> RuntimeException).
 *   - You can argue when an exception should be checked vs unchecked.
 *   - You understand try-with-resources and suppressed exceptions.
 *   - You can design a custom exception that preserves the cause chain.
 */
public class ExceptionsGuide {

    // ---------------------------------------------------------------------
    // 1) Custom exception that preserves the cause
    // ---------------------------------------------------------------------
    // Three things a well-formed exception class should do:
    //   (a) extend RuntimeException for application-level errors unless the
    //       caller genuinely has a recovery path (very rare in modern code).
    //   (b) expose a constructor that takes a Throwable cause, so wrapping
    //       another exception preserves the original stack trace.
    //   (c) include enough context in the message to be useful — but don't
    //       embed mutable state that might change before the log is written.
    public static class UserLookupException extends RuntimeException {
        private final String userId;

        public UserLookupException(String userId, String message) {
            super(message + " (userId=" + userId + ")");
            this.userId = userId;
        }

        public UserLookupException(String userId, String message, Throwable cause) {
            super(message + " (userId=" + userId + ")", cause);
            this.userId = userId;
        }

        public String userId() { return userId; }
    }

    // ---------------------------------------------------------------------
    // 2) Wrapping a checked exception into a domain exception
    // ---------------------------------------------------------------------
    // A common service pattern: lower-level code throws an SQLException /
    // IOException, but callers shouldn't care HOW the lookup failed — they
    // care that it DID. Catch the low-level type, throw your domain exception
    // chained to it. The cause is preserved for logging; the API contract
    // stays stable even if you swap the persistence layer.
    static String lookupUser(String id) {
        try {
            return doLookup(id);
        } catch (IOException e) {
            throw new UserLookupException(id, "failed to load user", e);
        }
    }

    static String doLookup(String id) throws IOException {
        if ("missing".equals(id)) throw new IOException("not found in store");
        return "User-" + id;
    }

    // ---------------------------------------------------------------------
    // 3) try-with-resources and suppressed exceptions
    // ---------------------------------------------------------------------
    // The trick is what happens when BOTH the body AND close() throw. The
    // body's exception is the "primary"; the close() exception is added to
    // it as a SUPPRESSED exception (Throwable.getSuppressed()). This way
    // you don't lose either one, and the primary failure (usually the more
    // diagnostic) is the one you catch.
    static void readAll() {
        // Try to read a line; close() also throws. The primary is the read
        // failure, with the close failure as a suppressed.
        try (var reader = new ThrowsOnClose()) {
            reader.read();
            throw new RuntimeException("read failed");      // primary
        } catch (RuntimeException e) {
            System.out.println("Caught primary: " + e.getMessage());
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("  suppressed: " + suppressed.getMessage());
            }
        }
    }

    static class ThrowsOnClose implements AutoCloseable {
        String read() { return "data"; }
        @Override public void close() { throw new RuntimeException("close failed"); }
    }

    // ---------------------------------------------------------------------
    // 4) Multi-catch — group exceptions sharing a handler
    // ---------------------------------------------------------------------
    // Pre-Java 7 you had to duplicate the handler for each type. Multi-catch
    // collapses them with `|`. The caught variable is implicitly final and
    // typed as the COMMON SUPERTYPE of the listed exceptions.
    static void parseAndOpen(String s) {
        try {
            int n = Integer.parseInt(s);
            if (n < 0) throw new IllegalArgumentException("negative");
            System.out.println("parsed: " + n);
        } catch (NumberFormatException | IllegalArgumentException e) {
            // Either branch lands here. `e` is typed as the common supertype:
            // RuntimeException (or NumberFormatException's nearer ancestor).
            System.out.println("bad input: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------------
    // 5) Restoring the interrupted flag
    // ---------------------------------------------------------------------
    // When you catch InterruptedException, the JVM has CLEARED the thread's
    // interrupted flag. If you don't re-raise it, code higher up the stack
    // (especially library code) won't know the thread was interrupted and
    // won't shut down promptly.
    //
    // The rule: either rethrow the InterruptedException, or call
    // Thread.currentThread().interrupt() to restore the flag.
    static void waitOnSomething() {
        try {
            Thread.sleep(1);                    // could throw InterruptedException
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // restore the flag
            System.out.println("interrupted; flag restored");
        }
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // 1, 2) Custom exception wrapping the cause
        try {
            lookupUser("missing");
        } catch (UserLookupException e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("Cause:  " + e.getCause());
        }

        // 3) Suppressed exception via try-with-resources
        readAll();

        // 4) Multi-catch
        parseAndOpen("abc");
        parseAndOpen("-5");
        parseAndOpen("42");

        // 5) InterruptedException flag handling
        waitOnSomething();

        // Sanity check: real try-with-resources on a reader
        try (BufferedReader r = new BufferedReader(new StringReader("hi\nthere"))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println("read: " + line);
            }
        } catch (IOException e) {
            System.out.println("io: " + e);
        }
    }
}
