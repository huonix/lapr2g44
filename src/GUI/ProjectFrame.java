package GUI;

import Core.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * Class used to the create the main window
 *
 * @author Group 44 LAPR2
 */
public final class ProjectFrame extends JFrame {

    //Definition of project
    private Project project;
    //Definition of colors
    private Color darkColor = new Color(0x367c84);
    private Color lightColor = new Color(0x95cfc5);
    private Color darkBgColor = new Color(0x004050);
    //Definition of fonts
    private Font fontLight = createFont("/fonts/light.ttf");
    private Font fontRegular = createFont("/fonts/regular.ttf");
    private Font fontBold = createFont("/fonts/bold.ttf");
    private Font fontBolder = createFont("/fonts/bolder.ttf");
    //Definition of dimentions
    private Dimension defaultSize;
    private Dimension maxSize = new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
    private Dimension footerSizeBtn = new Dimension(180, 40);
    private Dimension mediumButtonSize = new Dimension(180, 140);
    private Dimension bigButtonSize = new Dimension(300, 400);
    //Definition of path to storage project files
    private String storageFilesPath = "projectStorage";
    //Definiton of dialogs
    private LoadingDialog loadingDialog;
    private MessageDialog messageDialog;
    private Color textBtnColor = new Color(0xFFFFFF);
    private int borderWidth = 3;
    private JLabel mainPane;
    private JLabel label;
    private JLabel headerTitle;
    private JFrame frame;
    private JPanel wraper;
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    private JButton closebtn;
    //Definition of GUI Pages
    private OpenScreenPage openScreenPage;
    private NewProjectPage newProjectPage;
    private LoadProjectPage loadProjectPage;
    private MainMenuPage mainMenuPage;
    private SearchPage searchPage;
    private ListSearchPage listSearchPage;
    private ListResultPage listResultPage;
    private ResultMenuPage resultMenuPage;
    private ShowExportPage showExportPage;
    private FilterResultPage filterResultPage;
    private StatisticsPage statisticsPage;

    /**
     * Constructor used to create the main window
     *
     * @param minWidth Minimum width
     * @param minHeight Minimum height
     */
    public ProjectFrame(int minWidth, int minHeight) {
        setDefaultSize(minWidth, minHeight);
        initialise();
    }

    /**
     * Method used to initialize the window
     */
    public void initialise() {
        frame = this;
        project = new Project();
        closebtn = new CustomButton("Close", footerSizeBtn, null, 14f, true, darkColor, lightColor, textBtnColor, fontLight);
        setLayout(new GridLayout());
        setPreferredSize(defaultSize);
        setMinimumSize(defaultSize);
        generateBackground();
        wrap();
        openPage("Open Screen");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (GUIFunctions.optionDialog("Are you sure?", "Window Close")) {
                    dispose();
                }
            }
        });
        setResizable(true);
        pack();
        setVisible(true);
    }

    /**
     * Method used to get the Project
     *
     * @return Project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Method used to set the project
     *
     * @param project Project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Method used to get the dark color
     *
     * @return Color
     */
    public Color getDarkColor() {
        return darkColor;
    }

    /**
     * Method used to set the dark color
     *
     * @param darkColor Color
     */
    public void setDarkColor(Color darkColor) {
        this.darkColor = (darkColor != null) ? darkColor : new Color(0x000000);
    }

    /**
     * Method used to get the light color
     *
     * @return Color
     */
    public Color getLightColor() {
        return lightColor;
    }

    /**
     * Method used to set the light color
     *
     * @param lightColor Color
     */
    public void setLightColor(Color lightColor) {
        this.lightColor = (lightColor != null) ? lightColor : new Color(0xFFFFFF);
    }

    /**
     * Method used to get the background dark color
     *
     * @return Color
     */
    public Color getDarkBgColor() {
        return darkBgColor;
    }

    /**
     * Method used to set the background dark color
     *
     * @param darkBgColor Color
     */
    public void setDarkBgColor(Color darkBgColor) {
        this.darkBgColor = (darkBgColor != null) ? darkBgColor : new Color(0x333333);
    }

    /**
     * Method used to get the light font
     *
     * @return Font
     */
    public Font getFontLight() {
        return fontLight;
    }

    /**
     * Method used to set the light font
     *
     * @param fontLight Font
     */
    public void setFontLight(Font fontLight) {
        this.fontLight = (fontLight != null) ? fontLight : createFont(null);
    }

    /**
     * Method used to get the regular font
     *
     * @return Font
     */
    public Font getFontRegular() {
        return fontRegular;
    }

    /**
     * Method used to set the regular font
     *
     * @param fontRegular Font
     */
    public void setFontRegular(Font fontRegular) {
        this.fontRegular = (fontRegular != null) ? fontRegular : createFont(null);
    }

    /**
     * Method used to get the bold font
     *
     * @return Font
     */
    public Font getFontBold() {
        return fontBold;
    }

    /**
     * Method used to set the bold font
     *
     * @param fontBold Font
     */
    public void setFontBold(Font fontBold) {
        this.fontBold = (fontBold != null) ? fontBold : createFont(null);
    }

    /**
     * Method used to get the bolder font
     *
     * @return Font
     */
    public Font getFontBolder() {
        return fontBolder;
    }

    /**
     * Method used to set the bolder font
     *
     * @param fontBolder Font
     */
    public void setFontBolder(Font fontBolder) {
        this.fontBolder = (fontBolder != null) ? fontBolder : createFont(null);
    }

    /**
     * Method used to get the default size
     *
     * @return Dimension
     */
    public Dimension getDefaultSize() {
        return defaultSize;
    }

    /**
     * Method used to set the default size
     *
     * @param defaultSize Dimension
     */
    public void setDefaultSize(Dimension defaultSize) {
        this.defaultSize = defaultSize;
    }

    /**
     * Method used to get the storage files path
     *
     * @return String with the path
     */
    public String getStorageFilesPath() {
        return storageFilesPath;
    }

    /**
     * Method used to set the storage files path
     *
     * @param storageFilesPath String with the path
     */
    public void setStorageFilesPath(String storageFilesPath) {
        this.storageFilesPath = (storageFilesPath != null && !storageFilesPath.isEmpty()) ? storageFilesPath : "projectStorage";
    }

    /**
     * Method used to get the loading dialog
     *
     * @return LoadingDialog
     */
    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    /**
     * Method used to set the Loading dialog
     *
     * @param loadingDialog Loading dialog
     */
    public void setLoadingDialog(LoadingDialog loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    /**
     * Method used to get the Message Dialog
     *
     * @return Message Dialog
     */
    public MessageDialog getMessageDialog() {
        return messageDialog;
    }

    /**
     * Method used to set the Message Dialog
     *
     * @param messageDialog Message Dialog
     */
    public void setMessageDialog(MessageDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    /**
     * Method used to get the Main Panel
     *
     * @return JLabel
     */
    public JLabel getMainPane() {
        return mainPane;
    }

    /**
     * Method used to set the main panel
     *
     * @param mainPane JLabel
     */
    public void setMainPane(JLabel mainPane) {
        this.mainPane = mainPane;
    }

    /**
     * Method used to get the label
     *
     * @return JLabel
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Method used to set the label
     *
     * @param label JLabel
     */
    public void setLabel(JLabel label) {
        this.label = label;
    }

    /**
     * Method used to get the header title
     *
     * @return JLabel
     */
    public JLabel getHeaderTitle() {
        return headerTitle;
    }

    /**
     * Method used to set the header title
     *
     * @param headerTitle JLabel
     */
    public void setHeaderTitle(JLabel headerTitle) {
        this.headerTitle = headerTitle;
    }

    /**
     * Method used to get the frame
     *
     * @return JFrame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Method used to set the frame
     *
     * @param frame JFrame
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Method used to get the wrapper panel
     *
     * @return JPanel
     */
    public JPanel getWraper() {
        return wraper;
    }

    /**
     * Method used to set the wrapper panel
     *
     * @param wraper JPanel
     */
    public void setWraper(JPanel wraper) {
        this.wraper = wraper;
    }

    /**
     * Method used to get the header
     *
     * @return JPanel
     */
    public JPanel getHeader() {
        return header;
    }

    /**
     * Method used to set the Header
     *
     * @param header JPanel
     */
    public void setHeader(JPanel header) {
        this.header = header;
    }

    /**
     * Method used to get the content
     *
     * @return JPanel
     */
    public JPanel getContent() {
        return content;
    }

    /**
     * Method used to set the content
     *
     * @param content JPanel
     */
    public void setContent(JPanel content) {
        this.content = content;
    }

    /**
     * Method used to get the footer
     *
     * @return JPanel
     */
    public JPanel getFooter() {
        return footer;
    }

    /**
     * Method used to set the footer
     *
     * @param footer JPanel
     */
    public void setFooter(JPanel footer) {
        this.footer = footer;
    }

    /**
     * Method used to get the close button
     *
     * @return JButton
     */
    public JButton getClosebtn() {
        return closebtn;
    }

    /**
     * Method used to set the close button
     *
     * @param closebtn JButton
     */
    public void setClosebtn(JButton closebtn) {
        this.closebtn = closebtn;
    }

    /**
     * Method used to generate the background
     */
    public void generateBackground() {
        URL bgPath = getClass().getResource("/images/background3.png");
        if (bgPath != null) {
            mainPane = new JLabel(new ImageIcon(bgPath));
        } else {
            mainPane = new JLabel();
        }
        mainPane.setOpaque(true);
        mainPane.setBackground(darkBgColor);
        mainPane.setPreferredSize(maxSize);
        mainPane.setMaximumSize(maxSize);
        mainPane.setMinimumSize(maxSize);
        mainPane.setSize(maxSize);
        mainPane.setLayout(new GridLayout(0, 1));
        add(mainPane);
    }

    /**
     * Method used to wrap the window
     */
    public void wrap() {
        wraper = new JPanel();
        wraper.setLayout(new BorderLayout());
        wraper.setOpaque(false);

        header = new JPanel();
        Dimension headerSize = new Dimension(Short.MAX_VALUE, 80);
        header.setPreferredSize(headerSize);
        header.setLayout(new GridLayout());
        header.setOpaque(false);
        headerTitle = new JLabel();
        header.add(headerTitle);

        content = new JPanel();
        Dimension contentSize = new Dimension(Short.MAX_VALUE, 50);
        content.setPreferredSize(contentSize);
        content.setOpaque(false);
        content.setLayout(new FlowLayout());

        footer = new JPanel();
        Dimension footerSize = new Dimension(Short.MAX_VALUE, 80);
        footer.setPreferredSize(footerSize);
        footer.setOpaque(false);
        footer.setLayout(new FlowLayout());

        wraper.add(header, BorderLayout.NORTH);
        wraper.add(content, BorderLayout.CENTER);
        wraper.add(footer, BorderLayout.SOUTH);
        mainPane.add(wraper);
    }

    /**
     * Method used to open a page
     *
     * @param page String with the page name
     */
    public void openPage(final String page) {
        loadingDialog = new LoadingDialog(frame);
        loadingDialog.open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                switch (page) {
                    case "Open Screen":
                        if (openScreenPage == null) {
                            openScreenPage = new OpenScreenPage(ProjectFrame.this);
                        }
                        openScreenPage.open();
                        break;
                    case "New Project":
                        if (newProjectPage == null) {
                            newProjectPage = new NewProjectPage(ProjectFrame.this);
                        }
                        newProjectPage.open();
                        break;
                    case "Load Project":
                        if (loadProjectPage == null) {
                            loadProjectPage = new LoadProjectPage(ProjectFrame.this);
                        }
                        loadProjectPage.open();
                        break;
                    case "Main Menu":
                        if (mainMenuPage == null) {
                            mainMenuPage = new MainMenuPage(ProjectFrame.this);
                        }
                        mainMenuPage.open();
                        break;
                    case "Search Page":
                        if (searchPage == null) {
                            searchPage = new SearchPage(ProjectFrame.this);
                        }
                        searchPage.open(null, false);
                        break;
                    case "List of searchs":
                        if (listSearchPage == null) {
                            listSearchPage = new ListSearchPage(ProjectFrame.this);
                        }
                        listSearchPage.open();
                        break;
                    case "List of results":
                        if (listResultPage == null) {
                            listResultPage = new ListResultPage(ProjectFrame.this);
                        }
                        listResultPage.open();
                        break;
                    default:
                        if (openScreenPage == null) {
                            openScreenPage = new OpenScreenPage(ProjectFrame.this);
                        }
                        resetProject();
                        openScreenPage.open();
                        break;
                }
                loadingDialog.close();
                return true;
            }
        };
        worker.execute();
    }
    private Search tempSearch;

    /**
     * Method used to open the search page
     *
     * @param search Search
     * @param edit Boolean with the edit option
     */
    public void openSearchPage(Search search, final boolean edit) {
        tempSearch = search;
        loadingDialog = new LoadingDialog(frame);
        loadingDialog.open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                if (searchPage == null) {
                    searchPage = new SearchPage(ProjectFrame.this);
                }
                searchPage.open(tempSearch, edit);
                loadingDialog.close();
                return true;
            }
        };
        worker.execute();
    }
    private Result tempResult;

    /**
     * Method used to open a result page
     *
     * @param result Result
     * @param page String with the page
     */
    public void openResultPage(Result result, final String page) {
        tempResult = result;
        loadingDialog = new LoadingDialog(frame);
        loadingDialog.open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                switch (page) {
                    case "Menu":
                        if (resultMenuPage == null) {
                            resultMenuPage = new ResultMenuPage(ProjectFrame.this, tempResult);
                        }
                        resultMenuPage.open(tempResult);
                        break;
                    case "Show/Export":
                        if (showExportPage == null) {
                            showExportPage = new ShowExportPage(ProjectFrame.this, tempResult);
                        }
                        showExportPage.open(tempResult);
                        break;
                    case "Filter result":
                        if (filterResultPage == null) {
                            filterResultPage = new FilterResultPage(ProjectFrame.this, tempResult);
                        }
                        filterResultPage.open(tempResult);
                        break;
                    case "Statistics":
                        if (statisticsPage == null) {
                            statisticsPage = new StatisticsPage(ProjectFrame.this, tempResult);
                        }
                        statisticsPage.open(tempResult, null);
                        break;
                    default:
                        if (resultMenuPage == null) {
                            resultMenuPage = new ResultMenuPage(ProjectFrame.this, tempResult);
                        }
                        resultMenuPage.open(tempResult);
                        break;
                }
                loadingDialog.close();
                return true;
            }
        };
        worker.execute();
    }

    /**
     * Method used to open the statistics page
     *
     * @param result Result
     * @param tempPattern Pattern to show the initial statistics
     */
    public void openStatisticPage(Result result, final Pattern tempPattern) {
        tempResult = result;
        loadingDialog = new LoadingDialog(frame);
        loadingDialog.open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                if (statisticsPage == null) {
                    statisticsPage = new StatisticsPage(ProjectFrame.this, tempResult);
                }
                statisticsPage.open(tempResult, tempPattern);
                loadingDialog.close();
                return true;
            }
        };
        worker.execute();
    }

    /**
     * Method used to get the open screen Button
     *
     * @param check Boolean to check if a message of confirmation is shown
     * @return JButton
     */
    public JButton openScreenBtn(final boolean check) {
        JButton btn = new CustomButton("Back to open screen", footerSizeBtn, null, 12f, true, darkColor, lightColor, textBtnColor, fontLight);
        btn.addMouseListener(new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check && GUIFunctions.optionDialog("Are you sure?\nThis will delete all the project loaded data.", "Warning")) {
                    resetProject();
                    openPage("Open Screen");
                } else if (!check) {
                    resetProject();
                    openPage("Open Screen");
                }
            }
        });
        return btn;
    }

    /**
     * Method used to get the main menu button
     *
     * @param check boolean to check is a confirmation dialog is shown
     * @return JButton
     */
    public JButton openMainMenuBtn(final boolean check) {
        JButton btn = new CustomButton("Back to main menu", footerSizeBtn, null, 12f, true, darkColor, lightColor, textBtnColor, fontLight);
        btn.addMouseListener(new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check && GUIFunctions.optionDialog("Are you sure?\n", "Warning")) {
                    openPage("Main Menu");
                } else if (!check) {
                    openPage("Main Menu");
                }
            }
        });
        return btn;
    }

    /**
     * Method used to reset a project
     */
    public void resetProject() {
        project.reset();
    }

    /**
     * Method used to hide a page
     *
     * @param panel JPanel
     */
    public void fadeOut(JPanel panel) {
        footer.setVisible(false);
        footer.removeAll();
        panel.setVisible(false);
        panel.removeAll();
        revalidate();
    }

    /**
     * Method used to show a page
     *
     * @param panel JPanel
     */
    public void fadeIn(JPanel panel) {
        footer.setVisible(true);
        panel.setVisible(true);
        revalidate();
    }

    /**
     * Method used to update a page title
     *
     * @param title String with the title
     */
    public void updatePageTitle(String title) {
        if (title == null) {
            title = "";
        }
        setTitle(title);
        title = "   " + title;
        headerTitle.setForeground(textBtnColor);
        headerTitle.setFont(fontLight.deriveFont(22f));
        headerTitle.setText(title);
    }

    /**
     * Method used to import a font
     *
     * @param path String with the path of the font
     * @return Font
     */
    public Font createFont(String path) {
        Font font;
        try {
            InputStream in = getClass().getResourceAsStream(path);
            font = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            font = new Font("serif", Font.PLAIN, 12);
        } catch (Exception e) {
            font = new Font("serif", Font.PLAIN, 12);
        }
        return font;
    }

    /**
     * Method used to get the max size
     *
     * @return Dimension
     */
    public Dimension getMaxSize() {
        return maxSize;
    }

    /**
     * Method used to set the max size
     *
     * @param maxSize Dimension
     */
    public void setMaxSize(Dimension maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Method used to get the footer size of a button
     *
     * @return Dimension
     */
    public Dimension getFooterSizeBtn() {
        return footerSizeBtn;
    }

    /**
     * Method used to set the size of a button on the footer
     *
     * @param footerSizeBtn Dimension
     */
    public void setFooterSizeBtn(Dimension footerSizeBtn) {
        this.footerSizeBtn = footerSizeBtn;
    }

    /**
     * Method used to get a medium button size
     *
     * @return Dimension
     */
    public Dimension getMediumButtonSize() {
        return mediumButtonSize;
    }

    /**
     * Method used to set a minimum button size
     *
     * @param mediumButtonSize Dimension
     */
    public void setMediumButtonSize(Dimension mediumButtonSize) {
        this.mediumButtonSize = mediumButtonSize;
    }

    /**
     * Method used to get a big button size
     *
     * @return Dimension
     */
    public Dimension getBigButtonSize() {
        return bigButtonSize;
    }

    /**
     * Method used to set a big button size
     *
     * @param bigButtonSize Dimension
     */
    public void setBigButtonSize(Dimension bigButtonSize) {
        this.bigButtonSize = bigButtonSize;
    }

    /**
     * Method used to get the text color of a button
     *
     * @return Color
     */
    public Color getTextBtnColor() {
        return textBtnColor;
    }

    /**
     * Method used to set the text color for a button
     *
     * @param textBtnColor Color
     */
    public void setTextBtnColor(Color textBtnColor) {
        this.textBtnColor = textBtnColor;
    }

    /**
     * Method used to get the border width
     *
     * @return integer
     */
    public int getBorderWidth() {
        return borderWidth;
    }

    /**
     * Method used to set the border width
     *
     * @param borderWidth integer
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Method used to set the hover effect of a button
     */
    public class HoverBtnEffect extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            ((JButton) e.getComponent()).setBorder(BorderFactory.createLineBorder(lightColor, getBorderWidth()));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((JButton) e.getComponent()).setBorder(BorderFactory.createLineBorder(lightColor, 0));
        }
    }

    /**
     * Method used to set the default size
     *
     * @param minWidth Integer with the minimum size
     * @param minHeight Integer with the maximum size
     */
    private void setDefaultSize(int minWidth, int minHeight) {
        this.defaultSize = new Dimension(minWidth, minHeight);
    }
}
