package javacore.basics.exercises.solutions;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Reference solutions for BasicsExercises.
 */
public class BasicsSolutions {

    // Exercise 1 — case-insensitive currency in equals/hashCode.
    public static final class Money {
        final long minorUnits;
        final String currency;

        public Money(long minorUnits, String currency) {
            this.minorUnits = minorUnits;
            this.currency = currency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Money other)) return false;
            return minorUnits == other.minorUnits
                && currency.equalsIgnoreCase(other.currency);
        }

        @Override
        public int hashCode() {
            // Must agree with equals — use the same canonical form (upper-case).
            return Objects.hash(minorUnits, currency.toUpperCase());
        }

        @Override
        public String toString() { return minorUnits + " " + currency.toUpperCase(); }
    }

    // Exercise 2 — invariants behind methods.
    public static class Inventory {
        private final int total;
        private int reserved;

        public Inventory(int total) {
            if (total < 0) throw new IllegalArgumentException("negative total");
            this.total = total;
        }

        public void reserve(int n) {
            if (n <= 0) throw new IllegalArgumentException("non-positive reserve");
            if (reserved + n > total) throw new IllegalStateException("insufficient stock");
            reserved += n;
        }

        public int total() { return total; }
        public int reserved() { return reserved; }
        public int available() { return total - reserved; }
    }

    // Exercise 3 — original code inherited equals from Object, which compares
    // by reference identity. Two distinct Coordinate instances would never
    // be equal even with the same x, y. HashSet/HashMap would also bucket them
    // differently because Object.hashCode is identity-based.
    public static final class Coordinate {
        final int x, y;

        public Coordinate(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinate other)) return false;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }
    }

    // Exercise 4 — composition (wrap, don't extend).
    public static class TimedSet<E> {
        private final Set<E> delegate;
        private long totalAddNanos;

        public TimedSet(Set<E> delegate) { this.delegate = delegate; }

        public boolean add(E e) {
            long t0 = System.nanoTime();
            try {
                return delegate.add(e);
            } finally {
                totalAddNanos += System.nanoTime() - t0;
            }
        }

        public long totalAddNanos() { return totalAddNanos; }
        public int size() { return delegate.size(); }
    }

    public static void main(String[] args) {
        // Exercise 1
        Set<Money> wallet = new HashSet<>();
        wallet.add(new Money(100, "GBP"));
        System.out.println("Money equals (case-insens) = " + wallet.contains(new Money(100, "gbp"))); // true

        // Exercise 2
        Inventory inv = new Inventory(10);
        inv.reserve(3);
        System.out.println("inv.available = " + inv.available());    // 7
        try {
            inv.reserve(8);
        } catch (IllegalStateException e) {
            System.out.println("reserve(8) correctly threw: " + e.getMessage());
        }

        // Exercise 3
        System.out.println("Coordinate equals = " + new Coordinate(1, 2).equals(new Coordinate(1, 2))); // true

        // Exercise 4
        TimedSet<String> ts = new TimedSet<>(new HashSet<>());
        ts.add("a"); ts.add("b"); ts.add("c");
        System.out.println("TimedSet size = " + ts.size() + ", totalAddNanos > 0 = " + (ts.totalAddNanos() > 0));
    }
}
