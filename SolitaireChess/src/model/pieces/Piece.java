package model.pieces;

import model.Cell;
import model.Position;
import model.SolitaireChessModel;
import sun.java2d.Disposer;

import java.util.HashSet;
import java.util.Set;

/**
 * The Piece class represents a piece on the
 * board.
 *
 * @author Emmanuel Olaojo
 * @author Eronmonsele Matthew Omiyi
 */
public abstract class Piece{
    protected PieceEnum pieceEnum;
    protected Position position;
    protected Set<Position> validMoves = null;

    /**
     * Creates a piece with a Position and a PieceEnum
     *
     * @param position current position of piece
     * @param pieceEnum Enumeration value of Piece
     */
    public Piece(Position position, PieceEnum pieceEnum) {
        this.pieceEnum = pieceEnum;
        this.position = position;
    }


    public Piece(Piece p){
        this.pieceEnum = p.pieceEnum;
        this.position = new Position(p.position);
        try {
            this.validMoves = new HashSet<>(p.validMoves);
        }catch (NullPointerException e){
            this.validMoves = new HashSet<>();
        }
    }

    public Piece getCopyOfMe(Piece thisPiece){
        for (PieceEnum p : PieceEnum.values()) {
            if (p == thisPiece.pieceEnum) {
                  return thisPiece.clone();
            }
        }
        return null;
    }

    public abstract Piece clone();

    /**
     * Set the position of piece on board
     *
     * @param p - Position to be moved to
     */
    public void setPosition(Position p){
        this.position = p;
    }

    /**
     * Gets the Piece Position
     *
     * @return current position of piece
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Get the Enum value of the piece
     *
     * @return Enum representation of piece
     */
    public PieceEnum getPieceEnum(){
        return this.pieceEnum;
    }

    /**
     * @return setOfValidMoves
     */
    public Set<Position> getValidMoves() {
        return validMoves;
    }

    /**
     * Checks if a given position is in the Set of
     * valid moves
     *
     * @param position position to look up
     *
     * @return true if valid, false otherwise.
     */
    public boolean isValidMove(Position position, SolitaireChessModel scm){
        this.generateValidMoves(scm);
        return this.validMoves != null  && this.validMoves.contains(position);
    }

    /**
     * Adds a given position to the list of valid moves if
     * conditions defined in scm.canCapture are met
     *
     * @param position position to check
     * @param scm the SolitaireChessModel to work with
     */
    public void checkAndAdd(Position position, SolitaireChessModel scm){
        if(scm.canCapture(this.position, position)) this.validMoves.add(position);
    }

    /**
     * Generates the valid moves of each piece
     *
     * @param scm the SolitaireChessModel to work with
     */
    public abstract void generateValidMoves(SolitaireChessModel scm);

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(! (o instanceof Piece )) return false;
        Piece secPiece = (Piece) o;
        return secPiece.getPieceEnum() == this.getPieceEnum() && secPiece.getPosition().equals(this.getPosition());
    }

}
