package net.etf.project.model.rental;

/**
 * Represents a location in a 2D coordinate system.
 * This class is used to define the x and y coordinates of a location.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class Location
{
    /** The x-coordinate of the location. */
    private int x;

    /** The y-coordinate of the location. */
    private int y;

    /**
     * Constructs a new Location with the specified coordinates.
     *
     * @param x The x-coordinate of the location.
     * @param y The y-coordinate of the location.
     */
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the location.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the location.
     *
     * @param x The new x-coordinate to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the location.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the location.
     *
     * @param y The new y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns a string representation of the location in the format (x, y).
     *
     * @return A string representing the location.
     */
    @Override
    public String toString()
    {
        return "(" + x + " , " + y + ")";
    }
}