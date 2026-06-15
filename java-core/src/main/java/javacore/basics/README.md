# Basics — OOP, Object contracts

The "obvious" stuff that lead-level interviewers still ask, because how you frame it shows the depth of your model.

## Concept summary

The four OOP pillars:
- **Encapsulation** — hide state behind methods; mutate via well-defined invariants.
- **Inheritance** — `is-a` relationship; prefer composition unless the subtype really substitutes the parent (Liskov).
- **Polymorphism** — dynamic dispatch via virtual methods; the runtime type decides which method runs.
- **Abstraction** — separate WHAT from HOW; interfaces and abstract classes are the tools.

## What interviewers actually probe

| Question | What they want to hear |
| --- | --- |
| Why prefer composition over inheritance? | Inheritance leaks parent's API + tightly couples lifecycle; composition stays explicit and substitutable. Cite "Effective Java" Item 18. |
| `equals` and `hashCode` contract | If `a.equals(b)`, then `a.hashCode() == b.hashCode()`. Override both or neither. |
| What's `final` actually do? | On a variable → can't reassign; on a method → can't override; on a class → can't extend. Doesn't make the object immutable! |
| Interface vs abstract class | Interface = capability ("you can do X"). Abstract class = partial implementation + state. Java 8+ default methods blur the line. |
| Static method vs instance method | Static = belongs to the class, no `this`. Can't be overridden (hidden, not polymorphic). |

## Common pitfalls

- **Overriding equals but not hashCode** — breaks HashMap, HashSet, anything hash-based.
- **Calling overridable methods from a constructor** — subclass overrides see uninitialised fields.
- **Public mutable fields** — kills encapsulation; later refactor is breaking.
- **Mistaking field hiding for overriding** — fields are resolved by declared type, not runtime type. Only methods dispatch dynamically.

## Canned answers

> **"Walk me through `equals` and `hashCode`."**
>
> Reflexive (`x.equals(x)`), symmetric, transitive, consistent, and `x.equals(null) == false`. `hashCode` must agree: equal objects produce equal hashes. Unequal objects ideally produce different hashes for performance, but the contract doesn't require it. If you override one, you override both — IDEs and Lombok generate them; records do it automatically.

> **"Composition over inheritance — example?"**
>
> Forwarding wrapper: a `ForwardingSet<E>` that wraps a `Set<E>` and overrides only the methods you want to change. Subclassing the concrete `HashSet` would couple you to its internal calls (e.g. `addAll` calling `add`) — the wrapper sidesteps that.

## References

- *Effective Java* — Items 10–14 (equals/hashCode/toString/Comparable), Item 18 (composition over inheritance), Item 51 (method signatures).
- [Object class docs](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html).

## In this package

```
basics/
├── README.md
├── BasicsGuide.java                  # worked examples
├── exercises/BasicsExercises.java
├── exercises/solutions/BasicsSolutions.java
└── examples/
    ├── EqualsHashCodeContract.java   # what breaks when you violate it
    └── CompositionVsInheritance.java # forwarding wrapper pattern
```
