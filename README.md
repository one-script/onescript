# OneScript

## Script Example
Note that this script won't run (yet), as this
project is still heavily under development. 
This is just basically the current goal.
```java
//// MyScript1.os

/* Semi-colons (;) are optional but recommended. */

/*
 * Specifies a custom script name to use for this script,
 * by default this is derived from the filename which in this case
 * is "MyScript1.os" so the script name would be "MyScript1".
 */
this as "MyScript";

/* Specify the package this script should be loaded in.
 * All classes denoted script will be loaded as my.script.<classname>,
 * and the script init class will be loaded as my.script.Script$<scriptname>
 *
 * In the JVM, this package is translated to `oneclasses.<package>` meaning all classes
 * will be loaded as `oneclasses.my.script.<classname>` and the script init class will
 * be loaded as `oneclasses.my.script.Script$<scriptname>`
 *
 * In the default case, the <scriptname> would be MyScript1, from the MyScript1.os filename we
 * use the above statement to declare a custom name for this script unit. Following this the
 * script init class would be nameed `oneclasses.my.script.Script$MyScript`.
 */
package my.script;

/* Access modifiers:
   - package private (this package)
   - local (this script)
   - protected (this package and subsymbols)
   - pub/public (everything)

   Note that this does not determine accessibility
   from the JVM, in the JVM every symbol is automatically
   described as public.
*/

/*
 * Symbols are loaded on-demand, just like in the JVM.
 * This block describes the loading of classes.
 *
 * When the JVM tries to define a class symbol under `oneclasses.*`, the OneScript
 * class loader will look up the class in the runtime's registered class symbol table.
 * This is bound to the runtime and when a script is loaded it will, instead of loading
 * and initializing all classes, map all class descriptors by their JVM name (`oneclasses.*`).
 * Using this the class loader can look up the class to compile, load and initialize. Once
 * this is complete the program will continue.
 *
 * Classes can be annotated with `one.runtime.InitImmediate` (natvie class, JVM name `one.api.runtime.InitImmediate`)
 * which denotes them to be loaded and initialized immediately after the script has been loaded but before any code has
 * started executing.
 */

pub class MyPublicClass {

	val string myStringField; // explicitly sets string as the type
	final val myAnyTypeFinalField; // no type can be inferred, so this is an any-typed field
	final val myInferredFinalField = "gg"; // the string type is inferred by the parser/compiler

	/* New objects are created using ClassName(...) */
	pub this(string strParam, any anyParam, inferredAnyParam) {
		this.myStringField = strParam;
		this.myAnyTypeFinalField  anyParam;
	}

	/* The colon (:) after keywords signals a type following */
	pub func getMyString() -> myStringField;

}

/*
 * You can actually extend the language from within itself.
 * The extension can take place in the lexer, parser, compiler/loader and even
 * part of the runtime.
 *
 * The lexer and parser APIs are under `one.lang.parser`
 *    (native, mostly JVM package `one.api.lang.parser`)
 * The compiler/loader APIs are under `one.lang.loader`
 *    (native, mostly JVM package `one.api.lang.loader`)
 * The runtime APIs are under `one.lang.runtime` (more API) and `one.runtime` (more core)
 *    (native, mostly JVM packages `one.api.lang.runtime` and `one.api.runtime`)
 */ 

// Adding CraftTweaker-like MC ItemStack syntax support
/* .** recursively imports all packages */
import one.lang.parser.**;
// Has to somehow be exposed by the script loader.
import minecraft.item.ItemStack; 
import minecraft.item.Item;
import minecraft.core.Registries;
import minecraft.core.ResourceKey;

record ItemStackDescriptor(ResourceKey itemType, int count);

let parser = OneParser.current();
parser.addTokenType(RegexTokenType("ctItemStack", 
	// The regex string would have one capture group
	// for capturing the identifier and another optional
	// capture group for capturing the amount.
	//
	// Example string to match: <minecraft:dirt 500>
	"the regex string", 
	(ctx, tkType, regexResult) -> {
		int count = 1;
		if (regexResult.hasCapture(1)) {
			count = int(regexResult.getCapture(1));
		}

		ResourceKey type = ResourceKey.of(regexResult.getCapture(0));
		return ctx.newTokenAtCapturedPos(tkType, ItemStackDescriptor(type, count));
}).autoCapturePos());

/*
 * Add a parser rule to convert the tokens of an
 * item stack into an expression which creates an
 * item stack.
 *
 * Example in source: <minecraft:dirt 500> -> ItemStack(Registries.ITEM.get("minecraft:dirt"), 500)
 */ 
parser.addParserRule(
	PipelineParserRule(/* rule name */ "ctItemStackToExpr", /* rule tag */ "expr", 1000)
		.thenConsumeToken(tokens -> {
			Token tk = tokens.first();

			/* Types can assign getters and setters for
			 * fields, in this example `.value` actually
			 * calls `.getValue()` (Java method in this case). */
			ItemStackDescriptor stackDesc = tk.value;

			return NCall(/* The called value, not the type of the instance. */ 
				         NTypeConstant.unresolvedString("minecraft.item.ItemStack"), 
				         /* Parameters. */
				         /* NExpr.thenGet(...) is the same as NGetMember(this, ...) */
				         NCall(NTypeConstant.unresolvedString("minecraft.core.Registries").thenGet("ITEM").thenGet("get"),
				         	NConstant.of(stackDesc.itemType.toString())),
				         NConstant.of(500)
		}, "ctItemStack")
);
```