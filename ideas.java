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

// To load a script:
OneRuntime runtime = runtimeFactory.create();
APIScript script = runtime.loadScript("MyScript.one");
script.run(); // enters the runtime
              // and invokes ScriptClass#script$run()
/* get type by script name 
 * then get a method by signature 
 * then `invokeExternal` calls the method while entering and exiting the runtime */
OneMethod m = runtime.getType("one.lang.Sus").getMethod(...);
m.invokeExternal(...)

Object invokeExternal(OneRuntime runtime, Object on, Object... args) {
	OneRuntime.enterRuntime(runtime);
	Object res = invoke(on, args);
	OneRuntime.exitRuntime(runtime);
	return res;
}