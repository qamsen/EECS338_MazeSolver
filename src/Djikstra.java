import java.util.Comparator;
import java.util.HashMap;
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

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((room == null) ? 0 : room.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DepthRoom other = (DepthRoom) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (room == null) {
                if (other.room != null)
                    return false;
            } else if (!room.equals(other.room))
                return false;
            return true;
        }

        private Djikstra getOuterType() {
            return Djikstra.this;
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

        return list;
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
        HashMap<DepthRoom, DepthRoom> map = new HashMap<DepthRoom, DepthRoom>();
        Set<Maze.Room> visitedRooms = new HashSet<Maze.Room>();

        for (Maze.Room room : getMaze().getRooms()) {
            DepthRoom depthRoom = new DepthRoom(Integer.MAX_VALUE, room, null);
            map.put(depthRoom, depthRoom);
        }

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

                    int newDepth = newDepthRoom.depth;
                    int oldDepth = map.get(newDepthRoom).depth;

                    if (newDepth < oldDepth) {
                        map.put(newDepthRoom, newDepthRoom);
                        queue.add(newDepthRoom);
                    }

                }

            }

        }

        throw new MazeException(MazeException.ErrorCode.NO_PATH);
    }

    @Override
    public String toString() {
        return "Dijkstra:\n\n" + super.toString();
    }

}
