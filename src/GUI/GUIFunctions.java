package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class used to share some common functions
 *
 * @author Group 44 LAPR2
 */
public class GUIFunctions {

    /**
     * Method used to open a dialog
     *
     * @param message String with the message
     * @param title String with the title of the dialog
     * @return Boolean with the option clicked
     */
    public static boolean optionDialog(String message, String title) {
        Object[] btnOptions = {"Yes", "No"};
        if (JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, btnOptions, btnOptions[1]) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Method used to get all files names from a path
     *
     * @param path String with the path
     * @return ArrayList with the file list
     */
    public static ArrayList<String> listFilesFromDir(File path) {
        ArrayList<String> list = new ArrayList<>();
        if (!path.isDirectory()) {
            return list;
        }
        for (File fileEntry : path.listFiles()) {
            if (!fileEntry.isDirectory()) {
                list.add(fileEntry.getName());
            }
        }
        return list;
    }

    /**
     * Method used to create a panel line to use on the GUI
     *
     * @param height Integer with the height of the line
     * @return JPanel with the line
     */
    public static JPanel createLinePanel(int height) {
        JPanel linePanel = new JPanel();
        Dimension sizeLinePanel = new Dimension(Short.MAX_VALUE, height);
        linePanel.setPreferredSize(sizeLinePanel);
        linePanel.setSize(sizeLinePanel);
        linePanel.setMinimumSize(sizeLinePanel);
        linePanel.setMaximumSize(sizeLinePanel);
        linePanel.setOpaque(false);
        linePanel.setLayout(new FlowLayout());
        return linePanel;
    }
}
