package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuilderWindow implements ActionListener {
    private JFrame frame;
    private JPanel setupPanel;

    private JTextField completeN;
    private JTextField bipartN;
    private JTextField bipartM;

    public BuilderWindow(){
        this.frame= new JFrame("New Graph");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(200,200);

        createSetupPanel();
        createPane(frame.getContentPane());

        frame.setVisible(true);
    }

    private void createPane(Container root){
        JTabbedPane pane= new JTabbedPane();

        this.completeN= new JTextField(5);
        this.bipartN= new JTextField(5);
        this.bipartM= new JTextField(5);

        JPanel completeSetup= new JPanel();
        completeSetup.add(new JLabel("n="));
        completeSetup.add(completeN);

        pane.addTab("Complete",completeSetup);

        JPanel bipartiteSetup= new JPanel();
        bipartiteSetup.add(new JLabel("n="));
        //bipartiteSetup.add();
        bipartiteSetup.add(new JLabel("m="));
        //bipartiteSetup.add(field2);

        root.add(pane);
    }

    private void createSetupPanel(){
        this.setupPanel= new JPanel(new CardLayout());

        JTextField field1= new JTextField(5);
        JTextField field2= new JTextField(5);

        JPanel completeSetup= new JPanel();
        completeSetup.add(new JLabel("Complete graph with n="));
        completeSetup.add(field1);

        JPanel bipartiteSetup= new JPanel();
        bipartiteSetup.add(new JLabel("Complete graph with n="));
        bipartiteSetup.add(field1);
        bipartiteSetup.add(new JLabel("m="));
        bipartiteSetup.add(field2);

        setupPanel.add(completeSetup,"COMPLETE");
        setupPanel.add(bipartiteSetup,"BIPARTITE");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("showing "+e.getActionCommand());
        CardLayout layout= (CardLayout)setupPanel.getLayout();
        layout.show(setupPanel,e.getActionCommand());
    }
}
