package model.pieces;

import model.Position;
import model.SolitaireChessModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent the Knight on the board and all it properties
 *
 * @author Eronmonsele Omiyi
 */
public class Knight extends Piece {
    /**
     * @param position  current position of piece
     * @param pieceEnum Enumeration value of Piece
     */
    public Knight(Position position, PieceEnum pieceEnum) {
        super(position, pieceEnum);
    }

    /**
     * Copy constructor
     * @param k - Knight to be copied from
     */
    private Knight(Knight k){
        super(k);
    }

    /**
     * Generates the valid moves of each peice
     *
     * @param scm The SolitaireChessModel
     */
    @Override
    public void generateValidMoves(SolitaireChessModel scm) {
        this.validMoves = new HashSet<>();
        Position next = new Position(this.position);

        //OUTER UP ROW
        this.checkAndAdd(new Position(next.getX() -2 , next.getY() - 1), scm); //LEFT
        this.checkAndAdd(new Position(next.getX() -2 , next.getY() + 1), scm); //RIGHT

        //INNER UP ROW
        this.checkAndAdd(new Position(next.getX() -1 , next.getY() - 2), scm); //LEFT
        this.checkAndAdd(new Position(next.getX() -1 , next.getY() + 2), scm); //RIGHT

        //INNER DOWN ROW
        this.checkAndAdd(new Position(next.getX() +1 , next.getY() - 2), scm); //LEFT
        this.checkAndAdd(new Position(next.getX() +1 , next.getY() + 2), scm); //RIGHT

        //OUTER DOWN ROW
        this.checkAndAdd(new Position(next.getX() +2 , next.getY() - 1), scm); //LEFT
        this.checkAndAdd(new Position(next.getX() +2 , next.getY() + 1), scm); //RIGHT
    }

    /**
     * @return Copy of Knight
     */
    @Override
    public Piece clone() {
        return new Knight(this);
    }


}
