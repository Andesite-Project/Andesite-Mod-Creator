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

import info.varden.andesite.action.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which indexes and allows lookup of different action classes.
 * @author Marius
 */
public final class Actions {
    private static final HashMap<Integer, Class<? extends Action>> actionMap = new HashMap<Integer, Class<? extends Action>>() {{
        ArrayList<Class<? extends Action>> actionClasses = new ArrayList<Class<? extends Action>>() {{
            add(BlockLightValueAction.class);
            add(BlockStepSoundAction.class);
            add(BlockSlipperinessAction.class);
            add(BlockHardnessAction.class);
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
}
