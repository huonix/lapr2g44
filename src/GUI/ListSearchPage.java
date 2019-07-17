package GUI;

import Core.Search;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

/**
 * Class used to create the list search page
 *
 * @author Group 44 LAPR2
 */
public final class ListSearchPage {

    private ProjectFrame projectFrame;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel label;
    private Search search;
    private DefaultListModel searchModelList;
    private JList searchList;
    private JScrollPane scroolPaneSearch;

    /**
     * Constructor of the list search page
     *
     * @param projectFrame Parent Frame
     */
    public ListSearchPage(ProjectFrame projectFrame) {
        setProjectFrame(projectFrame);
    }

    /**
     * Method used to Open the list search page
     */
    public void open() {
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > List of Searchs");
        projectFrame.fadeOut(projectFrame.getContent());
        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        //List Search
        tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setPreferredSize(new Dimension(900, 360));
        if (!projectFrame.getProject().getSearchList().isEmpty()) {
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanelSec = GUIFunctions.createLinePanel(370);
            tempPanelSec.add(tempPanel);
            searchModelList = new DefaultListModel();
            for (Search entry : projectFrame.getProject().getSearchList()) {
                searchModelList.addElement(entry.getName());
            }
            searchList = new JList(searchModelList);
            searchList.setVisibleRowCount(10);
            searchList.setFont(projectFrame.getFontLight().deriveFont(18f));
            searchList.setForeground(Color.WHITE);
            searchList.setOpaque(false);
            searchList.setBackground(new Color(0, 0, 0, 0));

            scroolPaneSearch = new JScrollPane(searchList);
            scroolPaneSearch.setPreferredSize(new Dimension(890, 350));
            scroolPaneSearch.setOpaque(false);
            scroolPaneSearch.setBackground(new Color(0, 0, 0, 0));
            scroolPaneSearch.getViewport().setBackground(new Color(0, 0, 0, 0));
            scroolPaneSearch.getViewport().setOpaque(false);
            scroolPaneSearch.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            tempPanel.add(scroolPaneSearch);

            projectFrame.getContent().add(tempPanelSec);
        } else {
            tempPanel = new JPanel();
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(900, 150));
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanelSec = GUIFunctions.createLinePanel(160);
            tempPanelSec.add(tempPanel);
            panel.add(tempPanelSec);
            label = new JLabel("There are no saved search.");
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
        if (!projectFrame.getProject().getSearchList().isEmpty()) {
            btn = new CustomButton("Delete Search", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
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
                            Object selected = searchList.getSelectedValue();
                            if (selected == null) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please select a search.", projectFrame.getClosebtn()));
                                return false;
                            }
                            search = Search.getSearchByName(projectFrame.getProject().getSearchList(), selected.toString());
                            if (search == null) {
                                searchModelList.removeElement(selected);
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "We are sorry, the search was not found.", projectFrame.getClosebtn()));
                                return false;
                            }
                            searchModelList.removeElement(selected);
                            projectFrame.getProject().getSearchList().remove(search);
                            projectFrame.getLoadingDialog().close();
                            projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "The search and associated results were deleted.", projectFrame.getClosebtn()));
                            if (projectFrame.getProject().getSearchList().isEmpty()) {
                                projectFrame.openPage("List of searchs");
                            }
                            return true;
                        }
                    };
                    worker.execute();
                }
            });
            btn = new CustomButton("Copy Search", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
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
                            Object selected = searchList.getSelectedValue();
                            if (selected == null) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please select a search.", projectFrame.getClosebtn()));
                                return false;
                            }
                            search = Search.getSearchByName(projectFrame.getProject().getSearchList(), selected.toString());
                            if (search == null) {
                                projectFrame.getLoadingDialog().close();
                                searchModelList.removeElement(selected);
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "We are sorry, the search was not found.", projectFrame.getClosebtn()));
                                return false;
                            }
                            projectFrame.getLoadingDialog().close();
                            projectFrame.openSearchPage(search, false);
                            return true;
                        }
                    };
                    worker.execute();
                }
            });
            btn = new CustomButton("Edit Search", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
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
                            Object selected = searchList.getSelectedValue();
                            if (selected == null) {
                                projectFrame.getLoadingDialog().close();
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please select a search.", projectFrame.getClosebtn()));
                                return false;
                            }
                            search = Search.getSearchByName(projectFrame.getProject().getSearchList(), selected.toString());
                            if (search == null) {
                                projectFrame.getLoadingDialog().close();
                                searchModelList.removeElement(selected);
                                projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "We are sorry, the search was not found.", projectFrame.getClosebtn()));
                                return false;
                            }
                            projectFrame.getLoadingDialog().close();
                            projectFrame.openSearchPage(search, true);
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
     * @param projectFrame Parent Frame
     */
    public void setProjectFrame(ProjectFrame projectFrame) {
        this.projectFrame = projectFrame;
    }
}
