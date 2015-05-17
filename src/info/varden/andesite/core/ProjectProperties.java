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
package info.varden.andesite.core;

import java.awt.image.BufferedImage;

/**
 * Describes the properties of a project.
 * @author Marius
 */
public class ProjectProperties {
    /**
     * Constructs a property set for an AndesiteProject.
     * @param name The name of the project
     * @param modid The internal Andesite mod ID for the project
     * @param description A description of the project
     * @param version The version of the project
     * @param icon An icon representing the project
     */
    public ProjectProperties(String name, String modid, String description, String version, BufferedImage icon) {
        this.name = name;
        this.modid = modid;
        this.description = description;
        this.version = version;
        this.icon = icon;
    }
    
    /**
     * The name of the project.
     */
    public String name;
    /**
     * The internal Andesite mod ID for the project.
     */
    public String modid;
    /**
     * A description of the project.
     */
    public String description;
    /**
     * The version of the project.
     */
    public String version;
    /**
     * An icon representing the project.
     */
    public BufferedImage icon;
    
    /**
     * Checks whether or not this project has an icon.
     * @return True if an icon is present; false otherwise
     */
    public boolean hasIcon() {
        return icon != null;
    }
    
    
}
