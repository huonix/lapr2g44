package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Class used to create the open screen page
 *
 * @author Group 44 LAPR2
 */
public final class OpenScreenPage {

    private ProjectFrame projectFrame;
    private JButton newProjectBtn;
    private JButton loadProjectBtn;

    /**
     * Constructor used to create the open screen page
     *
     * @param projectFrame Parent frame
     */
    public OpenScreenPage(ProjectFrame projectFrame) {
        setProjectFrame(projectFrame);
    }

    /**
     * Method used to open the open screen page
     */
    public void open() {
        projectFrame.updatePageTitle("ISEP > Group 44");
        projectFrame.fadeOut(projectFrame.getContent());

        newProjectBtn = new CustomButton("New Project", projectFrame.getBigButtonSize(), "/images/newProject.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        loadProjectBtn = new CustomButton("Load Project", projectFrame.getBigButtonSize(), "/images/database.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());

        newProjectBtn.addMouseListener(projectFrame.new HoverBtnEffect());
        loadProjectBtn.addMouseListener(projectFrame.new HoverBtnEffect());

        projectFrame.getContent().add(newProjectBtn);
        projectFrame.getContent().add(loadProjectBtn);

        newProjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openPage("New Project");
            }
        });

        loadProjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openPage("Load Project");
            }
        });
        projectFrame.fadeIn(projectFrame.getContent());
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
