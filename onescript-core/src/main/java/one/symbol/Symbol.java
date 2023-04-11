package one.symbol;

import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.token.TokenType;
import one.parser.token.Tokens;
import one.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Denotes a symbol or multiple symbols if the
 * last part is a '*'.
 */
public class Symbol {

    public static Symbol parse(ParseContext context, SymbolType type) {
        if (context.currentType() != Tokens.IDENTIFIER)
            return null;
        List<String> parts = new ArrayList<>();
        TokenType<?> t;
        while ((t = context.currentType()) == Tokens.IDENTIFIER) {
            parts.add(context.current().getValueAs());
            context.next();
            if (context.currentType() != Tokens.DOT)
                break;
            context.next();
        }

        if (t == TokenType.OPERATOR && context.current().getValueAs() == OneOperator.MUL) {
            parts.add("*");
        }

        return new Symbol(parts, type);
    }

    public static Symbol parseGeneric(ParseContext context, SymbolType type) {
        Symbol symbol = parse(context, type);

        if (context.currentType() == Tokens.LEFT_ANGLE) {
            symbol.setOpenedTypeParams(true);
            context.next();

            // collect symbols
            while (context.currentType() == Tokens.IDENTIFIER) {
                Symbol s = parseGeneric(context, SymbolType.TYPE);
                symbol.getTypeParams().add(s);
                if (context.next() == null || context.currentType() != Tokens.COMMA)
                    break;
                context.next();
            }

            // skip >
            context.next();
        }

        return symbol;
    }

    public Symbol(String name, SymbolType type, List<Symbol> typeParams) {
        this.name = name;
        this.nameParts = Arrays.asList(name.split("\\."));
        this.type = type;
        this.typeParams = typeParams;
    }

    public Symbol(List<String> nameParts, SymbolType type, List<Symbol> typeParams) {
        this.name = Strings.join(nameParts, ".");
        this.nameParts = nameParts;
        this.type = type;
        this.typeParams = typeParams;
    }

    public Symbol(String name, SymbolType type) {
        this(name, type, new ArrayList<>());
    }

    public Symbol(List<String> nameParts, SymbolType type) {
        this(nameParts, type, new ArrayList<>());
    }

    /** The parts of the name separated by . */
    private List<String> nameParts;
    private String name;

    /** The type parameters. */
    private List<Symbol> typeParams;
    private boolean openedTypeParams;

    private SymbolType type;

    public Symbol getChild(Symbol name, SymbolType type) {
        List<String> parts = new ArrayList<>(getNameParts());
        parts.addAll(name.nameParts);
        return new Symbol(parts, type);
    }

    public Symbol setNameParts(List<String> nameParts) {
        this.nameParts = nameParts;
        return this;
    }

    public Symbol setName(String name) {
        this.name = name;
        return this;
    }

    public Symbol setTypeParams(List<Symbol> typeParams) {
        this.typeParams = typeParams;
        return this;
    }

    public boolean isOpenedTypeParams() {
        return openedTypeParams;
    }

    public Symbol setOpenedTypeParams(boolean openedTypeParams) {
        this.openedTypeParams = openedTypeParams;
        return this;
    }

    public Symbol setType(SymbolType type) {
        this.type = type;
        return this;
    }

    public boolean isEmpty() {
        return nameParts.isEmpty();
    }

    public boolean isSpecific() {
        return !isEmpty() && !nameParts.get(nameParts.size() - 1).equals("*");
    }

    public Symbol getDeclaring() {
        if (type.declaringGetter == null) {
            List<String> np = this.getNameParts();
            np = np.subList(0, np.size() - 1);
            return new Symbol(np, type);
        }

        return type.declaringGetter.apply(this);
    }

    public SymbolType getDeclaringType() {
        return type.declaringType;
    }

    public SymbolType getType() {
        return type;
    }

    public List<String> getNameParts() {
        return nameParts;
    }

    public String getName() {
        return name;
    }

    public List<Symbol> getTypeParams() {
        return typeParams;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(name);
        if (openedTypeParams) {
            b.append("<");

            for (int i = 0; i < typeParams.size(); i++) {
                if (i != 0)
                    b.append(",");
                b.append(typeParams.get(i));
            }

            b.append(">");
        }

        return b.toString();
    }

}
