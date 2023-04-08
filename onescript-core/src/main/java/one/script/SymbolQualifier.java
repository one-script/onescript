package one.script;

/**
 * Qualifies symbols of the correct type from their
 * referenced identifier to their full script name.
 */
public interface SymbolQualifier {

    static SymbolQualifier qualifyClasses(Symbol imported) {
        return null; // TODO
    }

    static SymbolQualifier qualifyMembers(Symbol imported) {
        return null; // TODO
    }

    ////////////////////////////////////

    Symbol qualify(Symbol symbol);

}
