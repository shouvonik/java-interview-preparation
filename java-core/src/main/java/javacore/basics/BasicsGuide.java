package javacore.basics;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Basics — OOP pillars, Object contracts, common keywords.
 *
 * Run: java javacore.basics.BasicsGuide
 *
 * Interviewers expect short, correct answers on this material — they're
 * checking you can frame the fundamentals, not just recite them.
 */
public class BasicsGuide {

    // ---------------------------------------------------------------------
    // 1) equals / hashCode contract
    // ---------------------------------------------------------------------
    // Override BOTH. Equal objects must have equal hashes.
    static final class UserId {
        final String namespace;
        final String value;

        UserId(String namespace, String value) {
            this.namespace = namespace;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserId other)) return false;
            return Objects.equals(namespace, other.namespace)
                && Objects.equals(value, other.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(namespace, value);
        }

        @Override
        public String toString() {
            return namespace + ":" + value;
        }
    }

    // ---------------------------------------------------------------------
    // 2) Encapsulation — invariants enforced behind methods
    // ---------------------------------------------------------------------
    // Caller cannot put the account into an invalid state.
    static final class Account {
        private long balance;

        Account(long openingBalance) {
            if (openingBalance < 0) throw new IllegalArgumentException("negative opening balance");
            this.balance = openingBalance;
        }

        void deposit(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("non-positive deposit");
            balance += amount;
        }

        void withdraw(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("non-positive withdraw");
            if (amount > balance) throw new IllegalStateException("insufficient funds");
            balance -= amount;
        }

        long balance() { return balance; }
    }

    // ---------------------------------------------------------------------
    // 3) Polymorphism — runtime type decides which method runs
    // ---------------------------------------------------------------------
    interface Shape {
        double area();
    }

    static final class Circle implements Shape {
        private final double radius;
        Circle(double radius) { this.radius = radius; }
        @Override public double area() { return Math.PI * radius * radius; }
    }

    static final class Square implements Shape {
        private final double side;
        Square(double side) { this.side = side; }
        @Override public double area() { return side * side; }
    }

    static double totalArea(Iterable<Shape> shapes) {
        double total = 0;
        for (Shape s : shapes) total += s.area();    // virtual dispatch
        return total;
    }

    // ---------------------------------------------------------------------
    // 4) Composition over inheritance — forwarding wrapper
    // ---------------------------------------------------------------------
    // Counts elements added without subclassing HashSet (which would couple
    // us to its internal calls — addAll() calls add(), so naive override
    // would double-count).
    static class CountingSet<E> implements Set<E> {
        private final Set<E> delegate;
        private int addsCount = 0;

        CountingSet(Set<E> delegate) { this.delegate = delegate; }

        @Override
        public boolean add(E e) {
            addsCount++;
            return delegate.add(e);
        }

        @Override
        public boolean addAll(java.util.Collection<? extends E> c) {
            // by going through delegate.addAll directly, we DON'T re-route via our add()
            // — but for the demo we'll loop to count each one explicitly.
            boolean modified = false;
            for (E e : c) modified |= add(e);
            return modified;
        }

        int addsCount() { return addsCount; }

        // --- everything else just forwards ---
        @Override public int size() { return delegate.size(); }
        @Override public boolean isEmpty() { return delegate.isEmpty(); }
        @Override public boolean contains(Object o) { return delegate.contains(o); }
        @Override public java.util.Iterator<E> iterator() { return delegate.iterator(); }
        @Override public Object[] toArray() { return delegate.toArray(); }
        @Override public <T> T[] toArray(T[] a) { return delegate.toArray(a); }
        @Override public boolean remove(Object o) { return delegate.remove(o); }
        @Override public boolean containsAll(java.util.Collection<?> c) { return delegate.containsAll(c); }
        @Override public boolean retainAll(java.util.Collection<?> c) { return delegate.retainAll(c); }
        @Override public boolean removeAll(java.util.Collection<?> c) { return delegate.removeAll(c); }
        @Override public void clear() { delegate.clear(); }
    }

    // ---------------------------------------------------------------------
    // 5) final / static / this / super — quick demos
    // ---------------------------------------------------------------------
    // final variable: cannot reassign (but the OBJECT it points to can mutate).
    // final method: cannot override.
    // final class: cannot extend.
    // static: belongs to the class, no `this`.
    static class Counter {
        static int instanceCount = 0;             // shared
        final int id;                              // assigned exactly once

        Counter() {
            this.id = ++instanceCount;             // `this` resolves the field even when shadowed
        }
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // equals/hashCode
        UserId a = new UserId("auth", "u-123");
        UserId b = new UserId("auth", "u-123");
        UserId c = new UserId("auth", "u-999");
        System.out.println("a.equals(b)         = " + a.equals(b));            // true
        System.out.println("a.hashCode==b.hash  = " + (a.hashCode() == b.hashCode())); // true
        System.out.println("a.equals(c)         = " + a.equals(c));            // false

        Set<UserId> users = new HashSet<>();
        users.add(a);
        System.out.println("set.contains(b)     = " + users.contains(b));      // true — equals + hashCode in action

        // Encapsulation
        Account account = new Account(100);
        account.deposit(50);
        account.withdraw(30);
        System.out.println("account.balance     = " + account.balance());     // 120

        // Polymorphism
        System.out.println("totalArea([circle r=1, square s=2]) = "
            + totalArea(java.util.List.of(new Circle(1), new Square(2))));     // ~7.14

        // Composition over inheritance
        CountingSet<String> cs = new CountingSet<>(new HashSet<>());
        cs.add("one"); cs.add("two"); cs.addAll(java.util.List.of("three", "four"));
        System.out.println("CountingSet adds    = " + cs.addsCount());        // 4

        // final / static / this
        Counter x = new Counter();
        Counter y = new Counter();
        System.out.println("Counter ids         = " + x.id + ", " + y.id);    // 1, 2
        System.out.println("instanceCount       = " + Counter.instanceCount); // 2
    }
}
