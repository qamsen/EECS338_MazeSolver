import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import java.util.ArrayDeque;
import java.util.HashMap;

public class BreadthFirstSearch extends PathFinder{
	
	BreadthFirstSearch(Maze maze) {
		super(maze);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Object that connects rooms as they are found in order to return the final path
	 */
	class MazePair {

        MazePair previousPair;
        Maze.Room currentRoom;

        public MazePair(MazePair previousPair, Maze.Room currentRoom) {
            this.currentRoom = currentRoom;
            this.previousPair = previousPair;
        }

    }

	/*
	 * Run on a Maze object, finds the path (if one exists) 
	 * from A to B in a Maze using Depth-First-Search
	 */
	@Override
	public List<Maze.Room> path() throws MazeException {
		
		MazePair pair = breadthFirstSearch();
		List<Maze.Room> list = new LinkedList<Maze.Room>();

        do {
            list.add(0, pair.currentRoom);
            pair = pair.previousPair;
        } while (pair != null);

        return list;
	}
	
	public MazePair breadthFirstSearch() throws MazeException{
		
		Set<Maze.Room> visited = new HashSet<Maze.Room>();
		MazePair start = new MazePair(null, getMaze().getStart());
		ArrayDeque<MazePair> q = new ArrayDeque<MazePair>();
		q.add(start);
		
		// Loops through every element(Room) in q(Que) evaluating each node until every node is evaluated or end(B) is found
		while(!q.isEmpty()) {			
			MazePair x = q.remove();
			this.incrementNodesExpanded();
			
			// Checks if B(the goal) was found
			if(x.currentRoom == (getMaze().getEnd())){
				return x;
			}
			
			visited.add(x.currentRoom);
			
			// Loops through all neighbors of x and add all unvisited neighbors to q(the Que)
			for(Maze.Room adjacentRoom : getMaze().adjacentRooms(x.currentRoom)) {
				// Adds adjacentRoom to q(the Que) if it has NOT been visited
				if(!visited.contains(adjacentRoom)) {
					MazePair nextPair = new MazePair(x, adjacentRoom);
                    q.add(nextPair);
				}
			}			
		}
		throw new MazeException(MazeException.ErrorCode.NO_PATH);
	}
	
	@Override
	public String toString() {
		return "Breadth First Search" + super.toString();
	}
}
