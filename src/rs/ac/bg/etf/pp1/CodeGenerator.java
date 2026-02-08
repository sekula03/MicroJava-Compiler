package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPC;

    public int getMainPC() {
        return mainPC;
    }

    // =================================================================================================================

    // method headers

    private void common_methodHeader(int b1, int b2) {
        Code.put(Code.enter);
        Code.put(b1);
        Code.put(b2);
    }

    @Override
    public void visit(MethodHeaderVoid methodHeaderVoid) {
        Obj obj = methodHeaderVoid.obj;
        common_methodHeader(obj.getLevel(), obj.getLocalSymbols().size());
    }

    @Override
    public void visit(MethodHeaderType methodHeaderType) {
        Obj obj = methodHeaderType.obj;
        common_methodHeader(obj.getLevel(), obj.getLocalSymbols().size());
    }

    @Override
    public void visit(GlobalMethodHeaderVoid globalMethodHeaderVoid) {
        Obj obj = globalMethodHeaderVoid.obj;
        common_methodHeader(obj.getLevel(), obj.getLocalSymbols().size());
    }

    @Override
    public void visit(GlobalMethodHeaderType globalMethodHeaderType) {
        Obj obj = globalMethodHeaderType.obj;
        common_methodHeader(obj.getLevel(), obj.getLocalSymbols().size());
    }

    // method declarations

    private void common_methodDeclaration() {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override
    public void visit(MethodDeclarationNoParams methodDeclarationNoParams) {
        common_methodDeclaration();
    }

    @Override
    public void visit(MethodDeclarationParams methodDeclarationParams) {
        common_methodDeclaration();
    }

    @Override
    public void visit(GlobalMethodDeclarationNoParams globalMethodDeclarationNoParams) {
        common_methodDeclaration();
    }

    @Override
    public void visit(GlobalMethodDeclarationParams globalMethodDeclarationParams) {
        common_methodDeclaration();
    }


}
