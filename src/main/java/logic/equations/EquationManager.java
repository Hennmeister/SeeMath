package logic.equations;

import gui.VisualizationCreator;

import java.util.ArrayList;
import java.util.Comparator;

public class EquationManager {
    private ArrayList<Equation> equationList = new ArrayList<>();
    private VisualizationCreator presenter;

    public EquationManager(VisualizationCreator visPresenter){
        this.presenter = visPresenter;
    }

    /**
     * Add an equation to the list of stored equations
     * If an equation is a modified version of an already stored equation, overwrite the old equation instead
     * @param e The equation to be added
     */
    public void add(Equation e){
        boolean found = false;
        for (Equation equation : equationList){
            if (equation.getMathBlockId().equals(e.getMathBlockId())){
                if (!equation.equals(e)) {
                    equationList.set(equationList.indexOf(equation), e);
                    presenter.updateVisualization(e);
                }
                return;
            }
        }
        equationList.add(e);
        presenter.updateVisualization(e);
    }

    /**
     * Sets the equation containing the expression node with the given id to incorrect
     * @param id The id of the incorrect expression node
     * @param isCorrect Whether this equation is logically correct
     */
    public void updateEquationCheckMathResults(String id, boolean isCorrect){
        for (Equation eqn : equationList){
            if(eqn.containsExpression(id)) {
                eqn.setCorrectness(isCorrect);
                return;
            }
        }
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
        equationList.sort(Comparator.comparing(Equation::getMathBlockId));
    }
    public void sortByProblemId(){
        equationList.sort(Comparator.comparing(Equation::getProblemId));
    }
}
