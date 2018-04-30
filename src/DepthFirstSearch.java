import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch extends PathFinder{


    class MazePair {

        MazePair previousPair;
        Maze.Room currentRoom;

        public MazePair(MazePair previousPair, Maze.Room currentRoom) {
            this.currentRoom = currentRoom;
            this.previousPair = previousPair;
        }

    }

    DepthFirstSearch(Maze maze) {
        super(maze);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Maze.Room> path() throws MazeException {

        MazePair pair = depthFirstSearch();
        List<Maze.Room> list = new LinkedList<Maze.Room>();

        do {
            list.add(0, pair.currentRoom);
            pair = pair.previousPair;
        } while (pair != null);

        return list;
    }


    public MazePair depthFirstSearch() throws MazeException {

        MazePair initialPair = new MazePair(null, getMaze().getStart());

        Stack<MazePair> stack = new Stack<MazePair>();
        Set<Maze.Room> visitedRooms = new HashSet<Maze.Room>();

        stack.push(initialPair);

        while (!stack.isEmpty()) {

            MazePair pair = stack.pop();
            this.incrementNodesExpanded();

            visitedRooms.add(pair.currentRoom);

            if (pair.currentRoom.equals(getMaze().getEnd()))
                return pair;

            for (Maze.Room room : getMaze().adjacentRooms(pair.currentRoom)) {

                if (!visitedRooms.contains(room)) {
                    MazePair nextPair = new MazePair(pair, room);
                    stack.push(nextPair);
                }

            }

        }

        throw new MazeException(MazeException.ErrorCode.NO_PATH);
    }

}
