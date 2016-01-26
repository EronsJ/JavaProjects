package backtracker;

import model.Position;
import model.pieces.Piece;


public class Move {
    private Piece piece;
    private Position start;
    private Position end;

    /**
     *
     * @param p
     * @param start
     * @param end
     */
    public Move(Piece p, Position start, Position end){
        this.piece = p;
        this.start = start;
        this.end = end;
    }

    public Move(Move p){
        this.start = p.start;
        this.end = p.end;
        this.piece = p.piece;
    }

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getEnd() {
        return end;
    }

    public Position getStart() {
        return start;
    }

    public Piece getPiece() {
        return piece;
    }

    public String toString(){
        return "Move "+this.piece.getPieceEnum()+" From "+this.start+" to "+this.end+"!";
    }
}
