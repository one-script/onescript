package one.parser.util;

import one.lang.OneOperator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Matches parsable objects in a tree like structure.
 */
public class ParsableRegistry<T extends Parsable> implements StringReader.ForwardMatcherProvider<T> {

    /** A node in the tree. */
    private class Node {
        public T value;
        public char character;
        public Map<Character, Node> children = new HashMap<>();

        public void insert(Node node) {
            children.put(node.character, node);
        }
    }

    /** All parsable objects mapped by name. */
    private final Map<String, T> map = new HashMap<>();

    /** The list. */
    private List<T> list;

    public ParsableRegistry<T> withList(List<T> list) {
        this.list = list;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    /** The root node. */
    private final Node root = new Node();

    public Map<String, T> getMap() {
        return map;
    }

    public void insert(T value) {
        map.put(value.getName(), value);
        if (list != null)
            list.add(value);
        for (String str : value.getAliases()) {
            Node current = root;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                Node prev = current;
                current = prev.children.get(c);
                if (current == null) {
                    current = new Node();
                    current.character = c;
                    prev.insert(current);
                }
            }

            current.value = value;
        }
    }

    public void remove(T value) {
        map.remove(value.getName());
        for (String str : value.getAliases()) {
            Node current = root;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                Node prev = current;
                current = prev.children.get(c);
                if (current == null) {
                    break;
                }
            }

            if (current != null)
                current.value = null;
        }
    }

    @Override
    public StringReader.ForwardMatcher<T> matcher() {
        return new StringReader.ForwardMatcher<>() {
            Node oldNode;
            Node currentNode = root;

            @Override
            public boolean next(StringReader reader) {
                char c = reader.next();
                oldNode = currentNode;
                currentNode = currentNode.children.get(c);
                return currentNode != null;
            }

            @Override
            public T getResult() {
                if (oldNode == null)
                    return null;
                return oldNode.value;
            }
        };
    }

}
