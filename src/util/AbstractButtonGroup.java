package util;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractButtonGroup implements ButtonContainer{
    private ArrayList<ButtonContainer> containers;

    public AbstractButtonGroup(){
        containers= new ArrayList<>();
    }

    public void add(ButtonContainer group){
        this.containers.add(group);
    }

    public void add(AbstractButton button){
        containers.add(new AbstractButtonSingle(button));
    }

    @Override
    public boolean isSelected(String actionCommand) {
        for(ButtonContainer container : containers){
            if(container.isSelected(actionCommand)){
                return true;
            }
        }

        return false;
    }

    @Override
    public List<AbstractButton> getButtons() {
        ArrayList<AbstractButton> list= new ArrayList<>();

        for(ButtonContainer container : containers){
            list.addAll(container.getButtons());
        }

        return list;
    }
}
