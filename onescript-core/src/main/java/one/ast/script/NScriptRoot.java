package one.ast.script;

import one.ast.statement.NBlock;
import one.ast.symbol.NClassBody;
import one.symbol.SymbolQualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the root scope of a script.
 */
public class NScriptRoot extends
        /* inherit the ability to define members of the script class */
        NClassBody
{

    /** The name of this script. */
    private String scriptName;

    /** The package specified. */
    private String scriptPackage = "";

    /** The symbol qualifiers declared, these are just the import symbol statements. */
    private final List<SymbolQualifier> symbolQualifiers = new ArrayList<>();

    /** The script import statements declared. */
    private final List<String> scriptImports = new ArrayList<>();

    /** The code of the script run block. */
    private final NBlock scriptRunBlock = new NBlock();

    public List<String> getScriptImports() {
        return scriptImports;
    }

    public NScriptRoot addScriptImport(String scriptName) {
        scriptImports.add(scriptName);
        return this;
    }

    public String getScriptName() {
        return scriptName;
    }

    public NScriptRoot setScriptName(String scriptName) {
        this.scriptName = scriptName;
        return this;
    }

    public NBlock getScriptRunBlock() {
        return scriptRunBlock;
    }

    public List<SymbolQualifier> getSymbolQualifiers() {
        return symbolQualifiers;
    }

    public NScriptRoot addSymbolQualifier(SymbolQualifier qualifier) {
        symbolQualifiers.add(qualifier);
        return this;
    }

    public NScriptRoot setScriptPackage(String scriptPackage) {
        this.scriptPackage = scriptPackage;
        return this;
    }

    public String getScriptPackage() {
        return scriptPackage;
    }

    @Override
    public String getTypeName() {
        return "scriptRoot";
    }

}
