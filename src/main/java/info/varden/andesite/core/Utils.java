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

import info.varden.andesite.io.AndesiteIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * General utilities for the Andesite Project.
 * @author Marius
 */
public class Utils {
    /**
     * Gets an array of actions from the given action list that are instances of the given action type class.
     * @param <T> The action type
     * @param actionList The list of actions to search
     * @param clazz The action type class
     * @return Array of actions in actionList which are instances of the given action type class
     */
    public static <T extends Action> T[] getActionsOfType(Action[] actionList, Class<T> clazz) {
        ArrayList<T> ret = new ArrayList<T>();
        for (Action a : actionList) {
            if (clazz.isInstance(a)) {
                ret.add((T) a);
            }
        }
        return ret.toArray((T[]) Array.newInstance(clazz, 0));
    }
    
    /**
     * Checks whether a comparison between A and B holds true.
     * @param <T> The data type
     * @param a Value A
     * @param b Value B
     * @param mode The comparison mode
     * @param clazz The data type class
     * @return True if the comparison holds true; false otherwise
     */
    public static <T> boolean comparisonValid(T a, T b, CompareMode mode, Class<T> clazz) {
        if (clazz == Float.class) {
            return compareFloat((Float) a, (Float) b, mode);
        } else if (clazz == Integer.class) {
            return compareInt((Integer) a, (Integer) b, mode);
        } else if (clazz == Long.class) {
            return compareLong((Long) a, (Long) b, mode);
        } else if (clazz == String.class) {
            return compareString((String) a, (String) b, mode);
        } else {
            // This turns into an InvocationTargetException when reflected in AndesiteIO.
            throw new UnsupportedOperationException("Type " + clazz.getName() + " is not supported by SingleFieldCondition");
        }
    }
    
    /**
     * Reads a generic value of type T from the given input stream.
     * @param <T> The value data type
     * @param input The input stream to read from
     * @param clazz The value data type class
     * @return The generic type value read
     * @throws IOException I/O fails when reading from input, or the given T is not supported by Utils
     */
    public static <T> T readGenericFromInput(DataInputStream input, Class<T> clazz) throws IOException {
        if (clazz == Float.class) {
            return (T) Float.valueOf(input.readFloat());
        } else if (clazz == Integer.class) {
            return (T) Integer.valueOf(input.readInt());
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(input.readLong());
        } else if (clazz == String.class) {
            return (T) AndesiteIO.readString(input);
        } else {
            // This turns into an InvocationTargetException when reflected in AndesiteIO.
            throw new IOException("Type " + clazz.getName() + " is not supported by SingleFieldCondition");
        }
    }
    
    /**
     * Writes a generic value of type T to the given output stream.
     * @param <T> The value data type
     * @param output The output stream to write to
     * @param data The data to write
     * @throws IOException I/O fails when writing to output, or the given T is not supported by Utils
     */
    public static <T> void writeGenericToOutput(DataOutputStream output, T data) throws IOException {
        if (data instanceof Float) {
            output.writeFloat(((Float) data));
        } else if (data instanceof Integer) {
            output.writeInt(((Integer) data));
        } else if (data instanceof Long) {
            output.writeLong(((Long) data));
        } else if (data instanceof String) {
            AndesiteIO.writeString((String) data, output);
        } else {
            // This turns into an InvocationTargetException when reflected in AndesiteIO.
            throw new IOException("Type " + data.getClass().getName() + " is not supported by SingleFieldCondition");
        }
    }
    
    /**
     * Compares two floats.
     * @param a Float A
     * @param b Float B
     * @param mode The comparison mode
     * @return Whether or not the comparison between A and B holds true
     */
    private static boolean compareFloat(float a, float b, CompareMode mode) {
        switch (mode) {
            case LESS_THAN:
                return a < b;
            case LESS_THAN_OR_EQUALS:
                return a <= b;
            case EQUALS:
                return a == b;
            case GREATER_THAN_OR_EQUALS:
                return a >= b;
            case GREATER_THAN:
                return a > b;
            case NOT_EQUALS:
                return a != b;
            default:
                break;
        }
        throw new UnsupportedOperationException("Comparison mode " + mode.toString() + " not supported by comparisonValid for this type!");
    }
    
    /**
     * Compares two integers.
     * @param a Integer A
     * @param b Integer B
     * @param mode The comparison mode
     * @return Whether or not the comparison between A and B holds true
     */
    private static boolean compareInt(int a, int b, CompareMode mode) {
        switch (mode) {
            case LESS_THAN:
                return a < b;
            case LESS_THAN_OR_EQUALS:
                return a <= b;
            case EQUALS:
                return a == b;
            case GREATER_THAN_OR_EQUALS:
                return a >= b;
            case GREATER_THAN:
                return a > b;
            case NOT_EQUALS:
                return a != b;
            default:
                break;
        }
        throw new UnsupportedOperationException("Comparison mode " + mode.toString() + " not supported by comparisonValid for this type!");
    }
    
    /**
     * Compares two longs.
     * @param a Long A
     * @param b Long B
     * @param mode The comparison mode
     * @return Whether or not the comparison between A and B holds true
     */
    private static boolean compareLong(long a, long b, CompareMode mode) {
        switch (mode) {
            case LESS_THAN:
                return a < b;
            case LESS_THAN_OR_EQUALS:
                return a <= b;
            case EQUALS:
                return a == b;
            case GREATER_THAN_OR_EQUALS:
                return a >= b;
            case GREATER_THAN:
                return a > b;
            case NOT_EQUALS:
                return a != b;
            default:
                    break;
        }
        throw new UnsupportedOperationException("Comparison mode " + mode.toString() + " not supported by comparisonValid for this type!");
    }
    
    /**
     * Compares two strings.
     * @param a String A
     * @param b String B
     * @param mode The comparison mode
     * @return Whether or not the comparison between A and B holds true
     */
    private static boolean compareString(String a, String b, CompareMode mode) {
        switch (mode) {
            case EQUALS:
                return a.equals(b);
            default:
                break;
        }
        throw new UnsupportedOperationException("Comparison mode " + mode.toString() + " not supported by comparisonValid for this type!");
    }
}
