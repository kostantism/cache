# Java Cache System (LRU, MRU, LFU Implementation)

A comprehensive Java-based Cache implementation supporting multiple replacement policies. This project was developed as part of a "Data Structures" course to demonstrate efficient data management and memory simulation.

## Features

The application provides a configurable Cache with the following replacement policies:

* **LRU (Least Recently Used):** Evicts the element that was accessed longest ago.
* **MRU (Most Recently Used):** Evicts the element that was most recently accessed.
* **LFU (Least Frequently Used):** Evicts the element with the lowest access frequency.

It also includes a simulation mechanism to calculate performance metrics such as **Hit Rate** and **Miss Rate** through extensive stress testing.

## Tech Stack

* **Language:** Java 21
* **Build Tool:** Maven
* **Testing:** JUnit 4
* **Core Structures:** HashMap (O(1) access), Doubly Linked List (for LRU/MRU ordering), TreeMap (for frequency management in LFU).

## Installation & Usage

### Prerequisites
* Java JDK 21
* Apache Maven

### Steps

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/cache-implementation-java.git](https://github.com/your-username/cache-implementation-java.git)
    cd cache-implementation-java
    ```

2.  **Compile the project:**
    ```bash
    mvn clean compile
    ```

3.  **Run unit tests:**
    ```bash
    mvn test
    ```

4.  **Run the simulation (Main):**
    ```bash
    mvn exec:java -Dexec.mainClass="org.hua.cache.App"
    ```

## Project Structure

* `Cache.java`: Interface defining core `get` and `put` operations.
* `MyCache.java`: Principal implementation logic for all policies.
* `CacheReplacementPolicy.java`: Enum for selecting the desired policy.
* `App.java`: Main entry point that runs 100,000 operations per policy to evaluate efficiency.

## Sample Output
The application generates performance reports like the one below:
```text
Replacement policy: Least Recently Used
Total operations: 100000
Cache Hits: 45230
Cache Misses: 54770
Hit Rate: 45.23%








