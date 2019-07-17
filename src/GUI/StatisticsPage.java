package GUI;

import Core.Pattern;
import Core.Result;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 * Class used to create the statistics page
 *
 * @author Group 44 LAPR2
 */
public final class StatisticsPage {

    private ProjectFrame projectFrame;
    private Result result;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel label;
    private JPanel panePattern;
    private DefaultListModel avgModelList;
    private JList avgList;
    private JScrollPane avgScrollPane;
    private ArrayList<Pattern> patternFiltered;
    private DefaultListModel modelList;
    private JList list;
    private JPanel footerRightPanel;
    private JTextField textField;
    private Pattern foundPattern;

    /**
     * Constructor used to create the statistics page
     *
     * @param projectFrame
     * @param result
     */
    public StatisticsPage(ProjectFrame projectFrame, Result result) {
        setProjectFrame(projectFrame);
        setResult(result);
    }

    /**
     * Method used to open the statistics page
     *
     * @param tempResult Result to be shown
     * @param tempPattern Pattern to be shown
     */
    public void open(Result tempResult, Pattern tempPattern) {
        setResult(tempResult);
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > " + result.getSearchName() + " > Result > Statistics");
        projectFrame.fadeOut(projectFrame.getContent());

        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        //Options
        tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(280, 540));
        //Options title
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(280, 30));
        label = new JLabel("SELECT PATTERN", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(18f));
        label.setForeground(Color.white);
        tempPanelSec.add(label);
        tempPanel.add(tempPanelSec);

        //Options by pattern number 
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(270, 450));

        JPanel panelPatterns = new JPanel(new GridLayout());
        panelPatterns.setOpaque(false);
        panelPatterns.setPreferredSize(new Dimension(260, 360));

        modelList = new DefaultListModel();
        int index = 1;
        for (Pattern entry : result.getPatterns()) {
            modelList.addElement("Pattern " + index);
            index++;
        }
        list = new JList(modelList);
        list.setVisibleRowCount(10);
        list.setFont(projectFrame.getFontLight().deriveFont(14f));
        list.setForeground(Color.WHITE);
        list.setOpaque(false);
        list.setBackground(new Color(0, 0, 0, 0));

        JScrollPane scrollPaneLeft = new JScrollPane(list);
        scrollPaneLeft.setOpaque(false);
        scrollPaneLeft.setBackground(new Color(0, 0, 0, 0));
        scrollPaneLeft.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPaneLeft.getViewport().setOpaque(false);
        scrollPaneLeft.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));

        panelPatterns.add(scrollPaneLeft);
        tempPanelSec.add(panelPatterns);
        //Add button
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                projectFrame.getLoadingDialog().open();
                SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() {
                        Object entry = list.getSelectedValue();
                        if (entry == null) {
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please select a pattern.", projectFrame.getClosebtn()));
                            return false;
                        }
                        String filtered = entry.toString().replaceAll("Pattern ", "").trim();
                        ArrayList<Integer> listOfPatternNumbers = new ArrayList<>();
                        try {
                            listOfPatternNumbers.add(Integer.parseInt(filtered) - 1);
                        } catch (Exception e) {
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Invalid pattern.", projectFrame.getClosebtn()));
                            return false;
                        }
                        patternFiltered = result.getPatternByNumbers(listOfPatternNumbers);
                        if (patternFiltered == null || patternFiltered.isEmpty()) {
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Invalid pattern.", projectFrame.getClosebtn()));
                            return false;
                        }
                        foundPattern = patternFiltered.get(0);
                        showStatistics("Statistics for pattern " + filtered, foundPattern);
                        projectFrame.getLoadingDialog().close();
                        return true;
                    }
                };
                worker.execute();
            }
        });
        tempPanel.add(tempPanelSec);

        //Options title
        tempPanel.add(tempPanelSec);
        panel.add(tempPanel);

        //Table
        panePattern = new JPanel();
        panePattern.setOpaque(false);
        panePattern.setPreferredSize(new Dimension(610, 540));

        if (tempPattern == null) {
            showStatistics("        Please select a pattern.", foundPattern);
        } else {
            foundPattern = tempPattern;
            showStatistics("Selected Pattern statistics.", foundPattern);
        }
        //pattern table
        panel.add(panePattern);

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
                foundPattern = null;
                projectFrame.openResultPage(result, "Menu");
            }
        });
        tempPanel.add(footerleftPanel);
        footerRightPanel = new JPanel();
        footerRightPanel.setOpaque(false);
        footerRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btn = new CustomButton("Back to filter results", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerleftPanel.add(btn);
        projectFrame.getFooter().add(tempPanelSec);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foundPattern = null;
                projectFrame.openResultPage(result, "Filter result");
            }
        });
        footerRightPanel.add(btn);

        tempPanel.add(footerRightPanel);
        projectFrame.getFooter().add(tempPanelSec);

        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());
    }

    /**
     * Method used to show the statistics for a pattern
     *
     * @param title Title of the details
     * @param tempPattern Pattern to be shown
     */
    private void showStatistics(String title, Pattern tempPattern) {
        panePattern.removeAll();
        if (tempPattern == null) {
            panePattern.setLayout(new GridLayout());
            label = new JLabel(title);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontRegular().deriveFont(24f));
            label.setForeground(Color.white);
            panePattern.add(label);
        } else {
            foundPattern = tempPattern;
            panePattern.setLayout(new FlowLayout());
            //Title
            tempPanel = new JPanel();
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(600, 50));
            label = new JLabel(title);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontRegular().deriveFont(24f));
            label.setForeground(Color.white);
            tempPanel.add(label);
            panePattern.add(tempPanel);
            //Average and standard deviation of student´s grades in a pattern
            tempPanel = new JPanel();
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(600, 50));
            label = new JLabel("Average and standard deviation of student´s grades in a pattern.");
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontLight().deriveFont(18f));
            label.setForeground(Color.white);
            tempPanel.add(label);
            panePattern.add(tempPanel);
            //By programme's average grade
            tempPanel = new JPanel();
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(600, 35));
            label = new JLabel("By programme's average grade: ");
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontLight().deriveFont(14f));
            label.setForeground(Color.white);
            label.setPreferredSize(new Dimension(185, 30));
            tempPanel.add(label);
            String textContent = "N/A";
            if (foundPattern != null && projectFrame.getProject().getSelectedScale() != null) {
                try {
                    textContent = result.getAvgStdGrade(foundPattern.getPattern(), projectFrame.getProject().getHeaderIndexECTS(), projectFrame.getProject().getSelectedScale());
                } catch (Exception ex) {
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Error calculating.\n" + ex.getMessage(), projectFrame.getClosebtn()));
                }
            }
            textField = new JTextField();
            textField.setText("   " + textContent);
            textField.setFont(projectFrame.getFontRegular().deriveFont(16f));
            textField.setPreferredSize(new Dimension(360, 30));
            textField.setBackground(new Color(0, 0, 0, 0));
            textField.setForeground(Color.white);
            textField.setOpaque(false);
            textField.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            textField.setEditable(false);
            tempPanel.add(textField);

            panePattern.add(tempPanel);
            //By unit included in the pattern
            tempPanel = new JPanel();
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(600, 190));
            label = new JLabel("By unit included in the pattern: ");
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontLight().deriveFont(14f));
            label.setForeground(Color.white);
            label.setPreferredSize(new Dimension(185, 30));
            tempPanel.add(label);
            tempPanelSec = new JPanel(new GridLayout());
            tempPanelSec.setOpaque(false);
            tempPanelSec.setPreferredSize(new Dimension(550, 145));

            avgModelList = new DefaultListModel();

            ArrayList<String> resList = new ArrayList<>();
            try {
                resList = result.getAverageStdByUnit(foundPattern.getPattern(), projectFrame.getProject().getSelectedScale());
            } catch (Exception ex) {
                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Error calculating.\n" + ex.getMessage(), projectFrame.getClosebtn()));
            }
            if (resList == null || resList.isEmpty()) {
                resList = new ArrayList<>();
                resList.add("N/A");
            }
            for (String entry : resList) {
                avgModelList.addElement(entry);
            }
            avgList = new JList(avgModelList);
            avgList.setVisibleRowCount(10);
            avgList.setFont(projectFrame.getFontLight().deriveFont(14f));
            avgList.setForeground(Color.WHITE);
            avgList.setOpaque(false);
            avgList.setBackground(new Color(0, 0, 0, 0));

            avgScrollPane = new JScrollPane(avgList);
            avgScrollPane.setOpaque(false);
            avgScrollPane.setBackground(new Color(0, 0, 0, 0));
            avgScrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
            avgScrollPane.getViewport().setOpaque(false);
            avgScrollPane.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            tempPanelSec.add(avgScrollPane);
            tempPanel.add(tempPanelSec);
            panePattern.add(tempPanel);

            //Average and standard deviation of student's number of years to graduate included in a pattern
            tempPanel = new JPanel();
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(600, 80));
            label = new JLabel("Average and standard deviation of student's number of years to graduate included in a pattern: ");
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontLight().deriveFont(14f));
            label.setForeground(Color.white);
            tempPanel.add(label);
            textField = new JTextField();
            textContent = "N/A";
            if (foundPattern != null && projectFrame.getProject().getSelectedScale() != null) {
                try {
                    textContent = result.getAvgStdYearsGraduate(foundPattern.getPattern(), projectFrame.getProject().getEquivalenceYear());
                } catch (Exception ex) {
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Error calculating.\n" + ex.getMessage(), projectFrame.getClosebtn()));
                }
            }
            textField.setText("   " + textContent);
            textField.setFont(projectFrame.getFontRegular().deriveFont(16f));
            textField.setPreferredSize(new Dimension(550, 30));
            textField.setBackground(new Color(0, 0, 0, 0));
            textField.setForeground(Color.white);
            textField.setOpaque(false);
            textField.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            textField.setEditable(false);
            tempPanel.add(textField);
            label = new JLabel(projectFrame.getProject().getSelectedScaleInfo());
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontLight().deriveFont(14f));
            label.setForeground(Color.white);
            tempPanel.add(label);
            panePattern.add(tempPanel);

        }
        panePattern.revalidate();
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
