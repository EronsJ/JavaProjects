package model.pieces;

import model.Position;
import model.SolitaireChessModel;
import model.movement.Moves;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Represent the Queen on the board and all it properties
 *
 * @author Emmanuel Olaojo
 */
public class Queen extends Piece {
    /**
     * @param position  current position of piece
     * @param pieceEnum Enumeration value of Piece
     */
    public Queen(Position position, PieceEnum pieceEnum) {
        super(position, pieceEnum);
    }

    /**
     * Copy Constructor
     * @param q - queen to be copied
     */
    private Queen(Queen q){
        super(q);
    }
    /**
     * Generates the valid moves of each peice
     *
     * @param scm SolitaireChessModel to work with
     */
    @Override
    public void generateValidMoves(SolitaireChessModel scm) {
        this.validMoves = new HashSet<>();

        Moves.addDiagonalMoves(this.position, scm, this.validMoves);
        Moves.addHorizontalMoves(this.position, scm, this.validMoves);
        Moves.addVerticalMoves(this.position, scm, this.validMoves);
    }

    /**
     * @return copy of Queen Object
     */
    @Override
    public Piece clone() {

        return new Queen(this);
    }


}
