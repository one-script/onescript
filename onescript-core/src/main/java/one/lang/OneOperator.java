package one.lang;

import one.parser.token.Token;
import one.parser.token.TokenType;
import one.runtime.OneRuntime;

/**
 * Describes and implements operators for OneScript.
 */
public class OneOperator {

    @OneMethod
    public static OneOperator getByName(@OneSpecial OneRuntime runtime, String name) {
        return runtime.getParser().getOperators().getMap().get(name);
    }

    // Arithmetic
    public static final OneOperator NEG = new OneOperator("neg");
    public static final OneOperator ADD = new OneOperator("add", "+");
    public static final OneOperator SUB = new OneOperator("sub", "-").withPrefixUnary(NEG);
    public static final OneOperator MUL = new OneOperator("mul", "*");
    public static final OneOperator DIV = new OneOperator("div", "/");

    //////////////////////////////////////////////

    private final String name;

    /**
     * The parsable aliases for this operator.
     * Used for matching and parsing it from source.
     */
    private final String[] aliases;

    // the unary forms of the operator
    private OneOperator prefixUnaryForm;
    private OneOperator postfixUnaryForm;

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

    protected OneOperator withPrefixUnary(OneOperator operator) {
        this.prefixUnaryForm = operator;
        return this;
    }

    public OneOperator withPostfixUnary(OneOperator operator) {
        this.postfixUnaryForm = operator;
        return this;
    }

    public OneOperator toPrefixUnary() {
        return prefixUnaryForm;
    }

    public OneOperator toPostfixUnary() {
        return postfixUnaryForm;
    }

    public String getName() {
        return name;
    }

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
