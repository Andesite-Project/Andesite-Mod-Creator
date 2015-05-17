package info.varden.andesite.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface storing ID and compatibility data for player conditions.
 * @author Marius
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface PlayerConditionData {
    /**
     * Gets the ID of the player condition.
     * @return The ID number
     */
    public int id();
    /**
     * Gets the minimum required Andesite version for this player condition.
     * @return The Andesite version number
     */
    public int version();
}
