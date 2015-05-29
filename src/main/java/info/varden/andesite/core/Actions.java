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

import info.varden.andesite.action.BlockHardnessAction;
import info.varden.andesite.action.BlockLightValueAction;
import info.varden.andesite.action.BlockParticleGravityAction;
import info.varden.andesite.action.BlockSlipperinessAction;
import info.varden.andesite.action.BlockStepSoundAction;
import info.varden.andesite.io.Serialization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which indexes and allows lookup of different action classes.
 * @author Marius
 */
public final class Actions {
    /**
     * List of action types indexed by their corresponding IDs.
     */
    private static final HashMap<Integer, Class<? extends Action>> actionMap = new HashMap<Integer, Class<? extends Action>>() {{
        ArrayList<Class<? extends Action>> actionClasses = new ArrayList<Class<? extends Action>>() {{
            add(BlockLightValueAction.class);
            add(BlockStepSoundAction.class);
            add(BlockSlipperinessAction.class);
            add(BlockHardnessAction.class);
            add(BlockParticleGravityAction.class);
        }};
        
        for (Class<? extends Action> actionClass : actionClasses) {
            put(actionClass.getAnnotation(ActionData.class).id(), actionClass);
        }
    }};
    
    /**
     * Finds an action type by its ID.
     * @param id The ID to look up
     * @return Class of action, constructable with reflection
     */
    public static Class<? extends Action> findClassById(int id) {
        if (actionMap.containsKey(id)) {
            return actionMap.get(id);
        } else {
            return null;
        }
    }
    
    /**
     * Reads an action from the given input stream.
     * @param input The input stream to read from
     * @return An action instance
     * @throws ClassCastException The action in the project format stream is not an instance of Action
     * @throws InstantiationException Can not instantiate the action contained in the project stream
     * @throws IllegalAccessException The action class corresponding to the action stored in the project stream can not be accessed
     * @throws InvocationTargetException The constructor for the action in the project format stream fails to invoke
     * @throws SecurityException The action cannot be read
     * @throws NoSuchMethodException The action does not support instantiation
     * @throws IOException I/O operation fails
     * @throws IllegalArgumentException The constructor for the action is invalid
     */
    public static Action readFromInput(DataInputStream input) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, IOException {
        int actionID = input.readInt();
        Class<? extends Action> actionClass = findClassById(actionID);
        if (actionClass == null) {
            return null;
        }
        return Serialization.readFromInput(input, actionClass);
    }
    
    /**
     * Writes an action to the given output stream.
     * @param action The action to write
     * @param output The output stream to write to
     * @throws IOException I/O operation fails
     */
    public static void writeToOutput(Action action, DataOutputStream output) throws IOException {
        ActionData data = action.getClass().getAnnotation(ActionData.class);
        output.writeInt(data.id());
        Serialization.writeToOutput(action, output);
    }
}
