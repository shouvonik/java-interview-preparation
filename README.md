# java-interview-preparation

Personal study notes and worked examples for a UK Lead Developer / Tech Lead job search.

The repo is split by purpose so each area can be revised independently:

| Folder | Purpose |
| --- | --- |
| [`dsa/`](dsa/) | Data structures & algorithms for pair-programming and coding screens — every topic has notes, a worked guide, exercise stubs, and reference solutions. |
| `lead-prep/` *(planned)* | Concept refresh for Java core, Spring Boot, MongoDB, Oracle, AWS, AWS AI — architecture and lead-level scenarios. |

## How to use

Each topic folder follows the same shape:

```
<topic>/
├── README.md                # concept notes + complexity + curated LeetCode list
├── <Topic>.java             # worked guide with runnable main()
└── exercises/
    ├── <Topic>Exercises.java     # stubs to attempt yourself
    └── solutions/
        └── <Topic>Solutions.java # reference answers (peek only after trying)
```

Run any file directly with the JDK (no Maven needed yet):

```bash
cd dsa/src/main/java
javac dsa/searching/Searching.java
java dsa.searching.Searching
```

## Java version

Targets JDK 26 (Maven `release` flag). Ensure `JAVA_HOME` points at a JDK 26 install before running `mvn`; `mvn -v` will show the active JDK.
