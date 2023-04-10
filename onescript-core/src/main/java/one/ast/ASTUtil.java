package one.ast;

import one.parser.util.StringReader;

import java.util.Stack;

public class ASTUtil {

    private static String indent(Stack<String> indents, int space) {
        final StringBuilder b = new StringBuilder();
        for (String s : indents) {
            b.append(s + " ".repeat(space));
        }

        return b.toString();
    }

    public static String newLineToString(ASTNode node) {
        final StringBuilder b = new StringBuilder();
        final StringReader  r = new StringReader(node.toString());
        final Stack<String> indents = new Stack<>();
        while (r.current() != StringReader.EOF) {
            char c = r.curr();
            String suffix = "";
            String prefix = "";
            switch (c) {
                case '('      -> { indents.push("."); suffix = "\n" + indent(indents, 1); }
                case '['      -> { indents.push("'"); suffix = "\n" + indent(indents, 1); }
                case ')', ']' -> { indents.pop(); prefix = "\n" + indent(indents, 1); }
            };

            b.append(prefix).append(c).append(suffix);

            r.next();
        }

        return b.toString();
    }

}
