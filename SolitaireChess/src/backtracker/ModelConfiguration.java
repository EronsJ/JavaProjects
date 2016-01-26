package backtracker;

import java.util.Collection;

/**
 * The representation of a single configuration for a puzzle.
 * The backtracking.backtracker.BackTracker depends on these routines in order to
 * solve a puzzle.  Therefore, all puzzles must implement this
 * interface.
 *
 * @author sps, Eronmonsele Matthew Omiyi, Emmanuel Olaojo
 */
public interface ModelConfiguration {


        /**
         * Get the collection of successors from the current one.
         *
         * @return All successors, valid and invalid
         */
        Collection<ModelConfiguration> getSuccessors();

        /**
         * Is the current configuration valid or not?
         *
         * @return true if valid; false otherwise
         */
        boolean isValid();

        /**
         * Is the current configuration a goal?
         * @return true if goal; false otherwise
         */
        boolean isGoal();

        Move getMove();

}
