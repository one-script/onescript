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

    private static boolean isNameChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_' || c == '-';
    }

    public static String newLineToString(ASTNode node, boolean color) {
        final StringBuilder b = new StringBuilder();
        final StringReader  r = new StringReader(node.toString());
        final Stack<String> indents = new Stack<>();

        final int col1min = 25;
        final int col2min = 25;

        int col1 = col1min;
        int col2 = col2min;

        while (r.current() != StringReader.EOF) {
            // key highlighting //
            if (isNameChar(r.curr())) {
                StringBuilder nb = new StringBuilder();
                while (isNameChar(r.curr())) {
                    nb.append(r.curr());
                    r.next();
                }

                // check current char
                String formatting = switch (r.current()) {
                    case ':' -> RED;
                    case '(' -> BLUE;
                    default -> RESET;
                };

                b.append(formatting).append(nb).append(RESET);
            }

            // string highlighting //
            if (r.current() == '"') {
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
            switch (r.current()) {
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

            col1 = col1min + indents.size() * 12;
            col2 = col2min + indents.size() * 12;

            b.append(prefix).append(r.curr()).append(suffix);

            r.next();
        }

        return b.toString();
    }

}
