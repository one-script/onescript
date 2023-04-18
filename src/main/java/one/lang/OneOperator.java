package one.lang;

import one.ast.expr.NBinaryOp;
import one.ast.expr.NConstant;
import one.ast.expr.NExpression;
import one.lang.annotation.OneMethod;
import one.lang.annotation.OneSpecial;
import one.parser.OneParser;
import one.parser.ParseContext;
import one.parser.token.Token;
import one.parser.token.TokenType;
import one.parser.util.Parsable;
import one.parser.util.StringLocation;
import one.runtime.OneRuntime;
import one.util.ListRegistrable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Describes and implements operators for OneScript.
 */
public class OneOperator implements Parsable, Comparable<OneOperator> {

    /**
     * Represents a set of operators with the same priority.
     */
    public static class PrioritySet implements Comparable<PrioritySet>, ListRegistrable {
        /** The priority. */
        private final int priority;

        /** The set for fast lookup. */
        private final java.util.Set<OneOperator> set = new HashSet<>();

        private int index;

        public PrioritySet(int priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PrioritySet o) {
            int tp = this.priority;
            int op = o.priority;

            if (tp == op) return 0;
            return op > tp ? 1 : -1;
        }

        public int getPriority() {
            return priority;
        }

        public java.util.Set<OneOperator> getSet() {
            return set;
        }

        public PrioritySet with(OneOperator operator) {
            set.add(operator);
            return this;
        }

        @Override
        public void registered(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        // parses an operand of higher priority
        // for to be used for these operators
        private NExpression<?> parseOperand(ParseContext context) {
            List<PrioritySet> sets = context.getParser().getOperatorSets();
            if (index == 0) {
                return context.tryParseNext("exprBase");
            } else {
                return sets.get(index - 1).parse(context);
            }
        }

        /**
         * Parses a repeating binary operator expression
         * from the given context.
         *
         * Handles optimization automatically.
         *
         * @param context The context.
         * @return The bi-op node.
         */
        public NExpression<?> parse(ParseContext context) {
            NExpression<?> node = parseOperand(context);
            if (node != null) {
                OneOperator currentOp;
                while (context.currentType() == TokenType.OPERATOR &&
                        set.contains(currentOp = context.current().getValueAs())) {
                    StringLocation loc = context.current().getLocation();
                    context.next();
                    NExpression<?> right = parseOperand(context);
                    NBinaryOp bNode = new NBinaryOp(currentOp, node, right);
                    bNode.setLocation(loc);
                    node = bNode;

                    // perform constant optimization
                    if (context.optimizeConstants() &&
                            bNode.getLeft() instanceof NConstant &&
                            right instanceof NConstant) {
                        try {
                            node = NConstant.of(node.evaluateSimple());
                        } catch (Exception ignored) { }
                    }
                }
            }

            return node;
        }
    }

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
            super(name, -1 , aliases);
        }

        public Unary prefix() {
            withPrefixUnary(this);
            return this;
        }

        public Unary postfix() {
            withPostfixUnary(this);
            return this;
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
        public Binary(String name, int priority, String... aliases) {
            super(name, priority, aliases);
        }
    }

    @OneMethod
    public static OneOperator getByName(@OneSpecial OneRuntime runtime, String name) {
        return runtime.getParser().getOperators().getMap().get(name);
    }

    // Arithmetic
    public static final Unary NEG = new Unary("neg")
            .prefix()
            .<Double, Double>withSimpleEvaluator(a -> -a);
    public static final Binary ADD = new Binary("add", 11, "+")
            .withSimpleEvaluator(Double::sum);
    public static final Binary SUB = new Binary("sub", 11, "-")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a - b)
            .withPrefixUnary(NEG);
    public static final Binary MUL = new Binary("mul", 12, "*")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a * b);
    public static final Binary DIV = new Binary("div", 12, "/")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a / b);
    public static final Binary MOD = new Binary("mod", 12, "%")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> a % b);
    public static final Binary POW = new Binary("pow", 13, "**")
            .<Double, Double, Double>withSimpleEvaluator(Math::pow);
    // TODO: distinguish prefix and postfix
    public static final Unary INCREMENT = new Unary("increment", "++")
            .prefix()
            .postfix();
    public static final Unary DECREMENT = new Unary("decrement", "--")
            .prefix()
            .postfix();

    // Binary
    public static final Binary SHL = new Binary("shl", 10, "<<")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() << b.longValue()));
    public static final Binary SHR = new Binary("shr", 10, ">>")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() >> b.longValue()));
    public static final Binary SHRZ = new Binary("shrz", 10, ">>>")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() >>> b.longValue()));
    public static final Unary BINARY_NEG = new Unary("binaryNeg", "~")
            .prefix()
            .<Double, Double>withSimpleEvaluator(a -> (double) ~a.longValue());
    public static final Binary BINARY_OR = new Binary("binaryOr", 5, "|")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() | b.longValue()));
    public static final Binary BINARY_AND = new Binary("binaryAnd", 7, "&&")
            .<Double, Double, Double>withSimpleEvaluator((a, b) -> (double)(a.longValue() & b.longValue()));

    // Logic
    public static final Binary LOGIC_OR = new Binary("logicOr", 3, "||")
            .withSimpleEvaluator(Boolean::logicalOr);
    public static final Binary LOGIC_AND = new Binary("logicAnd", 4, "&&")
            .withSimpleEvaluator(Boolean::logicalAnd);
    public static final Unary LOGIC_NEG = new Unary("logicNeg", "!")
            .prefix()
            .<Boolean, Boolean>withSimpleEvaluator(a -> !a);

    // Comparison
    public static final Binary COMP_EQ = new Binary("eq", 8, "==")
            .withSimpleEvaluator(Objects::equals);
    public static final Binary COMP_NEQ = new Binary("neq", 8, "!=")
            .withSimpleEvaluator((a, b) -> !Objects.equals(a, b));
    public static final Binary COMP_LESS = new Binary("less", 9, "<")
            .<Double, Double, Boolean>withSimpleEvaluator((a, b) -> a < b);
    public static final Binary COMP_LESS_OR_EQ = new Binary("lessOrEq", 9, "<=")
            .<Double, Double, Boolean>withSimpleEvaluator((a, b) -> a <= b);
    public static final Binary COMP_GR = new Binary("gr", 9, ">")
            .<Double, Double, Boolean>withSimpleEvaluator((a, b) -> a > b);
    public static final Binary COMP_GR_OR_EQ = new Binary("grOrEq", 9, ">=")
            .<Double, Double, Boolean>withSimpleEvaluator((a, b) -> a >= b);

    // Logic & Binary
    public static final Binary XOR = new Binary("xor", 6, "^")
            .<Object, Object, Object>withSimpleEvaluator((a, b) -> {
                if (a instanceof Boolean bA && b instanceof Boolean bB) {
                    return bA ^ bB;
                } else if (a instanceof Number dA && b instanceof Number dB) {
                    return (double)(dA.longValue() ^ dB.longValue());
                }

                throw new IllegalArgumentException("XOR cannot be applied to " + a.getClass() + " and " + b.getClass());
            });

    // Other
    public static final Binary ASSIGN = new Binary("assign", 0, "=");

    //////////////////////////////////////////////

    private final String name;

    /**
     * The parsable aliases for this operator.
     * Used for matching and parsing it from source.
     */
    private final String[] aliases;

    /**
     * The operators priority.
     */
    private final int priority;

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
     * @param priority The priority.
     */
    public OneOperator(String name, int priority, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        this.priority = priority;
    }

    @Override
    public int compareTo(OneOperator o) {
        int tp = this.priority;
        int op = o.priority;

        if (tp == op) return 0;
        return tp > op ? 1 : -1;
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

    public int getPriority() {
        return priority;
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
