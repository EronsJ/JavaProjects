package gui;

import backtracker.Move;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Position;
import model.pieces.PieceEnum;
import model.SolitaireChessModel;


import java.io.File;
import java.util.*;
import java.util.List;

/**
 * GUI for the puzzle. It works with a model to
 * display a graphical representation of the
 * Configuration file.
 *
 * @author Emmanuel Olaojo
 * @author Eronmonsele Matthew Omiyi
 */
public class SolitaireChessGUI extends Application implements Observer{
    private static final int BoardSize = 4;
    private final Image KNIGHT_IMG = new Image(this.getClass().getResourceAsStream("knight.png"));
    private final Image ROOK_IMG = new Image(this.getClass().getResourceAsStream("rook.png"));
    private final Image QUEEN_IMG = new Image(this.getClass().getResourceAsStream("queen.png"));
    private final Image PAWN_IMG = new Image(this.getClass().getResourceAsStream("pawn.png"));
    private final Image BISHOP_IMG = new Image(this.getClass().getResourceAsStream("bishop.png"));
    private final Image KING_IMG = new Image(this.getClass().getResourceAsStream("king.png"));
    private final Image LIGHT_IMG = new Image(this.getClass().getResourceAsStream("light.png"));
    private final Image Dark_IMG = new Image(this.getClass().getResourceAsStream("dark.png"));
    private final Image BLUE_IMG = new Image(this.getClass().getResourceAsStream("blue.png"));
    private final Image YELLOW_IMG = new Image(this.getClass().getResourceAsStream("yellow.png"));
    private SolitaireChessModel board;
    private ChessPiece selected = null;
    private ChessPiece[][] grid = new ChessPiece[BoardSize][BoardSize];
    private Stage stage;
    private GridPane gameArea;
    private List<Position> chosen = new ArrayList<>();
    private Collection<ChessPiece> possibleMoves = new LinkedList<>();
    private TextField gameStatus = new TextField();
    String puzzleID;
    //State of Board
    private boolean isSolved;
    List<Move> path;
    int next;

    /**
     * Creates an instance of SolitaireChessModel
     * Also parses out the puzzle id from the
     * file name
     */
    @Override
    public void init(){
        String fileName = this.getParameters().getRaw().get(0);
        this.puzzleID = "Puzzle ID: " + fileName.split(".txt")[0];
        this.board = new SolitaireChessModel(fileName);
        this.board.addObserver(this);
        this.next = 0;
        this.getSolution(); //Initialize path
        this.isSolved = true;
    }

    /**
     * Setup to run the gui with
     * @param stage - we build everything on stage
     */
    @Override
    public void start(Stage stage){
        stage.setTitle("Little Chess");
        stage.setWidth(485);
        stage.setHeight(565);
        stage.setScene(buildGui());
        stage.setResizable(false);
        stage.show();
        this.stage = stage;
    }

    /**
     * Places all the components in their
     * correct positions
     *
     * @return a Scene with all the components added to it
     */
    private Scene buildGui(){
        BorderPane bp = new BorderPane();
        this.gameArea = new GridPane();
        HBox controls = new HBox();
        Button newGame = new Button("New Game");
        Button undo = new Button("Undo");
        Button restart = new Button("Restart");
        Button hint = new Button("Hint");
        Button solve = new Button("Solve");

        BorderPane buttonPane = new BorderPane();

        //configure this.gameStatus
        this.gameStatus.setText(this.puzzleID);
        this.gameStatus.setEditable(false);
        this.gameStatus.setAlignment(Pos.CENTER);

        bp.setTop(new BorderPane(this.gameStatus));

        this.addPieces(this.gameArea, this.board);

        //add EventListeners to buttons
        restart.setOnAction(e -> {
            this.gameStatus.setText("Here we go again!!");
            this.next = 0;
            this.handleRestart();
            this.isSolved = false;
        });

        undo.setOnAction(e -> {
            this.gameStatus.setText("Was that such a bad move?");
            this.handleUndo();
            isSolved = false;
            next--;
        });
        newGame.setOnAction(e -> {
            this.gameStatus.setText(this.puzzleID);
            next = 0;
            this.handleNewGame();
            isSolved = false;
        });

        hint.setOnAction(e -> {
            this.gameStatus.setText("One Step Closer to the Answer");
            if (this.board.isWon()) {
                this.gameStatus.setText("No More hints...The game puzzle is solved");
                return;
            }
            next = (next < 0) ? 0 : next;
            this.handleHint();
            next++;
            isSolved = true;
        });

        solve.setOnAction(e -> {
            this.gameStatus.setText("Sure will show you how the pro's do it");
            if(this.board.isWon()){
                this.gameStatus.setText("The puzzle has been solved");
                return;
            }
            next = isSolved ? next : 0;
            this.handleSolve();
            isSolved = true;
        });

        //add buttons to controls
        controls.getChildren().add(newGame);
        controls.getChildren().add(undo);
        controls.getChildren().add(restart);
        controls.getChildren().add(hint);
        controls.getChildren().add(solve);
//        controls.getChildren().add(this.forward);
        controls.setMaxWidth(265);
        //add controls to buttonPane
        buttonPane.setCenter(controls);

        bp.setCenter(this.gameArea);
        bp.setBottom(buttonPane);


        return new Scene(bp);
    }

    /**
     * Populates a GridPane with pieces.
     *
     * @param gp GridPane representation of the board
     */
    private void addPieces(GridPane gp, SolitaireChessModel board){
        boolean light;

        for(int col=0; col<BoardSize; col++){
            light = col%2==0;

            for(int row=0; row<BoardSize; row++){
                PieceEnum pieceEnum = board.at(row, col);
                ChessPiece piece = setImg(new ChessPiece(row, col, pieceEnum));

                //set button size
                piece.setMinSize(120, 120);
                piece.setMaxSize(120, 120);

                piece.setOnAction(e -> handleClick(piece));
                piece.setBg(light ? this.LIGHT_IMG : this.Dark_IMG);
                Background prevbg = piece.getBackground();
                piece.setOnMouseEntered(e -> piece.setBg(BLUE_IMG) );
                piece.setOnMouseExited(e -> piece.setBg(prevbg.getImages().get(0).getImage()));
                gp.add(piece, col, row);
                this.grid[row][col] = piece;
                light = !light;
            }
        }
    }

    /**
     * Sets the image on the button depending on
     * the value of it's pieceEnum
     *
     * @param button The button we're operating on
     *
     * @return the button that was passed in
     */
    private ChessPiece setImg(ChessPiece button){
        boolean occupied = true;
        ImageView imageView = new ImageView();
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);

        switch(button.getPieceEnum()){
            case PAWN: imageView.setImage(PAWN_IMG);
                break;
            case BISHOP: imageView.setImage(BISHOP_IMG);
                break;
            case KNIGHT: imageView.setImage(KNIGHT_IMG);
                break;
            case ROOK: imageView.setImage(ROOK_IMG);
                break;
            case QUEEN: imageView.setImage(QUEEN_IMG);
                break;
            case KING: imageView.setImage(KING_IMG);
                break;
            case EMPTY: occupied = false;
                break;
        }

        button.setOccupied(occupied);

        if(occupied) button.setGraphic(imageView);

        return button;
    }

    /**
     * Gives us the ChessPiece at the position of another
     * ChessPiece.
     *
     * @param piece the ChessPiece whose position we'll copy
     *
     * @return a ChessPiece
     */
    private ChessPiece pieceAt(ChessPiece piece){
        return this.grid[piece.position.getX()][piece.position.getY()];
    }

    /**
     * Gives us the ChessPiece at a position
     *
     * @param position the position to check
     *
     * @return the ChessPiece at position
     */
    private ChessPiece pieceAt(Position position){
        return this.grid[position.getX()][position.getY()];
    }

    /**
     * clears the list of selected pieces
     */
    private void clearChosen(){
        this.chosen.clear();
    }

    /**
     * Shows all the possible positions that a piece
     * can move to.
     *
     * @param position starting position
     */
    private void showOptions(Position position){
        Collection<Position> moves = board.getPossibleMoves(position);

        moves.forEach(this::setPossibleMove);
    }

    /**
     * Sets the Piece at a given position as a possible move
     *
     * @param p the position representing aa possible move
     */
    private void setPossibleMove(Position p){
        ChessPiece current = this.pieceAt(p);
        ChessPiece duplicate = new ChessPiece(current);

        possibleMoves.add(duplicate);
        current.setBg(YELLOW_IMG);
    }

    private void clearPossibleMoves(){
        this.possibleMoves.forEach(piece -> this.pieceAt(piece).setBg(piece));
        this.possibleMoves.clear();
    }

    /**
     * Performs the capture operation
     *
     * @param selected a list of selected Positions
     */
    private void capture(List<Position> selected){
        if(selected.size() < 2 ) return;
        if(selected.get(0) == selected.get(1)){
            this.clearChosen();
            return;
        }

        ChessPiece startPiece = this.pieceAt(selected.get(0));
        ChessPiece endPiece = this.pieceAt(selected.get(1));
        String confirmMove = "" + startPiece.getPieceEnum() + startPiece.position + " Captures "
                + endPiece.getPieceEnum() + endPiece.position;

        String denyMove = "Invalid Move";

        boolean isValidMove = this.board.movePiece(selected.get(0), selected.get(1));
        if(isValidMove){
            next = 0;
            isSolved = false;
        }
        if(!this.board.isWon()) this.gameStatus.setText(isValidMove? confirmMove : denyMove);
        this.clearChosen();
    }

    /**
     * adds a position to the list of selected positions.
     * It also shows all the possible moves for the first
     * selected piece.
     *
     * @param selected the position to get the
     */
    private void addSelected(Position selected){
        if(this.chosen.size() >= 2) return;

        this.chosen.add(selected);

        if(this.chosen.size() == 1){
            this.selected = new ChessPiece(this.pieceAt(selected));
            this.pieceAt(selected).setBg(BLUE_IMG);
            this.showOptions(selected);
        }
        else{
            this.pieceAt(chosen.get(0)).setBg(this.selected);
            this.selected = null;
            if(!board.isWon()) this.gameStatus.setText("Selection canceled");
            this.clearPossibleMoves();
        }
    }

    /**
     * Handles the click event on a ChessPiece
     *
     * @param piece the ChessPiece to handle
     */
    private void handleClick(ChessPiece piece){
        if(piece.isOccupied()) {
            this.addSelected(piece.position);
            this.capture(this.chosen);
        }
        else {
            if(this.selected != null) this.pieceAt(this.selected).setBg(this.selected);
            if(!board.isWon()) this.gameStatus.setText("Selection canceled");
            this.clearState();
        }
    }

    /**
     * Handles the restart operation:
     * Clears the GUI state and calls restart on the model
     */
    private void handleRestart(){
        this.clearState();
        this.board.restart();
    }

    /**
     * Handles the undo operation
     * Calls undo on the model
     */
    private void handleUndo(){
        if(!this.board.undo()){
            this.gameStatus.setText("A valid move has to be made before an undo");
        }
    }

    /**
     * Handles the new game operation:
     * calls newGame on the model with a user selected
     * file.
     */
    private void handleNewGame(){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(this.stage);
        if(file != null){
            String[] fileNameArr = file.getName().split("\\.");

            if(!(fileNameArr[fileNameArr.length-1].trim().equals("txt"))){
                this.handleInvalidFile();
                return;
            }

            this.clearState();
            this.puzzleID = "Puzzle ID: " + file.getName().split(".txt")[0];
            this.gameStatus.setText(puzzleID);
            board.newGame(file);
        }
    }

    /**
     * Handles the hint operation:
     * Solves the puzzle and makes the first move
     */
    public void handleHint(){
        this.getSolution();
        if(path.isEmpty()) return;
        if(next >= path.size() ) return;
        Position start = path.get(next).getStart();
        Position end = path.get(next).getEnd();
        this.board.movePiece(start, end);
    }

    /**
     * Solves the puzzle
     */
    public void handleSolve() {
        this.getSolution();
        if (path.isEmpty()) return;
        if(next >= path.size() ) return;
        for(int i = next; i< path.size(); i++ ){
            autoMove(path.get(i));
        }
    }


    public void autoMove(Move move){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Solution in progress");
        alert.setHeaderText("Click Ok to continue");
        alert.setContentText(move.toString());
        alert.setX(750);
        alert.setY(750);
        alert.showAndWait();

        //Platform.runLater( {} ->
        this.board.movePiece(move.getStart(), move.getEnd());
        //)

    }

    public void getSolution(){
        if(!isSolved) {
            path = this.board.solve();
        }
        if(path.isEmpty()) gameStatus.setText("No solution from current state! Consider restarting the game");
    }

    /**
     * Clears the state of this GUI
     */
    private void clearState(){
        this.clearChosen();
        this.selected = null;
        this.clearPossibleMoves();
    }

    private void handleInvalidFile(){
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Invalid File");
        alert.setHeaderText("Why you always lying?");
        alert.setContentText("The selected file is invalid");
        alert.showAndWait();
    }

    /**
     * Updates the gui with new positions from the modeel
     *
     * @param o The Observable object(SolitaireChessModel)
     * @param error An argument of sorts
     */
    public synchronized void update(Observable o, Object error) {
        if (error != null && (Boolean) error) {
            handleInvalidFile();
            return;
        }

        SolitaireChessModel scm = (SolitaireChessModel) o;
        addPieces(this.gameArea, board);
        if (scm.isWon()) this.gameStatus.setText("You've won, you brilliant bastard");
    }

}