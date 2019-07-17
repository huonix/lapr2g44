package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * Class used to create the main menu
 *
 * @author Group 44 LAPR2
 */
public final class MainMenuPage {

    private ProjectFrame projectFrame;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;

    /**
     * Constructor used to create the main menu
     *
     * @param projectFrame Parent Frame
     */
    public MainMenuPage(ProjectFrame projectFrame) {
        setProjectFrame(projectFrame);
    }

    /**
     * Method used to open the main menu page
     */
    public void open() {
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > Main Menu");
        projectFrame.fadeOut(projectFrame.getContent());

        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        btn = new CustomButton("New search", projectFrame.getBigButtonSize(), "/images/newSearch.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openPage("Search Page");
            }
        });
        panel.add(btn);

        btn = new CustomButton("List of searchs", projectFrame.getBigButtonSize(), "/images/listSearchs.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openPage("List of searchs");
            }
        });
        panel.add(btn);

        btn = new CustomButton("List of results", projectFrame.getBigButtonSize(), "/images/listResult.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openPage("List of results");
            }
        });
        panel.add(btn);

        //Add buttons to footer
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 50));
        tempPanel.setLayout(new GridLayout(1, 2));
        tempPanelSec = GUIFunctions.createLinePanel(50);
        tempPanelSec.add(tempPanel);
        JPanel footerleftPanel = new JPanel();
        footerleftPanel.setOpaque(false);
        footerleftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(footerleftPanel);
        JPanel footerRightPanel = new JPanel();
        footerRightPanel.setOpaque(false);
        footerRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tempPanel.add(footerRightPanel);
        //Exit btn
        btn = new CustomButton("Exit", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerRightPanel.add(btn);
        projectFrame.getFooter().add(tempPanelSec);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.resetProject();
                projectFrame.openPage("Open Screen");
            }
        });
        //Save and exit btn
        btn = new CustomButton("Save Project & Exit", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerRightPanel.add(btn);
        projectFrame.getFooter().add(tempPanelSec);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndExit();
            }
        });
        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());
    }

    /**
     * Method used to save and exit the project
     */
    private void saveAndExit() {
        projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
        projectFrame.getLoadingDialog().open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    File dir = new File(projectFrame.getStorageFilesPath());
                    if (!dir.isDirectory()) {
                        dir.mkdirs();
                    }
                    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(projectFrame.getStorageFilesPath() + "\\" + projectFrame.getProject().getName() + ".bin"))) {
                        out.writeObject(projectFrame.getProject());
                        out.close();
                    }
                    projectFrame.getLoadingDialog().close();
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project saved successfully.", projectFrame.getClosebtn()));
                    projectFrame.resetProject();
                    projectFrame.openPage("Open Screen");
                } catch (IOException ex) {
                    projectFrame.getLoadingDialog().close();
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not saved, try again.", projectFrame.getClosebtn()));
                }
                return true;
            }
        };
        worker.execute();
    }

    /**
     * Method used to get the parent frame
     *
     * @return Parent Frame
     */
    public ProjectFrame getProjectFrame() {
        return projectFrame;
    }

    /**
     * Method used to set the parent frame
     *
     * @param projectFrame Parent Frame
     */
    public void setProjectFrame(ProjectFrame projectFrame) {
        this.projectFrame = projectFrame;
    }
}
