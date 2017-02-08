# graph-fun
This little tool was developed to be able to automatically layout matchstick graphs. More information about these graphs can be found here: https://en.wikipedia.org/wiki/Matchstick_graph

The program uses a force-based method to layout the graphs and find convergence. As graphs can be entangled, it tries randomly to untangle crossing edges.

## Installation and Test
You need a more or less recent Java version to run the program.
After downloading the sources, you can run the Test classes to try the tool.
Check out the test method "GraphLayoutTest.testHarborth()" with JUnit to see, how the algorithm tries to layout the famous smallest 4-degree matchstick graph.

You can also call the same method with 
```
java de.solti.fun.test.graph.GraphLayoutTest
```
after compiling the classes.

Have fun!
