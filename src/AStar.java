import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar extends PathFinder {

    AStar(Maze maze) {
        super(maze);
    }

    // Class which has a room, and the necessary values to perform A*
    class HeuristicRoom {

        private final int depth;

        // The fvalue in A*: depth + heuristic
        private final int value;

        private final Maze.Room room;

        // Used to rebuild the maze
        private final HeuristicRoom parent;

        HeuristicRoom(int depth, Maze.Room room, HeuristicRoom parent) {
            this.depth = depth;
            this.room = room;
            this.parent = parent;
            this.value = depth + manhattanDistance(room);
        }

        int getDepth() {
            return depth;
        }

        Maze.Room getRoom() {
            return room;
        }

        HeuristicRoom getParent() {
            return parent;
        }

        int getValue() {
            return value;
        }

    }

    @Override
    List<Maze.Room> path() throws MazeException {

        HeuristicRoom nextRoom = aStarSearch();
        List<Maze.Room> solution = new LinkedList<Maze.Room>();

        // builds solution
        while (nextRoom != null) {

            Maze.Room room = nextRoom.getRoom();
            solution.add(0, room);
            nextRoom = nextRoom.getParent();
        }

        return solution;
    }

    private HeuristicRoom aStarSearch() throws MazeException {

        // Comparator for the priority queue
        Comparator<HeuristicRoom> comparator = new Comparator<HeuristicRoom>() {

            @Override
            public int compare(HeuristicRoom room1, HeuristicRoom room2) {
                return room1.getValue() - room2.getValue();
            }

        };

        Set<HeuristicRoom> closedRooms = new HashSet<HeuristicRoom>();
        Set<Maze.Room> visitedRooms = new HashSet<Maze.Room>();

        PriorityQueue<HeuristicRoom> openRooms = new PriorityQueue<HeuristicRoom>(1, comparator);

        openRooms.add(new HeuristicRoom(0, getMaze().getStart(), null));

        while(!openRooms.isEmpty()) {

            HeuristicRoom prioHeuristicRoom = openRooms.poll();
            Maze.Room prioRoom = prioHeuristicRoom.getRoom();

            if (prioRoom.equals(getMaze().getEnd()))
                return prioHeuristicRoom;

            incrementNodesExpanded();
            closedRooms.add(prioHeuristicRoom);
            visitedRooms.add(prioRoom);

            for (Maze.Room room : getMaze().adjacentRooms(prioRoom)) {

                if (visitedRooms.contains(room))
                    continue;

                int nextDepth = prioHeuristicRoom.getDepth();
                HeuristicRoom nextHeuristicRoom = new HeuristicRoom(nextDepth, room, prioHeuristicRoom);

                boolean bestInOpen = isBestIn(openRooms, comparator, nextHeuristicRoom);
                boolean bestInClosed = isBestIn(closedRooms, comparator, nextHeuristicRoom);

                if (bestInOpen && bestInClosed)
                    openRooms.add(nextHeuristicRoom);
            }

        }

        throw new MazeException(MazeException.ErrorCode.NO_PATH);
    }

    private boolean isBestIn(
            Iterable<HeuristicRoom> iterable,
            Comparator<HeuristicRoom> comparator,
            HeuristicRoom room) {

        Iterator<HeuristicRoom> iterator = iterable.iterator();

        while (iterator.hasNext()) {

            HeuristicRoom nextRoom = iterator.next();

            Point location1 = room.getRoom().getLocation();
            Point location2 = nextRoom.getRoom().getLocation();

            if (location1.equals(location2)) {

                if (nextRoom.getValue() < room.getValue())
                    return false;
            }
        }

        return true;
    }

    public int manhattanDistance(Maze.Room room) {

        Maze.Room goalRoom = getMaze().getEnd();

        int xDiff = goalRoom.getLocation().x - room.getLocation().x;
        int yDiff = goalRoom.getLocation().y - room.getLocation().y;

        return Math.abs(xDiff) + Math.abs(yDiff);
    }

    @Override
    public String toString() {
        return "# A* Search \n\n" + super.toString();
    }

}