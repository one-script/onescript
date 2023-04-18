package one.lang;

import one.lang.annotation.OneClass;
import one.lang.annotation.OneMethod;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Wrapper for the {@link System} class.
 */
@OneClass("one.lang.System")
public class OneSystem {

    @OneMethod
    public static PrintStream out() {
        return System.out;
    }

    @OneMethod
    public static PrintStream err() {
        return System.err;
    }

    @OneMethod
    public static InputStream in() {
        return System.in;
    }

    @OneMethod
    public static Scanner scan() {
        return new Scanner(in());
    }

    @OneMethod
    public static void print(Object o) {
        System.out.print(o);
    }

    @OneMethod
    public static void println(Object o) {
        System.out.println(o);
    }

    @OneMethod
    public static void printErr(Object o) {
        System.err.print(o);
    }

    @OneMethod
    public static void printlnErr(Object o) {
        System.err.println(o);
    }

}
