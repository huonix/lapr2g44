package GUI;

import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Class used to open a loading GIF and block the GUI
 *
 * @author Group 44 LAPR2
 */
public class LoadingDialog extends JDialog {

    private JDialog dialogGlass;
    private JFrame frame;

    /**
     * Constructor used to create the loading
     *
     * @param frame JFrame to put the loading
     */
    public LoadingDialog(JFrame frame) {
        super(frame);
        this.frame = frame;
        createGlass();
        setPreferredSize(frame.getSize());
        setMinimumSize(frame.getSize());
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        getRootPane().setOpaque(false);
        URL imagePath = getClass().getResource("/images/loading.gif");
        JLabel labelLoad = new JLabel();
        labelLoad.setOpaque(false);
        if (imagePath != null) {
            ImageIcon icon = new ImageIcon(imagePath);
            labelLoad = new JLabel(icon);
        }
        add(labelLoad);
        setLocationRelativeTo(frame);
    }

    /**
     * Method used to create a panel with the effect of a glass
     */
    private void createGlass() {
        dialogGlass = new JDialog(frame);
        dialogGlass.setPreferredSize(frame.getSize());
        dialogGlass.setMinimumSize(frame.getSize());
        dialogGlass.setUndecorated(true);
        dialogGlass.setOpacity(0.01f);
        dialogGlass.getRootPane().setOpaque(false);
        dialogGlass.setLocationRelativeTo(frame);
    }

    /**
     * Method used to open the loading
     */
    public void open() {
        dialogGlass.setVisible(true);
        setVisible(true);
    }

    /**
     * Method used to close the loading
     */
    public void close() {
        dialogGlass.setVisible(false);
        dialogGlass.dispose();
        setVisible(false);
        dispose();
    }
}
