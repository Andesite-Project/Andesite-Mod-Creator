/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.varden.andesite.core;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Describes a project and holds all its data.
 * @author Marius
 */
public class AndesiteProject {
    /**
     * Constructs a project using project property settings.
     * @param properties Properties for the project, including name, version and description
     */
    public AndesiteProject(ProjectProperties properties) {
        this.properties = properties;
    }
    
    /**
     * Properties field
     */
    public ProjectProperties properties;
    
    
    private ArrayList<Screenshot> screenshots = new ArrayList<Screenshot>();
    private ArrayList<Action> actions = new ArrayList<Action>();
    public String author = "";
    
    /**
     * Adds a screenshot to this project's list of screenshots.
     * @param screenshot Screenshot to add
     */
    public void addScreenshot(Screenshot screenshot) {
        this.screenshots.add(screenshot);
    }
    
    /**
     * Removes a screenshot based on its index in getAllScreenshots().
     * @param index The index of the screenshot to remove
     */
    public void removeScreenshot(int index) {
        this.screenshots.remove(index);
    }
    
    /**
     * Returns a list of all screenshots in this project.
     * @return List of screenshots
     */
    public Screenshot[] getAllScreenshots() {
        return this.screenshots.toArray(new Screenshot[0]);
    }
    
    /**
     * Returns a list of all screenshots in this project.
     * @return List of screenshots
     */
    public ArrayList<Screenshot> listAllScreenshots() {
        return this.screenshots;
    }
    
    /**
     * Returns the number of screenshots in this project.
     * @return Integer representing the number of screenshots.
     */
    public int getScreenshotCount() {
        return this.screenshots.size();
    }
    
    /**
     * Swaps two screenshots in the list of screenshots.
     * @param a Index of the screenshot to be swapped.
     * @param b Index of the other screenshot to be swapped.
     */
    public void swapScreenshotIndices(int a, int b) {
        Collections.swap(this.screenshots, a, b);
    }
    
    /**
     * Adds an action to this project's list of actions.
     * @param action Action to add
     */
    public void addAction(Action action) {
        this.actions.add(action);
    }
    
    /**
     * Removes an action based on its index in getAllActions().
     * @param index The index of the action to remove
     */
    public void removeAction(int index) {
        this.actions.remove(index);
    }
    
    /**
     * Returns a list of all actions in this project.
     * @return List of actions
     */
    public Action[] getAllActions() {
        return this.actions.toArray(new Action[0]);
    }
    
    /**
     * Returns a list of all actions in this project.
     * @return List of actions
     */
    public ArrayList<Action> listAllActions() {
        return this.actions;
    }
    
    /**
     * Returns the number of actions in this project.
     * @return Integer representing the number of actions.
     */
    public int getActionCount() {
        return this.actions.size();
    }
}
