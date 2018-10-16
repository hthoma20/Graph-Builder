package util;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public interface ButtonContainer {
     /**
     * checks if the the button with the given action command
     * is selected
     *
     * @param actionCommand the action command to match
     * @return true iff this button container contains a button with
     *          the given action command, and that button is selected
     *
     *          note that if multiple buttons in this container match
     *          the given command, this should return true if any of
     *          them are selected
     */
    boolean isSelected(String actionCommand);

    /**
     *
     * @return a list of the buttons in this container
     */
    List<AbstractButton> getButtons();
}
