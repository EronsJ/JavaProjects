package model;

import backtracker.BackTracker;
import backtracker.ModelConfiguration;
import backtracker.Move;
import model.pieces.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class contains the Model for this game.
 * The model is responsible for maintaining
 * game state and other such purposes
 *
 * @author Emmanuel Olaojo
 * @author Eronmonsele Omiyi
 */
public class SolitaireChessModel extends Observable implements ModelConfiguration {
    private static final int DIMENSION = 4;
    private Cell[][] board = new Cell[DIMENSION][DIMENSION];
    private int numPieces;
    private List<Observer> observers = new LinkedList<>();
    private String gameName;
    private List<Piece> pieces;
    private Move path;
    private Move previousMove;
    private BackTracker b;

    /**
     * Builds the Model
     * @param fileName - Configuration file
     */
    public SolitaireChessModel(String fileName){
        this.gameName = fileName;
        buildModel(null);
        b =  new BackTracker();
    }

    /**
     * Copy Constructor
     * @param s - model to be copied
     */
    public SolitaireChessModel(SolitaireChessModel s){
        this.numPieces = s.numPieces;
        this.gameName = s.gameName;
        this.observers = new LinkedList<>();
        this.pieces = new LinkedList<>(s.pieces);
        this.b = new BackTracker();
        int i = 0;
        int j = 0;
        for(Cell[] c : s.board){
            for(Cell c1 : c){
                this.board[i][j] = new Cell(c1);
                j++;
            }
            i++;
            j= 0;
        }
    }

    /**
     * Stores the configuration file and builds the model
     */
    private void buildModel(File file){
        initializeState();
        initializeBoard(file);
        getPieceOnBoard();
    }

    /**
     * Initialize the board with information
     * found in a configuration file
     */
    private void initializeBoard(File file){
        try{
            if(file == null) file = new File(this.gameName);

            Scanner scan = new Scanner(file);

            for(int i=0; i<DIMENSION; i++){
                for(int j=0; j<DIMENSION; j++) {
                    try {
                        String s = scan.next();

                        try {
                            board[i][j] = makeCell(PieceEnum.fromString(s), new Position(i, j));
                        } catch (NullPointerException npe) {
                            this.fileError();
                            return;
                        }
                    } catch(NoSuchElementException nse){
                        this.fileError();
                        return;
                    }
                }
            }
            this.gameName = file.getAbsolutePath();

        } catch (FileNotFoundException fe){
            this.fileError();
        }
    }

    public void fileError(){
        if(this.observers.size()<=0)System.exit(1);
        else {
            this.notifyObservers(true);
        }
    }

    /**
     * Initializes the state's of the model
     */
    private void initializeState(){
        numPieces = 0;
    }

    /**
     * A query method for the view
     *
     * @param row - square's row number [0..DIMENSION)
     * @param col - square's column number [0..DIMENSION)
     *
     * @return the piece stored at the (row,col) location, or PieceEnum.EMPTY
     */
    public PieceEnum at(int row, int col){
        Piece piece = board[row][col].getPiece();

        return (piece != null)?  piece.getPieceEnum() : PieceEnum.EMPTY;
    }

    /**
     * Creates a cell out of a PieceEnum and a Position
     *
     * @param pieceEnum the pieceEnum of the Cell's piece
     * @param position the postion the cell will be created at
     *
     * @return A sexy cell with much properties
     */
    public Cell makeCell(PieceEnum pieceEnum, Position position){
        Piece piece;

        switch (pieceEnum){
            case PAWN: piece = new Pawn(position, pieceEnum);
                break;
            case BISHOP: piece = new Bishop(position, pieceEnum);
                break;
            case KNIGHT: piece = new Knight(position, pieceEnum);
                break;
            case ROOK: piece = new Rook(position, pieceEnum);
                break;
            case QUEEN: piece = new Queen(position, pieceEnum);
                break;
            case KING: piece = new King(position, pieceEnum);
                break;
            default: piece = null;
        }

        if(piece != null) numPieces++;

        return new Cell(piece, position);
    }

    /**
     * @param position the position of the cell
     *
     * @return the cell at the given position
     */
    public Cell getCell(Position position){
        return board[position.getX()][position.getY()];
    }

    /**
     * Checks if the game is won.
     *
     * @return true if the game iss won. false otherwise
     */
    public boolean isWon(){
        return this.numPieces == 1;
    }

    /**
     * Checks if a cell at a valid position can capture
     * a cell at another valid position.
     *
     * @param start start position
     * @param end end position
     *
     * @return true if the conditions are met, false otherwise
     */
    public boolean canCapture(Position start, Position end){
        return start.isValid()
                && end.isValid()
                && !(start.equals(end))
                && this.getCell(start).isOccupied()
                && this.getCell(end).isOccupied();
    }

    /**
     * Moves a piece from a start position to an end
     * if start to end is a valid move'
     *
     * @param start The start Position
     * @param end The end Position
     *
     * @return true if the move is successful, false otherwise
     */
    public boolean movePiece(Position start, Position end){
        Cell startCell = this.getCell(start);
        Cell endCell = this.getCell(end);
        Piece endPiece = endCell.getPiece().clone();
        boolean captured = false;

        if(this.canCapture(start, end)){
            captured = startCell.capture(endCell, this);
            if(captured){
                numPieces--;
                this.previousMove = new Move(endPiece, end, start);
            }

        }

        this.notifyObservers();
        return captured;
    }

    /**
     * Give a text based representation of the board
     */
/*    public void display(){
        for(Cell[] cells : this.board){
            for(Cell cell: cells){
                System.out.print(cell);
            }
            System.out.println("\n_______________________|"
                    + "_______________________|"
                    + "_______________________|"
                    + "_______________________|");
        }
    }*/

    /**
     * Adds a new Observer to this model
     *
     * @param o the observer to be added
     */
    public void addObserver(Observer o){
        this.observers.add(o);
    }

    /**
     * Calls the update method on all the Observers of
     * this model
     */
    public void notifyObservers(boolean error){
        System.out.println("Updating");
        observers.forEach(observer -> observer.update(this, error));
    }

    public void notifyObservers(){
        //System.out.println("Updating " + this.observers.size() + " Observers");
        observers.forEach(observer -> observer.update(this, null));
    }

    /**
     * Returns a collection of possible moves from a
     * starting position.
     *
     * @param start the starting position
     * @return a collection of possible moves
     */
    public Collection<Position> getPossibleMoves(Position start){
        Piece startPiece = this.getCell(start).getPiece();
        startPiece.generateValidMoves(this);
        return startPiece.getValidMoves();
    }

    /**
     * Tests the capture functionality
     *
     * @param expected expected result of the test
     * @param pos1 position of the piece being tested
     * @param pos2 position of the piece to be captured
     */
    /*
    public void testCapture(boolean expected, Position pos1, Position pos2){
        PieceEnum piece = this.getCell(pos1).getPiece().getPieceEnum();

        System.out.println("\nAttempting Capture with " + piece + "......... " + pos1 + " ===> " + pos2);
        System.out.print("expecting: " + expected + ", got: ");
        boolean result = this.movePiece(pos1, pos2);
        System.out.println(result);
        System.out.println("Test Passed ? " + (result==expected));
        System.out.println
                ("_______________________________________________________________________________________________|");
        this.display();
    }
*/
 /*   public void testMovements(Position[][] positions, boolean results[]){
        for(int i=0; i<positions.length; i++){
            Position[] p = positions[i];

            if(p.length !=2){
                System.out.println("Invalid config at index: " + i);
                break;
            }

            this.testCapture(results[i], p[0], p[1]);
            if(this.isWon()){
                System.out.println("You have won, you brilliant bastard");
            }
        }
    }*/

    /**
     * Model-Implementation of NewGame
     * Initialize the model to a new file
     *
     * @param fileName - New Configuration file
     */
    public void newGame(String fileName){
        File file = new File(fileName);

        this.buildModel(file);
        this.notifyObservers();
    }

    /**
     * Model-Implementation of NewGame
     * Initialize the model to a new file
     *
     * @param file - New Configuration file
     */
    public void newGame(File file){
        this.buildModel(file);
        this.notifyObservers();
    }

    public boolean undo(){
        boolean undone;
        if(this.previousMove == null) return false;
        Cell startCell = this.getCell(this.previousMove.getStart());
        Cell endCell = this.getCell(this.previousMove.getEnd());


        undone = startCell.uncapture(this.previousMove, endCell);
        if(undone) {
            numPieces++;
            previousMove = new Move(getCell(this.previousMove.getEnd()).getPiece(), this.previousMove.getEnd(), this.previousMove.getStart());
        }
        this.notifyObservers();
        return undone;
    }
    /**
     * Model-Implementation of Restart
     * * Reinitialize the model's state
     */
    public void restart(){
        buildModel(null);
        this.notifyObservers();
    }


    public void getPieceOnBoard(){
        this.pieces = new LinkedList<>();
        for(Cell[] c : board){
            for(Cell c1 : c){
                if(c1.isOccupied())this.pieces.add(c1.getPiece());
            }
        }
    }


    public Move getMove(){
        return this.path;
    }

    @Override
    public Collection<ModelConfiguration> getSuccessors() {
        List <ModelConfiguration> successors = new LinkedList<>();
        for(Piece p : this.pieces){
            p.generateValidMoves(this);

            for(Position m : p.getValidMoves()) { //Gets one position the given piece can move
                SolitaireChessModel newModel = new SolitaireChessModel(this); //Creates copy of the original model
                Position start = p.getPosition();

                if(!(newModel.movePiece(start, m))){
                    newModel.removePiece(newModel.getCell(m).getPiece());
                    continue;
                }  //Move the piece to the position on the new model

                newModel.path = new Move(p, start, m);

                if(!newModel.isValid())continue;

                newModel.getPieceOnBoard();
                successors.add(newModel); //Add the model to the successors' collection
            }

        }
        return successors;
    }

    public void removePiece(Piece p){
        this.pieces.remove(p);
    }

    /**
     * Check The model is valid
     * @return true if there are 2 piece on the board and they can't capture each other
     */
    @Override
    public boolean isValid() {
        return !(numPieces == 2 && this.getPossibleMoves(this.path.getEnd()).isEmpty());
    }


    @Override
    public boolean isGoal() {
        return isWon();
    }

    public List<Move> solve(){
        b.getAnswer(this);
        return b.getPathPTUI();
    }




}
