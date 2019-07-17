package GUI;

import Core.Result;
import Core.Search;
import IO.CSV;
import IO.HTML;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

/**
 * Class used to create the list result page
 *
 * @author Group 44 LAPR2
 */
public final class ListResultPage {

    private ProjectFrame projectFrame;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel label;
    private DefaultListModel modelList;
    private JList list;
    private JScrollPane scroolPane;
    private Result result;
    private JFileChooser chooser = new JFileChooser();

    /**
     * Constructor used to create the list result page
     *
     * @param projectFrame
     */
    public ListResultPage(ProjectFrame projectFrame) {
        setProjectFrame(projectFrame);
    }

    /**
     * Method used to open the list result page
     */
    public void open() {
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > List of Results");
        projectFrame.fadeOut(projectFrame.getContent());
        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        //List Search
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 360));
        if (existResults()) {
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanelSec = GUIFunctions.createLinePanel(370);
            tempPanelSec.add(tempPanel);
            modelList = new DefaultListModel();
            for (Search search : projectFrame.getProject().getSearchList()) {
                int index = 1;
                for (Result entry : search.getResult()) {
                    modelList.addElement(search.getName() + " > Result " + index + ": " + entry.getPatterns().size() + " patterns found for " + entry.getStudentList().size() + " students and " + entry.getUnitList().size() + " units. (min. common units = " + entry.getUnitsMinNumber() + ", min. students = " + entry.getStudentsMinNumber() + ")");
                    index++;
                }
            }
            list = new JList(modelList);
            list.setVisibleRowCount(10);
            list.setFont(projectFrame.getFontLight().deriveFont(18f));
            list.setForeground(Color.WHITE);
            list.setOpaque(false);
            list.setBackground(new Color(0, 0, 0, 0));

            scroolPane = new JScrollPane(list);
            scroolPane.setPreferredSize(new Dimension(890, 350));
            scroolPane.setOpaque(false);
            scroolPane.setBackground(new Color(0, 0, 0, 0));
            scroolPane.getViewport().setBackground(new Color(0, 0, 0, 0));
            scroolPane.getViewport().setOpaque(false);
            scroolPane.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            tempPanel.add(scroolPane);

            projectFrame.getContent().add(tempPanelSec);
        } else {
            tempPanel = new JPanel();
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(900, 150));
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanelSec = GUIFunctions.createLinePanel(160);
            tempPanelSec.add(tempPanel);
            panel.add(tempPanelSec);
            label = new JLabel("There are no saved result.");
            label.setFont(projectFrame.getFontLight().deriveFont(30f));
            label.setForeground(Color.white);
            tempPanel.add(label);
        }

        //Add buttons to footer
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 60));
        tempPanel.setLayout(new FlowLayout());
        tempPanelSec = GUIFunctions.createLinePanel(60);
        tempPanelSec.add(tempPanel);
        JPanel footerleftPanel = new JPanel();
        footerleftPanel.setOpaque(false);
        footerleftPanel.setPreferredSize(new Dimension(200, 60));
        footerleftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        footerleftPanel.add(projectFrame.openMainMenuBtn(false), FlowLayout.LEFT);
        tempPanel.add(footerleftPanel);
        JPanel footerRightPanel = new JPanel();
        footerRightPanel.setOpaque(false);
        footerRightPanel.setPreferredSize(new Dimension(690, 60));
        footerRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tempPanel.add(footerRightPanel);
        if (existResults()) {
            btn = new CustomButton("Export All", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
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
                        } else {
                            export(path);
                        }
                    }
                }
            });
            btn = new CustomButton("Open Result", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
            btn.addMouseListener(projectFrame.new HoverBtnEffect());
            footerRightPanel.add(btn);
            projectFrame.getFooter().add(tempPanelSec);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                    projectFrame.getLoadingDialog().open();
                    SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                        @Override
                        protected Boolean doInBackground() {
                            Object selected = list.getSelectedValue();
                            if (selected == null) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please select a result.", projectFrame.getClosebtn()));
                                return false;
                            }
                            String filter = selected.toString().substring(0, selected.toString().lastIndexOf(":"));
                            String searchName = filter.substring(0, filter.lastIndexOf(">")).trim();
                            String[] resultIndexStr = filter.substring(filter.lastIndexOf(">") + 1, filter.length()).trim().split(" ");
                            int resultIndex = 0;
                            try {
                                resultIndex = Integer.parseInt(resultIndexStr[1]) - 1;
                            } catch (Exception ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Error selecting the result, please try again.", projectFrame.getClosebtn()));
                                return false;
                            }
                            result = Search.getResultBySearchAndIndex(projectFrame.getProject().getSearchList(), searchName, resultIndex);
                            if (result == null) {
                                modelList.removeElement(selected);
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "We are sorry, the result was not found.", projectFrame.getClosebtn()));
                                return false;
                            }
                            projectFrame.getLoadingDialog().close();
                            projectFrame.openResultPage(result, "Main");
                            return true;
                        }
                    };
                    worker.execute();
                }
            });
        }
        projectFrame.getFooter().add(tempPanelSec);

        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());
    }

    /**
     * Method used to see if exist any result
     *
     * @return True if exists any result
     */
    private boolean existResults() {
        for (Search search : projectFrame.getProject().getSearchList()) {
            if (!search.getResult().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    private String path;
    private ArrayList<Result> resultList;

    /**
     * Method used to export all results
     *
     * @param tempPath Path to export
     */
    private void export(String tempPath) {
        path = tempPath;
        projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
        projectFrame.getLoadingDialog().open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                int resultsExported = 0;
                int totalResults = 0;
                for (Search search : projectFrame.getProject().getSearchList()) {
                    for (Result entryResult : search.getResult()) {
                        totalResults++;
                        switch (projectFrame.getProject().getExportType()) {
                            case HTML:
                                try {
                                    HTML html = new HTML(entryResult);
                                    html.exportPatternList(path);
                                    html.exportSearchLog(path);
                                    CSV.exportStudentList(path, entryResult);
                                    resultsExported++;
                                } catch (Exception ex) {
                                }
                                break;
                            case CSV:
                                try {
                                    CSV.exportStudentList(path, entryResult);
                                    CSV.exportSearchLog(path, entryResult);
                                    CSV.exportPatternList(path, entryResult);
                                    resultsExported++;
                                } catch (Exception ex) {
                                }
                                break;
                            default:
                                try {
                                    HTML html = new HTML(entryResult);
                                    html.exportPatternList(path);
                                    html.exportSearchLog(path);
                                    CSV.exportStudentList(path, entryResult);
                                    CSV.exportSearchLog(path, entryResult);
                                    CSV.exportPatternList(path, entryResult);
                                    resultsExported++;
                                } catch (Exception ex) {
                                }
                                break;
                        }
                    }
                }
                projectFrame.getLoadingDialog().close();
                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Total results expoted: " + resultsExported + "\n Failed results export: " + (totalResults - resultsExported), projectFrame.getClosebtn()));
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
     * Method used to set the Parent Frame
     *
     * @param projectFrame Parent Frame
     */
    public void setProjectFrame(ProjectFrame projectFrame) {
        this.projectFrame = projectFrame;
    }
}
