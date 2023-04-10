package one.symbol;

import one.util.Strings;

import java.util.Arrays;
import java.util.List;

/**
 * Denotes a symbol or multiple symbols if the
 * last part is a '*'.
 */
public class Symbol {

    public Symbol(String name, SymbolType type) {
        this.name = name;
        this.nameParts = Arrays.asList(name.split("\\."));
        this.type = type;
    }

    public Symbol(List<String> nameParts, SymbolType type) {
        this.name = Strings.join(nameParts, ".");
        this.nameParts = nameParts;
        this.type = type;
    }

    /** The parts of the name separated by . */
    private final List<String> nameParts;
    private final String name;

    private final SymbolType type;

    public boolean isEmpty() {
        return nameParts.isEmpty();
    }

    public boolean isSpecific() {
        return !isEmpty() && !nameParts.get(nameParts.size() - 1).equals("*");
    }

    public Symbol getDeclaring() {
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

    @Override
    public String toString() {
        return name;
    }

}
