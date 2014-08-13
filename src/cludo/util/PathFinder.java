package cludo.util;

import java.util.*;

/**
 * PathFinder class finds the shortest path from a point to another point.
 * @author Shane Brewer
 *
 */
public class PathFinder {

	/**
	 * Implementation of A*
	 * @param to - movment to this location
	 * @param from - movment from this location
	 * @return - returns a list of locations.
	 */
	public List<Location> findPath(Location to, Location from) {
		if (!Move.canMoveHere(to) || !Move.canMoveHere(from)){return null;}
		PriorityQueue<PathFinderNode> fringe = new PriorityQueue<PathFinderNode>();
		Stack<Location> visited = new Stack<Location>();
		fringe.offer(new PathFinderNode(to, null, 0, estimate(to, from)));
		PathFinderNode node = null;
		while (!fringe.isEmpty()) {
			node = fringe.poll();
			if (!visited.contains(node.location)) {
				visited.push(node.location);
				if (node.location.equals(from)) {
					List<Location> test = constructList(node);
					return constructList(node);
				}
				List<Location> temp = new ArrayList<Location>();
				temp.add(new Location(node.location.x + 1, node.location.y));
				temp.add(new Location(node.location.x - 1, node.location.y));
				temp.add(new Location(node.location.x, node.location.y + 1));
				temp.add(new Location(node.location.x, node.location.y - 1));
				for (Location l : temp) {
					if (!visited.contains(l) && Move.canMoveHere(l, node.location)) {
						fringe.offer(new PathFinderNode(l, node,
								node.costToHere+1, node.costToHere
										+ estimate(l, from)));
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Makes a list from a place to a place based on a path
	 * @param node - the end node of the path
	 * @return - returns the list 0 being the spot the player is.
	 */
	private List<Location> constructList(PathFinderNode node){
		List<Location> locationList = new ArrayList<Location>();
		while (node != null){
			locationList.add(0, node.location);
			node = node.preivous;
		}
		return locationList;
	}
	
	/**
	 * Used for the A* huristic 
	 * @param from - location they are moving from 
	 * @param to - location that is the end destination.
	 * @return - returns a good estimate fro the distance.
	 */
	private double estimate(Location from, Location to) {
		return Math.sqrt(Math.pow(from.x - to.x, 2)
				+ Math.pow(from.y - to.y, 2));
	}

	/**
	 * Node that is used in the path finding aguritum
	 * Must be comarable, store the location, a previous node,
	 *  costToHere , and totalDistance which is the cost + huristic.
	 * @author Shane Brewer
	 *
	 */
	private class PathFinderNode implements Comparable<PathFinderNode> {

		public PathFinderNode(Location location, PathFinderNode preivous,
				double costToHere, double totalDistance) {
			super();
			this.location = location;
			this.preivous = preivous;
			this.costToHere = costToHere;
			this.totalDistance = totalDistance;
		}

		public Location location;
		public PathFinderNode preivous;
		public double costToHere;
		public double totalDistance;

		@Override
		public int compareTo(PathFinderNode o) {
			if (totalDistance - o.totalDistance < 0) return -1;
			if (totalDistance - o.totalDistance > 0) return 1;
			return 0;
		}

	}
}
