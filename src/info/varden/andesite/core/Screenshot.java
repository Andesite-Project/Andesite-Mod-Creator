/*
 * The MIT License
 *
 * Copyright 2015 The Andesite Developer Team.
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
package info.varden.andesite.core;

import java.awt.image.BufferedImage;

/**
 * Holds data for a screenshot.
 * @author Marius
 */
public class Screenshot {
    /**
     * The actual screenshot.
     */
    public final BufferedImage image;
    /**
     * The title of the screenshot.
     */
    public String title;
    /**
     * The caption of the screenshot.
     */
    public String caption;
    
    /**
     * Constructs a screenshot object with an image, title and caption.
     * @param image The actual screenshot
     * @param title The title of the screenshot
     * @param caption The caption of the screenshot
     */
    public Screenshot(BufferedImage image, String title, String caption) {
        this.image = image;
        this.title = title;
        this.caption = caption;
    }
    
    /**
     * Disposes of all resources held by the screenshot object.
     */
    public void flush() {
        try {
            this.image.flush();
        } catch (Exception ex) {
            // Silently drop
        }
    }
}
