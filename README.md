# Please Read Before Working on this!
I created a main method within ``TravellingSalesman.java`` that will run the different optimization methods on a bunch of random paths, returning which has the best distances.
Comments are in there to outline how it works and feel free to change any of the final variables to run different tests. Note that it will take a LONG time to run with high values.

The algorithms currently implemented:
- Nearest Neighbor: Picks a random point within the path and connects it to the closest point. It then connects that point to its nearest neighbor, and repeats, until all points within the path are connected.
- Nearest Neighbor Aggregate: Runs the Nearest Neighbor algorithm on every point within the path and choses the resulting path with the smallest distance between points.
- Nearest Neighbor Both Ends: Instead of only looking at the front of the path's point and their nearest neighbor, this algorithm will also look at the back of the path's nearest neighbor, connecting which ever's point is closer to another point.
- Nearest Neighbor Both Ends Aggregate: Runs the Nearest Neighbor Both Ends algorithm on every point within the path and choses the resulting path with the smallest distance between points.

For analyzing runtime, I recommend looking at ``PathSecondary.java`` for the ``distancesToPoint`` and ``indexOfSmallestDouble`` static helper methods, as those are used in all of the other algorithms.
``Point.distance(Point compareTo)`` is a O(1) method.
``optimizeDistances(OptimizationMethod type)`` is just a switch case that calls other static helper methods within ``PathSecondary.java``. That's where you'll get everything you'll need.
Methods with the same names (overloaded) have the same runtimes.
This applies to ``nearestNeighbor(Path p)`` with ``nearestNeighbor(Path p, int indexOfStart)`` and ``nearestNeighborBothEnds(Path p)`` with ``nearestNeighborBothEnds(Path p, int indexOfStart)``.
These methods are overloaded because one starts with a random point within Path p, while the other starts with the point at the given index within Path p. Otherwise, they behave functionally the same, call the same methods and have the same runtime!
