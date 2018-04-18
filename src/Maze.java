import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Maze {

    public static class Room {

        private final Point location;
        private final Set<Room> adjacentRooms;

        public Room(Point location) {
            this.location = location;
            this.adjacentRooms = new HashSet<Room>();
        }

        /**
         * @return the location of the room in the maze
         */
        public Point getLocation() {
            return location;
        }
        private Set<Room> getAdjacentRooms() {
            return adjacentRooms;
        }

        private void addConnection(Room adjacentRoom) {
            adjacentRooms.add(adjacentRoom);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((location == null) ? 0 : location.hashCode());
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
            Room other = (Room) obj;
            if (location == null) {
                if (other.location != null)
                    return false;
            } else if (!location.equals(other.location))
                return false;
            return true;
        }

    }

    private final Room start;
    private final Room end;
    private final Dimension mazeSize;
    private final HashMap<Room, Room> rooms;

    private Maze(Room start, Room end, Dimension mazeSize, HashMap<Room, Room> rooms) {
        this.start = start;
        this.end = end;
        this.mazeSize = mazeSize;
        this.rooms = rooms;
    }

    /**
     * @return the start room of the maze
     */
    public Room getStart() {
        return start;
    }

    /**
     * @return the end room of the maze
     */
    public Room getEnd() {
        return end;
    }

    /**
     * @return the dimensions of the maze
     */
    public Dimension getMazeSize() {
        return mazeSize;
    }

    /**
     * @return the set of all rooms in this maze
     */
    public Set<Room> getRooms() {
        return new HashSet<Room>(rooms.values());
    }

    /**
     * @param room  a given room in the maze
     * @return      the set of all rooms adjacent to the given room
     */
    public Set<Room> adjacentRooms(Room room) {

        Set<Room> adjacentRooms = new HashSet<Room>();

        // Maintains the connections in the Maze
        for (Room adjacentRoom : room.getAdjacentRooms()) {
            adjacentRooms.add(rooms.get(adjacentRoom));
        }

        return adjacentRooms;
    }

    /**
     * Builds a maze given a 2d boolean matrix, where true implies a room and
     * false implies a wall.
     *
     * @param maze        matrix that represents the maze
     * @param startPoint  start point of the maze
     * @param endPoint    end point of the maze
     * @return            the maze as a graph
     */
    static Maze buildMaze(
            boolean[][] maze,
            Point startPoint,
            Point endPoint
            ) {

        // Preconditions: non-empty maze, start & end point in the maze
        assert maze != null;
        assert maze[0] != null;

        HashMap<Room, Room> rooms = new HashMap<Room, Room>();
        Dimension mazeSize = new Dimension(maze.length, maze[0].length);

        Room start;
        Room end;

        // Iterates across the maze
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {

                // True when there exists a room at i, j
                if (maze[i][j])
                    buildRoom(maze, new Point(i, j), rooms);
            }
        }

        start = buildRoom(maze, startPoint, rooms);
        end = buildRoom(maze, endPoint, rooms);

        return new Maze(start, end, mazeSize, rooms);
    }

    private static Room buildRoom(
            boolean[][] maze,
            Point roomPoint,
            HashMap<Room, Room> rooms
            ) {

        assert isIn(maze, roomPoint);

        Room room = new Room(roomPoint);

        if (rooms.containsKey(room))
            return rooms.get(room);

        for (Point adjacentPoint : adjacentPoints(roomPoint)) {

            if (isRoom(maze, adjacentPoint)) {
                Room adjacentRoom = new Room(adjacentPoint);
                room.addConnection(adjacentRoom);
            }
        }

        rooms.put(room, room);
        return room;
    }

    private static boolean isRoom(boolean[][] maze, Point point) {

        if (isIn(maze, point)) {
            return maze[point.x][point.y];
        } else {
            return false;
        }

    }

    private static boolean isIn(boolean[][] maze, Point point) {

        boolean inRangeX = 0 <= point.x && point.x < maze.length;
        boolean inRangeY = 0 <= point.y && point.y < maze[0].length;

        return inRangeX && inRangeY;
    }

    private static Set<Point> adjacentPoints(Point point) {

        Set<Point> adjacentPoints = new HashSet<Point>();

        // Adds each adjacent point
        adjacentPoints.add(new Point(point.x + 1, point.y));
        adjacentPoints.add(new Point(point.x - 1, point.y));
        adjacentPoints.add(new Point(point.x, point.y + 1));
        adjacentPoints.add(new Point(point.x, point.y - 1));

        return adjacentPoints;
    }

}