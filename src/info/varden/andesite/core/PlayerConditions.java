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

import info.varden.andesite.io.Serialization;
import info.varden.andesite.playercondition.*;

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
public final class PlayerConditions {
    private static final HashMap<Integer, Class<? extends PlayerCondition>> conditionMap = new HashMap<Integer, Class<? extends PlayerCondition>>() {{
        ArrayList<Class<? extends PlayerCondition>> conditionClasses = new ArrayList<Class<? extends PlayerCondition>>() {{
            add(PlayerAbsorbtionCondition.class);
        }};
        
        for (Class<? extends PlayerCondition> conditionClass : conditionClasses) {
            put(conditionClass.getAnnotation(PlayerConditionData.class).id(), conditionClass);
        }
    }};
    
    /**
     * Finds a player condition type by its ID.
     * @param id The ID to look up
     * @return Class of player condition, constructable with reflection
     */
    public static Class<? extends PlayerCondition> findClassById(int id) {
        if (conditionMap.containsKey(id)) {
            return conditionMap.get(id);
        } else {
            return null;
        }
    }
    
    public static PlayerCondition readFromInput(DataInputStream input) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, IOException {
        int condID = input.readInt();
        Class<? extends PlayerCondition> condClass = findClassById(condID);
        if (condClass == null) {
            return null;
        }
        return Serialization.readFromInput(input, condClass);
    }
    
    public static void writeToOutput(PlayerCondition cond, DataOutputStream output) throws IOException {
        PlayerConditionData data = cond.getClass().getAnnotation(PlayerConditionData.class);
        output.writeInt(data.id());
        Serialization.writeToOutput(cond, output);
    }
}
