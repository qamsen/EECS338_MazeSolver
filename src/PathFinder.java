import java.util.List;
import java.util.Optional;

public abstract class PathFinder {

    private Optional<List<Maze.Room>> solution;
    private Optional<Double> timeToSolve;
    private boolean isSolved;
    private final Maze maze;

    PathFinder(Maze maze) {
        this.maze = maze;
        this.setSolution(Optional.<List<Maze.Room>>empty());
        this.setTimeToSolve(Optional.<Double>empty());
        this.setSolved(false);
    }

    // Will implement a more specific exception later
    public void solveMaze() throws MazeException {

        long startTime = System.currentTimeMillis();
        List<Maze.Room> solution = path();
        long endTime = System.currentTimeMillis();

        double timeToSolve = (endTime - startTime) / 1000.0;

        setSolution(Optional.<List<Maze.Room>>of(solution));
        setTimeToSolve(Optional.<Double>of(timeToSolve));
        setSolved(true);
    }

    // If no path exists, `throw new MazeException(MazeException.ErrorCode.NO_PATH);`
    public abstract List<Maze.Room> path() throws MazeException;

    /**
     * @return the solution
     */
    public Optional<List<Maze.Room>> getSolution() {
        return solution;
    }

    /**
     * @param solution the solution to set
     */
    private void setSolution(Optional<List<Maze.Room>> solution) {
        this.solution = solution;
    }

    /**
     * @return the timeToSolve
     */
    public Optional<Double> getTimeToSolve() {
        return timeToSolve;
    }

    /**
     * @param timeToSolve the timeToSolve to set
     */
    private void setTimeToSolve(Optional<Double> timeToSolve) {
        this.timeToSolve = timeToSolve;
    }

    /**
     * @return the isSolved
     */
    public boolean isSolved() {
        return isSolved;
    }

    /**
     * @param isSolved the isSolved to set
     */
    private void setSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    /**
     * @return the maze
     */
    public Maze getMaze() {
        return maze;
    }

    @Override
    public String toString() {

        if (isSolved())
            // Will need to a text representation of the solution, time it took
            // to solve, and the nodes evaluated
            return "";
        else
            return "Path not found";
    }
    
    public static void main(String args[]) {
    	
    }

}
