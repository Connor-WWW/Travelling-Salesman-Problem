import components.path.Path;
import components.path.Path.OptimizationMethod;
import components.path.Path1;
import components.random.Random;
import components.random.Random1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Test class for testing implementations of the travelling salesman problem.
 *
 */
public final class TravellingSalesman {

    /**
     * Empty no arg constructor.
     */
    private TravellingSalesman() {

    }

    /**
     * Generates a path out of random points in {@code p} with given
     * {@code length} within ({@code lowerBound}, {@code upperBound}).
     *
     * @param p
     *            The path to place the random points into.
     * @param length
     *            The length of the outputted path.
     * @param lowerBound
     *            The limit to how small a coordinate can be.
     * @param upperBound
     *            The limit to how large a coordinate can be.
     *
     * @replaces p
     * @requires length > 0
     * @ensures |p| = length, p is full of random points.
     */
    public static void generateRandomPath(Path p, int length, int lowerBound,
            int upperBound) {
        // Random ints that are too large or too small are divided by
        // this integer until they are within the correct bounds.
        final int reducingFactor = 10;
        Random rand = new Random1L();
        for (int i = 0; i < length; i++) {
            int pointX = rand.nextInt();
            while (!(lowerBound < pointX && pointX < upperBound)) {
                pointX = pointX / reducingFactor;
            }
            int pointY = rand.nextInt();
            while (!(lowerBound < pointY && pointY < upperBound)) {
                pointY = pointY / reducingFactor;
            }
            if (!p.hasPoint(pointX, pointY)) {
                p.add(0, pointX, pointY);
            }
        }
    }

    /**
     * Runs our test methods to see the effectiveness of different distance
     * optimization methods.
     *
     * @param args
     */
    public static void main(String... args) {
        /*
         * Step 1: Generate a fixed number of random paths, stored in a
         * sequence. The length of these paths will also be random, but at least
         * more than 2. That way, there's distances to be optimized. There is
         * also a bound set for point values, to prevent hitting the double size
         * limit with very far points. At the same time, store their orginial
         * euclidean connected distances.
         */
        System.out.println("Step 1: Generating dataSet");
        Sequence<Path> dataSet = new Sequence1L<>();
        Sequence<Double> connectedDist = new Sequence1L<>();
        final int totalPaths = 100;
        final int minPathLength = 10;
        final int maxPathLength = 100;
        final int pointLowerBound = -10000;
        final int pointUpperBound = 10000;
        Random rand = new Random1L();
        for (int i = 0; i < totalPaths; i++) {
            Path current = new Path1();
            int pathLength = rand.nextInt();
            while (!(maxPathLength >= pathLength
                    && pathLength >= minPathLength)) {
                pathLength = rand.nextInt();
            }
            generateRandomPath(current, pathLength, pointLowerBound,
                    pointUpperBound);
            connectedDist.add(i, current.totalDistanceConnected());
            dataSet.add(i, current);
            // Output % of complete.
            System.out.println("Step 1 Percent complete: "
                    + Math.floor(((i + 1) / (double) totalPaths) * 100) + "%");
        }
        /*
         * Step 2: Run nearest neighbor on all of these, storing their connected
         * distance afterwards into another sequence.
         */
        System.out.println("Step 2: Running Nearest Neighbor");
        Sequence<Double> nnTotals = new Sequence1L<>();
        for (int i = 0; i < totalPaths; i++) {
            // We want to do all calculations on a copy of each path so that
            // dataSet is never modified and results are comparable.
            Path current = dataSet.entry(i);
            Path copy = current.newInstance();
            copy.copyFrom(current);
            copy.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR);
            nnTotals.add(i, copy.totalDistanceConnected());
            // Output % of complete.
            System.out.println("Step 2 Percent complete: "
                    + Math.floor(((i + 1) / (double) totalPaths) * 100) + "%");
        }

        /*
         * Step 3: Run nearest neighbor aggregate on all of these, storing their
         * connected distance afterwards into another sequence.
         */
        System.out.println("Step 3: Running Nearest Neighbor Aggregate");
        Sequence<Double> nnAgTotals = new Sequence1L<>();
        for (int i = 0; i < totalPaths; i++) {
            // We want to do all calculations on a copy of each path so that
            // dataSet is never modified and results are comparable.
            Path current = dataSet.entry(i);
            Path copy = current.newInstance();
            copy.copyFrom(current);
            copy.optimizeDistances(
                    OptimizationMethod.NEAREST_NEIGHBOR_AGGREGATE);
            nnAgTotals.add(i, copy.totalDistanceConnected());
            // Output % of complete.
            System.out.println("Step 3 Percent complete: "
                    + Math.floor(((i + 1) / (double) totalPaths) * 100) + "%");
        }

        /*
         * Step 4: Run nearest neighbor both ends on all of these, storing their
         * connected distance afterwards into another sequence.
         */
        System.out.println("Step 4: Running Nearest Neighbor Both Ends");
        Sequence<Double> nnBothEndsTotals = new Sequence1L<>();
        for (int i = 0; i < totalPaths; i++) {
            // We want to do all calculations on a copy of each path so that
            // dataSet is never modified and results are comparable.
            Path current = dataSet.entry(i);
            Path copy = current.newInstance();
            copy.copyFrom(current);
            copy.optimizeDistances(
                    OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS);
            nnBothEndsTotals.add(i, copy.totalDistanceConnected());
            // Output % of complete.
            System.out.println("Step 4 Percent complete: "
                    + Math.floor(((i + 1) / (double) totalPaths) * 100) + "%");
        }

        /*
         * Step 5: Run nearest neighbor both ends aggregate on all of these,
         * storing their connected distance afterwards into another sequence.
         */
        System.out.println(
                "Step 5: Running Nearest Neighbor Both Ends Aggregate");
        Sequence<Double> nnBothEndsAgTotals = new Sequence1L<>();
        for (int i = 0; i < totalPaths; i++) {
            // We want to do all calculations on a copy of each path so that
            // dataSet is never modified and results are comparable.
            Path current = dataSet.entry(i);
            Path copy = current.newInstance();
            copy.copyFrom(current);
            copy.optimizeDistances(
                    OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS_AGGREGATE);
            nnBothEndsAgTotals.add(i, copy.totalDistanceConnected());
            // Output % of complete.
            System.out.println("Step 5 Percent complete: "
                    + Math.floor(((i + 1) / (double) totalPaths) * 100) + "%");
        }

        /*
         * Step 6: Display this data to our user!
         */
        System.out.println("Step 6: Displaying Results!");
        int originalWins = 0;
        int nnWins = 0;
        int nnAgWins = 0;
        int nnBothEndsWins = 0;
        int nnBothEndsAgWins = 0;
        for (int i = 0; i < totalPaths; i++) {

            // Toss all results into a sequence to find the best option.
            Sequence<Double> currentResults = new Sequence1L<>();
            currentResults.add(0, nnBothEndsAgTotals.entry(i));
            currentResults.add(0, nnBothEndsTotals.entry(i));
            currentResults.add(0, nnAgTotals.entry(i));
            currentResults.add(0, nnTotals.entry(i));
            currentResults.add(0, connectedDist.entry(i));
            // Find the smallest out of all 5.
            int indexOfSmallest = 0;
            for (int j = 1; j < currentResults.length(); j++) {
                if (currentResults.entry(j) < currentResults
                        .entry(indexOfSmallest)) {
                    indexOfSmallest = j;
                }
            }
            // Depending on indexOfSmallest, set winner to that value.
            String winner = "";
            switch (indexOfSmallest) {
                case 0: {
                    winner = "Original";
                    originalWins++;
                    break;
                }
                case 1: {
                    winner = "NN";
                    nnWins++;
                    break;
                }
                case 2: {
                    winner = "NNAg";
                    nnAgWins++;
                    break;
                }
                case 3: {
                    winner = "NNBothEnds";
                    nnBothEndsWins++;
                    break;
                }
                case 4: {
                    winner = "NNBothEndsAg";
                    nnBothEndsAgWins++;
                    break;
                }
                default: {
                    // This shouldn't ever happen, since indexOfSmallest is
                    // initialized to 0 and is only changed if something smaller is found.
                    winner = "Something broke!";
                    break;
                }
            }

            System.out.print("Index: ");
            System.out.print(i);
            System.out.print(" | Num of points: ");
            System.out.print(dataSet.entry(i).length());
            System.out.print(" | Original: ");
            System.out.print(connectedDist.entry(i));
            System.out.print(" | NN: ");
            System.out.print(nnTotals.entry(i));
            System.out.print(" | NNAG: ");
            System.out.print(nnAgTotals.entry(i));
            System.out.print(" | NNBothEnds: ");
            System.out.print(nnBothEndsTotals.entry(i));
            System.out.print(" | NNBothEndsAg: ");
            System.out.print(nnBothEndsAgTotals.entry(i));
            System.out.print(" | Winner: ");
            System.out.print(winner);
            System.out.println();
        }

        // Output totals of which methods were the best.
        System.out.print("Winners Totals: ");
        System.out.print("Original: ");
        System.out.print(originalWins);
        System.out.print(" | NN: ");
        System.out.print(nnWins);
        System.out.print(" | NNAG: ");
        System.out.print(nnAgWins);
        System.out.print(" | NNBothEnds: ");
        System.out.print(nnBothEndsWins);
        System.out.print(" | NNBothEndsAg: ");
        System.out.print(nnBothEndsAgWins);
    }
}
