package backtracker;




import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class BackTracker {

    boolean reversed;
    boolean isSolved;
    public List<Move> path;


    public BackTracker(){
        this.path = new LinkedList<>();
        this.reversed = false;
        this.isSolved = false;
    }

    public BackTracker(BackTracker b){
        this.path = new LinkedList<>();
        this.reversed = false;
        this.isSolved = false;
    }


    /**
     * Solve the model by considering every possible combination
     * @param mc - the model to be solve
     * @return The completed model
     * Post Condition: Creates a path in the List;
     */
    public ModelConfiguration solve(ModelConfiguration mc){
        if(mc.isGoal()){
            path.add(mc.getMove());
            isSolved = true;
            return mc;
        }


        for(ModelConfiguration config : mc.getSuccessors()){
            ModelConfiguration m = solve(config);
            if(m == null) path.clear();
            else {
                path.add(mc.getMove());
                return m;
            }

        }

        return null;
    }

    /**
     * @return Returns path taken to solve problem
     */
    public List<Move> getPathPTUI(){
        path.remove(null);
        if(!reversed){
            Collections.reverse(path);
            reversed = true;
        }
        return path;
    }


    public List<Move> getPath(){ return path; }


    /**
     * Gets the solution to the puzzle
     * @param mc - Model to be solved
     */
    public void getAnswer(ModelConfiguration mc){
        if(isSolved) path.clear();
        this.solve(mc);
        this.reversed = false;
    }


}
