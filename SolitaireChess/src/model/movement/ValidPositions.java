package model.movement;

import model.Coordinate;
import model.Position;
import model.SolitaireChessModel;

import java.util.Set;

/**
 * Generates all valid Positions in a given direction
 *
 * @author Emmanuel Olaojo
 * @author Eronmonsele Omiyi
 */
public class ValidPositions{

    /**
     * Gets all the possible position of a piece
     *
     * @param start - The piece that needs the information Piece
     * @param scm - Contains the cells on which the pieces move (The model)
     * @param dir - Directions
     */
    public static void genPositions(Position start, SolitaireChessModel scm, Set<Position> validMoves, Directions dir){
        Position nextPos = new Position(start);

        while(nextPos.isValid()){
            switch(dir){
                case LEFT:
                    nextPos.decrement(Coordinate.Y);
                    break;

                case RIGHT:nextPos.increment(Coordinate.Y);
                    break;

                case UP:
                    nextPos.decrement(Coordinate.X);
                    break;

                case DOWN: nextPos.increment(Coordinate.X);
                    break;

                case LEFT_UPPER_DIAG:
                    nextPos.decrement(Coordinate.X);
                    nextPos.decrement(Coordinate.Y);
                    break;

                case RIGHT_LOWER_DIAG:
                    nextPos.increment(Coordinate.X);
                    nextPos.increment(Coordinate.Y);
                    break;

                case RIGHT_UPPER_DIAG:
                    nextPos.decrement(Coordinate.X);
                    nextPos.increment(Coordinate.Y);
                    break;

                case LEFT_LOWER_DIAG:
                    nextPos.increment(Coordinate.X);
                    nextPos.decrement(Coordinate.Y);
                    break;
            }

            //add the move and break if it's valid
            if(scm.canCapture(start, nextPos)){
                validMoves.add(nextPos);
                break;
            }
        }
    }
}
