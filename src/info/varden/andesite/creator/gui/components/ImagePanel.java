/*
 * The MIT License
 *
 * Copyright 2015 Marius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
    /**
     * The image to draw.
     */
    private BufferedImage img = null;
    /**
     * Image anchor style.
     */
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
        /**
         * Image anchors to the top-left corner of the picture box.
         */
        TOPLEFT,
        /**
         * Image anchors in the middle of the picture box.
         */
        CENTERED
    }
}
