import gui.SolitaireChessGUI;
import ptui.ControllerPTUI;
import javafx.application.Application;
import ptui.SolitaireChessPTUI;

/**
 * Runs The program
 *
 * @author Emmanuel Olaojo
 */
public class SolitaireChess {
    public static final int CMD_LINE_ERROR = 1;

    enum UIMode {huh, gui, ptui}

    private static void usage() {
        System.err.println(
                "Usage: java SoltrChess ( gui | ptui ) config-file");
        System.exit(CMD_LINE_ERROR);
    }

    /**
     * Start up a Chess Solitaire game in a terminal window or GUI.
     *
     * @param args string array containing [0] "gui" or "ptui";
     *             [1] the game's configuration file
     */
    public static void main(String[] args) {
        UIMode mode = UIMode.huh;
        System.out.println("Welcome to Solitaire Chess!");
        String fileName = null;
        switch (args.length) {
            case 2:
                fileName = args[1];
                try {
                    mode = UIMode.valueOf(args[0]);
                } catch (IllegalArgumentException badEnumString) {
                    usage();
                }
                break;
            default:
                usage();
        }

        // Informing a JavaFX application of other objects is a bit
        // tricky. So we are letting it start the model. To be
        // consistent, we're having the PTUI application do so as well.
        //
        // For the JavaFX application we did not separate out a
        // controller, but the PTUI has a separate controller class that
        // loops, taking commands from the user and calling methods in the
        // model.
        //
        // SoltrChessGUI and SoltrChessPTUI implement Observer.update()
        //
        switch (mode) {
            case gui:
                Application.launch(SolitaireChessGUI.class, fileName);
                break;
            case ptui:
                SolitaireChessPTUI ui = new SolitaireChessPTUI(fileName);
                ControllerPTUI ctrlr = new ControllerPTUI( ui.getModel() );
                ctrlr.run();
                break;
            default:
                usage();
        }
    }
}
