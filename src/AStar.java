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
    private class DepthRoom {

        private final int depth;

        // The fvalue in A*: depth + heuristic
        private final int value;

        private final Maze.Room room;

        // Used to rebuild the maze
        private final DepthRoom parent;

        DepthRoom(int depth, Maze.Room room, DepthRoom parent) {
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

        DepthRoom getParent() {
            return parent;
        }

        int getValue() {
            return value;
        }

    }

    @Override
    List<Maze.Room> path() throws MazeException {

        DepthRoom nextRoom = aStarSearch();
        List<Maze.Room> solution = new LinkedList<Maze.Room>();

        // builds solution
        while (nextRoom != null) {

            Maze.Room room = nextRoom.getRoom();
            solution.add(0, room);
            nextRoom = nextRoom.getParent();
        }

        return solution;
    }

    private DepthRoom aStarSearch() throws MazeException {

        // Comparator for the priority queue
        Comparator<DepthRoom> comparator = new Comparator<DepthRoom>() {

            @Override
            public int compare(DepthRoom room1, DepthRoom room2) {
                return room1.getValue() - room2.getValue();
            }

        };

        Set<DepthRoom> closedRooms = new HashSet<DepthRoom>();
        Set<Maze.Room> visitedRooms = new HashSet<Maze.Room>();

        PriorityQueue<DepthRoom> openRooms = new PriorityQueue<DepthRoom>(1, comparator);

        openRooms.add(new DepthRoom(0, getMaze().getStart(), null));

        while(!openRooms.isEmpty()) {

            DepthRoom prioDepthRoom = openRooms.poll();
            Maze.Room prioRoom = prioDepthRoom.getRoom();

            if (prioRoom.equals(getMaze().getEnd()))
                return prioDepthRoom;

            incrementNodesExpanded();
            closedRooms.add(prioDepthRoom);
            visitedRooms.add(prioRoom);

            for (Maze.Room room : getMaze().adjacentRooms(prioRoom)) {

                if (visitedRooms.contains(room))
                    continue;

                int nextDepth = prioDepthRoom.getDepth() + 1;
                DepthRoom nextDepthRoom = new DepthRoom(nextDepth, room, prioDepthRoom);

                boolean bestInOpen = isBestIn(openRooms, comparator, nextDepthRoom);
                boolean bestInClosed = isBestIn(closedRooms, comparator, nextDepthRoom);

                if (bestInOpen && bestInClosed)
                    openRooms.add(nextDepthRoom);
            }

        }

        throw new MazeException(MazeException.ErrorCode.NO_PATH);
    }

    private boolean isBestIn(
            Iterable<DepthRoom> iterable,
            Comparator<DepthRoom> comparator,
            DepthRoom room) {

        Iterator<DepthRoom> iterator = iterable.iterator();

        while (iterator.hasNext()) {

            DepthRoom nextRoom = iterator.next();

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

}