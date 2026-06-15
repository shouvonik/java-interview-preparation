package javacore.basics.exercises;

import java.util.HashSet;
import java.util.Set;

/**
 * Basics — exercises.
 */
public class BasicsExercises {

    /**
     * Exercise 1 — Implement equals and hashCode for this Money type.
     * Two Money instances are equal iff their amounts AND currencies match.
     * Currency comparison must be case-INSENSITIVE.
     * Verify with the HashSet test in main().
     */
    public static final class Money {
        final long minorUnits;
        final String currency;
        public Money(long minorUnits, String currency) {
            this.minorUnits = minorUnits;
            this.currency = currency;
        }

        // TODO — override equals and hashCode following the contract.
    }

    /**
     * Exercise 2 — Encapsulate the inventory.
     * Currently both fields are public — anyone can break invariants.
     * Make total/reserved private, expose a `reserve(int n)` method that
     * fails if n &le; 0 or would push reserved past total.
     */
    public static class Inventory {
        public int total;         // TODO — make private
        public int reserved;      // TODO — make private

        public Inventory(int total) {
            if (total < 0) throw new IllegalArgumentException();
            this.total = total;
        }

        // TODO — reserve(int n) and accessors
    }

    /**
     * Exercise 3 — Why does this print false?
     * Both objects look equal, but the test fails. Fix the bug by completing
     * the Coordinate type's equals/hashCode. Then explain in a comment WHY the
     * original code was broken.
     */
    public static class Coordinate {
        int x, y;
        public Coordinate(int x, int y) { this.x = x; this.y = y; }
        // TODO — add equals and hashCode
    }

    /**
     * Exercise 4 — Composition over inheritance.
     * Make a wrapper that times every add to a Set without subclassing HashSet.
     * Return the running total of nanoseconds spent in add().
     */
    public static class TimedSet<E> {
        // TODO — wrap a Set<E>, expose add(E) and totalAddNanos()
    }

    public static void main(String[] args) {
        // Test exercise 1
        Set<Money> wallet = new HashSet<>();
        wallet.add(new Money(100, "GBP"));
        System.out.println("Money equals test (expect true): "
            + wallet.contains(new Money(100, "gbp")));

        // Test exercise 2
        // Inventory inv = new Inventory(10);
        // inv.reserve(3);  // should succeed
        // inv.reserve(8);  // should throw — only 7 left

        // Test exercise 3
        System.out.println("Coordinate equals test: "
            + new Coordinate(1, 2).equals(new Coordinate(1, 2)));
    }
}
