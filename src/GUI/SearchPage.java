package GUI;

import Core.Search;
import Core.Unit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;

/**
 * Class used to create the search page
 *
 * @author Group 44 LAPR2
 */
public final class SearchPage {

    private ProjectFrame projectFrame;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel label;
    private JTextField searchNameField;
    private JList leftProjectList;
    private DefaultListModel leftProjectModelList;
    private JScrollPane leftListPaneProject;
    private JList rightProjectList;
    private DefaultListModel rightProjectModelList;
    private JScrollPane rightListPaneProject;
    private JSpinner unitSpinner;
    private SpinnerNumberModel unitModelSpinner;
    private JSpinner studentSpinner;
    private SpinnerNumberModel studentModelSpinner;
    private Search search;
    private boolean edit;
    private int index;

    /**
     * Constructor used to create the search page
     *
     * @param projectFrame Parent frame
     */
    public SearchPage(ProjectFrame projectFrame) {
        setProjectFrame(projectFrame);
    }

    /**
     * Method used to open the search page
     *
     * @param paramSearch Parent search
     * @param tempEdit True if edit the search
     */
    public void open(Search paramSearch, boolean tempEdit) {
        this.edit = tempEdit;
        if (edit && paramSearch != null) {
            search = paramSearch;
        } else if (paramSearch != null) {
            search = new Search(paramSearch);
        } else {
            search = null;
        }
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > Search");
        projectFrame.fadeOut(projectFrame.getContent());
        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        //Search name definition
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 50));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(60);
        tempPanelSec.add(tempPanel);
        label = new JLabel("SEARCH NAME: ");
        label.setFont(projectFrame.getFontLight().deriveFont(22f));
        label.setForeground(Color.white);
        searchNameField = new JTextField();
        searchNameField.setFont(projectFrame.getFontRegular().deriveFont(16f));
        searchNameField.setPreferredSize(new Dimension(700, 45));
        searchNameField.setBackground(Color.white);
        searchNameField.setOpaque(true);
        searchNameField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        if (search != null) {
            searchNameField.setText(search.getName());
        }
        tempPanel.add(label);
        tempPanel.add(searchNameField);
        panel.add(tempPanelSec);

        //Unit list selection title
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 50));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(40);
        tempPanelSec.add(tempPanel);
        label = new JLabel("CHOOSE THE UNITS INVOLVED: ");
        label.setFont(projectFrame.getFontLight().deriveFont(22f));
        label.setForeground(Color.white);
        tempPanel.add(label);
        panel.add(tempPanelSec);

        //Unit list selection
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 220));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(230);
        tempPanelSec.add(tempPanel);

        //LEFT LIST
        JPanel midPanel = new JPanel();
        midPanel.setPreferredSize(new Dimension(375, 210));
        midPanel.setOpaque(false);
        leftProjectModelList = new DefaultListModel();
        if (search == null) {
            for (Unit unit : projectFrame.getProject().getUnits()) {
                leftProjectModelList.addElement(unit.getName() + " (" + unit.getId() + ")");
            }
        } else {
            for (Unit unit : projectFrame.getProject().getUnits()) {
                if (!search.getUnitList().contains(unit)) {
                    leftProjectModelList.addElement(unit.getName() + " (" + unit.getId() + ")");
                }
            }
        }
        leftProjectList = new JList(leftProjectModelList);
        leftProjectList.setVisibleRowCount(10);
        leftProjectList.setFont(projectFrame.getFontLight().deriveFont(18f));
        leftProjectList.setForeground(Color.WHITE);
        leftProjectList.setOpaque(false);
        leftProjectList.setBackground(new Color(0, 0, 0, 0));

        leftListPaneProject = new JScrollPane(leftProjectList);
        leftListPaneProject.setPreferredSize(new Dimension(375, 200));
        leftListPaneProject.setOpaque(false);
        leftListPaneProject.setBackground(new Color(0, 0, 0, 0));
        leftListPaneProject.getViewport().setBackground(new Color(0, 0, 0, 0));
        leftListPaneProject.getViewport().setOpaque(false);
        leftListPaneProject.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));

        midPanel.add(leftListPaneProject);
        tempPanel.add(midPanel);

        //MIDDLE ADD BUTTONS
        midPanel = new JPanel();
        midPanel.setPreferredSize(new Dimension(120, 210));
        midPanel.setOpaque(false);

        btn = new CustomButton("<< remove all", new Dimension(110, 52), null, 10f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList objectList = new ArrayList<>();
                for (int i = 0; i < rightProjectModelList.getSize(); i++) {
                    objectList.add(rightProjectModelList.get(i));
                }
                for (Object entry : objectList) {
                    rightProjectModelList.removeElement(entry);
                    leftProjectModelList.addElement(entry);
                }
                if (((int) unitSpinner.getValue()) > rightProjectModelList.size()) {
                    unitModelSpinner.setValue(rightProjectModelList.size());
                }
                unitModelSpinner.setMaximum(rightProjectModelList.size());
            }
        });
        midPanel.add(btn);

        btn = new CustomButton("<< remove", new Dimension(110, 40), null, 10f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List selectedList = rightProjectList.getSelectedValuesList();
                if (selectedList == null || selectedList.isEmpty()) {
                    if (((int) unitSpinner.getValue()) > rightProjectModelList.size()) {
                        unitModelSpinner.setValue(rightProjectModelList.size());
                    }
                    unitModelSpinner.setMaximum(rightProjectModelList.size());
                    return;
                }
                for (Object entry : selectedList) {
                    rightProjectModelList.removeElement(entry);
                    leftProjectModelList.addElement(entry);
                }
                if (((int) unitSpinner.getValue()) > rightProjectModelList.size()) {
                    unitModelSpinner.setValue(rightProjectModelList.size());
                }
                unitModelSpinner.setMaximum(rightProjectModelList.size());
            }
        });
        midPanel.add(btn);

        btn = new CustomButton("add >>", new Dimension(110, 40), null, 10f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List selectedList = leftProjectList.getSelectedValuesList();
                if (selectedList == null || selectedList.isEmpty()) {
                    unitModelSpinner.setValue(rightProjectModelList.size());
                    unitModelSpinner.setMaximum(rightProjectModelList.size());
                    return;
                }
                for (Object entry : selectedList) {
                    leftProjectModelList.removeElement(entry);
                    rightProjectModelList.addElement(entry);
                }
                unitModelSpinner.setValue(rightProjectModelList.size());
                unitModelSpinner.setMaximum(rightProjectModelList.size());
            }
        });
        midPanel.add(btn);

        btn = new CustomButton("add all >>", new Dimension(110, 52), null, 10f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList objectList = new ArrayList<>();
                for (int i = 0; i < leftProjectModelList.getSize(); i++) {
                    objectList.add(leftProjectModelList.get(i));
                }
                for (Object entry : objectList) {
                    leftProjectModelList.removeElement(entry);
                    rightProjectModelList.addElement(entry);
                }
                unitModelSpinner.setValue(rightProjectModelList.size());
                unitModelSpinner.setMaximum(rightProjectModelList.size());
            }
        });
        midPanel.add(btn);

        tempPanel.add(midPanel);

        //RIGHT LIST
        midPanel = new JPanel();
        midPanel.setPreferredSize(new Dimension(375, 210));
        midPanel.setOpaque(false);

        rightProjectModelList = new DefaultListModel();
        if (search != null) {
            for (Unit unit : search.getUnitList()) {
                rightProjectModelList.addElement(unit.getName() + " (" + unit.getId() + ")");
            }
        }

        rightProjectList = new JList(rightProjectModelList);
        rightProjectList.setVisibleRowCount(10);
        rightProjectList.setFont(projectFrame.getFontLight().deriveFont(18f));
        rightProjectList.setForeground(Color.WHITE);
        rightProjectList.setOpaque(false);
        rightProjectList.setBackground(new Color(0, 0, 0, 0));

        rightListPaneProject = new JScrollPane(rightProjectList);
        rightListPaneProject.setPreferredSize(new Dimension(375, 200));
        rightListPaneProject.setOpaque(false);
        rightListPaneProject.setBackground(new Color(0, 0, 0, 0));
        rightListPaneProject.getViewport().setBackground(new Color(0, 0, 0, 0));
        rightListPaneProject.getViewport().setOpaque(false);
        rightListPaneProject.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));

        midPanel.add(rightListPaneProject);
        tempPanel.add(midPanel);

        panel.add(tempPanelSec);

        //Mininum units common
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 40));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(50);
        tempPanelSec.add(tempPanel);
        label = new JLabel("Minimum common units: ");
        label.setFont(projectFrame.getFontLight().deriveFont(22f));
        label.setForeground(Color.white);
        tempPanel.add(label);
        if (search == null) {
            unitModelSpinner = new SpinnerNumberModel(rightProjectModelList.size(), 0, rightProjectModelList.size(), 1);
        } else {
            unitModelSpinner = new SpinnerNumberModel(search.getMinUnits(), 0, rightProjectModelList.size(), 1);
        }
        unitSpinner = new JSpinner(unitModelSpinner);
        unitSpinner.setPreferredSize(new Dimension(100, 30));
        unitSpinner.setFont(projectFrame.getFontLight().deriveFont(22f));
        unitSpinner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        unitSpinner.setOpaque(true);
        unitSpinner.setBackground(Color.white);
        unitSpinner.setForeground(Color.black);
        tempPanel.add(unitSpinner);
        panel.add(tempPanelSec);

        //Mininum students per pattern
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 40));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(50);
        tempPanelSec.add(tempPanel);
        label = new JLabel("Minimum students per pattern: ");
        label.setFont(projectFrame.getFontLight().deriveFont(22f));
        label.setForeground(Color.white);
        tempPanel.add(label);
        if (search == null) {
            studentModelSpinner = new SpinnerNumberModel(1, 1, projectFrame.getProject().getStudents().size(), 1);
        } else {
            studentModelSpinner = new SpinnerNumberModel(search.getMinStudents(), 1, projectFrame.getProject().getStudents().size(), 1);
        }
        studentSpinner = new JSpinner(studentModelSpinner);
        studentSpinner.setPreferredSize(new Dimension(100, 30));
        studentSpinner.setFont(projectFrame.getFontLight().deriveFont(22f));
        studentSpinner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        studentSpinner.setOpaque(true);
        studentSpinner.setBackground(Color.white);
        studentSpinner.setForeground(Color.black);
        tempPanel.add(studentSpinner);
        panel.add(tempPanelSec);

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
        footerleftPanel.add(projectFrame.openMainMenuBtn(false), FlowLayout.LEFT);
        tempPanel.add(footerleftPanel);
        JPanel footerRightPanel = new JPanel();
        footerRightPanel.setOpaque(false);
        footerRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tempPanel.add(footerRightPanel);
        btn = new CustomButton("Save Search", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
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
                        if (saveSearch()) {
                            projectFrame.getLoadingDialog().close();
                            if (!edit) {
                                projectFrame.getProject().getSearchList().add(getSearch());
                            }
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Search saved successfully.", projectFrame.getClosebtn()));
                            projectFrame.openPage("List of searchs");
                        } else {
                            projectFrame.getLoadingDialog().close();
                        }
                        return true;
                    }
                };
                worker.execute();
            }
        });
        btn = new CustomButton("Save & Run Search", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
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
                        if (saveSearch()) {
                            if (!edit) {
                                projectFrame.getProject().getSearchList().add(getSearch());
                            }
                            try {
                                getSearch().runSearch();
                                index = getSearch().getResult().size() - 1;
                                if (index >= 0) {
                                    projectFrame.getLoadingDialog().close();
                                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Search saved and runned in " + getSearch().getRunTime() + "(ms).\n" + getSearch().getResult().get(index).getPatterns().size() + " patterns found.", projectFrame.getClosebtn()));
                                } else {
                                    projectFrame.getLoadingDialog().close();
                                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Search saved and runned in " + getSearch().getRunTime() + "(ms).\nNo results saved.", projectFrame.getClosebtn()));
                                }
                            } catch (Exception ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Search saved but not runned.\n" + ex.getMessage(), projectFrame.getClosebtn()));
                            }
                            projectFrame.getLoadingDialog().close();
                            if (index >= 0) {
                                projectFrame.openResultPage(getSearch().getResult().get(index), "Main");
                            } else {
                                projectFrame.openPage("List of searchs");
                            }
                        } else {
                            projectFrame.getLoadingDialog().close();
                        }
                        return true;
                    }
                };
                worker.execute();
            }
        });
        projectFrame.getFooter().add(tempPanelSec);

        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());
    }

    /**
     * Method used to save a search
     *
     * @return true if saved
     */
    private boolean saveSearch() {
        String searchName = searchNameField.getText();
        if (searchName == null || searchName.isEmpty()) {
            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please give a name to the search.", projectFrame.getClosebtn()));
            return false;
        }
        if (Search.getSearchByName(projectFrame.getProject().getSearchList(), searchName) != null) {
            if (!edit || !(search != null && search.getName().equalsIgnoreCase(searchName))) {
                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "There is already a search with this name.\nPlease give another name.", projectFrame.getClosebtn()));
                return false;
            }
        }
        if (rightProjectModelList.getSize() == 0) {
            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please choose the units.", projectFrame.getClosebtn()));
            return false;
        }
        ArrayList<Unit> unitList = new ArrayList<>();
        for (int i = 0; i < rightProjectModelList.size(); i++) {
            String entry = rightProjectModelList.get(i).toString();
            String unitID = entry.substring(entry.lastIndexOf("(") + 1, entry.length() - 1);
            try {
                Unit unit = Unit.getUnit(projectFrame.getProject().getUnits(), unitID);
                unitList.add(unit);
            } catch (Exception ex) {
                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Error loading units list.\n" + ex.getMessage(), projectFrame.getClosebtn()));
                return false;
            }
        }
        int commonUnit = (int) unitSpinner.getValue();
        if (commonUnit == 0) {
            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please choose the minimum number of\n common units per pattern.", projectFrame.getClosebtn()));
            return false;
        }
        int minStudent = (int) studentSpinner.getValue();
        if (minStudent == 0) {
            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please choose the minimum number of\n students per pattern.", projectFrame.getClosebtn()));
            return false;
        }
        searchName = searchName.trim();
        try {
            if (edit && search != null) {
                search.removeResults();
            }
            if (search == null) {
                search = new Search(projectFrame.getProject().getName(), searchName, projectFrame.getProject().getStudents(), unitList, commonUnit, minStudent);
            } else {
                search.setProjectName(projectFrame.getProject().getName());
                search.setName(searchName);
                search.setStudentList(projectFrame.getProject().getStudents());
                search.setUnitList(unitList);
                search.setMinUnits(commonUnit);
                search.setMinStudents(minStudent);
            }
        } catch (Exception ex) {
            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Search not saved.\n" + ex.getMessage(), projectFrame.getClosebtn()));
        }
        return true;
    }

    /**
     * Method used to get the parent frame
     *
     * @return Parent frame
     */
    public ProjectFrame getProjectFrame() {
        return projectFrame;
    }

    /**
     * Method used to set the parent frame
     *
     * @param projectFrame Parent frame
     */
    public void setProjectFrame(ProjectFrame projectFrame) {
        this.projectFrame = projectFrame;
    }

    /**
     * Method used to get the search
     *
     * @return Search
     */
    public Search getSearch() {
        return search;
    }

    /**
     * Method used to set the search
     *
     * @param search Search
     */
    public void setSearch(Search search) {
        this.search = search;
    }
}
