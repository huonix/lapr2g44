package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Class used to create a message dialog
 *
 * @author Group 44 LAPR2
 */
public class MessageDialog extends JDialog implements ActionListener {

    private final Dimension dialogSize = new Dimension(600, 200);
    private final Dimension upComponent = new Dimension(600, 150);
    private final Dimension bottomComponent = new Dimension(600, 50);

    /**
     * Constructor used to create a custom message dialog
     *
     * @param frame JFrame parent
     * @param modal Boolean with the modal option
     * @param bgColor Background color
     * @param font Font to be used
     * @param colorText Color of the text
     * @param textSize Text size
     * @param message Message to be presented
     * @param closeBtn Button used to close the dialog
     */
    public MessageDialog(JFrame frame, boolean modal, Color bgColor, Font font, Color colorText, float textSize, String message, JButton closeBtn) {
        super(frame, modal);
        setLayout(new GridLayout());
        setBackground(bgColor);
        setUndecorated(true);
        getRootPane().setBackground(bgColor);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        panel.setBackground(bgColor);
        JTextPane label = new JTextPane();
        label.setText("\n" + message);
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setFont(font.deriveFont(textSize));
        label.setPreferredSize(upComponent);
        label.setEditable(false);
        label.setForeground(colorText);
        StyledDocument stylePane = label.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        stylePane.setParagraphAttributes(0, stylePane.getLength(), center, false);
        JScrollPane scroolPane = new JScrollPane(label);
        scroolPane.setBorder(BorderFactory.createLineBorder(Color.white, 0));
        panel.add(scroolPane, BorderLayout.NORTH);
        JLabel labelBottom = new JLabel();
        labelBottom.setBackground(bgColor);
        labelBottom.setOpaque(true);
        labelBottom.setPreferredSize(bottomComponent);
        labelBottom.setMinimumSize(bottomComponent);
        labelBottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        closeBtn.addActionListener(this);
        labelBottom.add(closeBtn);
        panel.add(labelBottom, BorderLayout.CENTER);
        add(panel);
        setMinimumSize(dialogSize);
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    /**
     * Method used to close the message dialog
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
