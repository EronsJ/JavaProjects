package ptui;

import backtracker.Move;
import model.Position;
import model.SolitaireChessModel;
import model.pieces.PieceEnum;

import java.util.*;

/**
 * A controller for the ptui.ptui.SolitaireChessPTUI
 *
 * @author Eronmonsele Matthew Omiyi
 * @author Emmanuel Olaojo
 */
public class ControllerPTUI {
    private SolitaireChessModel model;
    private boolean isSolved;
    List<Move> path;
    int next;

    /**
     * Constructor takes in a model and stores a
     * reference to that model
     *
     * @param model the model to store
     */
    public ControllerPTUI(SolitaireChessModel model){
        this.model = model;
        this.next = 0;
        this.getPath(); //Initialize path
        this.isSolved = true;

    }

    /**
     * Command loop. Takes input from the user until
     * the user enters quit.
     */
    public void receiveInput(){
        String command = "";
        Scanner in = new Scanner(System.in);

        while(!command.equals("quit") && !command.equals("q")){
            System.out.print("\n[ move[m], undo[u], new[n], restart[r], hint[h], solve[s], quit[q] ]> ");
            command = in.next().trim().toLowerCase();
            System.out.println();
            switch (command){
                case "m":
                case "move":
                    next = 0;
                    this.handleMove(in);
                    isSolved = false;
                    break;
                case "r":
                case "restart":
                    next = 0;
                    this.handleRestart();
                    isSolved = false;
                    break;
                case "u":
                case "undo":
                    this.handleUndo();
                    isSolved = false;
                    next--;
                    break;
                case "n":
                case "new":
                    next = 0;
                    this.handleNewGame(in);
                    isSolved = false;
                    break;
                case "s":
                case "solve":
                    if(this.model.isWon()){
                        System.out.println("The puzzle has been solved");
                        break;
                    }
                    next = isSolved ? next : 0;
                    this.handleSolve();
                    isSolved = true;
                    break;
                case "h":
                case "hint":
                    if(this.model.isWon()){
                        System.out.println("No More hints...The game puzzle is solved");
                        break;
                    }
                    next = (next < 0) ? 0 : next;
                    this.handleHint();
                    next++;
                    isSolved = true;
                    break;
            }
        }
        System.exit(0);
    }

    public void handleUndo(){
        if(!this.model.undo())
            System.out.println("A valid move has to be made before an undo");
    }
    /**
     * Displays the solution path if one exist
     */
    public void handleSolve() {
        this.getPath();
        if(path.isEmpty()) return;
        for(int i = next; i< path.size(); i++ ){
            Position start = path.get(i).getStart();
            Position end = path.get(i).getEnd();
            System.out.println(path.get(i));
            this.model.movePiece(start, end);
            System.out.println();
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){

            }
        }
    }

    /**
     * Gets the next possible move from the path, if one exist
     */
    public void handleHint(){
        this.getPath();
        if(path.isEmpty()) return;
        if(next >= path.size() ) return;
        System.out.println(path.get(next));
        Position start = path.get(next).getStart();
        Position end = path.get(next).getEnd();
        this.model.movePiece(start, end);
    }

    /**
     * Solve the initial puzzle on start up! The checks if a move has been made if so, resolves the puzzle
     * else it returns the already developed path
     */
    public void getPath(){
        if(!isSolved) path = this.model.solve();
        if(path.isEmpty()) System.out.println("NO Solution! Consider restarting the game");
    }


    /**
     * Handles the move command by calling move on the
     * model.
     *
     * @param in a scanner that reads from stdin
     */
    public void handleMove(Scanner in){
        Position start = new Position();
        Position end = new Position();

        start.setX(enterCoordinate("source row? ", in));
        start.setY(enterCoordinate("source col? ", in));

        end.setX(enterCoordinate("dest row? ", in));
        end.setY(enterCoordinate("dest col? ", in));

        PieceEnum startPiece = model.at(start.getX(), start.getY());
        PieceEnum endPiece = model.at(end.getX(), end.getY());

        System.out.println(""+startPiece + start + " to " + endPiece + end);

        boolean success = this.model.movePiece(start, end);

        if(!success) System.out.println("Invalid move");
    }

    /**
     * Handles restarts by calling restart on
     * the model with a null so the model uses the same
     * file.
     */
    public void handleRestart(){
        this.model.restart();
    }

    /**
     * Handles new games by calling restart on
     * the model with a file so the model attempts to
     * start a new game with that file.
     *
     * @param in a scanner that reads from stdin
     */
    public void handleNewGame(Scanner in){
        String filename = enterFilename(in);
        System.out.println("New Game: " + filename);
        this.model.newGame(filename);
    }

    /**
     * Accepts and returns an int value from stdin. Loops
     * until the input is valid
     *
     * @param message a message to prompt the user with
     * @param in a scanner that reads from stdin
     *
     * @return an integer between 0 and 4
     */
    public int enterCoordinate(String message, Scanner in){
        int pos = -1;

        while(invalidCoordinate(pos)) {
            try {
                System.out.print(message);
                pos = in.nextInt();
                if (invalidCoordinate(pos)) System.out.println("Invalid entry. Try again");
            } catch (InputMismatchException ime){
                System.out.println("Invalid entry. Try again");
                pos = -1;
                in.next();
            }
        }

        return pos;
    }

    /**
     * Checks if a coordinate is
     * invalid (less than 0 or grater than 3)
     *
     * @param pos the integer value to be checked
     *
     * @return true if invalid, false otherwise
     */
    public boolean invalidCoordinate(int pos){
        return pos<0 || pos>3;
    }

    /**
     * Prompts for a filename while the filename is invalid
     *
     * @param in a scanner that reads from stdin
     *
     * @return a valid filename
     */
    public String enterFilename(Scanner in){
        String fileName = "";
        in.nextLine();

        while(isInvalid(fileName)){
            System.out.print("Enter a valid game file name: ");
            fileName = in.nextLine();
        }

        return fileName.trim();
    }

    /**
     * Checks if a filename is valid (ends with .txt)
     *
     * @param filename the filename to check
     *
     * @return true if invalid, false otherwise
     */
    public boolean isInvalid(String filename){
        String[] nameArr = filename.split("\\.");
        String ext = nameArr[nameArr.length-1].trim();
        return !ext.equals("txt");
    }

    /**
     * Calls receiveInput
     */
    public void run(){
        this.receiveInput();
    }
}
