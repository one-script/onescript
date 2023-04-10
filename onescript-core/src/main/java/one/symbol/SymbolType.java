package one.symbol;

import java.util.List;
import java.util.function.Function;

public enum SymbolType {

    /**
     * References a fully qualified package.
     */
    PACKAGE(null, null),

    /**
     * References a class or other kind of type.
     */
    TYPE(PACKAGE, klass -> {
        List<String> np = klass.getNameParts();
        np = np.subList(0, np.size() - 1);
        return new Symbol(np, PACKAGE);
    }),

    /**
     * References a class member.
     */
    MEMBER(TYPE, member -> {
        List<String> np = member.getNameParts();
        np = np.subList(0, np.size() - 1);
        return new Symbol(np, TYPE);
    }),

    /**
     * A name.
     */
    NAME(null, null)

    ;

    SymbolType declaringType;
    Function<Symbol, Symbol> declaringGetter;

    SymbolType(SymbolType declaringType, Function<Symbol, Symbol> declaringGetter) {
        this.declaringType = declaringType;
        this.declaringGetter = declaringGetter;
    }

    public SymbolType getDeclaringType() {
        return declaringType;
    }

    public Function<Symbol, Symbol> getDeclaringGetter() {
        return declaringGetter;
    }

}
