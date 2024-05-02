1- for this question, `Thread will be finished here!!!` and `Thread was interrupted!` will be printed anyway, because after 
```java
Thread.sleep(10000);
``` 
is finished, an `InterruptedException` exception will be created, after execution of
```java
thread.interrupt();
```
, an `InterruptedException` exception will be created too, so it does not matter if it's after passed time or execution of `interrupt()`.
for `Thread will be finished here!!!`, it is printed in `finally` statement, no matter what, this block will be executed.

2- It will run inside the main thread!

3- the main thread will wait for the new thread to get finished, and then will continue to do its next works.