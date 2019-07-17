package GUI;

import Core.Pattern;
import Core.Result;
import Core.Search;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * Class used to create the filter result page
 *
 * @author Group 44 LAPR2
 */
public final class FilterResultPage {

    private ProjectFrame projectFrame;
    private Result result;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel label;
    private JSpinner unitCommonSpinner;
    private SpinnerNumberModel unitCommonModelSpinner;
    private JSpinner studentCommonSpinner;
    private SpinnerNumberModel studentCommonModelSpinner;
    private JSpinner studentNumberSpinner;
    private SpinnerNumberModel studentModelSpinner;
    private JPanel panePattern;
    private JScrollPane scroollPanePattern;
    private ArrayList<Pattern> patternFiltered;
    private DefaultListModel modelList;
    private JList list;
    private JPanel footerRightPanel;
    private int minStudentsFiltered;
    private int minUnitsFiltered;

    /**
     * Constructor to create the Filter result page object
     *
     * @param projectFrame Parent frame
     * @param result Object Result
     */
    public FilterResultPage(ProjectFrame projectFrame, Result result) {
        setProjectFrame(projectFrame);
        setResult(result);
    }

    /**
     * Method used to open the page
     *
     * @param tempResult Object Result
     */
    public void open(Result tempResult) {
        setResult(tempResult);
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > " + result.getSearchName() + " > Result > Filter Result");
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
        label = new JLabel("SELECT PATTERNS", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(18f));
        label.setForeground(Color.white);
        tempPanelSec.add(label);
        tempPanel.add(tempPanelSec);

        //Options by pattern number 
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(270, 220));
        label = new JLabel("By pattern number or set of numbers: ", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(12f));
        label.setPreferredSize(new Dimension(280, 20));
        label.setForeground(Color.white);
        tempPanelSec.add(label);

        JPanel panelPatterns = new JPanel(new GridLayout());
        panelPatterns.setOpaque(false);
        panelPatterns.setPreferredSize(new Dimension(260, 140));

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
        //Add button to select and update pattern list
        btn = new CustomButton("Show pattern(s)", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                projectFrame.getLoadingDialog().open();
                SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() {
                        List selectedList = list.getSelectedValuesList();
                        if (selectedList == null || selectedList.isEmpty()) {
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please select a pattern.", projectFrame.getClosebtn()));
                            return false;
                        }
                        ArrayList<Integer> listOfPatternNumbers = new ArrayList<>();
                        for (Object entry : selectedList) {
                            String filtered = entry.toString().replaceAll("Pattern ", "").trim();
                            try {
                                listOfPatternNumbers.add(Integer.parseInt(filtered) - 1);
                            } catch (Exception e) {
                            }
                        }
                        patternFiltered = result.getPatternByNumbers(listOfPatternNumbers);
                        minStudentsFiltered = result.getStudentsMinNumber();
                        minUnitsFiltered = result.getUnitsMinNumber();
                        updatePatternTable(patternFiltered, "Filtered by pattern number or set of number(s).");
                        projectFrame.getLoadingDialog().close();
                        return true;
                    }
                };
                worker.execute();
            }
        });
        tempPanelSec.add(btn);
        tempPanel.add(tempPanelSec);

        //Options title
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(280, 30));
        label = new JLabel("BY SIMILARITY", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(16f));
        label.setForeground(Color.white);
        tempPanelSec.add(label);
        tempPanel.add(tempPanelSec);

        //Options by pattern unit common
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(280, 60));
        label = new JLabel("By units (patterns with +/- n common units): ", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(11f));
        label.setForeground(Color.white);
        tempPanelSec.add(label);
        unitCommonModelSpinner = new SpinnerNumberModel(result.getUnitsMinNumber(), result.getUnitsMinNumber(), result.getUnitList().size(), 1);
        unitCommonSpinner = new JSpinner(unitCommonModelSpinner);
        unitCommonSpinner.setPreferredSize(new Dimension(100, 30));
        unitCommonSpinner.setFont(projectFrame.getFontLight().deriveFont(22f));
        unitCommonSpinner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        unitCommonSpinner.setOpaque(true);
        unitCommonSpinner.setBackground(Color.white);
        unitCommonSpinner.setForeground(Color.black);
        tempPanelSec.add(unitCommonSpinner);

        btn = new CustomButton("LESS", new Dimension(70, 30), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                projectFrame.getLoadingDialog().open();
                SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() {
                        int number = (int) unitCommonSpinner.getValue();
                        patternFiltered = result.getPatternByCommonUnits(number, false);
                        minStudentsFiltered = result.getStudentsMinNumber();
                        minUnitsFiltered = result.getUnitsMinNumber();
                        updatePatternTable(patternFiltered, "Filtered by similary, patterns with less than " + number + " common units.");
                        projectFrame.getLoadingDialog().close();
                        return true;
                    }
                };
                worker.execute();
            }
        });
        tempPanelSec.add(btn);

        btn = new CustomButton("PLUS", new Dimension(70, 30), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                projectFrame.getLoadingDialog().open();
                SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() {
                        int number = (int) unitCommonSpinner.getValue();
                        patternFiltered = result.getPatternByCommonUnits(number, true);
                        minStudentsFiltered = result.getStudentsMinNumber();
                        minUnitsFiltered = number;
                        updatePatternTable(patternFiltered, "Filtered by similary, patterns with more than " + number + " common units.");
                        projectFrame.getLoadingDialog().close();
                        return true;
                    }
                };
                worker.execute();
            }
        });
        tempPanelSec.add(btn);

        tempPanel.add(tempPanelSec);

        //Options by pattern student common
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(280, 60));
        label = new JLabel("By students (patterns with +/- n common students): ", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(11f));
        label.setForeground(Color.white);
        tempPanelSec.add(label);
        studentCommonModelSpinner = new SpinnerNumberModel(result.getStudentsMinNumber(), result.getStudentsMinNumber(), result.getStudentList().size(), 1);
        studentCommonSpinner = new JSpinner(studentCommonModelSpinner);
        studentCommonSpinner.setPreferredSize(new Dimension(100, 30));
        studentCommonSpinner.setFont(projectFrame.getFontLight().deriveFont(22f));
        studentCommonSpinner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        studentCommonSpinner.setOpaque(true);
        studentCommonSpinner.setBackground(Color.white);
        studentCommonSpinner.setForeground(Color.black);
        tempPanelSec.add(studentCommonSpinner);

        btn = new CustomButton("LESS", new Dimension(70, 30), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                projectFrame.getLoadingDialog().open();
                SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() {
                        int number = (int) studentCommonSpinner.getValue();
                        patternFiltered = result.getPatternByCommonStudents(number, false);
                        minStudentsFiltered = result.getStudentsMinNumber();
                        minUnitsFiltered = result.getUnitsMinNumber();
                        updatePatternTable(patternFiltered, "Filtered by similary, patterns with less than " + number + " common students.");
                        projectFrame.getLoadingDialog().close();
                        return true;
                    }
                };
                worker.execute();
            }
        });
        tempPanelSec.add(btn);

        btn = new CustomButton("PLUS", new Dimension(70, 30), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                projectFrame.getLoadingDialog().open();
                SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() {
                        int number = (int) studentCommonSpinner.getValue();
                        patternFiltered = result.getPatternByCommonStudents(number, true);
                        minStudentsFiltered = number;
                        minUnitsFiltered = result.getUnitsMinNumber();
                        updatePatternTable(patternFiltered, "Filtered by similary, patterns with more than " + number + " common students.");
                        projectFrame.getLoadingDialog().close();
                        return true;
                    }
                };
                worker.execute();
            }
        });
        tempPanelSec.add(btn);

        tempPanel.add(tempPanelSec);


        //Options title
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(280, 30));
        label = new JLabel("OTHER", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(16f));
        label.setForeground(Color.white);
        tempPanelSec.add(label);
        tempPanel.add(tempPanelSec);

        //Options by pattern number 
        tempPanelSec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanelSec.setOpaque(false);
        tempPanelSec.setPreferredSize(new Dimension(280, 60));
        label = new JLabel("By number of students in the pattern: ", SwingConstants.LEFT);
        label.setFont(projectFrame.getFontRegular().deriveFont(11f));
        label.setForeground(Color.white);
        tempPanelSec.add(label);

        studentModelSpinner = new SpinnerNumberModel(result.getStudentsMinNumber(), result.getStudentsMinNumber(), result.getStudentList().size(), 1);
        studentNumberSpinner = new JSpinner(studentModelSpinner);
        studentNumberSpinner.setPreferredSize(new Dimension(100, 30));
        studentNumberSpinner.setFont(projectFrame.getFontLight().deriveFont(22f));
        studentNumberSpinner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        studentNumberSpinner.setOpaque(true);
        studentNumberSpinner.setBackground(Color.white);
        studentNumberSpinner.setForeground(Color.black);
        tempPanelSec.add(studentNumberSpinner);
        //Add button to select and update pattern list
        btn = new CustomButton("Show pattern(s)", new Dimension(150, 30), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                projectFrame.getLoadingDialog().open();
                SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() {
                        int numberOfStudents = (int) studentNumberSpinner.getValue();
                        patternFiltered = result.getPatternByNumberOfStudents(numberOfStudents);
                        minStudentsFiltered = numberOfStudents;
                        minUnitsFiltered = result.getUnitsMinNumber();
                        updatePatternTable(patternFiltered, "Filtered by number of students in the pattern (number: " + numberOfStudents + ").");
                        projectFrame.getLoadingDialog().close();
                        return true;
                    }
                };
                worker.execute();
            }
        });
        tempPanelSec.add(btn);
        tempPanel.add(tempPanelSec);
        panel.add(tempPanel);

        //Table
        panePattern = new JPanel();
        panePattern.setOpaque(false);
        panePattern.setPreferredSize(new Dimension(610, 540));

        //pattern table
        updatePatternTable(result.getPatterns(), "ALL PATTERNS.");

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
                projectFrame.openResultPage(result, "Menu");
            }
        });
        tempPanel.add(footerleftPanel);
        footerRightPanel = new JPanel();
        footerRightPanel.setOpaque(false);
        footerRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tempPanel.add(footerRightPanel);
        projectFrame.getFooter().add(tempPanelSec);

        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());
    }
    private JTable table;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JPanel bottomPanelTop;
    private JPanel bottomPanelBottom;
    private Pattern patternSelected;
    private String studentList;

    /**
     * Method used to update the pattern table
     *
     * @param patternList Pattern List
     * @param title Title of the table
     */
    private void updatePatternTable(ArrayList<Pattern> patternList, String title) {
        panePattern.removeAll();
        if (patternList.isEmpty()) {
            panePattern.setLayout(new GridLayout());
            label = new JLabel("    No patterns found.");
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setFont(projectFrame.getFontLight().deriveFont(26f));
            label.setForeground(Color.white);
            panePattern.add(label);
        } else {
            //top panel
            panePattern.setLayout(new BorderLayout());

            topPanel = new JPanel();
            topPanel.setOpaque(false);
            topPanel.setPreferredSize(new Dimension(610, 40));
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            label = new JLabel("PATTERN(S) FOUND: " + patternList.size() + ". " + title);
            label.setFont(projectFrame.getFontLight().deriveFont(14f));
            label.setForeground(Color.white);
            topPanel.add(label);

            centerPanel = new JPanel();
            centerPanel.setOpaque(false);
            centerPanel.setPreferredSize(new Dimension(610, 490));
            centerPanel.setLayout(new GridLayout());

            //Bottom panel
            bottomPanel = new JPanel();
            bottomPanel.setOpaque(false);
            bottomPanel.setPreferredSize(new Dimension(610, 80));
            bottomPanel.setLayout(new BorderLayout());

            bottomPanelTop = new JPanel();
            bottomPanelTop.setOpaque(false);
            bottomPanelTop.setPreferredSize(new Dimension(610, 50));
            bottomPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));

            bottomPanelBottom = new JPanel();
            bottomPanelBottom.setOpaque(false);
            bottomPanelBottom.setPreferredSize(new Dimension(610, 30));
            bottomPanelBottom.setLayout(new FlowLayout(FlowLayout.LEFT));

            bottomPanel.add(bottomPanelTop, BorderLayout.NORTH);
            bottomPanel.add(bottomPanelBottom, BorderLayout.SOUTH);

            //Header
            String[] columnNames = new String[result.getUnitList().size()];
            for (int i = 0; i < result.getUnitList().size(); i++) {
                columnNames[i] = result.getUnitList().get(i).getId();
            }
            //Content
            Object[][] dataTable = new Object[patternList.size()][result.getUnitList().size()];
            int count = 0;
            for (Pattern patt : patternList) {
                for (int j = 0; j < result.getUnitList().size(); j++) {
                    dataTable[count][j] = patt.getPattern()[j];
                }
                count++;
            }
            //Creation
            table = new JTable(dataTable, columnNames) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            TableColumn column;
            for (int i = 0; i < result.getUnitList().size(); i++) {
                column = table.getColumnModel().getColumn(i);
                column.setMinWidth(85);
                column.setCellRenderer(centerRenderer);
            }

            table.getTableHeader().setReorderingAllowed(false);
            JTableHeader tHeader = table.getTableHeader();
            tHeader.setBackground(projectFrame.getDarkColor());
            tHeader.setForeground(Color.white);
            tHeader.setFont(projectFrame.getFontLight().deriveFont(16f));
            tHeader.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setFont(projectFrame.getFontLight().deriveFont(13f));
            table.setAutoCreateRowSorter(false);
            table.setFillsViewportHeight(true);
            table.setOpaque(false);
            table.setBackground(new Color(0, 0, 0, 0));
            table.setForeground(Color.white);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));

            //Event listeners
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    bottomPanelTop.removeAll();
                    tempPanel = new JPanel();
                    tempPanel.setOpaque(false);
                    tempPanel.setPreferredSize(new Dimension(160, 40));
                    label = new JLabel("Loading details . . .");
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    label.setFont(projectFrame.getFontLight().deriveFont(16f));
                    label.setForeground(Color.white);
                    tempPanel.add(label);
                    bottomPanelTop.add(tempPanel);
                    bottomPanelTop.revalidate();
                    SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                        @Override
                        protected Boolean doInBackground() {
                            bottomPanelTop.removeAll();
                            int row = table.rowAtPoint(e.getPoint());
                            int column = table.columnAtPoint(e.getPoint());
                            if (row >= 0 && column >= 0) {
                                String patt = "";
                                for (int i = 0; i < result.getUnitList().size(); i++) {
                                    if (i == 0) {
                                        patt += "[" + table.getValueAt(row, i);
                                    } else if (i == result.getUnitList().size() - 1) {
                                        patt += ", " + table.getValueAt(row, i) + "]";
                                    } else {
                                        patt += ", " + table.getValueAt(row, i);
                                    }
                                }
                                patternSelected = result.getPatternListMap().get(patt);
                                if (patternSelected != null) {
                                    tempPanel = new JPanel();
                                    tempPanel.setOpaque(false);
                                    tempPanel.setPreferredSize(new Dimension(160, 40));
                                    label = new JLabel("Pattern size: " + patternSelected.getPatternSize());
                                    label.setHorizontalAlignment(SwingConstants.LEFT);
                                    label.setFont(projectFrame.getFontLight().deriveFont(16f));
                                    label.setForeground(Color.white);
                                    tempPanel.add(label);
                                    bottomPanelTop.add(tempPanel);

                                    tempPanel = new JPanel();
                                    tempPanel.setOpaque(false);
                                    tempPanel.setPreferredSize(new Dimension(160, 40));
                                    label = new JLabel("Students: " + patternSelected.getStudentList().size());
                                    label.setHorizontalAlignment(SwingConstants.LEFT);
                                    label.setFont(projectFrame.getFontLight().deriveFont(16f));
                                    label.setForeground(Color.white);
                                    tempPanel.add(label);
                                    bottomPanelTop.add(tempPanel);

                                    tempPanel = new JPanel();
                                    tempPanel.setOpaque(false);
                                    tempPanel.setPreferredSize(new Dimension(130, 50));
                                    btn = new CustomButton("Show Students", new Dimension(120, 40), null, 10f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
                                    btn.addMouseListener(projectFrame.new HoverBtnEffect());
                                    studentList = "";
                                    for (int i = 0; i < patternSelected.getStudentList().size(); i++) {
                                        if (i < patternSelected.getStudentList().size() - 1) {
                                            studentList += patternSelected.getStudentList().get(i).getNumber() + ", ";
                                        } else {
                                            studentList += patternSelected.getStudentList().get(i).getNumber() + "";
                                        }
                                    }
                                    btn.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Students: " + studentList, projectFrame.getClosebtn()));
                                        }
                                    });
                                    tempPanel.add(btn);
                                    bottomPanelTop.add(tempPanel);
                                    tempPanel = new JPanel();
                                    tempPanel.setOpaque(false);
                                    tempPanel.setPreferredSize(new Dimension(130, 50));
                                    btn = new CustomButton("Show Statistics", new Dimension(120, 40), null, 10f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
                                    btn.addMouseListener(projectFrame.new HoverBtnEffect());
                                    btn.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            projectFrame.openStatisticPage(result, patternSelected);
                                        }
                                    });
                                    tempPanel.add(btn);
                                    bottomPanelTop.add(tempPanel);
                                }
                            }
                            bottomPanelTop.revalidate();
                            return true;
                        }
                    };
                    worker.execute();
                }
            });

            //Creation of jscrollpane
            scroollPanePattern = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroollPanePattern.setPreferredSize(new Dimension(600, 440));
            scroollPanePattern.setOpaque(false);
            scroollPanePattern.setBackground(new Color(0, 0, 0, 0));
            scroollPanePattern.getViewport().setBackground(new Color(0, 0, 0, 0));
            scroollPanePattern.getViewport().setOpaque(false);
            scroollPanePattern.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            centerPanel.add(scroollPanePattern);

            if (patternFiltered != null && !patternList.isEmpty()) {
                footerRightPanel.removeAll();
                btn = new CustomButton("Save as a new search result", new Dimension(220, 40), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
                btn.addMouseListener(projectFrame.new HoverBtnEffect());
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
                        projectFrame.getLoadingDialog().open();
                        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
                            @Override
                            protected Boolean doInBackground() {
                                Search search = Search.getSearchByName(projectFrame.getProject().getSearchList(), result.getSearchName());
                                if (search != null) {
                                    Result newResult = new Result(projectFrame.getProject().getName(), search, patternFiltered, minStudentsFiltered, minUnitsFiltered);
                                    search.addResult(newResult);
                                    footerRightPanel.setVisible(false);
                                    footerRightPanel.removeAll();
                                    footerRightPanel.revalidate();
                                    footerRightPanel.setVisible(true);
                                    projectFrame.getLoadingDialog().close();
                                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Filtered result successfully saved as a new search result.", projectFrame.getClosebtn()));
                                } else {
                                    projectFrame.getLoadingDialog().close();
                                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "We are sorry, the result was not saved.", projectFrame.getClosebtn()));
                                }
                                return true;
                            }
                        };
                        worker.execute();
                    }
                });
                footerRightPanel.add(btn);
                footerRightPanel.revalidate();
            }
            panePattern.add(topPanel, BorderLayout.NORTH);
            panePattern.add(centerPanel, BorderLayout.CENTER);
            panePattern.add(bottomPanel, BorderLayout.SOUTH);
        }
        panePattern.revalidate();
    }

    /**
     * Method used to get the result
     *
     * @return Object Result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Method used to set the result
     *
     * @param result Object Result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Method used to get the parent frame
     *
     * @return Parent JFrame
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
