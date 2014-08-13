package cludo.util;

import java.awt.Point;

import cludo.gui.CludoCanvas;

/**
 * Location a point in space that some object might 
 * ocupy.
 * @author Shane Brewer.
 *
 */
public class Location {
	public final int x;
	public final int y;
	
	/**
	 * Constructs a Location from the x and y from the posions 
	 * on the board most likely from the spawn locations.
	 * @param x - x position in the array.
	 * @param y - y position in the array.
	 */
	public Location(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs a Location from a point that is passed in
	 * this uses a constent squareSize that is final to 
	 * work out where in the board the player will be.
	 * @param point - point that was passed mostlikly a click.
	 */
	public Location(Point point){
		x = point.x/CludoCanvas.squareSize;
		y = point.y/CludoCanvas.squareSize;
	}

	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + "]";
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	
}
