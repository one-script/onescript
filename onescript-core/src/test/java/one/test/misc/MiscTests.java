package one.test.misc;

import one.ast.expr.NBinaryOp;
import one.ast.expr.NConstant;
import one.lang.OneOperator;
import org.junit.jupiter.api.Test;

public class MiscTests {

    @Test
    void test_AstNodePrint() {
        System.out.println(
                new NBinaryOp(OneOperator.ADD, NConstant.of(6), NConstant.of(10))
        );
    }

}
