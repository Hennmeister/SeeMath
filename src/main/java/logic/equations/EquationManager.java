package logic.equations;

import java.util.ArrayList;
import java.util.Comparator;

public class EquationManager {
    private ArrayList<Equation> lst = new ArrayList<>();
    public void add(Equation e){
        lst.add(e);
    }
    /**
     * @return Returns equation at this index within the EquationManager.
     */
    public Equation get(int index){
        return lst.get(index);
    }
    /**
     * @return Removes and returns equation at this index within the EquationManager.
     */
    public Equation remove(int index){
        return lst.remove(index);
    }
    /**
     * @return Removes and returns the passed in equation within the EquationManager.
     */
    public boolean remove(Equation e){
        return lst.remove(e);
    }
    public void sortById(){
        lst.sort(Comparator.comparing(Equation::getId));
    }
    public void sortByProblemId(){
        lst.sort(Comparator.comparing(Equation::getProblemId));
    }
    public void sortByVisualizationType(){
        lst.sort(Comparator.comparing(Equation::getVisualizationType));
    }
}
