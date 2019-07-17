package GUI;

import Core.Result;
import IO.CSV;
import IO.HTML;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 * Class used to create the show export page
 *
 * @author Group 44 LAPR2
 */
public final class ShowExportPage {

    private ProjectFrame projectFrame;
    private Result result;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel label;
    private JTextField textField;
    private JFileChooser chooser = new JFileChooser();

    /**
     * Constructor used to create the show export page
     *
     * @param projectFrame Parent Frame
     * @param result Result
     */
    public ShowExportPage(ProjectFrame projectFrame, Result result) {
        setProjectFrame(projectFrame);
        setResult(result);
    }

    /**
     * Method used to open the show export page
     *
     * @param tempResult Result to be shown
     */
    public void open(Result tempResult) {
        setResult(tempResult);
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > " + result.getSearchName() + " > Result > Show/Export");
        projectFrame.fadeOut(projectFrame.getContent());

        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        //Project name
        panel.add(createShow("PROJECT NAME: ", result.getProjectName()));
        //Search name
        panel.add(createShow("SEARCH NAME: ", result.getSearchName()));
        //Date and Time
        panel.add(createShow("DATE AND TIME: ", result.getDate().toString()));
        //Number of units
        panel.add(createShow("NUM UNITS: ", result.getUnitList().size() + ""));
        //Number of students
        panel.add(createShow("NUM STUDENTS: ", result.getStudentList().size() + ""));
        //Runtime
        panel.add(createShow("RUNTIME: ", result.getRunTime() + "ms"));
        //Patterns
        panel.add(createShow("PATTERNS: ", result.getPatterns().size() + ""));
        //Min UNits
        panel.add(createShow("MIN. COMMON UNITS: ", result.getUnitsMinNumber() + ""));
        //Min STUDENTS
        panel.add(createShow("MIN. STUDENTS: ", result.getStudentsMinNumber() + ""));

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
        btn = new CustomButton("Back to result menu", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerleftPanel.add(btn);
        projectFrame.getFooter().add(tempPanelSec);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openResultPage(result, "Menu");
            }
        });
        tempPanel.add(footerleftPanel);
        JPanel footerRightPanel = new JPanel();
        footerRightPanel.setOpaque(false);
        footerRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tempPanel.add(footerRightPanel);
        btn = new CustomButton("Export", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerRightPanel.add(btn);
        projectFrame.getFooter().add(tempPanelSec);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int opt = chooser.showSaveDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    if (path == null) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please choose a valid path.", projectFrame.getClosebtn()));
                        return;
                    }
                    export(path);
                }
            }
        });
        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());

    }
    private String path;

    /**
     * Method used to export the result
     *
     * @param tempPath Path to export to
     */
    private void export(String tempPath) {
        path = tempPath;
        projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
        projectFrame.getLoadingDialog().open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                switch (projectFrame.getProject().getExportType()) {
                    case HTML:
                        try {
                            HTML html = new HTML(result);
                            if (!html.exportPatternList(path)) {
                                throw new Exception("An error occurred while exporting the pattern list.");
                            }
                            if (!html.exportSearchLog(path)) {
                                throw new Exception("An error occurred while exporting the search log");
                            }
                            if (!CSV.exportStudentList(path, result)) {
                                throw new Exception("An error occurred while exporting the student list.");
                            }
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Result exported successfully.", projectFrame.getClosebtn()));
                        } catch (Exception ex) {
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Result not exported.\n" + ex.getMessage(), projectFrame.getClosebtn()));
                        }
                        break;
                    case CSV:
                        try {
                            if (!CSV.exportPatternList(path, result)) {
                                throw new Exception("An error occurred while exporting the pattern list.");
                            }
                            if (!CSV.exportSearchLog(path, result)) {
                                throw new Exception("An error occurred while exporting the search log");
                            }
                            if (!CSV.exportStudentList(path, result)) {
                                throw new Exception("An error occurred while exporting the student list.");
                            }
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Result exported successfully.", projectFrame.getClosebtn()));
                        } catch (Exception ex) {
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Result not exported.\n" + ex.getMessage(), projectFrame.getClosebtn()));
                        }
                        break;
                    default:
                        try {
                            HTML html = new HTML(result);
                            if (!html.exportPatternList(path)) {
                                throw new Exception("An error occurred while exporting the pattern list.");
                            }
                            if (!html.exportSearchLog(path)) {
                                throw new Exception("An error occurred while exporting the search log");
                            }
                            if (!CSV.exportStudentList(path, result)) {
                                throw new Exception("An error occurred while exporting the student list.");
                            }
                            if (!CSV.exportSearchLog(path, result)) {
                                throw new Exception("An error occurred while exporting the search log");
                            }
                            if (!CSV.exportPatternList(path, result)) {
                                throw new Exception("An error occurred while exporting the pattern list.");
                            }
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Result exported successfully.", projectFrame.getClosebtn()));
                        } catch (Exception ex) {
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Result not exported.\n" + ex.getMessage(), projectFrame.getClosebtn()));
                        }
                        break;
                }
                return true;
            }
        };
        worker.execute();
    }

    /**
     * Method used to create a line with the desired information
     *
     * @param title Title of the information
     * @param content Text with the information
     * @return Panel with the information
     */
    private JPanel createShow(String title, String content) {
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 40));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(50);
        tempPanelSec.add(tempPanel);
        label = new JLabel(title, SwingConstants.RIGHT);
        label.setFont(projectFrame.getFontLight().deriveFont(18f));
        label.setForeground(Color.white);
        label.setPreferredSize(new Dimension(185, 35));
        textField = new JTextField();
        textField.setText("   " + content);
        textField.setFont(projectFrame.getFontRegular().deriveFont(20f));
        textField.setPreferredSize(new Dimension(700, 35));
        textField.setBackground(new Color(0, 0, 0, 0));
        textField.setForeground(Color.white);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
        textField.setEditable(false);
        tempPanel.add(label);
        tempPanel.add(textField);
        return tempPanelSec;
    }

    /**
     * Method used to get the result
     *
     * @return Result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Method used to set the result
     *
     * @param result Result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Method used to get the Parent Frame
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
