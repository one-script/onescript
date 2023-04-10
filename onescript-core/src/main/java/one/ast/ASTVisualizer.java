package one.ast;

import one.parser.util.StringReader;

import static one.util.ANSI.*;

import java.util.Stack;

public class ASTVisualizer {

    private static String indent(Stack<String> indents, int space) {
        final StringBuilder b = new StringBuilder();
        for (String s : indents) {
            b.append(s + " ".repeat(space));
        }

        return b.toString();
    }

    private static String BLUE = getRGBForeground(100, 100, 200);

    public static String newLineToString(ASTNode node, boolean color) {
        final StringBuilder b = new StringBuilder();
        final StringReader  r = new StringReader(node.toString());
        final Stack<String> indents = new Stack<>();

        int col1 = 120;
        int col2 = 120;

        while (r.current() != StringReader.EOF) {
            char c = r.curr();

            // string highlighting //
            if (c == '"') {
                b.append(GREEN).append('"');
                r.next();
                while (r.curr() != '"') {
                    b.append(r.curr());
                    r.next();
                }

                b.append('"').append(RESET);
                r.next();
                continue;
            }

            // other chars //
            String suffix = "";
            String prefix = "";
            switch (c) {
                case '('      -> { indents.push(format("|", getRGBForeground(col1, col1, col1 * 2)));
                    prefix = BLUE;
                    suffix = RESET + "\n" + indent(indents, 1); }
                case '['      -> { indents.push(format("|", getRGBForeground(col2, col2 * 2, col2 * 2)));
                    prefix = CYAN;
                    suffix = RESET + "\n" + indent(indents, 1); }
                case ')' -> { indents.pop();
                    prefix = "\n" + indent(indents, 1) + BLUE;
                    suffix = RESET; }
                case ']' -> { indents.pop();
                    prefix = "\n" + indent(indents, 1) + CYAN;
                    suffix = RESET; }

                case ':' -> { prefix = RED; suffix = RESET; }
                case ',' -> { prefix = YELLOW; suffix = RESET; }
            };

            col1 = 25 + indents.size() * 12;
            col2 = 25 + indents.size() * 12;

            b.append(prefix).append(c).append(suffix);

            r.next();
        }

        return b.toString();
    }

}
