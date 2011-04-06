# The curious case of Benjamin Benchmarking
This is very much a work in progress and you should assume a skeptic stance.

As always when benchmarking - we're doing it wrong. Hence timing and size reports will probably favour this library. If you know how to make any case perform better or make the comparisons more accurate - fork, fix and issue a pull request.

## What we're comparing
* Size of the output
* Speed of serializing 2000 repetitions over the same object

As a side effect these tests also provide a rough comparison of how to use the different libraries. We've also tried to comment where a specifica library imposes a specific limitation (such as non-final fields etc).
