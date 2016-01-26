package model.pieces;

import model.Position;
import model.SolitaireChessModel;
import model.movement.Moves;
import java.util.HashSet;


/**
 * Represent the Bishop on the board and all it properties
 *
 * @author Emmanuel Olaojo
 */
public class Bishop extends Piece{

    /**
     * @param position  current position of piece
     * @param pieceEnum Enumeration value of Piece
     */
    public Bishop(Position position, PieceEnum pieceEnum) {
        super(position, pieceEnum);
    }

    /**
     * Copy Constructor
     * @param b - Bishop to be copied from
     */
    private Bishop(Bishop b){
        super(b);
    }
    /**
     * Generates the valid moves of each piece
     *
     * @param scm the SolitaireChessModel to work with
     */
    @Override
    public void generateValidMoves(SolitaireChessModel scm) {
        this.validMoves = new HashSet<>();

        Moves.addDiagonalMoves(this.getPosition(), scm, this.validMoves);
    }

    /**
     * @return A copy of Bishop Object
     */
    @Override
    public Piece clone() {
        return new Bishop(this);
    }

}
