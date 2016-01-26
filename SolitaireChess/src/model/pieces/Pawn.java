package model.pieces;

import model.Position;
import model.SolitaireChessModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent the Pawn on the board and all it properties
 *
 * @author Emmanuel Olaojo
 */
public class Pawn extends Piece{
    /**
     * @param position  current position of piece
     * @param pieceEnum Enumeration value of Piece
     */
    public Pawn(Position position, PieceEnum pieceEnum) {
        super(position, pieceEnum);
    }

    /**
     * Copy Constructor
     * @param p - Pawn to be copied from
     */
    public Pawn(Pawn p){
        super(p);
    }

    /**
     * Generates the valid moves of each peice
     *
     * @param scm the SolitaireChessModel to work with
     */
    @Override
    public void generateValidMoves(SolitaireChessModel scm) {
        this.validMoves = new HashSet<>();

        Position leftDiag = new Position(this.position.getX()-1, this.position.getY()-1);
        Position rightDiag = new Position(this.position.getX()-1, this.position.getY()+1);

        this.checkAndAdd(leftDiag, scm);
        this.checkAndAdd(rightDiag, scm);
    }

    /**
     *
     * @return Copy of Pawn
     */
    @Override
    public Piece clone() {
        return  new Pawn(this);
    }


}
