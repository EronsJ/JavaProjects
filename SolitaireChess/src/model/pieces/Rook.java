package model.pieces;

import model.Position;
import model.SolitaireChessModel;
import model.movement.Moves;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent the Rook on the board and all it properties
 *
 * @author Eronmonsele Omiyi
 */
public class Rook extends Piece{

    /**
     * @param position  current position of piece
     * @param pieceEnum Enumeration value of Piece
     */
    public Rook(Position position, PieceEnum pieceEnum) {
        super(position, pieceEnum);
    }

    /**
     * Copy Constructor
     * @param r - rook to be copied
     */
    private Rook(Rook r){
        super(r);
    }
    /**
     * Generates the valid moves of each piece
     *
     * @param scm The SolitaireChessModel
     */
    @Override
    public void generateValidMoves(SolitaireChessModel scm) {
        this.validMoves = new HashSet<>();

        Moves.addHorizontalMoves(this.position, scm, this.validMoves);
        Moves.addVerticalMoves(this.position, scm, this.validMoves);
    }

    /**
     * Used to access Copy Constructor
     * @return
     */
    @Override
    public Piece clone() {
        return new Rook(this);
    }

}
