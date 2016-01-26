package model.movement;

import model.Position;
import model.SolitaireChessModel;

import java.util.Set;

/**
 * Generates valid moves in every direction
 *
 * @author Emmanuel Olaojo
 */
public class Moves {
    /**
     * Adds all valid horizontal moves to a Set
     * of valid moves
     *
     * @param start start position
     * @param scm SolitaireChessModel to work with
     * @param validMoves Set of valid moves
     */
    public static void addHorizontalMoves(Position start, SolitaireChessModel scm, Set<Position> validMoves){
        ValidPositions.genPositions(start, scm, validMoves, Directions.RIGHT);
        ValidPositions.genPositions(start, scm, validMoves, Directions.LEFT);
    }

    /**
     * Adds all valid vertical moves to a Set
     * of valid moves
     *
     * @param start start position
     * @param scm SolitaireChessModel to work with
     * @param validMoves Set of valid moves
     */
    public static void addVerticalMoves(Position start, SolitaireChessModel scm, Set<Position> validMoves){
        ValidPositions.genPositions(start, scm, validMoves, Directions.UP);
        ValidPositions.genPositions(start, scm, validMoves, Directions.DOWN);
    }

    /**
     * Adds all valid diagonal moves to a Set
     * of valid moves
     *
     * @param start start position
     * @param scm SolitaireChessModel to work with
     * @param validMoves Set of valid moves
     */
    public static void addDiagonalMoves(Position start, SolitaireChessModel scm, Set<Position> validMoves){
        ValidPositions.genPositions(start, scm, validMoves, Directions.LEFT_UPPER_DIAG);
        ValidPositions.genPositions(start, scm, validMoves, Directions.LEFT_LOWER_DIAG);
        ValidPositions.genPositions(start, scm, validMoves, Directions.RIGHT_UPPER_DIAG);
        ValidPositions.genPositions(start, scm, validMoves, Directions.RIGHT_LOWER_DIAG);
    }

}
