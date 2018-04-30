import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Djikstra extends PathFinder{

    private class DepthRoom {

        int depth;
        Maze.Room room;
        DepthRoom parent;

        DepthRoom(int depth, Maze.Room room, DepthRoom parent) {

            this.depth = depth;
            this.room = room;
            this.parent = parent;
        }

    }

    Djikstra(Maze maze) {
        super(maze);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Maze.Room> path() throws MazeException {

        DepthRoom depthRoom = dijkstra();
        List<Maze.Room> list = new LinkedList<Maze.Room>();

        do {
            list.add(depthRoom.room);
            depthRoom = depthRoom.parent;
        } while (depthRoom.parent != null);

        return null;
    }

    DepthRoom dijkstra() throws MazeException {

        DepthRoom startRoom = new DepthRoom(0, getMaze().getStart(), null);

        Comparator<DepthRoom> comparator = new Comparator<DepthRoom>() {

            @Override
            public int compare(DepthRoom arg0, DepthRoom arg1) {
                return arg0.depth - arg1.depth;
            }

        };
        PriorityQueue<DepthRoom> queue = new PriorityQueue<DepthRoom>(1, comparator);
        Set<Maze.Room> visitedRooms = new HashSet<Maze.Room>();
        queue.add(startRoom);

        while (!queue.isEmpty()) {

            DepthRoom nextDepthRoom = queue.poll();
            visitedRooms.add(nextDepthRoom.room);
            incrementNodesExpanded();

            if (nextDepthRoom.room.equals(getMaze().getEnd()))
                return nextDepthRoom;

            for (Maze.Room room : getMaze().adjacentRooms(nextDepthRoom.room)) {

                if (!visitedRooms.contains(room)) {

                    DepthRoom newDepthRoom = new DepthRoom(nextDepthRoom.depth + 1, room, nextDepthRoom);
                    queue.add(newDepthRoom);
                }

            }
        }

        throw new MazeException(MazeException.ErrorCode.NO_PATH);
    }

}
