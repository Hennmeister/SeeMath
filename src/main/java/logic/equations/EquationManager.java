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
