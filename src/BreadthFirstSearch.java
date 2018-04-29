import java.util.HashSet;
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
	 * Run on a Maze object, finds the path (if one exists) 
	 * from A to B in a Maze using Depth-First-Search
	 */
	@Override
	public List<Maze.Room> path() throws MazeException {
		
		boolean solved = false;
		Set<Maze.Room> visited = new HashSet<Maze.Room>();
		Maze.Room start = getMaze().getStart();
		ArrayDeque<Maze.Room> q = new ArrayDeque<Maze.Room>();
		q.add(start);
		
		// Loops through every element(Room) in q(Que) evaluating each node until every node is evaluated or end(B) is found
		while(!q.isEmpty() && !solved) {			
			Maze.Room x = q.remove();	
			
			// Checks if B(the goal) was found
			if(x == getMaze().getEnd()){
				//setSolved(true); // setSolved() is private
				solved = true;
			}
			// Node has NOT been visited
			if(!visited.contains(x) && !solved) {
				
				visited.add(x);
				Set<Maze.Room> adjacent = getMaze().adjacentRooms(x);
				
				// Loops through all neighbors of x and add all unvisited neighbors to q(the Que)
				for(Maze.Room adjacentRoom : adjacent) {
					
					// Adds adjacentRoom to q(the Que) if it has NOT been visited
					if(!visited.contains(adjacentRoom)) {
						q.add(adjacentRoom);
					}
				}				
			}			
		}
		
		return null;
	}
}
