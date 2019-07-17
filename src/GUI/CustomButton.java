package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Class to used t create a custom JButton
 *
 * @author Group 44 LAPR2
 */
public final class CustomButton extends JButton {

    private String btnName;
    private Color lightColor;
    private Color darkColor;
    private int borderWidth;

    /**
     * Constructor of a nwe JButton
     *
     * @param btnName Button text
     * @param size Button size
     * @param pathImage Path to image
     * @param fontSize Size for the text font
     * @param upper Boolean if true put the text uppercase
     * @param darkColor Dark color for the button
     * @param lighColor Light color for the button
     * @param textBtnColor Color of the text
     * @param font Font to use
     */
    public CustomButton(String btnName, Dimension size, String pathImage, float fontSize, boolean upper, Color darkColor, Color lighColor, Color textBtnColor, Font font) {
        setBtnName(btnName);
        setDarkColor(darkColor);
        setLightColor(lightColor);
        setBorderWidth(borderWidth);
        setBackground(darkColor);
        setPreferredSize(size);
        setOpaque(true);
        if (pathImage != null && !pathImage.isEmpty()) {
            URL imagePath = getClass().getResource(pathImage);
            if (imagePath != null) {
                ImageIcon icon = new ImageIcon(imagePath);
                setIcon(icon);
            }
        }
        setForeground(textBtnColor);
        setFont(font.deriveFont(fontSize));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        if (upper) {
            setText(btnName.toUpperCase());
        } else {
            setText(btnName);
        }
    }

    /**
     * Method used to get a button name
     *
     * @return String with the button name
     */
    public String getBtnName() {
        return btnName;
    }

    /**
     * Method used to set the button name
     *
     * @param btnName String with the button name
     */
    public void setBtnName(String btnName) {
        this.btnName = (btnName != null && !btnName.isEmpty()) ? btnName : "";
    }

    /**
     * Method used to get the light color
     *
     * @return Color with the light color
     */
    public Color getLightColor() {
        return lightColor;
    }

    /**
     * Method used to set the light color
     *
     * @param lightColor Color with the light color of the button
     */
    public void setLightColor(Color lightColor) {
        this.lightColor = (lightColor != null) ? lightColor : new Color(0xFFFFFF);
    }

    /**
     * Method used to get the dark color of the button
     *
     * @return Color with the dark color
     */
    public Color getDarkColor() {
        return darkColor;
    }

    /**
     * Method used to set the dark color of the button
     *
     * @param darkColor Color with the dark color
     */
    public void setDarkColor(Color darkColor) {
        this.darkColor = (darkColor != null) ? darkColor : new Color(0x000000);
    }

    /**
     * Method used to get the border with
     *
     * @return Integer with the border width
     */
    public int getBorderWidth() {
        return borderWidth;
    }

    /**
     * Method used to set the border width
     *
     * @param borderWidth Integer with the border width
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = (borderWidth > 0) ? borderWidth : 0;
    }
}
