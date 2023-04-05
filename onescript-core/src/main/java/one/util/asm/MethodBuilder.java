package one.util.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
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

    // Primitive Conversions //
    public void i2f() { visitor.visitInsn(I2F); }
    public void i2b() { visitor.visitInsn(I2B); }
    public void i2c() { visitor.visitInsn(I2C); }
    public void i2d() { visitor.visitInsn(I2D); }
    public void i2s() { visitor.visitInsn(I2S); }
    public void i2l() { visitor.visitInsn(I2L); }
    public void l2i() { visitor.visitInsn(L2I); }
    public void l2d() { visitor.visitInsn(L2D); }
    public void l2f() { visitor.visitInsn(L2F); }
    public void d2l() { visitor.visitInsn(D2L); }
    public void d2i() { visitor.visitInsn(D2I); }
    public void d2f() { visitor.visitInsn(D2F); }
    public void f2d() { visitor.visitInsn(F2D); }
    public void f2l() { visitor.visitInsn(F2L); }
    public void f2i() { visitor.visitInsn(F2I); }

    // Constants //
    public void ldc(Object val) { visitor.visitLdcInsn(val); }

    public void intConst(int val) {
        switch (val) {
            case 0  -> visitor.visitInsn(ICONST_0);
            case 1  -> visitor.visitInsn(ICONST_1);
            case 2  -> visitor.visitInsn(ICONST_2);
            case 3  -> visitor.visitInsn(ICONST_3);
            case 4  -> visitor.visitInsn(ICONST_4);
            case 5  -> visitor.visitInsn(ICONST_5);
            default -> {
                if (val <= Byte.MAX_VALUE) visitor.visitIntInsn(BIPUSH, val);
                else if (val <= Short.MAX_VALUE) visitor.visitIntInsn(SIPUSH, val);
                else ldc(val);
            }
        }
    }

    public void floatConst(float val) {
        if (val == 0f) visitor.visitInsn(FCONST_0);
        else if (val == 1f) visitor.visitInsn(FCONST_1);
        else if (val == 2f) visitor.visitInsn(FCONST_2);
        else ldc(val);
    }

    public void longConst(long val) {
        if (val == 0) visitor.visitInsn(LCONST_0);
        else if (val == 1) visitor.visitInsn(LCONST_1);
        else ldc(val);
    }

    public void doubleConst(double val) {
        if (val == 0) visitor.visitInsn(DCONST_0);
        else if (val == 1) visitor.visitInsn(DCONST_1);
        else ldc(val);
    }

    public void constNull() {
        visitor.visitInsn(ACONST_NULL);
    }

    // Primitive Operations //
    public void iAdd() { visitor.visitInsn(IADD); }
    public void iSub() { visitor.visitInsn(ISUB); }
    public void iMul() { visitor.visitInsn(IMUL); }
    public void iDiv() { visitor.visitInsn(IDIV); }
    public void iShl() { visitor.visitInsn(ISHL); }
    public void iShr() { visitor.visitInsn(ISHR); }
    public void fAdd() { visitor.visitInsn(FADD); }
    public void fSub() { visitor.visitInsn(FSUB); }
    public void fMul() { visitor.visitInsn(FMUL); }
    public void fDiv() { visitor.visitInsn(FDIV); }
    public void lAdd() { visitor.visitInsn(LADD); }
    public void lSub() { visitor.visitInsn(LSUB); }
    public void lMul() { visitor.visitInsn(LMUL); }
    public void lDiv() { visitor.visitInsn(LDIV); }
    public void lShl() { visitor.visitInsn(LSHL); }
    public void lShr() { visitor.visitInsn(LSHR); }
    public void dAdd() { visitor.visitInsn(DADD); }
    public void dSub() { visitor.visitInsn(DSUB); }
    public void dMul() { visitor.visitInsn(DMUL); }
    public void dDiv() { visitor.visitInsn(DDIV); }
    public void tAdd(Type type) { visitor.visitInsn(type.getOpcode(IADD)); }
    public void tSub(Type type) { visitor.visitInsn(type.getOpcode(ISUB)); }
    public void tMul(Type type) { visitor.visitInsn(type.getOpcode(IMUL)); }
    public void tDiv(Type type) { visitor.visitInsn(type.getOpcode(IDIV)); }
    public void tShl(Type type) { visitor.visitInsn(type.getOpcode(ISHL)); }
    public void tShr(Type type) { visitor.visitInsn(type.getOpcode(ISHR)); }

    // Object Operations //
    public void anew(Type type)   { visitor.visitTypeInsn(NEW, type.getInternalName()); }
    public void anew(String name) { visitor.visitTypeInsn(NEW, name); }

    // Array Operations //
    public void newArray(Type type) {
        int sort = type.getSort();
        if (sort == Type.OBJECT || sort == Type.ARRAY) {
            visitor.visitTypeInsn(ANEWARRAY, type.getDescriptor());
        } else {
            visitor.visitIntInsn(NEWARRAY, ASMUtil.getPrimitiveType(sort));
        }
    }

    public void arrayLength() {
        visitor.visitInsn(ARRAYLENGTH);
    }

    public void baLoad() { visitor.visitInsn(BALOAD); }
    public void caLoad() { visitor.visitInsn(CALOAD); }
    public void iaLoad() { visitor.visitInsn(IALOAD); }
    public void laLoad() { visitor.visitInsn(LALOAD); }
    public void faLoad() { visitor.visitInsn(FALOAD); }
    public void daLoad() { visitor.visitInsn(DALOAD); }
    public void aaLoad() { visitor.visitInsn(AALOAD); }
    public void taLoad(Type type) { visitor.visitInsn(type.getOpcode(IALOAD)); }

    public void baStore() { visitor.visitInsn(BASTORE); }
    public void caStore() { visitor.visitInsn(CASTORE); }
    public void iaStore() { visitor.visitInsn(IASTORE); }
    public void laStore() { visitor.visitInsn(LASTORE); }
    public void faStore() { visitor.visitInsn(FASTORE); }
    public void daStore() { visitor.visitInsn(DASTORE); }
    public void aaStore() { visitor.visitInsn(AASTORE); }
    public void taStore(Type type) { visitor.visitInsn(type.getOpcode(IASTORE)); }

    // Local Variables //
    public void iLoad() { visitor.visitInsn(ILOAD); }
    public void lLoad() { visitor.visitInsn(LLOAD); }
    public void fLoad() { visitor.visitInsn(FLOAD); }
    public void dLoad() { visitor.visitInsn(DLOAD); }
    public void aLoad() { visitor.visitInsn(ALOAD); }
    public void tLoad(Type type) { visitor.visitInsn(type.getOpcode(ILOAD)); }

    public void iStore() { visitor.visitInsn(ISTORE); }
    public void lStore() { visitor.visitInsn(LSTORE); }
    public void fStore() { visitor.visitInsn(FSTORE); }
    public void dStore() { visitor.visitInsn(DSTORE); }
    public void aStore() { visitor.visitInsn(ASTORE); }
    public void tStore(Type type) { visitor.visitInsn(type.getOpcode(ISTORE)); }

}
