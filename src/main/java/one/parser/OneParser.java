package one.parser;

import one.ast.ASTNode;
import one.lang.OneModifier;
import one.lang.OneOperator;
import one.parser.error.OneParseException;
import one.parser.rule.ParserRule;
import one.parser.rule.expr.*;
import one.parser.rule.script.RScriptRoot;
import one.parser.rule.element.RClass;
import one.parser.rule.element.RClassBody;
import one.parser.rule.statement.RBlock;
import one.parser.rule.statement.RStatement;
import one.parser.token.*;
import one.parser.util.ParsableRegistry;
import one.parser.util.StringLocation;
import one.parser.util.StringReader;
import one.util.SortedList;

import java.util.*;

/**
 * The primary OneScript parser.
 *
 *
 */
public class OneParser {

    private static boolean isValidIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '$';
    }

    private static boolean isValidIdentifierChar(char c) {
        return isValidIdentifierStart(c) || (c >= '0' && c <= '9');
    }

    //////////////////////////////////////

    /**
     * All registered token types mapped by name.
     */
    final Map<String, TokenType<?>> tokenTypes = new HashMap<>();
    final List<TokenParser<?>> tokenParsers = new ArrayList<>();

    /**
     * All registered keyword types.
     */
    final Map<String, Keyword> keywordTypes = new HashMap<>();

    /**
     * The registered operators.
     */
    final ParsableRegistry<OneOperator> operators = new ParsableRegistry<>();

    /**
     * The sorted list of operator sets.
     * This list is sorted from lowest to highest priority.
     */
    final SortedList<OneOperator.PrioritySet> operatorSets = new SortedList<>(OneOperator.PrioritySet::compareTo);

    /**
     * The registered static tokens.
     */
    final ParsableRegistry<StaticToken> staticTokens = new ParsableRegistry<>();

    /**
     * The registered parsable modifiers.
     */
    final ParsableRegistry<OneModifier> parsableModifiers = new ParsableRegistry<>();

    /**
     * The registered parser rules by name.
     */
    final Map<String, ParserRule<?>> parserRuleMap = new HashMap<>();
    final List<ParserRule<?>> parserRuleList = new ArrayList<>();

    public OneParser addParserRule(ParserRule<?> rule) {
        parserRuleMap.put(rule.getName(), rule);
        parserRuleList.add(rule);
        return this;
    }

    public OneParser removeParserRule(ParserRule<?> rule) {
        parserRuleMap.remove(rule.getName());
        parserRuleList.remove(rule);;
        return this;
    }

    public ParserRule<?> getParserRule(String name) {
        return parserRuleMap.get(name);
    }

    public OneParser addTokenType(TokenType<?> type) {
        tokenTypes.put(type.getName(), type);
        tokenParsers.add(type);
        return this;
    }

    public OneParser removeTokenType(TokenType<?> type) {
        tokenTypes.remove(type.getName());
        tokenParsers.remove(type);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> TokenType<T> getTokenType(String name) {
        return (TokenType<T>) tokenTypes.get(name);
    }

    public OneParser addTokenParser(TokenParser<?> parser) {
        tokenParsers.add(parser);
        return this;
    }

    public OneParser removeTokenParser(TokenParser<?> parser) {
        tokenParsers.remove(parser);
        return this;
    }

    public OneParser addKeywordType(Keyword type) {
        keywordTypes.put(type.getName(), type);
        return this;
    }

    public OneParser removeKeywordType(Keyword type) {
        keywordTypes.remove(type.getName());
        return this;
    }

    public Keyword getKeywordType(String name) {
        return keywordTypes.get(name);
    }

    public OneParser addOperator(OneOperator operator) {
        // insert operator to be parsed
        operators.insert(operator);

        if (operator.getPriority() != -1) {
            // get set to insert into
            int setIndex = operatorSets.find(s -> s.getPriority() == operator.getPriority());
            OneOperator.PrioritySet set = setIndex == -1 ?
                    operatorSets.with(new OneOperator.PrioritySet(operator.getPriority())) :
                    operatorSets.get(setIndex);
            set.with(operator);
        }

        return this;
    }

    public OneParser removeOperator(OneOperator operator) {
        operators.remove(operator);
        return this;
    }

    public ParsableRegistry<OneOperator> getOperators() {
        return operators;
    }

    public SortedList<OneOperator.PrioritySet> getOperatorSets() {
        return operatorSets;
    }

    public OneParser addStaticToken(StaticToken token) {
        staticTokens.insert(token);
        return this;
    }

    public OneParser removeStaticToken(StaticToken token) {
        staticTokens.remove(token);
        return this;
    }

    public ParsableRegistry<StaticToken> getStaticTokens() {
        return staticTokens;
    }

    /* Initialize Defaults */
    {
        OneOperator.registerAll(this);

        addParserRule(new RScriptRoot());
        addParserRule(new RClassBody());
        addParserRule(new RClass());
        addParserRule(new RBlock());
        addParserRule(new RStatement());
        addParserRule(new RExpression());
        addParserRule(new RBase());
        addParserRule(new RLiteral());

        Tokens.registerAllKeywords(this);
        Tokens.registerAllStatics(this);

        // register all modifiers
        for (OneModifier modifier : OneModifier.values()) {
            if (modifier.getAlias() != null) {
                parsableModifiers.insert(modifier);
            }
        }
    }

    /* ----------- Lexer -------------- */

    /**
     * Lexically analyzes a string given the context.
     *
     * @param context The context.
     * @return The context.
     */
    public LexContext lex(final LexContext context) {
        while (context.hasNext()) {
            context.consumeWhitespace();

            // match single line comment
            if (context.curr() == '/' && context.peek(1) == '/') {
                context.next();
                while (context.next() != '\n');
                continue;
            }

            // match multi-line comment
            if (context.curr() == '/' && context.peek(1) == '*') {
                context.next();
                while (!context.pConsumeFullString("*/"))
                    context.next();
                context.next(2);
                continue;
            }

            // match modifiers
            context.begin();
            OneModifier modifier = context.matchForward(parsableModifiers);
            if (modifier == null) {
                context.reset();
            } else {
                Token<OneModifier> token = new Token<>(TokenType.MODIFIER, modifier);
                token.setLocation(context.end());
                context.addToken(token);
                continue;
            }

            // match literals
            context.begin();
            StaticToken staticToken = context.matchForward(staticTokens);
            if (staticToken == null) {
                context.reset();
            } else {
                Token<Void> tk = new Token<>(staticToken);
                tk.setLocation(context.end());
                context.addToken(tk);
                continue;
            }

            // match string literal
            if (context.curr() == '"') {
                context.addToken(Tokens.STRING_LITERAL.parseToken(context));
                continue;
            }

            // match number literal
            // we can match in radix 10 because other bases
            // will start with 0cNUMBER, and 0 is a base-10 digit
            if (StringReader.isDigit(context.curr(), 10)) {
                context.addToken(Tokens.NUMBER_LITERAL.parseToken(context));
                continue;
            }

            // match identifiers and keywords
            // keywords are just reserved identifiers
            if (isValidIdentifierStart(context.curr())) {
                int startIndex = context.index();
                int startLine = context.currentLine();
                String id = context.collect(OneParser::isValidIdentifierChar);
                int endIndex = context.index() - 1;
                Token<?> token;
                Keyword kw = getKeywordType(id);
                token = kw != null ? new Token<>(kw) : new Token<>(Tokens.IDENTIFIER, id);
                context.addToken(token
                        .setLocation(new StringLocation(context.getFile(), context.aStr(), startIndex, endIndex, startLine)));
                continue;
            }

            // try and parse operators
            context.begin();
            int startLine = context.currentLine();
            int startIndex = context.index();
            OneOperator operator = context.matchForward(operators);
            if (operator == null) {
                context.reset();
            } else {
                int endIndex = context.index() - 1;
                context.addToken(operator.createToken()
                        .setLocation(new StringLocation(context.getFile(), context.aStr(), startIndex, endIndex, startLine)));
                continue;
            }

            /* trail-and-error all remaining non-builtin token parsers */
            Token<?> tk = null;
            for (TokenParser<?> parser : tokenParsers) {
                tk = parser.parseToken(context);
            }

            if (tk == null) {
                // throw lexer error
                throw context.endOrHere(new OneParseException("unexpected character: '" + context.curr() + "'"));
            } else {
                context.addToken(tk);
            }
        }

        return context;
    }

    /* ------------ Parser -------------- */

    /**
     * Parses an incoming sequence of tokens provided
     * by the given context into an abstract syntax
     * tree consisting of {@link ASTNode}s.
     *
     * @param context The context to parse in.
     * @return The context back.
     */
    public ParseContext parse(ParseContext context) {
        ParserRule<?> rule = getParserRule(context.getRootParserRuleName());
        if (rule == null)
            throw new IllegalArgumentException("No parser rule by name '" + context.getRootParserRuleName() + "', " +
                    "root rule is required");
        context.setRootNode(rule.parseNode(context));
        return context;
    }

}
