# Cache

This code is the first and the second part of the assignment for the Data Structures course at 
Harokopio University of Athens, Dept. of Informatics and Telematics.  It implements 
an LRU-MRU Cache using a HashMap and a Double Linked List. 

### Names:
it2023043 - Konstantinos Mavraganis<br>
it2023085 - Giannis Fykas<br>
it2023096 - Panagiotis Dimopoulos


For the first part we only use tests to check if the code is correct.
We do not need to use a main class to run it.

Compile using

```
mvn clean compile
```

Run only unit tests using

```
mvn test
```

For the second part we also have a main class to test and print the hit/miss stats.

Make the package (also runs the tests)

```
mvn package
```

Run the Main program to print the stats

```
java -cp target/cache-0.0.1-SNAPSHOT.jar org.hua.cache.App
```





