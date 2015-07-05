#jLinqer

"**jLinqer**" is a Java implementation of LINQ.

##LINQ - jLinqer Matrix

| LINQ(C#) | jLinqer(Java) | Stream(Java) |
|------|-------|--------|
| Where | [where](#where) | filter |
| Select | [select](#select) | map |
| OrderBy | [orderBy](#orderby) | sorted |
| OrderByDescending | [orderByDescending](#orderbydescending) | n/a |
| ThenBy | [thenBy](#thenby) | n/a |
| ThenByDescending | [thenByDescending](#thenbydescending) | n/a |
| SelectMany | [selectMany](#selectmany) | flatMap |
|||||
| Skip | [skip](#skip) | skip |
| SkipWhile | [skipWhile](#skipwhile) | n/a |
| Take | [take](#take) | limit | 
| TakeWhile | [takeWhile](#takewhile) | n/a |
|||||
| Concat | [concat](#concat) | concat |
| Intersect | [intersect](#intersect) | n/a |
| Union | [union](#union) | n/a |
| Except | [except](#except) | n/a |
| Join | n/a | n/a |
| GroupJoin | n/a | n/a |
| Reverse | [reverse](#reverse) | n/a |
| Zip | [zip](#zip) | n/a |
|||||
| Distinct | [distinct](#distinct) | distinct |
| Aggregate | [aggregate](#aggregate) | reduce |
| GroupBy | [groupBy](#groupby) | Collectors.groupingBy |
| Average | [averageXXX](#average) | Collectors.summarizingXXX |
| Count / LongCount | [count](#count) | count |
| Max | [max](#max) | max |
| Min | [min](#min) | min |
| Sum | [sumXXX](#sum) | Collectors.summarizingXXX |
| First | [first](#firstordefault) | findFirst |
| FirstOrDefault | [firstOrDefault](#firstordefault) | n/a |
| Last | [last](#lastordefault) | n/a |
| LastOrDefault | [lastOrDefault](#lastordefault) | n/a |
| Single | [single](#singleordefault) | n/a |
| SingleOrDefault | [singleOrDefault](#singleordefault) | n/a |
| DefaultIfEmpty | [defaultIfEmpty](#defaultifempty) | n/a |
| ElementAt | [elementAt](#elementatordefault) | n/a |
| ElementAtOrDefault | [elementAtOrDefault](#elementatordefault) | n/a |
| All | [all](#all) | allMatch |
| Any | [any](#any) | anyMatch |
|||||
| Empty | [empty](#empty) | n/a |
| Range | [range](#range) | n/a |
| Repeat | [repeat](#repeat) | n/a |
|||||
| SequenceEqual | [sequenceEqual](#sequenceequal) | n/a |
| Cast | [cast](#cast) | n/a |
| OfType | [ofType](#oftype) | n/a |

## Maven

```XML
<dependency>
    <groupId>com.github.jlinqer</groupId>
    <artifactId>jlinqer</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

The following operations are available.

### Where

```Java
List<Integer> list = new List<>(1, 2, 3);

List<Integer> actual = list.where(x -> x == 1 || x == 3).toList();

assertEquals(true , actual.contains(1));
assertEquals(false, actual.contains(2));
assertEquals(true , actual.contains(3));
```

### Select

```Java
List<Person> list = new List<>(
        new Person("React"   , 1),
        new Person("Angular" , 3),
        new Person("Backbone", 5)
);

List<String> actual = list.select(x -> x.name).toList();

assertEquals("React"   , actual.get(0));
assertEquals("Angular" , actual.get(1));
assertEquals("Backbone", actual.get(2));
```

### OrderBy

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.orderBy(x -> x).toList();

assertEquals("Angular" , actual.get(0));
assertEquals("Backbone", actual.get(1));
assertEquals("React"   , actual.get(2));
```

### OrderByDescending

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.orderByDescending(x -> x).toList();

assertEquals("React"   , actual.get(0));
assertEquals("Backbone", actual.get(1));
assertEquals("Angular" , actual.get(2));
```

### ThenBy

```Java
List<Person> list = new List<>(
        new Person("Angular2", 2),
        new Person("Angular1", 2),
        new Person("React"   , 1)
);

List<String> actual = list.orderBy(x -> x.age).thenBy(x -> x.name).toList();

assertEquals("React" , actual.get(0).name);
assertEquals("Angular1", actual.get(1).name);
assertEquals("Angular2"   , actual.get(2).name);
```

### ThenByDescending

```Java
List<Person> list = new List<>(
        new Person("Angular2", 2),
        new Person("Angular1", 2),
        new Person("React"   , 1)
);

List<String> actual = list.orderBy(x -> x.age).thenByDescending(x -> x.name).toList();

assertEquals("React" , actual.get(0).name);
assertEquals("Angular2", actual.get(1).name);
assertEquals("Angular1"   , actual.get(2).name);
```

### SelectMany

```Java
List<Person> list = new List<>(
        new Person("Angular", 3, new List("1.0.1", "1.0.2")),
        new Person("React"  , 1, new List("2.0.1", "2.0.2"))
);

List<String> actual = list.selectMany(x -> x.versionHistory).toList();

assertEquals("1.0.1", actual.get(0));
assertEquals("1.0.2", actual.get(1));
assertEquals("2.0.1", actual.get(2));
assertEquals("2.0.2", actual.get(3));
```

### Skip

```Java
List<Integer> list = new List<>(1, 2, 3);

List<Integer> actual = list.skip(2).toList();

assertEquals(3, actual.get(0).intValue());
```

### SkipWhile

```Java
List<Integer> list = new List<>(1, 2, 3, 4, 5);

List<Integer> actual = list.skipWhile(x -> x <= 3).toList();

assertEquals(4, actual.get(0).intValue());
assertEquals(5, actual.get(1).intValue());
```

### Take

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.take(2).toList();

assertEquals(2, actual.size());
assertEquals("Backbone", actual.get(0));
assertEquals("Angular" , actual.get(1));
```

### TakeWhile

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.takeWhile(x -> x.equals("Backbone") || x.equals("Angular")).toList();

assertEquals(2, actual.size());
assertEquals("Backbone", actual.get(0));
assertEquals("Angular" , actual.get(1));
```

### Concat

```Java
List<Integer> first  = new List<>(1, 2);
List<Integer> second = new List<>(2, 3);

List<Integer> actual = first.concat(second).toList();

assertEquals(1, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(2, actual.get(2).intValue());
assertEquals(3, actual.get(3).intValue());
```

### Intersect

```Java
List<Integer> first  = new List<>(1, 2, 3);
List<Integer> second = new List<>(1, 3);

List<Integer> actual = first.intersect(second).toList();

assertEquals(1, actual.get(0).intValue());
assertEquals(3, actual.get(1).intValue());
```

### Union

```Java
List<Integer> first = new List<>(1, 2, 3);
List<Integer> second = new List<>(0, 1, 3, 4);

List<Integer> actual = first.union(second).toList();

assertEquals(5, actual.size());
assertEquals(1, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(3, actual.get(2).intValue());
assertEquals(0, actual.get(3).intValue());
assertEquals(4, actual.get(4).intValue());
```

### Except

```Java
List<Integer> first  = new List<>(1, 2, 3);
List<Integer> second = new List<>(1, 3);

List<Integer> actual = first.except(second).toList();

assertEquals(2, actual.get(0).intValue());
```

### Reverse

```Java
List<Integer> list = new List<>(1, 2, 3);

List<Integer> actual = list.reverse().toList();

assertEquals(3, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(1, actual.get(2).intValue());
```

### Zip

```Java
List<Integer> first = new List<>(1, 2, 3);
List<String> second = new List<>("Angular", "React", "Backbone");

List<Integer> actual = first.zip(second, (x, y) -> String.format("%s %d", x, y)).toList();

assertEquals("1 Angular" , actual.get(0));
assertEquals("2 React"   , actual.get(1));
assertEquals("3 Backbone", actual.get(2));
```

### Distinct

```Java
List<Integer> list =
        new List<>(
                1, 2, 3,
                1, 2, 3, 4
        );

List<Integer> actual = list.distinct().toList();

assertEquals(1, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(3, actual.get(2).intValue());
assertEquals(4, actual.get(3).intValue());
```

### Aggregate

```Java
List<Integer> list = new List<>(1, 2, 3);

int actual = list.aggregate((sum, elem) -> sum + elem);

assertEquals(6, actual);
```

### GroupBy

```Java
List<Person> list = new List<>(
        new Person("React"   , 1),
        new Person("Angular" , 1),
        new Person("Backbone", 5)
);

Map<Integer, IEnumerable<Person>> actual = list.groupBy(x -> x.age);

assertEquals(true, actual.get(1).any(x -> x.name.equals("React")));
assertEquals(true, actual.get(1).any(x -> x.name.equals("Angular")));
assertEquals(true, actual.get(5).any(x -> x.name.equals("Backbone")));
```

### Average

```Java
List<Long> listLong = new List<>(1l, 2l, 3l, 4l);

double actualLong = listLong.averageLong(x -> x);

assertEquals(2.5d, actualLong, 0);
```

### Count

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

long actual = list.longCount();
int actualNone = list.count(x -> x.equals("jquery"));

assertEquals(3, actual);
assertEquals(0, actualNone);
```

### Max

```Java
List<Double> listDouble = new List<>(1d, 2d, 3d);

double actualDouble = listDouble.max(x -> x);

assertEquals(3d, actualDouble, 0);
```

### Min

```Java
List<BigDecimal> listBigDecimal = new List<>(
        new BigDecimal(1d),
        new BigDecimal(2d),
        new BigDecimal(3d)
);

BigDecimal actualBigDecimal = listBigDecimal.min(x -> x);

assertEquals(1d, actualBigDecimal.doubleValue(), 0);
```

### Sum

```Java
List<Integer> listInt = new List<>(1, 2, 3);

int actualInt = listInt.sumInt(x -> x);

assertEquals(6, actualInt);
```

### FirstOrDefault

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

String actualFirst   = list.firstOrDefault();
String actualMatch   = list.firstOrDefault(x -> x.equals("Angular"));
String actualUnMatch = list.firstOrDefault(x -> x.equals("jquery"));

assertEquals("Backbone", actualFirst);
assertEquals("Angular" , actualMatch);
assertEquals(null      , actualUnMatch);
```

### LastOrDefault

```Java
List<Integer> list = new List<>(1, 2, 3);
List<Integer> listEmpty = new List<>();

int actual = list.lastOrDefault();
Integer actualDefaultNone = listEmpty.lastOrDefault(x -> x == 0);

assertEquals(3, actual);
assertEquals(null, actualDefaultNone);
```

### SingleOrDefault

```Java
List<Integer> listMany = new List<>(1, 2, 3);
List<Integer> listEmpty = new List<>();

int actualFilter = listMany.singleOrDefault(x -> x == 3);
Integer actualUnMatch = listEmpty.singleOrDefault(x -> x == 0);

assertEquals(3, actualFilter);
assertEquals(null, actualUnMatch);
```

### DefaultIfEmpty

```Java
List<String> listEmpty = new List<>();

List<String> actualDefault = listEmpty.defaultIfEmpty("ES7").toList();

assertEquals("ES7", actualDefault.get(0));
```

### ElementAtOrDefault

```Java
List<Integer> list = new List<>(1, 2, 3);

int actual = list.elementAtOrDefault(2);
Integer actualDefault = list.elementAtOrDefault(3);

assertEquals(3, actual);
assertEquals(null, actualDefault);
```

### All

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

boolean actual = list.all(x -> x.equals("Angular") || x.equals("Backbone") || x.equals("React"));
boolean actualNotFound = list.all(x -> x.equals("Angular") || x.equals("React"));

assertEquals(true, actual);
assertEquals(false, actualNotFound);
```

### Any

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

boolean actual = list.any(x -> x.equals("Angular"));
boolean actualNotFound = list.any(x -> x.equals("jquery"));

assertEquals(true, actual);
assertEquals(false, actualNotFound);
```

### Empty

```Java
List<Double> actual = IEnumerable.empty(Double.class);

assertEquals(0, actual.count());
```

### Range

```Java
List<Integer> actual = IEnumerable.range(-2, 3);

assertEquals(-2, actual.get(0).intValue());
assertEquals(-1, actual.get(1).intValue());
assertEquals(0 , actual.get(2).intValue());
```

### Repeat

```Java
List<String> actual = IEnumerable.repeat(String.class, "Law of Cycles", 10);

assertEquals(10, actual.count());
assertEquals("Law of Cycles", actual.get(9));
```

### SequenceEqual

```Java
List<Integer> first = new List<>(1, 2, 3);
List<Integer> secondMatch = new List<>(1, 2, 3);
List<Integer> secondUnMatchElem = new List<>(1, 2, 4);

boolean actualMatch = first.sequenceEqual(secondMatch);
boolean actualUnMatchElm = first.sequenceEqual(secondUnMatchElem);

assertEquals(true, actualMatch);
assertEquals(false, actualUnMatchElm);
```

### Cast

```Java
List<Object> list = new List<>(1, 2, 3);

List<Integer> actual = list.cast(Integer.class).toList();

assertEquals(1, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(3, actual.get(2).intValue());
```

### OfType

```Java
List<Object> list = new List<>(1, "2", 3, "4");

List<String>  actualStr = list.ofType(String.class).toList();
List<Integer> actualInt = list.ofType(Integer.class).toList();

assertEquals("2", actualStr.get(0));
assertEquals("4", actualStr.get(1));
assertEquals(1  , actualInt.get(0).intValue());
assertEquals(3  , actualInt.get(1).intValue());
```

#References

1. Microsoft Reference Source, "Enumerable.cs", http://referencesource.microsoft.com/#System.Core/System/Linq/Enumerable.cs
1. GitHub, "javaLinq", https://github.com/sircodesalotOfTheRound/javaLinq
1. Qiita, "LINQ to Objects と Java8-Stream API の対応表", http://qiita.com/amay077/items/9d2941283c4a5f61f302
1. stackoverflow, "Generic method to perform a map-reduce operation. (Java-8)", http://stackoverflow.com/questions/30826674/generic-method-to-perform-a-map-reduce-operation-java-8
