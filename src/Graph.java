import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Maze {

    private final Room start;
    private final Room end;
    private final Dimension mazeSize;
    private final Set<Room> rooms;

    static class Room {

        private final Point location;
        private final Set<Room> adjacentRooms;

        private Room(Point location) {
            this.location = location;
            this.adjacentRooms = new HashSet<Room>();
        }

        Set<Room> getAdjacentRooms() {
            return adjacentRooms;
        }

        public Point getLocation() {
            return location;
        }

        void addAdjacentRoom(Room adjacentRoom) {
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

    private Maze(Room start, Room end, Dimension mazeSize, Set<Room> rooms) {
        this.start = start;
        this.end = end;
        this.mazeSize = mazeSize;
        this.rooms = rooms;
    }

    // TODO: Implement the Maze builder
    static Maze buildMaze(boolean[][] maze, Point startPoint, Point endPoint) {

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
                if (maze[i][j]) {
                }
            }
        }

        return null;
    }

    private static Room buildRoom(boolean[][] maze, Point point, HashMap<Room, Room> rooms) {

        assert isIn(maze, point);

        return null;
    }

    static private boolean isIn(boolean[][] maze, Point point) {

        boolean inRangeX = 0 <= point.x && point.x < maze.length;
        boolean inRangeY = 0 <= point.y && point.y < maze[0].length;

        return inRangeX && inRangeY;
    }

    Set<Point> adjacentPoints(Point point) {

        Set<Point> adjacentPoints = new HashSet<Point>();

        // Adds each adjacent point
        adjacentPoints.add(new Point(point.x + 1, point.y));
        adjacentPoints.add(new Point(point.x - 1, point.y));
        adjacentPoints.add(new Point(point.x, point.y + 1));
        adjacentPoints.add(new Point(point.x, point.y - 1));

        return adjacentPoints;
    }

}