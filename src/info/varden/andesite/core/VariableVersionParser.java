package info.varden.andesite.core;

/**
 * Interface which signifies that the given class has variable support depending on the contents of the class or de-serialization results.
 * @author Marius
 */
public interface VariableVersionParser {
    /**
     * Gets the minimum required Andesite version supported by the class instance.
     * @return The minimum Andesite version number
     */
    public int getVersion();
    /**
     * Gets whether or not the action is fully supported under the current Andesite version.
     * @return True if supported; false otherwise
     */
    public boolean isSupported();
}
