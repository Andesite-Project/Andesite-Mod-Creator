/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.varden.andesite.creator.gui.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Picture box for Swing.
 * @author Marius
 */
public class ImagePanel extends JPanel {
    private BufferedImage img = null;
    private AnchorStyle style = AnchorStyle.TOPLEFT;
    
    /**
     * Constructs an image panel with a given image.
     * @param img The image. Set to null for no image
     */
    public ImagePanel(BufferedImage img) {
        super();
        this.img = img;
    }
    
    /**
     * Paints the component.
     * @param g A Graphics instance
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            Image scaled;
            if ((double) img.getWidth() / (double) img.getHeight() >= (double) getWidth() / (double) getHeight()) {
                scaled = img.getScaledInstance(this.getWidth(), -1, Image.SCALE_SMOOTH);
            } else {
                scaled = img.getScaledInstance(-1, this.getHeight(), Image.SCALE_SMOOTH);
            }
            int x;
            int y;
            switch (this.style) {
                case TOPLEFT:
                    x = 0;
                    y = 0;
                    break;
                case CENTERED:
                    x = (getWidth() - scaled.getWidth(null)) / 2;
                    y = (getHeight() - scaled.getHeight(null)) / 2;
                    break;
                default:
                    scaled.flush();
                    return;
            }
            g.drawImage(scaled, x, y, null);
            scaled.flush();
        }
    }
    
    /**
     * Disposes of resources held by the object.
     */
    public void flush() {
        try {
            img.flush();
            img = null;
        } catch (Exception ex) {
            // Silently suppress
        }
    }
    
    /**
     * Loads a new image to the panel.
     * @param img The new image
     */
    public void loadImage(BufferedImage img) {
        if (img != null) {
            flush();
        }
        this.img = img;
        repaint();
    }
    
    /**
     * Sets the image anchor style.
     * @param style The new anchor style.
     */
    public void setAnchorStyle(AnchorStyle style) {
        
    }
    
    /**
     * Defines anchor styles for the image.
     */
    public enum AnchorStyle {
        TOPLEFT, CENTERED
    }
}
