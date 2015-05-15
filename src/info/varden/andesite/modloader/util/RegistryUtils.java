// Adapted from CoFHLib, LGPLv3

package info.varden.andesite.modloader.util;

import info.varden.andesite.modloader.AndesiteML;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ModAPIManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.RegistryDelegate;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;

public class RegistryUtils {
    private RegistryUtils() {

    }

    private static class Repl {

        private static IdentityHashMap<RegistryNamespaced, Multimap<String, Object>> replacements;
        private static Class<RegistryDelegate<?>> DelegateClass;

        @SuppressWarnings({ "rawtypes", "unchecked" })
        private static void overwrite_do(RegistryNamespaced registry, String name, Object object, Object oldThing) throws IllegalArgumentException, IllegalAccessException {

            int id = registry.getIDForObject(oldThing);
            
            Field[] nspf = RegistryNamespaced.class.getDeclaredFields();
            Field regField = null;
            Field uimField = null;
            for (Field f : nspf) {
                if (f.getType().isAssignableFrom(ObjectIntIdentityMap.class)) {
                    f.setAccessible(true);
                    uimField = f;
                } else if (f.getType().isAssignableFrom(Map.class)) {
                    f.setAccessible(true);
                    regField = f;
                }
            }
            BiMap map = (BiMap) regField.get(registry);
            ObjectIntIdentityMap underlyingIntegerMap = (ObjectIntIdentityMap) uimField.get(registry);
            
            Field[] undmaparr = RegistrySimple.class.getDeclaredFields();
            for (Field f : undmaparr) {
            	if (f.getType().isAssignableFrom(Map.class)) {
            		f.setAccessible(true);
            		Map umap = (Map) f.get(registry);
            		umap.remove(name);
            		umap.put(name, object);
            	}
            }
            
            underlyingIntegerMap.put(object, id);
            map.remove(object);
            map.forcePut(object, name);
            map.remove(name);
            map.forcePut(name, object);
            
        }

        private static void alterDelegateChain(RegistryNamespaced registry, String id, Object object) {

            Multimap<String, Object> map = replacements.get(registry);
            List<Object> c = (List<Object>) map.get(id);
            int i = 0, e = c.size() - 1;
            Object end = c.get(e);
            for (; i <= e; ++i) {
                Object t = c.get(i);
                Repl.alterDelegate(t, end);
            }
        }

        private static void alterDelegate(Object obj, Object repl) {

            if (obj instanceof Item) {
                RegistryDelegate<Item> delegate = ((Item) obj).delegate;
                ReflectionHelper.setPrivateValue(DelegateClass, delegate, repl, "referant");
            }
        }

        static {

            replacements = new IdentityHashMap<RegistryNamespaced, Multimap<String, Object>>(2);
            MinecraftForge.EVENT_BUS.register(new RegistryUtils());
            try {
                DelegateClass = (Class<RegistryDelegate<?>>) Class.forName(RegistryDelegate.Delegate.class.getName());
            } catch (Throwable e) {
                Throwables.propagate(e);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void _(WorldEvent.Load event) {

        if (Repl.replacements.size() < 1) {
            return;
        }
        for (Map.Entry<RegistryNamespaced, Multimap<String, Object>> entry : Repl.replacements.entrySet()) {
            RegistryNamespaced reg = entry.getKey();
            Multimap<String, Object> map = entry.getValue();
            Iterator<String> v = map.keySet().iterator();
            while (v.hasNext()) {
                String id = v.next();
                List<Object> c = (List<Object>) map.get(id);
                int i = 0, e = c.size() - 1;
                Object end = c.get(e);
                if (reg.getIDForObject(c.get(0)) != reg.getIDForObject(end)) {
                    for (; i <= e; ++i) {
                        Object t = c.get(i);
                        Object oldThing = reg.getObject(id);
                        try {
                            Repl.overwrite_do(reg, id, t, oldThing);
                        } catch (IllegalArgumentException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        }
                        Repl.alterDelegate(oldThing, end);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void overwriteEntry(RegistryNamespaced registry, String name, Object object) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, ClassNotFoundException, InvocationTargetException {

        if (ModAPIManager.INSTANCE.hasAPI("CoFHLib")) {
            AndesiteML.info("Found CoFHLib; reflecting into it for compatibility");
            Method m = Class.forName("cofh.lib.util.RegistryUtils").getMethod("overwriteEntry", new Class<?>[] {RegistryNamespaced.class, String.class, Object.class});
            m.invoke(null, new Object[] {registry, name, object});
            return;
        }
        
        AndesiteML.warn("CoFHLib not found, using fallback utils. This might affect compatibility with other mods!");
        Object oldThing = registry.getObject(name);
        Repl.overwrite_do(registry, name, object, oldThing);
        Multimap<String, Object> reg = Repl.replacements.get(registry);
        if (reg == null) {
            Repl.replacements.put(registry, reg = ArrayListMultimap.create());
        }
        if (!reg.containsKey(name)) {
            reg.put(name, oldThing);
        }
        reg.put(name, object);
        Repl.alterDelegateChain(registry, name, object);
        
        
    }
    
    private static IdentityHashMap<RegistryNamespaced, Multimap<String, Object>> replacements;
    private static Class<RegistryDelegate<?>> DelegateClass;
    
    static {
        if (ModAPIManager.INSTANCE.hasAPI("CoFHLib")) {
            replacements = new IdentityHashMap<RegistryNamespaced, Multimap<String, Object>>(2);
            MinecraftForge.EVENT_BUS.register(new RegistryUtils());
            try {
                DelegateClass = (Class<RegistryDelegate<?>>) Class.forName(RegistryDelegate.Delegate.class.getName());
            } catch (Throwable t) {
                Throwables.propagate(t);
            }
        }
    }
}
