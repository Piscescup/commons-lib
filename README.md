# commons-lib

`commons-lib` is a general-purpose Java 21 utility library focused on reusable, low-coupling building blocks. 
It provides string and array utilities, argument/state validation helpers, 
interval models, pair and tuple-like structures, counter implementations, 
and a richer set of functional interfaces than the JDK offers out of the box.

It is suitable for:

- shared infrastructure across multiple Java projects
- Minecraft/Fabric utilities and common modules
- projects that need better interval modeling and multi-argument functional interfaces

## Installation

Maven:

```xml
<dependency>
    <groupId>io.github.piscescup</groupId>
    <artifactId>commons-lib</artifactId>
    <version>1.1.2</version>
</dependency>
```

Gradle:

```groovy
dependencies {
    implementation 'io.github.piscescup:commons-lib:1.1.2'
}
```

## Core Modules

### 1. General Utilities

`io.github.piscescup.util` contains high-frequency helper classes, including:

- `StringUtils`: null/blank checks, naming-style conversion, word splitting, and case formatting
- `ArrayUtils`: empty checks, index validation, cloning, copying, search, and removal operations
- `CompareUtils`: fluent comparison building for `compareTo` and complex ordering logic
- `ClassUtils`, `MethodUtils`: reflection and type-related helpers

### 2. Validation Utilities

`io.github.piscescup.util.validation` provides fail-fast validation helpers:

- `NullCheck`: null validation and null-element checks
- `ArgumentCheck`: argument validation
- `StateCheck`: object state and runtime state validation

### 3. Interval Model

`io.github.piscescup.interval` and `io.github.piscescup.interval.primitive` provide a structured interval system:

- `IntervalType` supports open, closed, open-closed, and closed-open intervals
- `NaturalOrderedInterval<T>` supports object intervals based on natural ordering
- `ComparatorOrderedInterval<T>` supports object intervals based on a custom comparator
- `ByteInterval`, `ShortInterval`, `IntInterval`, `LongInterval`, `FloatInterval`, `DoubleInterval`, and `CharInterval` support primitive intervals
- Common operations include `contains`, `containsInterval`, `overlaps`, `intersection`, and `formattedString`

### 4. Pair and Fixed-Arity Entries

For small structured values, the project provides:

- `Pair<L, R>`: a two-value structure with mapping and consuming helpers
- `ImmutablePair<L, R>` / `MutablePair<L, R>`: immutable and mutable implementations
- `Entry`, `BinEntry`, `TriEntry`, `QuadEntry`, `QuinEntry`: fixed-arity tuple-like carriers

### 5. Counters

`io.github.piscescup.counter` provides two counter implementations:

- `SimpleCounter`: a simple mutable counter
- `AtomicCounter`: a thread-safe counter backed by `AtomicLong`

Common operations are unified through the `Counter` interface, such as `increment`, `decrement`, `addAndGet`, and `reset`.

### 6. Extended Functional Interfaces

`io.github.piscescup.interfaces.exfunction` is one of the more distinctive parts of this library. It includes:

- `Bin*`, `Tri*`, `Quad*`, `Quin*` multi-argument functional interfaces
- primitive-specialized interfaces under the `primitive` package
- throwable variants under the `failable` package
- utilities such as currying, partial application, argument reversal, and memoization

If you often find the standard JDK functional interfaces too limited for multi-argument use cases, this package is likely the most useful part of the library.

## Quick Examples

### String naming conversion

```java
import io.github.piscescup.util.StringUtils;

String table = StringUtils.toSnakeLowerCase("HelloWorld");   // hello_world
String clazz = StringUtils.toUpperCamel("player_inventory"); // PlayerInventory
String field = StringUtils.toLowerCamel("PLAYER_LEVEL");     // playerLevel
```

### Interval checks

```java
import io.github.piscescup.interval.IntervalType;
import io.github.piscescup.interval.primitive.IntInterval;

IntInterval range = IntInterval.of(1, 10, IntervalType.CLOSED_OPEN_INTERVAL);

boolean containsOne = range.contains(1);   // true
boolean containsTen = range.contains(10);  // false
String display = range.formattedString();  // Int Interval: [1, 10)
```

### Counters

```java
import io.github.piscescup.counter.Counter;

Counter counter = Counter.atomic();
counter.increment();
counter.addAndGet(4);

long current = counter.get(); // 5
```

### Extended Function

```java
import io.github.piscescup.interfaces.exfunction.TriFunction;

TriFunction<Integer, Integer, Integer, Integer> volume = (x, y, z) -> x * y * z;

int v1 = volume.apply(2, 3, 4);       // 24
int v2 = volume.apply(2).apply(3, 4); // 24

TriFunction<Integer, Integer, Integer, Integer> cached = volume.memorized();
int v3 = cached.apply(2, 3, 4);
```

### Pair mapping

```java
import io.github.piscescup.interfaces.Pair;

Pair<String, Integer> pair = Pair.of("apple", 3);
Pair<String, Integer> mapped = pair.mappedLeft(String::toUpperCase);

String left = mapped.getLeft();    // APPLE
Integer right = mapped.getRight(); // 3
```

## License

This project declares the Apache License 2.0 in `pom.xml`.
