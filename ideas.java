/* OneScript ideas */

expr -> {
	abc(); // If abc() exists on expr, this is equal
	       // to doing `expr.abc()`
}

// Example:
commands.create("mycommand") -> {
	executes(ctx -> { /* ... */});
	then("a")
	then("b")
}

// Generated functional interfaces
// Also you can call functional interfaces
// without the method name.
boolean myFunc(boolean(String, int) otherFunc) {
	return otherFunc("Hello", 69);
}

myFunc((str, i) -> str.length == i);

// The above example would generate a type similar to:
@FunctionalInterface
interface GenFunc$0 {
	boolean func(String p1, int p2);
}

// Would be loaded under oneclasses.shared.GenFunc$0