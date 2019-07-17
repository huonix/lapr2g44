package GUI;

import Core.Project;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
 * Class used to create the load project page
 *
 * @author Group 44 LAPR2
 */
public final class LoadProjectPage {

    private ProjectFrame projectFrame;
    private ArrayList<String> projectFileList = new ArrayList<>();
    private JList projectList;
    private DefaultListModel projectModelList;
    private JScrollPane listPaneProject;
    private JPanel tempPanel;
    private JPanel tempPanelSec;
    private JLabel label;
    private JFileChooser chooser = new JFileChooser();
    private JButton btn;

    /**
     * Constructor used to create the load project page
     *
     * @param projectFrame
     */
    public LoadProjectPage(ProjectFrame projectFrame) {
        setProjectFrame(projectFrame);
    }

    /**
     * Method used to open the load project page
     */
    public void open() {
        projectFrame.updatePageTitle("ISEP > Load Project");
        projectFrame.fadeOut(projectFrame.getContent());

        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        File dir = new File(projectFrame.getStorageFilesPath());
        projectFileList = GUIFunctions.listFilesFromDir(dir);
        if (!projectFileList.isEmpty()) {
            tempPanel = new JPanel();
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(900, 360));
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanelSec = GUIFunctions.createLinePanel(370);
            tempPanelSec.add(tempPanel);
            projectModelList = new DefaultListModel();
            for (String file : projectFileList) {
                projectModelList.addElement(file.replaceAll(".bin", "").trim());
            }
            projectList = new JList(projectModelList);
            projectList.setVisibleRowCount(10);
            projectList.setFont(projectFrame.getFontLight().deriveFont(18f));
            projectList.setForeground(Color.WHITE);
            projectList.setOpaque(false);
            projectList.setBackground(new Color(0, 0, 0, 0));

            listPaneProject = new JScrollPane(projectList);
            listPaneProject.setPreferredSize(new Dimension(890, 350));
            listPaneProject.setOpaque(false);
            listPaneProject.setBackground(new Color(0, 0, 0, 0));
            listPaneProject.getViewport().setBackground(new Color(0, 0, 0, 0));
            listPaneProject.getViewport().setOpaque(false);
            listPaneProject.setBorder(BorderFactory.createLineBorder(projectFrame.getDarkColor(), 1));
            tempPanel.add(listPaneProject);

            projectFrame.getContent().add(tempPanelSec);
        } else {
            tempPanel = new JPanel();
            tempPanel.setOpaque(false);
            tempPanel.setPreferredSize(new Dimension(900, 150));
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempPanelSec = GUIFunctions.createLinePanel(160);
            tempPanelSec.add(tempPanel);
            panel.add(tempPanelSec);
            label = new JLabel("There are no project to be loaded.");
            label.setFont(projectFrame.getFontLight().deriveFont(30f));
            label.setForeground(Color.white);
            tempPanel.add(label);
        }
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
        footerleftPanel.add(projectFrame.openScreenBtn(false), FlowLayout.LEFT);
        tempPanel.add(footerleftPanel);
        JPanel footerRightPanel = new JPanel();
        footerRightPanel.setOpaque(false);
        footerRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tempPanel.add(footerRightPanel);
        Dimension newSize = new Dimension(140, 40);
        btn = new CustomButton("Search Project", newSize, null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerRightPanel.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = chooser.showOpenDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    searchProject();
                }
            }
        });
        if (!projectFileList.isEmpty()) {
            btn = new CustomButton("Delete Project", newSize, null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
            btn.addMouseListener(projectFrame.new HoverBtnEffect());
            footerRightPanel.add(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeProject();
                }
            });

            btn = new CustomButton("Open Project", newSize, null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
            btn.addMouseListener(projectFrame.new HoverBtnEffect());
            footerRightPanel.add(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openProject();
                }
            });
        }
        projectFrame.getFooter().add(tempPanelSec);

        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());
    }

    /**
     * Method used to search a project
     */
    private void searchProject() {
        projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
        projectFrame.getLoadingDialog().open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                String caminho = chooser.getSelectedFile().getAbsolutePath();
                File projectPath = new File(caminho);
                boolean openPage = false;
                if (projectPath.exists()) {
                    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(projectPath.getPath()))) {
                        projectFrame.setProject((Project) in.readObject());
                        projectFrame.getLoadingDialog().close();
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project loaded successfully.", projectFrame.getClosebtn()));
                        openPage = true;
                    } catch (IOException | ClassNotFoundException e) {
                        projectFrame.getLoadingDialog().close();
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not loaded, an error occured.", projectFrame.getClosebtn()));
                        return false;
                    }
                } else {
                    projectFrame.getLoadingDialog().close();
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not found.", projectFrame.getClosebtn()));
                }
                if (openPage) {
                    projectFrame.getLoadingDialog().close();
                    projectFrame.openPage("Main Menu");
                }
                return true;
            }
        };
        worker.execute();
    }

    /**
     * Method used to remove a project
     */
    private void removeProject() {
        projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
        projectFrame.getLoadingDialog().open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                Object select = projectList.getSelectedValue();
                if (select == null) {
                    projectFrame.getLoadingDialog().close();
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please choose a project to delete.", projectFrame.getClosebtn()));
                    return true;
                }
                String selected = select.toString() + ".bin";
                String cleanName = selected.toString().replaceAll(".bin", "").trim();
                File resultDir = new File(cleanName);
                if (resultDir.isDirectory()) {
                    resultDir.delete();
                }
                File projectPath = new File(projectFrame.getStorageFilesPath() + "\\" + selected.toString());
                boolean showLastMessage = false;
                if (projectPath.exists()) {
                    if (projectPath.delete()) {
                        projectFrame.getLoadingDialog().close();
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project deleted successfully.", projectFrame.getClosebtn()));
                        projectModelList.removeElement(select);
                    } else {
                        projectFrame.getLoadingDialog().close();
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not deleted.\nThere was a problem deleting.", projectFrame.getClosebtn()));
                        return false;
                    }
                } else {
                    showLastMessage = true;
                    projectModelList.removeElement(select);
                }
                projectFrame.getLoadingDialog().close();
                if (showLastMessage) {
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not deleted.\nThere are no associated file.", projectFrame.getClosebtn()));
                }
                if (projectModelList.isEmpty()) {
                    projectFrame.openPage("Load Project");
                }
                return true;
            }
        };
        worker.execute();
    }

    /**
     * Method used to open a project
     */
    private void openProject() {
        projectFrame.setLoadingDialog(new LoadingDialog(projectFrame.getFrame()));
        projectFrame.getLoadingDialog().open();
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                Object selected = projectList.getSelectedValue();
                if (selected == null) {
                    projectFrame.getLoadingDialog().close();
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Please choose a project to open.", projectFrame.getClosebtn()));
                    return true;
                }
                File projectPath = new File(projectFrame.getStorageFilesPath() + "\\" + selected.toString() + ".bin");
                boolean openPage = false;
                if (projectPath.exists()) {
                    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(projectPath.getPath()))) {
                        projectFrame.setProject((Project) in.readObject());
                        projectFrame.getLoadingDialog().close();
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project loaded successfully.", projectFrame.getClosebtn()));
                        openPage = true;
                    } catch (IOException | ClassNotFoundException e) {
                        projectFrame.getLoadingDialog().close();
                        projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not loaded, an error occured.", projectFrame.getClosebtn()));
                        return false;
                    }
                } else {
                    projectFrame.getLoadingDialog().close();
                    projectFrame.setMessageDialog(new MessageDialog(projectFrame.getFrame(), true, projectFrame.getDarkBgColor(), projectFrame.getFontLight(), Color.white, 22f, "Project not found.", projectFrame.getClosebtn()));
                }
                if (openPage) {
                    projectFrame.getLoadingDialog().close();
                    projectFrame.openPage("Main Menu");
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
