package javacore.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Generics — bounded types, wildcards (PECS), type erasure.
 *
 * Run: java javacore.generics.GenericsGuide
 *
 * The mental model: generics are a COMPILE-TIME type-safety mechanism. The
 * runtime knows nothing about your `<T>` — every parameterised type is erased
 * to its raw form (or its first bound) when bytecode is produced. Most of the
 * weird behaviour you'll be asked about comes from that erasure step.
 */
public class GenericsGuide {

    // ---------------------------------------------------------------------
    // 1) Generic methods — type inference
    // ---------------------------------------------------------------------
    // A generic method has its OWN type parameter list, declared before the
    // return type. The compiler picks T at the call site by looking at the
    // arguments. You rarely need to spell it out explicitly.
    //
    //   GenericsGuide.<String>singleton("hi");   // explicit, almost never needed
    //   singleton("hi");                          // inferred from the argument
    static <T> List<T> singleton(T value) {
        List<T> list = new ArrayList<>();
        list.add(value);
        return list;
    }

    // ---------------------------------------------------------------------
    // 2) Bounded type parameters — `<T extends Bound>`
    // ---------------------------------------------------------------------
    // `extends` here is used for BOTH classes and interfaces (no `implements`
    // keyword in generics). You can have multiple bounds with `&`, but only
    // ONE may be a class — and it must come first.
    //
    // The benefit: we get to call methods of the bound inside the body, even
    // though T is generic. Without the bound, T is just Object.
    static <T extends Comparable<T>> T max(List<T> list) {
        T best = list.get(0);
        for (T t : list) if (t.compareTo(best) > 0) best = t;
        return best;
    }

    // ---------------------------------------------------------------------
    // 3) PECS — Producer Extends, Consumer Super
    // ---------------------------------------------------------------------
    // Wildcards encode VARIANCE — how a type parameter behaves under
    // subtyping. By default, generic types in Java are INVARIANT:
    // List<Integer> is NOT a List<Number>. Wildcards relax that:
    //
    //   List<? extends Number>  - a list that PRODUCES Numbers (read-only-ish)
    //                              you can READ as Number; CANNOT add anything
    //                              (the compiler doesn't know the exact element type)
    //
    //   List<? super Integer>   - a list that CONSUMES Integers (write-only-ish)
    //                              you can ADD Integers; reading gives back Object
    //
    // Mnemonic from Effective Java: "PECS — Producer Extends, Consumer Super".
    //
    // The copy() method below is the canonical PECS example. The destination
    // CONSUMES (so `? super T`), the source PRODUCES (so `? extends T`).
    static <T> void copy(Collection<? super T> dest, Collection<? extends T> src) {
        for (T item : src) dest.add(item);
    }

    // ---------------------------------------------------------------------
    // 4) Type erasure — what survives at runtime?
    // ---------------------------------------------------------------------
    // At runtime, `List<String>` and `List<Integer>` are both just `List`.
    // The compiler verifies generic constraints at the call site and inserts
    // any necessary casts in the bytecode. Two visible consequences:
    //
    //   - You cannot `instanceof List<String>` — only `instanceof List`.
    //   - You cannot create `new T[10]` — the array would need its element
    //     type at runtime to fail-fast on bad writes, but T has been erased.
    //
    // The workaround for arrays: pass in a Class<T> or an existing array as
    // a "type token" so the runtime can reify the type.
    @SuppressWarnings("unchecked")
    static <T> T[] newArray(Class<T> clazz, int size) {
        return (T[]) java.lang.reflect.Array.newInstance(clazz, size);
    }

    // ---------------------------------------------------------------------
    // 5) Bridge methods — invisible compiler glue
    // ---------------------------------------------------------------------
    // When you override a generic method with a more specific type, the
    // compiler synthesises a BRIDGE method to preserve runtime dispatch:
    //
    //   class StringBox extends Box<String> { void put(String s) {...} }
    //
    // The compiler emits BOTH put(String) and put(Object), the latter
    // delegating to the former. You won't see this in source, but it
    // explains why @Override and overloading sometimes conflict mysteriously.
    static class Box<T> {
        T value;
        void put(T value) { this.value = value; }
    }

    static class StringBox extends Box<String> {
        @Override
        void put(String value) { this.value = value.toUpperCase(); }
        // bytecode also contains put(Object) — that's the bridge.
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // 1) Generic method inference
        List<String> hi = singleton("hi");
        System.out.println("singleton(\"hi\") = " + hi);

        // 2) Bounded type
        System.out.println("max([3, 1, 4, 1, 5, 9, 2, 6]) = " + max(List.of(3, 1, 4, 1, 5, 9, 2, 6)));

        // 3) PECS — copy Integer to Number-typed destination
        List<Integer> src = List.of(1, 2, 3);
        List<Number> dest = new ArrayList<>();
        copy(dest, src);
        System.out.println("copy result = " + dest);

        // 4) Type token for array creation
        String[] arr = newArray(String.class, 3);
        arr[0] = "a"; arr[1] = "b"; arr[2] = "c";
        System.out.println("newArray = " + Arrays.toString(arr));

        // 5) Override dispatches through the bridge
        Box<String> box = new StringBox();
        box.put("hello");
        System.out.println("StringBox stored = " + box.value);   // "HELLO"
    }
}
