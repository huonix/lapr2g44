package GUI;

import Core.Project;
import Core.Student;
import Core.Unit;
import IO.CSV;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * Class used to create a new project page
 *
 * @author Group 44 LAPR2
 */
public final class NewProjectPage {

    private ProjectFrame projectFrame;
    private JTextField projectNameField;
    private JButton loadUnitsBtn;
    private JButton loadStudentsBtn;
    private JFileChooser chooser = new JFileChooser();
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel labelUnitsNumber;
    private JLabel labelStudentsNumber;
    private JLabel label;
    private JComboBox exportCombo;
    private ComboBoxModel exportComboModel;
    private JComboBox scalesCombo;
    private ComboBoxModel scalesComboModel;
    private JComboBox weightCombo;
    private ComboBoxModel weightComboModel;
    private JTextField equivalenceYearField;

    /**
     * Constructor used to create the new project page
     *
     * @param projectFrame
     */
    public NewProjectPage(ProjectFrame projectFrame) {
        setProjectFrame(projectFrame);
    }

    /**
     * Method used to open the new project page
     */
    public void open() {
        projectFrame.updatePageTitle("ISEP > New Project");
        projectFrame.fadeOut(projectFrame.getContent());

        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        //Project name definition
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 50));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(60);
        tempPanelSec.add(tempPanel);
        label = new JLabel("PROJECT NAME: ");
        label.setFont(projectFrame.getFontLight().deriveFont(22f));
        label.setForeground(Color.white);
        projectNameField = new JTextField();
        projectNameField.setFont(projectFrame.getFontRegular().deriveFont(16f));
        projectNameField.setPreferredSize(new Dimension(700, 45));
        projectNameField.setBackground(Color.white);
        projectNameField.setOpaque(true);
        projectNameField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        tempPanel.add(label);
        tempPanel.add(projectNameField);
        panel.add(tempPanelSec);

        //Unit file upload
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 150));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(160);
        tempPanelSec.add(tempPanel);
        loadUnitsBtn = new CustomButton("Load Units", projectFrame.getMediumButtonSize(), "/images/data/units.png", 16f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        loadUnitsBtn.addMouseListener(projectFrame.new HoverBtnEffect());
        tempPanel.add(loadUnitsBtn, FlowLayout.LEFT);
        panel.add(tempPanelSec);
        loadUnitsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = chooser.showOpenDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                    projectFrame.getLoadingDialog().open();
                    SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                        @Override
                        protected Boolean doInBackground() throws Exception {
                            try {
                                String caminho = chooser.getSelectedFile().getAbsolutePath();
                                ArrayList<Unit> unitList = CSV.readUnitFile(caminho);
                                if (unitList.isEmpty()) {
                                    projectFrame.getLoadingDialog().close();
                                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "There are no unit in the file.\n Please choose another file.", projectFrame.getClosebtn()));
                                    return false;
                                } else {
                                    projectFrame.getProject().setUnits(unitList);
                                    labelUnitsNumber.setText(Integer.toString(projectFrame.getProject().getUnits().size()));
                                    projectFrame.getProject().setStudents(null);
                                    projectFrame.getProject().setHeaderIndexECTS(-1);
                                    Student.equivalenceYear = 0;
                                    equivalenceYearField.setText("0");
                                    labelStudentsNumber.setText("0");
                                    projectFrame.getProject().setEquivalenceYear(0);
                                    List listWeight = new ArrayList();
                                    Object selectWeight = null;
                                    for (int i = 4; i < unitList.get(0).getHeaders().length; i++) {
                                        listWeight.add(unitList.get(0).getHeaders()[i]);
                                        if (unitList.get(0).getHeaders()[i].equalsIgnoreCase("WCTS")) {
                                            selectWeight = unitList.get(0).getHeaders()[i];
                                            scalesComboModel.setSelectedItem("T.D.P.A.E.O");
                                        } else if (unitList.get(0).getHeaders()[i].equalsIgnoreCase("ECTS")) {
                                            selectWeight = unitList.get(0).getHeaders()[i];
                                            scalesComboModel.setSelectedItem("0...20");
                                        }
                                    }
                                    weightComboModel = new DefaultComboBoxModel(listWeight.toArray());
                                    if (selectWeight != null) {
                                        weightComboModel.setSelectedItem(selectWeight);
                                    }
                                    weightCombo.setModel(weightComboModel);
                                }
                            } catch (FileNotFoundException ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, ex.getMessage(), projectFrame.getClosebtn()));
                                return false;
                            } catch (IOException ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, ex.getMessage(), projectFrame.getClosebtn()));
                                return false;
                            } catch (Exception ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, ex.getMessage(), projectFrame.getClosebtn()));
                                return false;
                            }
                            projectFrame.getLoadingDialog().close();
                            return true;
                        }
                    };
                    worker.execute();
                }
            }
        });
        label = new JLabel("   Number of units loaded:  ");
        label.setFont(projectFrame.getFontLight().deriveFont(30f));
        label.setForeground(Color.white);
        tempPanel.add(label);
        labelUnitsNumber = new JLabel("0");
        labelUnitsNumber.setFont(projectFrame.getFontRegular().deriveFont(40f));
        labelUnitsNumber.setForeground(Color.white);
        tempPanel.add(labelUnitsNumber);

        //Student file upload
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 150));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec = GUIFunctions.createLinePanel(160);
        tempPanelSec.add(tempPanel);
        loadStudentsBtn = new CustomButton("Load Students", projectFrame.getMediumButtonSize(), "/images/data/student.png", 16f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        loadStudentsBtn.addMouseListener(projectFrame.new HoverBtnEffect());
        loadStudentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = chooser.showOpenDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                    projectFrame.getLoadingDialog().open();
                    SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                        @Override
                        protected Boolean doInBackground() {
                            try {
                                String caminho = chooser.getSelectedFile().getAbsolutePath();
                                ArrayList<Student> studentList = CSV.readStudentsFile(caminho, projectFrame.getProject().getUnits());
                                if (studentList.isEmpty()) {
                                    projectFrame.getLoadingDialog().close();
                                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "There are no student in the file.\n Please choose another file.", projectFrame.getClosebtn()));
                                    return false;
                                } else {
                                    projectFrame.getProject().setStudents(studentList);
                                    labelStudentsNumber.setText(Integer.toString(projectFrame.getProject().getStudents().size()));
                                    equivalenceYearField.setText(Student.equivalenceYear + "");
                                }
                            } catch (FileNotFoundException ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, ex.getMessage(), projectFrame.getClosebtn()));
                                return false;
                            } catch (IOException ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, ex.getMessage(), projectFrame.getClosebtn()));
                                return false;
                            } catch (Exception ex) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, ex.getMessage(), projectFrame.getClosebtn()));
                                return false;
                            }
                            projectFrame.getLoadingDialog().close();
                            return true;
                        }
                    };
                    worker.execute();
                }
            }
        });
        tempPanel.add(loadStudentsBtn, FlowLayout.LEFT);
        panel.add(tempPanelSec);
        label = new JLabel("   Number of students loaded:  ");
        label.setFont(projectFrame.getFontLight().deriveFont(30f));
        label.setForeground(Color.white);
        tempPanel.add(label);
        labelStudentsNumber = new JLabel("0");
        labelStudentsNumber.setFont(projectFrame.getFontRegular().deriveFont(40f));
        labelStudentsNumber.setForeground(Color.white);
        tempPanel.add(labelStudentsNumber);

        //Project export type  definition
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(200, 100));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Export type: ");
        label.setPreferredSize(new Dimension(190, 30));
        label.setFont(projectFrame.getFontLight().deriveFont(16f));
        label.setForeground(Color.white);
        tempPanel.add(label);

        exportComboModel = new DefaultComboBoxModel(Project.ExportType.values());
        exportCombo = new JComboBox(exportComboModel);
        exportCombo.setFont(projectFrame.getFontLight().deriveFont(16f));
        exportCombo.setOpaque(false);
        exportCombo.setForeground(Color.BLACK);
        exportCombo.setMinimumSize(new Dimension(100, 40));
        exportCombo.setPreferredSize(new Dimension(100, 40));
        exportCombo.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
        tempPanel.add(exportCombo);
        panel.add(tempPanel);

        //Add equivalence year
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(200, 100));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Equivalence year: ");
        label.setPreferredSize(new Dimension(190, 30));
        label.setFont(projectFrame.getFontLight().deriveFont(16f));
        label.setForeground(Color.white);
        tempPanel.add(label);

        equivalenceYearField = new JTextField("0");
        equivalenceYearField.setFont(projectFrame.getFontRegular().deriveFont(16f));
        equivalenceYearField.setPreferredSize(new Dimension(80, 40));
        equivalenceYearField.setBackground(Color.white);
        equivalenceYearField.setOpaque(true);
        equivalenceYearField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        tempPanel.add(equivalenceYearField);

        panel.add(tempPanel);

        //Project scales type definition
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(200, 100));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Scales: ");
        label.setFont(projectFrame.getFontLight().deriveFont(16f));
        label.setPreferredSize(new Dimension(190, 30));
        label.setForeground(Color.white);
        tempPanel.add(label);

        scalesComboModel = new DefaultComboBoxModel(projectFrame.getProject().getPredefinedScales().keySet().toArray());
        scalesCombo = new JComboBox(scalesComboModel);
        scalesCombo.setFont(projectFrame.getFontLight().deriveFont(16f));
        scalesCombo.setOpaque(false);
        scalesCombo.setForeground(Color.BLACK);
        scalesCombo.setMinimumSize(new Dimension(100, 40));
        scalesCombo.setPreferredSize(new Dimension(100, 40));
        scalesCombo.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
        tempPanel.add(scalesCombo);
        panel.add(tempPanel);

        //Project scales type definition
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(200, 100));
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Credits: ");
        label.setPreferredSize(new Dimension(190, 30));
        label.setFont(projectFrame.getFontLight().deriveFont(16f));
        label.setForeground(Color.white);
        tempPanel.add(label);

        weightComboModel = new DefaultComboBoxModel();
        weightCombo = new JComboBox(weightComboModel);
        weightCombo.setFont(projectFrame.getFontLight().deriveFont(16f));
        weightCombo.setOpaque(false);
        weightCombo.setForeground(Color.BLACK);
        weightCombo.setMinimumSize(new Dimension(100, 40));
        weightCombo.setPreferredSize(new Dimension(100, 40));
        weightCombo.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
        tempPanel.add(weightCombo);
        panel.add(tempPanel);

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
        footerleftPanel.add(projectFrame.openScreenBtn(true), FlowLayout.LEFT);
        JButton btn = new CustomButton("Create Project", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerRightPanel.add(btn);
        projectFrame.getFooter().add(tempPanelSec);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String projectName = projectNameField.getText();
                    projectFrame.getProject().setName(projectName);
                    if (projectFrame.getProject().getName() == null || projectFrame.getProject().getName().isEmpty()) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not created.\nPlease give a name to the project.", projectFrame.getClosebtn()));
                    } else if (projectFrame.getProject().getUnits().isEmpty()) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not created.\nPlease upload the units.", projectFrame.getClosebtn()));
                    } else if (projectFrame.getProject().getStudents().isEmpty()) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not created.\nPlease upload the students.", projectFrame.getClosebtn()));
                    } else if (!createExportType()) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not created.\nPlease select a valid export type.", projectFrame.getClosebtn()));
                    } else if (!createEquivalenceYear()) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not created.\nPlease enter a valid equivalence year (must be an integer number).", projectFrame.getClosebtn()));
                    } else if (!createPredefinedScale()) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not created.\nPlease select a valid scale.", projectFrame.getClosebtn()));
                    } else if (!createWeight()) {
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not created.\nPlease select a valid weight average base.", projectFrame.getClosebtn()));
                    } else {
                        projectFrame.openPage("Main Menu");
                    }
                } catch (Exception ex) {
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, ex.getMessage(), projectFrame.getClosebtn()));
                }
            }
        });
        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());
    }

    /**
     * Method used to set the export type
     *
     * @return true if created
     */
    private boolean createExportType() {
        Object selected = exportCombo.getSelectedItem();
        if (selected == null) {
            return false;
        }
        String exportType = selected.toString();
        if (exportType.isEmpty()) {
            return false;
        }
        projectFrame.getProject().setExportType(Project.ExportType.valueOf(exportType));
        return true;
    }

    /**
     * Method used to create the type of credits
     *
     * @return True if created
     */
    private boolean createWeight() {
        Object selected = weightCombo.getSelectedItem();
        if (selected == null) {
            return false;
        }
        String weightType = selected.toString();
        if (weightType.isEmpty()) {
            return false;
        }
        if (projectFrame.getProject().getUnits().isEmpty()) {
            return false;
        }
        Unit unit = projectFrame.getProject().getUnits().get(0);
        for (int i = 4; i < unit.getHeaders().length; i++) {
            if (weightType.equalsIgnoreCase(unit.getHeaders()[i])) {
                int value = i - 4;
                if (value >= 0) {
                    projectFrame.getProject().setHeaderIndexECTS(value);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Method used to create the predefined scale
     *
     * @return True if created
     */
    private boolean createPredefinedScale() {
        Object selected = scalesCombo.getSelectedItem();
        if (selected == null) {
            return false;
        }
        String scale = selected.toString();
        if (scale.isEmpty()) {
            return false;
        }
        HashMap<String, Double> foundScale = projectFrame.getProject().getPredefinedScales().get(scale);
        String infoScale = projectFrame.getProject().getPredefinedScalesInfo().get(scale);
        if (foundScale == null || infoScale == null) {
            return false;
        }
        projectFrame.getProject().setSelectedScale(foundScale);
        projectFrame.getProject().setSelectedScaleInfo(infoScale);
        return true;
    }

    /**
     * Method used to create the equivalence year
     *
     * @return True if created
     */
    private boolean createEquivalenceYear() {
        String year = equivalenceYearField.getText();
        if (year == null || year.isEmpty()) {
            return false;
        }
        try {
            int yearValue = Integer.parseInt(year);
            projectFrame.getProject().setEquivalenceYear(yearValue);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Method used to get the parent frame
     *
     * @return parent Frame
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
