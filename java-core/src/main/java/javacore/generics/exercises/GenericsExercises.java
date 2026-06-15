package javacore.generics.exercises;

import java.util.*;

/**
 * Generics — exercises.
 */
public class GenericsExercises {

    /**
     * Exercise 1 — Generic Pair.
     * Add type parameters so Pair stores any two types. Provide equals/hashCode.
     */
    public static class Pair {            // TODO — make generic in <A, B>
        Object first;
        Object second;
        public Pair(Object first, Object second) { this.first = first; this.second = second; }
    }

    /**
     * Exercise 2 — Bounded max with Comparator (not Comparable).
     * Implement max that uses an EXTERNAL Comparator rather than the natural
     * ordering. The signature must let callers pass any subtype of T's comparator.
     */
    static <T> T max(List<? extends T> list, Comparator<? super T> comparator) {
        // TODO
        return null;
    }

    /**
     * Exercise 3 — PECS in practice.
     * Move items from `src` to `dest`, transforming each via the given mapper.
     * Hint: src is a producer (? extends), dest is a consumer (? super),
     * mapper input is a consumer of the src element, mapper output is a producer
     * of the dest element. Wildcards everywhere.
     */
    interface Mapper<S, D> { D map(S source); }

    static <S, D> void transferMapped(
        // TODO — fill in the wildcard-laden signatures.
        Collection<S> src,
        Collection<D> dest,
        Mapper<S, D> mapper
    ) {
        // TODO
    }

    /**
     * Exercise 4 — Heterogeneous container.
     * Build a typesafe heterogeneous container backed by Map<Class<?>, Object>.
     * put(Class<T> key, T value) stores; get(Class<T> key) returns T (no cast at call site).
     */
    public static class Favorites {
        // TODO
        public <T> void put(Class<T> type, T instance) {}
        public <T> T get(Class<T> type) { return null; }
    }

    public static void main(String[] args) {
        // Exercise 1
        // Pair<String, Integer> p = new Pair<>("alice", 42);

        // Exercise 2
        System.out.println("max by length = " + max(
            List.of("foo", "applesauce", "bar"),
            Comparator.comparingInt(String::length)));   // applesauce

        // Exercise 3
        // List<Integer> src = List.of(1, 2, 3);
        // List<String> dest = new ArrayList<>();
        // transferMapped(src, dest, Object::toString);

        // Exercise 4
        // Favorites favs = new Favorites();
        // favs.put(String.class, "hello"); favs.put(Integer.class, 7);
        // System.out.println("str fav = " + favs.get(String.class));
    }
}
