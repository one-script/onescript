package one.runtime;

import one.ast.NExpression;
import one.lang.OneSystem;
import one.lang.annotation.OneMethod;
import one.lang.annotation.OneSpecial;
import one.parser.LexContext;
import one.parser.OneParser;
import one.parser.ParseContext;
import one.runtime.classes.NativeClassProvider;
import one.runtime.classes.OneJVMClassLoader;
import one.runtime.classes.ScriptClassLoader;
import one.runtime.classes.OneClassRegistry;
import one.util.Sequence;

/**
 * The central class for the OneScript runtime.
 */
public class OneRuntime {

    /* TODO: replace this with sourcing instances
        from a runtime factory or builder */
    public OneRuntime(ClassLoader scriptParentLoader) {
        this.jvmClassLoader = new OneJVMClassLoader(scriptParentLoader, this);
    }

    /**
     * The JVM OneScript class loader.
     */
    private final OneJVMClassLoader jvmClassLoader;

    /**
     * The class pool of loaded and unloaded script classes.
     */
    private final OneClassRegistry classRegistry = new OneClassRegistry();

    /**
     * The system/main script class loader.
     */
    private final ScriptClassLoader scriptClassLoader = new ScriptClassLoader(this);

    /**
     * The native class provider.
     */
    private final NativeClassProvider nativeClassProvider = new NativeClassProvider();

    /**
     * The script/expression parser to use.
     */
    private final OneParser parser = new OneParser();

    /* Getters */

    public OneJVMClassLoader getJvmClassLoader() {
        return jvmClassLoader;
    }

    public OneClassRegistry getClassRegistry() {
        return classRegistry;
    }

    public ScriptClassLoader getScriptClassLoader() {
        return scriptClassLoader;
    }

    public NativeClassProvider getNativeClassProvider() {
        return nativeClassProvider;
    }

    public OneParser getParser() {
        return parser;
    }

    /**
     * Parse a simple expression into an expression node.
     *
     * @param src The source string.
     * @param optimizeConstants If it should optimize constant expressions.
     * @return The expression node.
     */
    public NExpression<?> parseExpr(String src, boolean optimizeConstants) {
        LexContext lexContext = parser.lex(new LexContext(parser, src, 0, "<src>"));
        ParseContext parseContext = new ParseContext(parser, Sequence.ofList(lexContext.getTokens()), "expr")
                .setOptimizeConstants(optimizeConstants);
        return (NExpression<?>) parser.parse(parseContext).getRootNode();
    }

    /**
     * Evaluate a simple expression.
     * Utilizes {@link #parseExpr(String, boolean)} to parse the expression,
     * then calls {@link NExpression#evaluateSimple()} to evaluate it.
     *
     * @param src The source string.
     * @return The result of evaluation.
     * @throws UnsupportedOperationException If simple evaluation is not supported for the given expression.
     */
    public Object evalExprSimple(String src) {
        return parseExpr(src,
                /* dont waste time optimizing constants as the
                   result of the parsing will only be evaluated once */
                false).evaluateSimple();
    }

}
