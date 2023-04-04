package one.parser.util;

import one.lang.OneOperator;

import java.util.HashMap;
import java.util.Map;

/**
 * Matches operators in a tree like structure.
 */
public class OperatorRegistry implements StringReader.ForwardMatcherProvider<OneOperator> {

    /** A node in the tree. */
    private class Node {
        public OneOperator operator;
        public char character;
        public Map<Character, Node> children = new HashMap<>();

        public void insert(Node node) {
            children.put(node.character, node);
        }
    }

    /** Linearly stored list of operators. */
    private final Map<String, OneOperator> map = new HashMap<>();

    /** The root node. */
    private final Node root = new Node();

    public Map<String, OneOperator> getMap() {
        return map;
    }

    public void insert(OneOperator operator) {
        map.put(operator.getName(), operator);
        for (String str : operator.getAliases()) {
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

            current.operator = operator;
        }
    }

    public void remove(OneOperator operator) {
        map.remove(operator.getName());
        for (String str : operator.getAliases()) {
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
                current.operator = null;
        }
    }

    @Override
    public StringReader.ForwardMatcher<OneOperator> matcher() {
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
            public OneOperator getResult() {
                if (oldNode == null)
                    return null;
                return oldNode.operator;
            }
        };
    }

}
