package model.pieces;

import model.Position;
import model.SolitaireChessModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent the King on the board and all it properties
 *
 * @author Eronmonsele Omiyi
 */
public class King extends Piece {
    /**
     * @param position  current position of piece
     * @param pieceEnum Enumeration value of Piece
     */
    public King(Position position, PieceEnum pieceEnum) {
        super(position, pieceEnum);
    }

    /**
     * Copy Constructor
     * @param k - King to be copied from
     */
    private King(King k){
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

        this.checkAndAdd(new Position(next.getX(), next.getY() + 1), scm); //EAST
        this.checkAndAdd(new Position(next.getX(), next.getY() - 1), scm); //WEST
        this.checkAndAdd(new Position(next.getX() - 1, next.getY()), scm); //NORTH
        this.checkAndAdd(new Position(next.getX() - 1, next.getY() + 1), scm); //NORTH-EAST
        this.checkAndAdd(new Position(next.getX() - 1, next.getY() - 1), scm); //NORTH-WEST
        this.checkAndAdd(new Position(next.getX() + 1, next.getY()), scm); //SOUTH
        this.checkAndAdd(new Position(next.getX() + 1, next.getY() + 1), scm); //SOUTH-EAST
        this.checkAndAdd(new Position(next.getX() + 1, next.getY() - 1), scm); //SOUTH-WEST
    }

    /**
     * @return A copy of a King Object
     */
    @Override
    public Piece clone() {
        return new King(this);
    }

}
