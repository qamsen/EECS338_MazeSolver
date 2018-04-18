import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    // More specific exception
    public static Maze parseMaze(String fileName) throws Exception {

        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        List<String> fileLines = new ArrayList<String>();

        while (reader.ready())
            fileLines.add(reader.readLine());

        return toMaze(fileLines);
    }

    // More specific exception
    private static Maze toMaze(List<String> input) throws Exception {
        // verify string lengths
        // verify string contents

        /* Set<Character> validChars = new HashSet();

        validChars.add('0');
        validChars.add('1');
        validChars.add('A');
        validChars.add('B'); */

        int columnCount = input.get(0).length();
        int rowCount = input.size();

        char[][] rows = toCharMatrix(input);
        boolean[][] maze = new boolean[rowCount][columnCount];

        Point startPoint = null;
        Point endPoint = null;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {

                // Checks whether the cell represents a wall or a room
                if (rows[i][j] == '0')
                    maze[i][j] = false;
                else
                    maze[i][j] = true;

                // 'A' implies start point
                if (rows[i][j] == 'A')
                    startPoint = new Point(i, j);

                // 'B' implies end point
                if (rows[i][j] == 'B')
                    endPoint = new Point(i, j);
            }
        }

        return Maze.buildMaze(maze, startPoint, endPoint);
    }

    private static char[][] toCharMatrix(List<String> strings) {

        char[][] charMatrix = new char[strings.size()][];

        for (int i = 0; i < charMatrix.length; i++) {
            String s = strings.get(i);
            charMatrix[i] = s.toCharArray();
        }

        return charMatrix;
    }

    public static List<String> deMaze(Maze maze, List<Maze.Room> solution) {

        int row = maze.getMazeSize().height;
        int column = maze.getMazeSize().width;

        char[][] charMaze = initializeCharMaze(row, column);
        addRooms(charMaze, maze);
        addSolutionPath(charMaze, solution);

        // Adds start and end
        int startI = maze.getStart().getLocation().x;
        int startJ = maze.getStart().getLocation().y;
        charMaze[startI][startJ] = 'A';

        int endI = maze.getEnd().getLocation().x;
        int endJ = maze.getEnd().getLocation().y;
        charMaze[endI][endJ] = 'B';

        return matrixToList(charMaze);
    }

    private static List<String> matrixToList(char[][] charMatrix) {

        List<String> list = new ArrayList<String>();

        for (char[] charList : charMatrix) {
            list.add(new String(charList));
        }

        return list;
    }

    private static void addSolutionPath(char[][] charMaze, List<Maze.Room> solution) {

        for (Maze.Room room : solution) {

            int i = room.getLocation().x;
            int j = room.getLocation().y;

            charMaze[i][j] = 'X';
        }
    }

    private static void addRooms(char[][] charMaze, Maze maze) {

        for (Maze.Room room : maze.getRooms()) {

            int i = room.getLocation().x;
            int j = room.getLocation().y;

            charMaze[i][j] = '1';
        }
    }

    private static char[][] initializeCharMaze(int row, int column) {

        char[][] chars = new char[row][column];

        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[0].length; j++) {
                chars[i][j] = '0';
            }
        }

        return chars;
    }

}