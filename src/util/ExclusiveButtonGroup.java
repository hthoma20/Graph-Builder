package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * a button group that ensures only one button is selected
 */
public class ExclusiveButtonGroup implements ButtonContainer, ActionListener {
    //boolean that signifies whether one button must be selected
    //if optional is true, either 0 or 1 buttons in this group will be selected
    //if optional is false, exactly 1 button is selected
    private boolean optional= true;

    private ArrayList<AbstractButton> buttons= new ArrayList<>();

    public ExclusiveButtonGroup(boolean optional){
        if(!optional){
            throw new IllegalArgumentException("non-optional ExclusiveButtonGroup must be constructed with a default button");
        }

        this.optional= optional;
    }

    public ExclusiveButtonGroup(boolean optional, AbstractButton defaultButton){
        this.optional= optional;

        add(defaultButton);
        defaultButton.setSelected(true);
    }

    public void add(AbstractButton button){
        buttons.add(button);
        button.setSelected(false);
        button.addActionListener(this);
    }

    @Override
    public boolean isSelected(String actionCommand) {
        for(AbstractButton button : buttons){
            if(actionCommand.equals(button.getActionCommand()) && button.isSelected()){
                return true;
            }
        }

        return false;
    }

    @Override
    public List<AbstractButton> getButtons() {
        return (ArrayList<AbstractButton>)buttons.clone();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source= e.getSource();

        if(!buttons.contains(source)){
            return;
        }

        //deselect all buttons except source
        for(AbstractButton button : buttons){
            if(button != source){
                button.setSelected(false);
            }
            //if this is the button that was pushed, and we must have one selected
            else if(!optional){
                button.setSelected(true);
            }
        }
    }
}
