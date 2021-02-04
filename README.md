# Slicer

This project was created as a solution for **Slice code challenge**.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone git@github.com:name/<reponame>.git
```

## Running unit tests
If environment variables for **Java** are configured:
In the project's *terminal* window, write the `gradlew test` for Windows or `./gradlew test` for MacOS command to run the tests.
You can also run tests if in the *project structure window* you find the `app/srс/test/java` folder, call the context menu by right-clicking and select **Run Tests in java**

You can also add your tests to the test directory and run them together, or edit the tests presented.

## Algorithm and presentation.
Instead of working with the command line, test coverage is used to confirm successful conditions.
The critical test file has the name PizzaBotTest. The rest of the test files check the behavior of other classes necessary for the correct operation of the PizzaBot.

Since the conditions did not require finding the most optimal path, two simple algorithms were implemented: The vertical slice algorithm and the greedy algorithm. The first algorithm is based on movement along vertical slices grouped by horizontal position. And the second algorithm takes the direction to the nearest neighbor as a step.
As a result of the tests carried out, it was revealed that the greedy algorithm gives a more optimal path for most cases, which is why it is used as the main one for the PizzaBot.