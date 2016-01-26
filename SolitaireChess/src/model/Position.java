package model;

/**
 * The Position class represents a row, col or x,y
 * position on a grid.
 *
 * @author Emmanuel Olaojo
 * @author Eronmonsele Omiyi
 */
public class Position {
    private int x;
    private int y;
    public static final int DIM = 4;

    public Position(){}

    /**
     * The constructor creates a new position
     * from the given x, y values
     *
     * @param x the row
     * @param y the column
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor: Creates a new Position
     * from an existing Position
     *
     * @param p The position to be copied
     */
    public Position(Position p){
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Sets the x value of this position
     * @param x the new x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y value of this position
     * @param y the new y value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return The x value
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y value
     */
    public int getY() {
        return y;
    }

    /**
     * @return are the coordinates are out of bounds?
     */
    public boolean isValid(){
        return !(this.x < 0 || this.y < 0 || this.x > DIM - 1 || this.y > DIM - 1);
    }


    /**
     * increments the value of the given coordinate
     *
     * @param coordinate coordinate to increment
     */
    public void increment(Coordinate coordinate){
        switch(coordinate){
            case X: x++;
                break;
            case Y: y++;
                break;
        }
    }

    /**
     * decrements the value of the given coordinate
     *
     * @param coordinate coordinate to increment
     */
    public void decrement(Coordinate coordinate){
        switch(coordinate){
            case X: x--;
                break;
            case Y: y--;
                break;
        }
    }

    /**
     * Checks if this Position is equal to another Position
     *
     * @param o the other position
     * @return true if they're equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return this.x == position.x && this.y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    /**
     * @return A string representation of this position
     */
    public String toString(){
        return "(" + this.x + ", " + this.y +")";
    }
}
