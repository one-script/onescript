package one.test.misc;

import one.util.AnalyzedString;
import org.junit.jupiter.api.Test;

public class MiscTests {

    @Test
    void test_AnalyzedString() {
        final String src = """
                a
                b
                c
                ddddd
                eeeeeeeeeeeeeeeee
                f
                ggg
                """;
        AnalyzedString string = new AnalyzedString(src);

        System.out.println(string.getLine(0));
    }

}
