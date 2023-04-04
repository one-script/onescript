package one.test.misc;

import one.ast.NBinaryOp;
import one.ast.NConstant;
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
