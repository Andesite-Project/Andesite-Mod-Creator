/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public String name;
    public String modid;
    public String description;
    public String version;
    public BufferedImage icon;
    
    /**
     * Checks whether or not this project has an icon.
     * @return True if an icon is present; false otherwise.
     */
    public boolean hasIcon() {
        return icon != null;
    }
    
    
}
