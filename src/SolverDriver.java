import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SolverDriver {

    private static class SolveRunnable implements Runnable {

        PathFinder pathFinder;

        @Override
        public void run() {

            try {
                pathFinder.solveMaze();
            } catch (MazeException e) {
                System.out.println("Maze can't be solved");
            }

        }

        SolveRunnable(PathFinder pathFinder) {
            this.pathFinder = pathFinder;
        }

    }

    public static void main(String[] args) throws Exception {

        if (args.length == 1) {
            String fileName = args[0];
            runProgram(fileName);
        } else {
            System.out.println("Cannot read file name");
        }

    }

    private static void runProgram(String fileName) {

        try {
            List<String> lines = Parser.readFile(fileName);
            executeMazeSolver(lines);
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found");
        }
    }

    private static void executeMazeSolver(List<String> lines) {

        try {
            Maze maze = Parser.toMaze(lines);
            concurrentSolve(maze);
        } catch (MazeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void concurrentSolve(Maze maze) {

        List<SolveRunnable> runnables = runnables(maze);
        executeConcurrrentProgram(runnables);
        List<PathFinder> pathFinders = pathFinders(runnables);

        if (pathFinders.get(0).isSolved()) {
            printSolutions(pathFinders);
        } else {
            System.out.println(new MazeException(MazeException.ErrorCode.NO_PATH).getMessage());
        }

    }

    private static List<PathFinder> pathFinders(List<SolveRunnable> runnables) {
        List<PathFinder> list = new ArrayList<PathFinder>();

        for (SolveRunnable runnable : runnables) {
            list.add(runnable.pathFinder);
        }

        return list;
    }

    private static void printSolutions(List<PathFinder> list) {

        for (PathFinder pathFinder : list) {
            System.out.println(pathFinder.toString());
            System.out.println();
        }

    }

    private static List<SolveRunnable> runnables(Maze maze) {

        SolveRunnable aStar = new SolveRunnable(new AStar(maze));
        SolveRunnable dfs = new SolveRunnable(new DepthFirstSearch(maze));

        List<SolveRunnable> list = new ArrayList<SolveRunnable>();

        list.add(aStar);
        list.add(dfs);

        return list;
    }

    private static void executeConcurrrentProgram(List<SolveRunnable> runnables) {

        List<Thread> threads = new ArrayList<Thread>();

        for (Runnable runnable : runnables)
            threads.add(new Thread(runnable));

        for (Thread t : threads)
            t.start();

        for (Thread t : threads) {

            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }

    }

}