package logic.equations;

import java.util.ArrayList;
import java.util.Comparator;

public class EquationManager {
    private ArrayList<Equation> equationList = new ArrayList<>();
    public void add(Equation e){
        equationList.add(e);
    }
    /**
     * @return Returns equation at this index within the EquationManager.
     */
    public Equation get(int index){
        return equationList.get(index);
    }
    /**
     * @return Removes and returns equation at this index within the EquationManager.
     */
    public Equation removeAt(int index){
        return equationList.remove(index);
    }
    /**
     * @return Removes and returns whether the passed in equation within the EquationManager was removed.
     */
    public boolean remove(Equation e){
        return equationList.remove(e);
    }
    public void sortById(){
        equationList.sort(Comparator.comparing(Equation::getId));
    }
    public void sortByProblemId(){
        equationList.sort(Comparator.comparing(Equation::getProblemId));
    }
    public void sortByVisualizationType(){
        equationList.sort(Comparator.comparing(Equation::getVisualizationType));
    }
}
