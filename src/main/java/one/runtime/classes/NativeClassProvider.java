package one.runtime.classes;

import one.type.OneClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Resolves and loads/composes native classes by
 * referenced script name.
 */
public class NativeClassProvider {

    /* ------ Resolver Tree ------ */
    private static class TreeNode {
        private TreeNode(String value) {
            this.value = value;
        }

        private final Map<String, TreeNode> children = new HashMap<>();
        private final String value;
        private List<NativeClassResolver> resolvers = new ArrayList<>();

        public void add(NativeClassResolver resolver) {
            for (int i = 0; i < resolvers.size(); i++) {
                NativeClassResolver q = resolvers.get(i);

                if (q.getPriority() < resolver.getPriority()) {
                    resolvers.add(i, resolver);
                }
            }
        }
    }

    /** The cached resolved classes. */
    private final Map<String, OneClass> loadedByScriptName = new HashMap<>();
    private final Map<String, OneClass> loadedByJVMName = new HashMap<>();

    /** The qualifier tree node. */
    private final TreeNode resolverTree = new TreeNode(null);

    public void addResolver(NativeClassResolver resolver) {
        for (String pk : resolver.getPackages()) {
            String[] elements = pk.split("\\.");
            TreeNode current = resolverTree;
            for (String element : elements) {
                TreeNode parent = current;
                current = parent.children.get(element);
                if (current == null) {
                    current = new TreeNode(element);
                    parent.children.put(element, current);
                }
            }

            current.add(resolver);
        }
    }

    /**
     * Resolves or composes a native class to a
     * OneScript class type by the script name.
     *
     * Used during composition/compilation.
     *
     * @param scriptName The class name as referenced by scripts.
     * @return The class type.
     */
    public OneClass findByScriptName(String scriptName) {
        OneClass classType = loadedByScriptName.get(scriptName);
        if (classType != null) {
            return classType;
        }

        // find the name of the JVM class
        String foundName = null;
        String[] splitName = scriptName.split("\\.");
        TreeNode node = resolverTree;
        // iterating over the name elements
        // makes sure the most specific qualifiers
        // come last, which means they have higher
        // priority which is exactly what we want
        for (String nameElement : splitName) {
            // iterate over all qualifiers
            // on the node, these are already
            // sorted based on priority
            for (NativeClassResolver qualifier : node.resolvers) {
                String name = qualifier.qualifyJVMName(scriptName);
                if (name != null) {
                    foundName = name;
                    break;
                }
            }

            node = node.children.get(nameElement);
            if (node == null)
                break;
        }

        if (foundName == null) {
            throw new IllegalArgumentException("Could not find native class for script name '" + scriptName + "'");
        }

        OneClass type = loadedByJVMName.get(foundName);
        if (type != null) {
            return type;
        }

        try {
            Class<?> nativeClass = Class.forName(foundName);
            type = composeNativeClass(nativeClass);
            loadedByScriptName.put(scriptName, type);
            loadedByJVMName.put(nativeClass.getName(), type);
            return type;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("No Java class by name '" + foundName + "'");
        } catch (Exception e) {
            throw new IllegalStateException("Error while composing native class " + foundName, e);
        }
    }

    /**
     * Composes a native class from the JVM class
     * type. The provided primary name is the internal/script
     * name given to the class type.
     *
     * @param klass The native class.
     * @return The class type.
     */
    public OneClass composeNativeClass(Class<?> klass)
            throws Exception {
        throw new UnsupportedOperationException("TODO"); // todo
    }

}
