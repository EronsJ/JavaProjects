package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.Position;
import model.pieces.PieceEnum;

/**
 * This class represents every button(ChessPiece)
 * on the board and contains methods that make my
 * life less miserable
 *
 * @author Emmanuel Olaojo
 */
public class ChessPiece extends Button{
    protected Position position;
    private Background bg;
    private boolean occupied = false;
    private PieceEnum pieceEnum;
    private static final int BG_SIZE = 100;
    private final BackgroundSize B_SIZE= new BackgroundSize(BG_SIZE, BG_SIZE, true, true, true, true);

    /**
     * Create a new ChessPiece with just
     * coordinates
     *
     * @param x the x position
     * @param y the y position
     */
    public ChessPiece(int x, int y, PieceEnum pieceEnum){
        this.position = new Position(x, y);
        this.pieceEnum = pieceEnum;
    }

    /**
     * Copy constructor; used to save the state
     * of a ChessPiece before changing it.
     *
     * @param cp The ChessPiece to copy
     */
    public ChessPiece(ChessPiece cp){
        this.position = new Position(cp.position);
        this.bg = cp.bg;
        this.pieceEnum = cp.pieceEnum;
    }

    /**
     * Sets the background image to the given
     * Image object.
     *
     * @param img - Image to set background image to
     */
    public void setBg(Image img){
        this.bg = new Background(new BackgroundImage(
                img
                ,BackgroundRepeat.NO_REPEAT
                ,BackgroundRepeat.NO_REPEAT
                ,BackgroundPosition.CENTER
                ,B_SIZE));

        this.setBackground(this.bg);
    }

    /**
     * Sets the background of this ChessPiece
     * to what ever the background of the passed
     * in ChessPiece is.
     *
     * @param cp - passed in ChessPiece
     */
    public void setBg(ChessPiece cp){
        this.bg = cp.bg;
        this.setBackground(this.bg);
    }

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    public boolean isOccupied(){
        return this.occupied;
    }

    public PieceEnum getPieceEnum(){
        return this.pieceEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessPiece that = (ChessPiece) o;

        return !(position != null ? !position.equals(that.position) : that.position != null);
    }

    public String toString(){
        return this.position.toString();
    }
}
