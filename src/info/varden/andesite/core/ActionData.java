package info.varden.andesite.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface storing ID and compatibility data for actions.
 * @author Marius
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ActionData {
    /**
     * Gets the ID of the action.
     * @return The ID number
     */
    public int id();
    /**
     * Gets the minimum required Andesite version for this action.
     * @return The Andesite version number
     */
    public int version();
}
