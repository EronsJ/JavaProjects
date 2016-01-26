package ptui;

import model.SolitaireChessModel;

import java.util.Observable;
import java.util.Observer;

/**
 * A plain text UI for the game
 *
 * @author Emmanuel Olaojo
 */
public class SolitaireChessPTUI implements Observer{
    private SolitaireChessModel model;
    private static final int DIMENSION = 4;

    /**
     * Constructor takes a file name
     *
     * @param filename the name of the file used to create the model
     */
    public SolitaireChessPTUI(String filename){
        this.model = new SolitaireChessModel(filename);

        this.model.addObserver(this);

        System.out.println("Game File: " + filename);
        this.display();
    }

    /**
     * Gets a String representation of a piece based
     * on it's PieceEnum
     *
     * @param x the x position
     * @param y the y position
     *
     * @return a String representation of the piece
     */
    private String getPiece(int x, int y){
        String piece;

        switch (model.at(x, y)){
            case PAWN: piece = "P ";
                break;
            case KNIGHT: piece = "N ";
                break;
            case BISHOP: piece = "B ";
                break;
            case ROOK: piece = "R ";
                break;
            case QUEEN: piece = "Q ";
                break;
            case KING: piece = "K ";
                break;
            default: piece = "- ";
        }

        return piece;
    }

    /**
     * Displays the current state of the board
     */
    public void display(){
        for(int i=0; i<DIMENSION; i++){
            if(i == 0){
                System.out.print("  ");
                for(int count = 0; count < DIMENSION; count++)
                    System.out.print(count + " ");
                System.out.println();
            }
            System.out.print(i + " ");
            for(int j=0; j<DIMENSION; j++){

                System.out.print(this.getPiece(i, j));
            }
            System.out.println();
        }
    }

    /**
     * Updates the view and checks for winners.
     *
     * @param o an Observable object (SolitaireChessModel)
     * @param error An Error if any.
     */
    public void update(Observable o, Object error){
        if(error != null && (boolean) error) {
            System.out.println("The provided file is invalid");
            return;
        }

        display();
        if(model.isWon()) System.out.println("You won. Congratulations");
    }

    /**
     * @return the model for this view
     */
    public SolitaireChessModel getModel(){
        return this.model;
    }

}
