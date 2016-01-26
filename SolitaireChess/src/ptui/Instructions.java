package ptui;

import java.util.HashMap;
import java.util.Map;

public enum Instructions {
    MOVE("move"), NEW("new"), RESTART("restart"),
    HINT("hint"), SOLVE("solve"), QUIT("quit");

    private String sName;

    Instructions(String abbreviation ) {
        this.sName = abbreviation;
    }

    private static Map< String, Instructions> abbreviations = null;

    /**
     * What PieceEnum corresponds to this character?
     * @param strName the character code
     * @return the PieceEnum instance associated with the code
     */
    public static Instructions fromString(String strName ) {
        if ( abbreviations == null ) { // initialize on first call
            abbreviations = new HashMap<>();
            for ( Instructions p: Instructions.values() ) {
                abbreviations.put( p.sName, p );
            }
        }
        return abbreviations.get( strName );
    }
}
