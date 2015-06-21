#jLinqer

"**jLinqer**" is a Java implementation of LINQ.


## Usage

The following operations are available.

### Where

```Java
List<Integer> list = new List<>(1, 2, 3);

List<Integer> actual = list.where(x -> x == 1 || x == 3);

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

List<String> actual = list.select(x -> x.name);

assertEquals("React"   , actual.get(0));
assertEquals("Angular" , actual.get(1));
assertEquals("Backbone", actual.get(2));
```

### SelectMany

```Java
List<Person> list = new List<>(
        new Person("Angular", 3, Arrays.asList("1.0.1", "1.0.2")),
        new Person("React"  , 1, Arrays.asList("2.0.1", "2.0.2"))
);

// act
List<String> actual = list.selectMany(x -> x.versionHistory.stream());

// assert
assertEquals("1.0.1", actual.get(0));
assertEquals("1.0.2", actual.get(1));
assertEquals("2.0.1", actual.get(2));
assertEquals("2.0.2", actual.get(3));
```

### OrderBy

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.orderBy(x -> x);

assertEquals("Angular" , actual.get(0));
assertEquals("Backbone", actual.get(1));
assertEquals("React"   , actual.get(2));
```

### OrderByDescending

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.orderByDescending(x -> x);

assertEquals("React"   , actual.get(0));
assertEquals("Backbone", actual.get(1));
assertEquals("Angular" , actual.get(2));
```


### Union

```Java
List<Integer> first = new List<>(1, 2, 3);
List<Integer> second = new List<>(0, 1, 3, 4);

List<Integer> actual = first.union(second);

assertEquals(5, actual.size());
assertEquals(1, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(3, actual.get(2).intValue());
assertEquals(0, actual.get(3).intValue());
assertEquals(4, actual.get(4).intValue());
```

### Intersect

```Java
List<Integer> first  = new List<>(1, 2, 3);
List<Integer> second = new List<>(1, 3);

List<Integer> actual = first.intersect(second);

assertEquals(1, actual.get(0).intValue());
assertEquals(3, actual.get(1).intValue());
```

### Except

```Java
List<Integer> first  = new List<>(1, 2, 3);
List<Integer> second = new List<>(1, 3);

List<Integer> actual = first.except(second);

assertEquals(2, actual.get(0).intValue());
```
### Concat

```Java
List<Integer> first  = new List<>(1, 2);
List<Integer> second = new List<>(2, 3);

List<Integer> actual = first.concat(second);

assertEquals(1, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(2, actual.get(2).intValue());
assertEquals(3, actual.get(3).intValue());
```

### Reverse

```Java
List<Integer> list = new List<>(1, 2, 3);

List<Integer> actual = list.reverse();

assertEquals(3, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(1, actual.get(2).intValue());
```

### GroupBy

```Java
List<Person> list = new List<>(
        new Person("React"   , 1),
        new Person("Angular" , 1),
        new Person("Backbone", 5)
);

Map<Integer, List<Person>> actual = list.groupBy(x -> x.age);

// assert
assertEquals(true, actual.get(1).any(x -> x.name.equals("React")));
assertEquals(true, actual.get(1).any(x -> x.name.equals("Angular")));
assertEquals(true, actual.get(5).any(x -> x.name.equals("Backbone")));
```

### Distinct

```Java
List<Integer> list =
        new List<>(
                1, 2, 3,
                1, 2, 3, 4
        );

List<Integer> actual = list.distinct();

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

### FirstOrDefault

```Java
// arrange
List<String> list = new List<>("Backbone", "Angular", "React");

// act
String actualFirst   = list.firstOrDefault();
String actualMatch   = list.firstOrDefault(x -> x.equals("Angular"));
String actualUnMatch = list.firstOrDefault(x -> x.equals("jquery"));

// assert
assertEquals("Backbone", actualFirst);
assertEquals("Angular" , actualMatch);
assertEquals(null      , actualUnMatch);
```

### LastOrDefault

```Java
List<Integer> list = new List<>(1, 2, 3);

int actual = list.lastOrDefault();
Integer actualDefaultNone = listEmpty.lastOrDefault(x -> x == 0);

assertEquals(3, actual);
assertEquals(null, actualDefaultNone);
```

### SingleOrDefault

```Java
List<Integer> listMany = new List<>(1, 2, 3);

int actualFilter = listMany.singleOrDefault(x -> x == 3);
Integer actualUnMatch = listEmpty.singleOrDefault(x -> x == 0);

assertEquals(3, actualFilter);
assertEquals(null, actualUnMatch);
```

### ElementAtOrDefault

```Java
List<Integer> list = new List<>(1, 2, 3);

int actual = list.elementAtOrDefault(2);
Integer actualDefault = list.elementAtOrDefault(3);

assertEquals(3, actual);
assertEquals(null, actualDefault);
```

### Max

```Java
List<Double> listDouble = new List<>(1d, 2d, 3d);

double actualDouble = listDouble.maxDouble(x -> x);

assertEquals(3d, actualBigDecimal.doubleValue(), 0);
```

### Min

```Java
List<BigDecimal> listBigDecimal = new List<>(
        new BigDecimal(1d),
        new BigDecimal(2d),
        new BigDecimal(3d)
);

BigDecimal actualBigDecimal = listBigDecimal.minBigDecimal(x -> x);

assertEquals(1d, actualBigDecimal.doubleValue(), 0);
```

### Sum

```Java
List<Integer> listInt = new List<>(1, 2, 3);

int actualInt = listInt.sumInt(x -> x);

assertEquals(6, actualInt);
```

### Average

```Java
List<Long> listLong = new List<>(1l, 2l, 3l, 4l);

double actualLong = listLong.averageLong(x -> x);

assertEquals(2.5d, actualLong);
```

### Count

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

long actual = list.count();
long actualNone = list.count(x -> x.equals("jquery"));

// assert
assertEquals(3, actual);
assertEquals(0, actualNone);
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

### Skip

```Java
List<Integer> list = new List<>(1, 2, 3);

List<Integer> actual = list.skip(2);

assertEquals(3, actual.get(0).intValue());
```

### SkipWhile

```Java
List<Integer> list = new List<>(1, 2, 3, 4, 5);

List<Integer> actual = list.skipWhile(x -> x <= 3);

assertEquals(4, actual.get(0).intValue());
assertEquals(5, actual.get(1).intValue());
```

### Take

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.take(2);

assertEquals(2, actual.size());
assertEquals("Backbone", actual.get(0));
assertEquals("Angular" , actual.get(1));
```

### TakeWhile

```Java
List<String> list = new List<>("Backbone", "Angular", "React");

List<String> actual = list.takeWhile(x -> x.equals("Backbone") || x.equals("Angular"));

assertEquals(2, actual.size());
assertEquals("Backbone", actual.get(0));
assertEquals("Angular" , actual.get(1));
```

### Range

```Java
List<Integer> actual = List.range(-2, 3);

assertEquals(-2, actual.get(0).intValue());
assertEquals(-1, actual.get(1).intValue());
assertEquals(0 , actual.get(2).intValue());
```

### Repeat

```Java
List<String> actual = List.repeat(String.class, "Law of Cycles", 10);

assertEquals(10, actual.count());
assertEquals("Law of Cycles", actual.get(9));
```

### Empty

```Java
List<Double> actual = List.empty(Double.class);

assertEquals(0, actual.count());
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

### DefaultIfEmpty

```Java
List<String> listEmpty = new List<>();

List<String> actualDefault = listEmpty.defaultIfEmpty("ES7");

assertEquals("ES7", actualDefault.get(0));
```

### Cast

```Java
List<Object> list = new List<>(1, 2, 3);

List<Integer> actual = list.cast(Integer.class);

assertEquals(1, actual.get(0).intValue());
assertEquals(2, actual.get(1).intValue());
assertEquals(3, actual.get(2).intValue());
```

### OfType

```Java
List<Object> list = new List<>(1, "2", 3, "4");

List<String>  actualStr = list.ofType(String.class);
List<Integer> actualInt = list.ofType(Integer.class);

assertEquals("2", actualStr.get(0));
assertEquals("4", actualStr.get(1));
assertEquals(1  , actualInt.get(0).intValue());
assertEquals(3  , actualInt.get(1).intValue());
```
