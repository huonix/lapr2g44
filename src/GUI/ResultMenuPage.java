package GUI;

import Core.Result;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class used to create the result menu page
 *
 * @author Group 44 LAPR2
 */
public final class ResultMenuPage {

    private ProjectFrame projectFrame;
    private Result result;
    private JButton btn;
    private JPanel tempPanel;
    private JPanel tempPanelSec;

    /**
     * Constructor used to creation the result menu page
     *
     * @param projectFrame Parent Frame
     * @param result Result
     */
    public ResultMenuPage(ProjectFrame projectFrame, Result result) {
        setProjectFrame(projectFrame);
        setResult(result);
    }

    /**
     * Method used to open the result menu page
     *
     * @param tempResult Result to be shown
     */
    public void open(Result tempResult) {
        setResult(tempResult);
        projectFrame.updatePageTitle("ISEP > " + projectFrame.getProject().getName() + " > " + result.getSearchName() + " > Result");
        projectFrame.fadeOut(projectFrame.getContent());

        //Sub Container creation with Flowlayout
        JPanel panel = new JPanel();
        panel.setPreferredSize(projectFrame.getMaxSize());
        panel.setOpaque(false);

        btn = new CustomButton("Show / Export", projectFrame.getBigButtonSize(), "/images/export.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openResultPage(result, "Show/Export");
            }
        });
        panel.add(btn);

        btn = new CustomButton("Filter result", projectFrame.getBigButtonSize(), "/images/filter.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openResultPage(result, "Filter result");
            }
        });
        panel.add(btn);

        btn = new CustomButton("Statistics", projectFrame.getBigButtonSize(), "/images/statistics.png", 22f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openResultPage(result, "Statistics");
            }
        });
        panel.add(btn);

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
        btn = new CustomButton("Back to result list", projectFrame.getFooterSizeBtn(), null, 12f, true, projectFrame.getDarkColor(), projectFrame.getLightColor(), projectFrame.getTextBtnColor(), projectFrame.getFontLight());
        btn.addMouseListener(projectFrame.new HoverBtnEffect());
        footerRightPanel.add(btn);
        projectFrame.getFooter().add(tempPanelSec);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFrame.openPage("List of results");
            }
        });
        //Add page to content
        projectFrame.getContent().add(panel);
        projectFrame.fadeIn(projectFrame.getContent());

    }

    /**
     * Method used to get the Result
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