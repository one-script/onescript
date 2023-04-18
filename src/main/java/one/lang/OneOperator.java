package one.lang;

import one.lang.annotation.OneMethod;
import one.lang.annotation.OneSpecial;
import one.parser.OneParser;
import one.parser.token.Token;
import one.parser.token.TokenType;
import one.parser.util.Parsable;
import one.runtime.OneRuntime;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Describes and implements operators for OneScript.
 */
public class OneOperator implements Parsable {

    public static void registerAll(OneParser parser) {
        try {
            for (Field field : OneOperator.class.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) || !OneOperator.class.isAssignableFrom(field.getType()))
                    continue;
                parser.addOperator((OneOperator) field.get(null));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class Unary extends OneOperator {
        private Function<Object, Object> simpleEval;

        @SuppressWarnings("unchecked")
        public <A, C> Unary withSimpleEvaluator(Function<A, C> simpleEval) {
            this.simpleEval = (Function<Object, Object>) simpleEval;
            return this;
        }

        public Function<Object, Object> getSimpleEvaluator() {
            return simpleEval;
        }

        public Object evaluateSimple(Object in) {
            if (simpleEval == null)
                throw new UnsupportedOperationException("No simple evaluation defined for operator '" + this + "'");
            return simpleEval.apply(in);
        }

        /**
         * Create a new basic operator.
         * An operator without any aliases is un-parsable directly
         * from string source.
         *
         * @param name    The name.
         * @param aliases The aliases.
         */
        public Unary(String name, String... aliases) {
            super(name, aliases);
        }
    }

    public static class Binary extends OneOperator {
        private BiFunction<Object, Object, Object> simpleEval;

        @SuppressWarnings("unchecked")
        public <A, B, C> Binary withSimpleEvaluator(BiFunction<A, B, C> simpleEval) {
            this.simpleEval = (BiFunction<Object, Object, Object>) simpleEval;
            return this;
        }

        public BiFunction<Object, Object, Object> getSimpleEvaluator() {
            return simpleEval;
        }

        public Object evaluateSimple(Object a, Object b) {
            if (simpleEval == null)
                throw new UnsupportedOperationException("No simple evaluation defined for operator '" + this + "'");
            return simpleEval.apply(a, b);
        }

        /**
         * Create a new basic operator.
         * An operator without any aliases is un-parsable directly
         * from string source.
         *
         * @param name    The name.
         * @param aliases The aliases.
         */
        public Binary(String name, String... aliases) {
            super(name, aliases);
        }
    }

    @OneMethod
    public static OneOperator getByName(@OneSpecial OneRuntime runtime, String name) {
        return runtime.getParser().getOperators().getMap().get(name);
    }

    // Arithmetic
    public static final Unary  NEG = new Unary("neg")
            .<Double, Double>withSimpleEvaluator(a -> -a);
    public static final Binary ADD = new Binary("add", "+")
            .withSimpleEvaluator(Double::sum);
    public static final Binary SUB = new Binary("sub", "-")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a - b)
            .withPrefixUnary(NEG);
    public static final Binary MUL = new Binary("mul", "*")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a * b);
    public static final Binary DIV = new Binary("div", "/")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a / b);
    public static final Binary MOD = new Binary("mod", "%")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a % b);
    public static final Binary POW = new Binary("pow", "**")
            .<Double, Double, Double>withSimpleEvaluator(Math::pow);

    // Binary
    public static final Binary SHL = new Binary("shl", "<<")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() << b.longValue()));
    public static final Binary SHR = new Binary("shr", ">>")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() >> b.longValue()));
    public static final Unary BINARY_NEG = new Unary("binaryNeg", "~")
            .<Double, Double>withSimpleEvaluator(a -> (double) ~a.longValue());
    public static final Binary BINARY_OR = new Binary("binaryOr", "|")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() | b.longValue()));
    public static final Binary BINARY_AND = new Binary("binaryAnd", "&&")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() & b.longValue()));

    // Logic
    public static final Binary LOGIC_OR = new Binary("logicOr", "||")
            .withSimpleEvaluator(Boolean::logicalOr);
    public static final Binary LOGIC_AND = new Binary("logicAnd", "&&")
            .withSimpleEvaluator(Boolean::logicalAnd);
    public static final Unary LOGIC_NEG = new Unary("logicNeg", "!")
            .<Boolean, Boolean>withSimpleEvaluator(a -> !a);

    // Logic & Binary
    public static final Binary XOR = new Binary("xor", "^")
            .<Object, Object, Object>withSimpleEvaluator((a, b) -> {
                if (a instanceof Boolean bA && b instanceof Boolean bB) {
                    return bA ^ bB;
                } else if (a instanceof Double dA && b instanceof Double dB) {
                    return (double)(dA.longValue() ^ dB.longValue());
                }

                throw new IllegalArgumentException("XOR cannot be applied to " + a.getClass() + " and " + b.getClass());
            });

    // Other
    public static final Binary ASSIGN = new Binary("assign", "=");

    //////////////////////////////////////////////

    private final String name;

    /**
     * The parsable aliases for this operator.
     * Used for matching and parsing it from source.
     */
    private final String[] aliases;

    // the unary forms of the operator
    private OneOperator.Unary prefixUnaryForm;
    private OneOperator.Unary postfixUnaryForm;

    /**
     * Create a new basic operator.
     * An operator without any aliases is un-parsable directly
     * from string source.
     *
     * @param name The name.
     * @param aliases The aliases.
     */
    public OneOperator(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    // utility function
    // get this as a type S
    @SuppressWarnings("unchecked")
    private <S extends OneOperator> S self() {
        return (S) this;
    }

    protected <S extends OneOperator> S withPrefixUnary(OneOperator.Unary operator) {
        this.prefixUnaryForm = operator;
        return self();
    }

    public <S extends OneOperator> S withPostfixUnary(OneOperator.Unary operator) {
        this.postfixUnaryForm = operator;
        return self();
    }

    public OneOperator.Unary toPrefixUnary() {
        return prefixUnaryForm;
    }

    public OneOperator.Unary toPostfixUnary() {
        return postfixUnaryForm;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    public boolean isParsable() {
        return aliases != null && aliases.length != 0;
    }

    public Token<OneOperator> createToken() {
        return new Token<>(TokenType.OPERATOR, this);
    }

    @Override
    public String toString() {
//        return primaryAlias;
        return name;
    }

}
