package gui;

import util.AbstractButtonGroup;
import util.ButtonContainer;
import util.ExclusiveButtonGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ControlPanel extends JPanel {
    private JButton selectedMode;

    private Map<GraphController.EditMode,JButton> modeButtonMap;

    private ButtonContainer checkBoxes;

    public ControlPanel(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(setupModeButtons());
        add(setupCheckBoxes());
    }

    private Container setupModeButtons(){
        JPanel panel= new JPanel(new GridBagLayout());
        //create buttons and add buttons to mode map
        createModeButtonMap();

        //set action commands
        //disallow focus for buttons
        for(GraphController.EditMode mode : modeButtonMap.keySet()){
            JButton modeButton= modeButtonMap.get(mode);
            modeButton.setActionCommand(mode.toString());
            modeButton.setFocusable(false);

        }

        //and add the buttons to the panel
        GridBagConstraints c= new GridBagConstraints();
        c.insets= new Insets(0,3,0,3);
        c.gridx= 0;
        for(JButton modeButton : modeButtonMap.values()){
            panel.add(modeButton,c);
            c.gridx++;
        }

        return panel;
    }

    private void createModeButtonMap(){
        this.modeButtonMap= new LinkedHashMap<>(4);
        modeButtonMap.put(GraphController.EditMode.VERTEX, new JButton("add (V)ertex"));
        modeButtonMap.put(GraphController.EditMode.EDGE, new JButton("add (E)dge"));
        modeButtonMap.put(GraphController.EditMode.DRAG, new JButton("(D)rag vertex"));
        modeButtonMap.put(GraphController.EditMode.REMOVE, new JButton("(R)emove element"));
    }

    private Box setupCheckBoxes(){
        Box box= new Box(BoxLayout.X_AXIS);

        AbstractButtonGroup checkBoxes= new AbstractButtonGroup();

        JCheckBox colorCheckBox= makeCheckBox("Vertex Coloring", "colorCheckBox");
        box.add(colorCheckBox);

        JCheckBox degreeCheckBox= makeCheckBox("Degrees", "degreeCheckBox");
        box.add(degreeCheckBox);

        JCheckBox labelCheckBox= makeCheckBox("Labels", "labelCheckBox");
        box.add(labelCheckBox);

        ExclusiveButtonGroup vertexCheckBoxes= new ExclusiveButtonGroup(true);
        vertexCheckBoxes.add(degreeCheckBox);
        vertexCheckBoxes.add(labelCheckBox);

        checkBoxes.add(colorCheckBox);
        checkBoxes.add(vertexCheckBoxes);

        this.checkBoxes= checkBoxes;

        return box;
    }

    private JCheckBox makeCheckBox(String text, String actionCommand){
        JCheckBox checkBox= new JCheckBox(text);
        checkBox.setActionCommand(actionCommand);
        checkBox.setFocusable(false);

        return checkBox;
    }

    public void addActionListener(ActionListener listener){
        for(JButton b : modeButtonMap.values()){
            b.addActionListener(listener);
        }

        for(AbstractButton checkBox : checkBoxes.getButtons()){
            checkBox.addActionListener(listener);
        }
    }

    public void setSelectedMode(GraphController.EditMode mode){
        selectedMode= modeButtonMap.get(mode);
        borderSelectedMode();
    }

    private void borderSelectedMode(){
        for(JButton b : modeButtonMap.values()){
            if(b != selectedMode){
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
        if(selectedMode != null){
            selectedMode.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }
    }

    public boolean checkBoxChecked(String actionCommand){
        return checkBoxes.isSelected(actionCommand);
    }
}
