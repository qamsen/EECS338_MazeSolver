import java.util.List;

public class MazeException extends Exception {

    private static final long serialVersionUID = 1L;

    public enum ErrorCode {
        INVALID_SIZE ("Maze is not an NxM matrix"),
        UNEXPECTED_CHARACTER ("Maze contains an unexpected character"),
        INVALID_START ("Maze does not contain exactly one start point"),
        INVALID_END ("Maze does not contain exactly one end point"),
        NO_PATH ("No path from start to end exists"),
        NO_MAZE ("No maze provided");

        private String errorMessage;

        private ErrorCode (String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public String toString() {
            return errorMessage;
        }
    }

    private final ErrorCode errorCode;

    public MazeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.toString();
    }

    public static void verifyMazeContents(List<String> mazeRows) throws MazeException {

        String pattern = "[01AB]*";
        int startPoints = 0;
        int endPoints = 0;

        for (String row : mazeRows) {
            startPoints += countOccurrences(row, 'A');
            endPoints += countOccurrences(row, 'B');

            if (!row.matches(pattern))
                throw new MazeException(ErrorCode.UNEXPECTED_CHARACTER);
        }

        if (startPoints != 1)
            throw new MazeException(ErrorCode.INVALID_START);

        if (endPoints != 1)
            throw new MazeException(ErrorCode.INVALID_END);
    }

    private static int countOccurrences(String str, char c) {

        int occurrences = 0;

        char[] asChar = str.toCharArray();

        for (int i = 0; i < asChar.length; i++) {

            if (asChar[i] == c)
                occurrences++;
        }

        return occurrences;
    }

    public static void verifyMazeSize(List<String> mazeRows)
            throws MazeException {

        if (mazeRows.size() == 0)
            throw new MazeException(ErrorCode.NO_MAZE);
        else
            verifyUniformLength(mazeRows);
    }

    private static void verifyUniformLength(List<String> mazeRows)
            throws MazeException {

        int rowLength = mazeRows.get(0).length();

        for (String row : mazeRows) {

            if (rowLength != row.length())
                throw new MazeException(ErrorCode.INVALID_SIZE);
        }
    }

}