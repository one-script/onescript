package one.util.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/** Wrapper for ASM {@link MethodVisitor} to make it a bit easier. */
public class MethodBuilder {

    /** The method visitor to wrap. */
    private final MethodVisitor visitor;

    /** Counter for local variables. */
    private int localVarPos = 0;

    public MethodBuilder(MethodVisitor visitor) {
        this.visitor = visitor;
    }

    public MethodVisitor getVisitor() {
        return visitor;
    }

    /*
        Labels
     */

    public Label labelHere() {
        Label label = new Label();
        visitor.visitLabel(label);
        return label;
    }

    /*
        Locals
     */

    public int newLocal(Type type) {
        int old = localVarPos;
        localVarPos += type.getSize();
        return old;
    }

    public void releaseLocal(Type type) {
        localVarPos -= type.getSize();
    }

    /*
        Instructions
     */

    public void i2f() { visitor.visitInsn(I2F); }
    public void i2b() { visitor.visitInsn(I2B); }
    public void i2c() { visitor.visitInsn(I2C); }
    public void i2d() { visitor.visitInsn(I2D); }
    public void i2s() { visitor.visitInsn(I2S); }
    public void i2l() { visitor.visitInsn(I2L); }



}
