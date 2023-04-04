package one.test.misc;

import one.lang.OneOperator;
import one.ast.NBinaryOp;
import one.ast.NNumberConstant;
import org.junit.jupiter.api.Test;

public class MiscTests {

    @Test
    void test_AstNodePrint() {
        System.out.println(
                new NBinaryOp(OneOperator.ADD, new NNumberConstant(6), new NNumberConstant(10))
        );
    }

}
