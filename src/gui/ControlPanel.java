package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ControlPanel extends JPanel {
    private JButton selectedMode;

    private Map<GraphController.EditMode,JButton> modeButtonMap;

    private JCheckBox colorCheckBox;

    public ControlPanel(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(setupModeButtons());
        add(setupColorCheckBox());
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

    private Box setupColorCheckBox(){
        Box box= new Box(BoxLayout.X_AXIS);
        this.colorCheckBox= new JCheckBox("Vertex Coloring");
        colorCheckBox.setActionCommand("colorCheckBox");
        colorCheckBox.setFocusable(false);
        box.add(colorCheckBox);

        return box;
    }

    public void addActionListener(ActionListener listener){
        for(JButton b : modeButtonMap.values()){
            b.addActionListener(listener);
        }

        colorCheckBox.addActionListener(listener);
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

    public boolean colorCheckBoxChecked(){
        return colorCheckBox.isSelected();
    }
}
