package util;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractButtonSingle implements ButtonContainer {
    private AbstractButton button;

    public AbstractButtonSingle(AbstractButton button){
        this.button= button;
    }

    @Override
    public boolean isSelected(String actionCommand) {
        return actionCommand.equals(button.getActionCommand()) && button.isSelected();
    }

    @Override
    public List<AbstractButton> getButtons() {
        ArrayList<AbstractButton> list= new ArrayList<>(1);
        list.add(button);

        return list;
    }
}
