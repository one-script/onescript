package one.runtime;

import one.ast.expr.NExpression;
import one.parser.LexContext;
import one.parser.OneParser;
import one.parser.ParseContext;
import one.runtime.classes.NativeClassProvider;
import one.runtime.classes.OneJVMClassLoader;
import one.runtime.classes.ScriptClassLoader;
import one.runtime.classes.OneClassRegistry;
import one.util.Sequence;
import one.util.asm.JavaMethod;
import one.util.asm.MethodBuilder;

import java.util.Stack;

/**
 * The central class for the OneScript runtime.
 */
@SuppressWarnings("unchecked")
public class OneRuntime {

    /*
     * Runtime Instance Stack
     */

    // the array used to store the runtime stacks
    // the java thread ids are used to index this array
    private static Stack<OneRuntime>[] runtimeStacks = new Stack[10];

    public static Stack<OneRuntime> getRuntimeStack(Thread t) {
        int id = (int) t.getId();
        if (id >= runtimeStacks.length) {
            Stack<OneRuntime> stack = new Stack<>();

            // expand array
            Stack<OneRuntime>[] oldStacks = runtimeStacks;
            runtimeStacks = new Stack[id + 1];
            System.arraycopy(oldStacks, 0, runtimeStacks, 0, oldStacks.length);

            runtimeStacks[id] = stack;
            return stack;
        }

        return runtimeStacks[(int) t.getId()];
    }

    public static Stack<OneRuntime> currentRuntimeStack() {
        return getRuntimeStack(Thread.currentThread());
    }

    public static OneRuntime currentRuntime() {
        return getRuntimeStack(Thread.currentThread()).peek();
    }

    public static OneRuntime currentRuntime(Thread t) {
        return getRuntimeStack(t).peek();
    }


    public static void enterRuntime(OneRuntime runtime) {
        currentRuntimeStack().push(runtime);
    }

    public static void exitRuntime(/* for verification */ OneRuntime runtime) {
        OneRuntime popped = currentRuntimeStack().pop();
        if (runtime != null && popped != runtime)
            throw new IllegalStateException();
    }

    // the method referencing OneRuntime#currentRuntime
    private static final JavaMethod CURR_RUNTIME_METHOD =
            JavaMethod.find(OneRuntime.class, "currentRuntime");

    public static void compileCurrentRuntime(MethodBuilder builder) {
        CURR_RUNTIME_METHOD.putInvokeStatic(builder);
    }

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
