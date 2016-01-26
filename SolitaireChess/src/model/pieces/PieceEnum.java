package model.pieces;

import java.util.Map;
import java.util.HashMap;

/**
 * An enumeration that allows a secondary string name (actually a single
 * character) for each instance
 * @author James Heliotis
 * October 2015
 */
public enum PieceEnum {
    EMPTY( "-" ),
    ROOK( "R" ), KNIGHT( "N" ), BISHOP( "B" ),
    QUEEN( "Q" ), KING( "K" ), PAWN( "P" );

    /**
     * The one-character name of the piece
     */
    private String abrv;

    /**
     * Create a piece.
     * @param abbreviation the one-character name of the piece
     */
    PieceEnum(String abbreviation ) {
        this.abrv = abbreviation;
    }

    /**
     * A lookup table: one-character name --&gt; PieceEnum
     */
    private static Map< String, PieceEnum> abbreviations = null;

    /**
     * What PieceEnum corresponds to this character?
     * @param abrv the character code
     * @return the PieceEnum instance associated with the code
     */
    public static PieceEnum fromString(String abrv ) {
        if ( abbreviations == null ) { // initialize on first call
            abbreviations = new HashMap<>();
            for ( PieceEnum p: PieceEnum.values() ) {
                abbreviations.put( p.abrv, p );
            }
        }
        return abbreviations.get( abrv );
    }

    /**
     * What is the short name of this PieceEnum?
     * @return the one-character name of this PieceEnum
     */
    public String shortString() {
        return this.abrv;
    }
}
