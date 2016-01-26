package model;

import backtracker.Move;
import model.pieces.*;


/**
 * The Cell class represents each cell
 * on the board.
 *
 * @author Emmanuel Olaojo
 */
public class Cell {
    private Position position;
    private boolean isOccupied;
    private Piece piece;

    /**
     * Creates a new Cell
     *
     * @param piece the piece to be placed on the Cell
     * @param position the Cell's position
     */
    public Cell(Piece piece, Position position){
        this.position = position;
        this.piece = piece;
        this.isOccupied = piece != null;
    }

    /**
     * Mutator for the piece property
     *
     * @param piece the Cell's new Piece
     */
    public void setPiece(Piece piece){
        this.piece = piece;
        this.isOccupied = true;
    }

    public Cell(Cell c) {
        this.position = new Position(c.position);
        this.isOccupied = c.isOccupied();
        try {
            this.piece = c.getPiece().getCopyOfMe(c.getPiece());
        }catch (NullPointerException e){
            this.piece = null;
        }
    }


    /**
     * Accessor for the piece property
     *
     * @return this Cell's Piece
     */
    public Piece getPiece(){
        return this.piece;
    }

    public Position getPosition(){
        return this.position;
    }

    /**
     * Captures the given cell
     *
     * @param cell the cell to capture
     */
    public boolean capture(Cell cell, SolitaireChessModel scm){
        if(!(this.isOccupied && cell.isOccupied())) return false;
        if(!this.piece.isValidMove(cell.position, scm)) return false;

        this.piece.setPosition(cell.position);
        cell.piece = this.piece;
        cell.isOccupied = true;

        this.piece = null;
        this.isOccupied = false;

        return true;
    }

    public boolean uncapture(Move m, Cell endCell){

        if (endCell.piece != null) return false;

        endCell.piece = this.piece;
        endCell.piece.setPosition(endCell.position);
        endCell.isOccupied = true;

        this.piece = m.getPiece();
        this.piece.setPosition(this.position);
        this.isOccupied = true;
        
        return true;
    }

    /**
     * @return isOccupied
     */
    public boolean isOccupied(){
        return this.isOccupied;
    }

    /**
     * @return String representation of a Cell
     */
    public String toString(){
        PieceEnum pieceEnum = (this.piece == null)? PieceEnum.EMPTY : this.piece.getPieceEnum();

       return String.format("%6s, %5s, %6s, %1s", this.position.toString(), this.isOccupied, pieceEnum, "|");
    }
}
