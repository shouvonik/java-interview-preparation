package javacore.generics.exercises.solutions;

import java.util.*;

/**
 * Reference solutions for GenericsExercises.
 */
public class GenericsSolutions {

    // Exercise 1 — straightforward two-type-parameter class.
    public static final class Pair<A, B> {
        public final A first;
        public final B second;
        public Pair(A first, B second) { this.first = first; this.second = second; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair<?, ?> p)) return false;
            return Objects.equals(first, p.first) && Objects.equals(second, p.second);
        }

        @Override public int hashCode() { return Objects.hash(first, second); }
        @Override public String toString() { return "(" + first + ", " + second + ")"; }
    }

    // Exercise 2 — note the PECS wildcards:
    //   list is a PRODUCER of T -> `? extends T`
    //   comparator CONSUMES T values -> `? super T`
    // These let callers pass List<Integer> with Comparator<Number> etc.
    static <T> T max(List<? extends T> list, Comparator<? super T> comparator) {
        T best = list.get(0);
        for (T candidate : list) {
            if (comparator.compare(candidate, best) > 0) best = candidate;
        }
        return best;
    }

    // Exercise 3 — full PECS treatment.
    //   src produces S -> `? extends S`
    //   dest consumes D -> `? super D`
    //   mapper takes any-supertype-of-S, returns any-subtype-of-D
    public interface Mapper<S, D> { D map(S source); }

    static <S, D> void transferMapped(
        Collection<? extends S> src,
        Collection<? super D> dest,
        Mapper<? super S, ? extends D> mapper) {
        for (S item : src) dest.add(mapper.map(item));
    }

    // Exercise 4 — typesafe heterogeneous container (Effective Java item 33).
    // The map key is the Class<T> token; we cast on read using `type.cast(...)`
    // which throws ClassCastException at the actual offending call rather than
    // somewhere unrelated.
    public static class Favorites {
        private final Map<Class<?>, Object> store = new HashMap<>();

        public <T> void put(Class<T> type, T instance) {
            store.put(Objects.requireNonNull(type), instance);
        }

        public <T> T get(Class<T> type) {
            return type.cast(store.get(type));
        }
    }

    public static void main(String[] args) {
        // Exercise 1
        Pair<String, Integer> p = new Pair<>("alice", 42);
        System.out.println("pair = " + p + ", equals copy? "
            + p.equals(new Pair<>("alice", 42)));

        // Exercise 2
        System.out.println("max by length = " + max(
            List.of("foo", "applesauce", "bar"),
            Comparator.comparingInt(String::length)));   // applesauce

        // Exercise 3
        List<Integer> src = List.of(1, 2, 3);
        List<String> dest = new ArrayList<>();
        transferMapped(src, dest, Object::toString);
        System.out.println("transferMapped result = " + dest);

        // Exercise 4
        Favorites favs = new Favorites();
        favs.put(String.class, "hello");
        favs.put(Integer.class, 7);
        favs.put(Class.class, Favorites.class);
        System.out.println("favs[String] = " + favs.get(String.class));
        System.out.println("favs[Integer] = " + favs.get(Integer.class));
        System.out.println("favs[Class] = " + favs.get(Class.class).getSimpleName());
    }
}
