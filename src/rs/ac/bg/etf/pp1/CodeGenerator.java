package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPC;

    public int getMainPC() {
        return mainPC;
    }

    public CodeGenerator() {
        common(Tab.chrObj, false);
        common(Tab.ordObj, false);
        common(Tab.lenObj, true);
    }

    private void common(Obj method, boolean length) {
        common_methodHeader(method);
        Code.put(Code.load_n);
        if (length) Code.put(Code.arraylength);
        common_methodDeclarationReturn();
    }

    // method headers

    private void common_methodHeader(Obj obj) {
        obj.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(obj.getLevel());
        Code.put(obj.getLocalSymbols().size());
    }

    @Override
    public void visit(MethodHeaderVoid methodHeaderVoid) {
        common_methodHeader(methodHeaderVoid.obj);
    }

    @Override
    public void visit(MethodHeaderType methodHeaderType) {
        common_methodHeader(methodHeaderType.obj);
    }

    @Override
    public void visit(GlobalMethodHeaderVoid globalMethodHeaderVoid) {
        if (globalMethodHeaderVoid.getI1().equals("main"))
            mainPC = Code.pc;
        common_methodHeader(globalMethodHeaderVoid.obj);
    }

    @Override
    public void visit(GlobalMethodHeaderType globalMethodHeaderType) {
        common_methodHeader(globalMethodHeaderType.obj);
    }

    // method declarations

    private void common_methodDeclarationReturn() {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override
    public void visit(MethodDeclarationNoParams methodDeclarationNoParams) {
        common_methodDeclarationReturn();
    }

    @Override
    public void visit(MethodDeclarationParams methodDeclarationParams) {
        common_methodDeclarationReturn();
    }

    @Override
    public void visit(GlobalMethodDeclarationNoParams globalMethodDeclarationNoParams) {
        common_methodDeclarationReturn();
    }

    @Override
    public void visit(GlobalMethodDeclarationParams globalMethodDeclarationParams) {
        common_methodDeclarationReturn();
    }

    // statements

    @Override
    public void visit(StatementPrintNoNumConst statementPrintNoNumConst) {
        Code.loadConst(0);
        Code.put(statementPrintNoNumConst.getExpression().struct == Tab.charType ? Code.bprint : Code.print);
    }

    @Override
    public void visit(StatementPrintNumConst statementPrintNumConst) {
        Code.loadConst(statementPrintNumConst.getN2());
        Code.put(statementPrintNumConst.getExpression().struct == Tab.charType ? Code.bprint : Code.print);
    }

    @Override
    public void visit(StatementRead statementRead) {
        Code.put(statementRead.getDesignator().obj.getType() == Tab.charType ? Code.bread : Code.read);
        Code.store(statementRead.getDesignator().obj);
    }

    @Override
    public void visit(StatementReturnNoExpression statementReturnNoExpression) {
        common_methodDeclarationReturn();
    }

    @Override
    public void visit(StatementReturnExpression statementReturnExpression) {
        common_methodDeclarationReturn();
    }

    // factors

    @Override
    public void visit(FactorNumConst factorNumConst) {
        Code.loadConst(factorNumConst.getN2());
        if (factorNumConst.getSign() instanceof SignMinus)
            Code.put(Code.neg);
    }

    @Override
    public void visit(FactorCharConst factorCharConst) {
        Code.loadConst(factorCharConst.getC1());
    }

    @Override
    public void visit(FactorBoolConst factorBoolConst) {
        Code.loadConst(factorBoolConst.getB1() ? 1 : 0);
    }

    @Override
    public void visit(FactorVariable factorVariable) {
        if (factorVariable.getDesignator().obj.getName().contains(".length"))
            Code.put(Code.arraylength);
        else Code.load(factorVariable.getDesignator().obj);
        if (factorVariable.getSign() instanceof SignMinus)
            Code.put(Code.neg);
    }

    @Override
    public void visit(FactorFunctionCallNoArgs factorFunctionCallNoArgs) {
        common_FunctionCall(factorFunctionCallNoArgs.getDesignator().obj);
        if (factorFunctionCallNoArgs.getSign() instanceof SignMinus)
            Code.put(Code.neg);
    }

    @Override
    public void visit(FactorFunctionCallWithArgs factorFunctionCallWithArgs) {
        common_FunctionCall(factorFunctionCallWithArgs.getDesignator().obj);
        if (factorFunctionCallWithArgs.getSign() instanceof SignMinus)
            Code.put(Code.neg);
    }

    @Override
    public void visit(FactorNewVar factorNewVar) {
        Code.put(Code.new_);
        Code.put2(factorNewVar.getType().struct.getNumberOfFields() << 2);
    }

    @Override
    public void visit(FactorNewArray factorNewArray) {
        Code.put(Code.newarray);
        Code.put(factorNewArray.getType().struct == Tab.charType ? 0 : 1);
    }

    @Override
    public void visit(FactorNested factorNested) {
        if (factorNested.getSign() instanceof SignMinus)
            Code.put(Code.neg);
    }

    // factor & term list

    @Override
    public void visit(FactorListMulOp factorListMulOp) {
        MulOp mulOp = factorListMulOp.getMulOp();
        if (mulOp instanceof MulOpTimes)
            Code.put(Code.mul);
        else if (mulOp instanceof MulOpDiv)
            Code.put(Code.div);
        else
            Code.put(Code.rem);
    }

    @Override
    public void visit(TermListAddOp termListAddOp) {
        AddOp addOp = termListAddOp.getAddOp();
        if (addOp instanceof AddOpPlus)
            Code.put(Code.add);
        else
            Code.put(Code.sub);
    }

    // designators

    @Override
    public void visit(DesignatorFieldVar designatorFieldVar) {
        Code.load(designatorFieldVar.getDesignator().obj);
    }

    @Override
    public void visit(FieldArrayName fieldArrayName) {
        Code.load(fieldArrayName.getDesignator().obj);
        Code.load(fieldArrayName.obj);
    }

    @Override
    public void visit(DesignatorLength designatorLength) {
        Code.load(designatorLength.getDesignator().obj);
    }

    @Override
    public void visit(EndArrayName endArrayName) {
        Code.load(endArrayName.obj);
    }

    // designator statements

    @Override
    public void visit(DesignatorStatementAssign designatorStatementAssign) {
        Code.store(designatorStatementAssign.getDesignator().obj);
    }

    private void common_FunctionCall(Obj obj) {
        int offset = obj.getAdr() - Code.pc;
        Code.put(Code.call);
        Code.put2(offset);
    }

    @Override
    public void visit(DesignatorStatementFunctionCallParams designatorStatementFunctionCallParams) {
        Obj obj = designatorStatementFunctionCallParams.getDesignator().obj;
        common_FunctionCall(obj);
        if (obj.getType() != Tab.noType) Code.put(Code.pop);
    }

    @Override
    public void visit(DesignatorStatementFunctionCallNoParams designatorStatementFunctionCallNoParams) {
        Obj obj = designatorStatementFunctionCallNoParams.getDesignator().obj;
        common_FunctionCall(obj);
        if (obj.getType() != Tab.noType) Code.put(Code.pop);
    }

    private void common_designatorStatementIncDec(Obj obj, int op) {
        if (obj.getKind() == Obj.Elem) Code.put(Code.dup2);
        else if (obj.getKind() == Obj.Fld) Code.put(Code.dup);
        Code.load(obj);
        Code.loadConst(1);
        Code.put(op);
        Code.store(obj);
    }

    @Override
    public void visit(DesignatorStatementIncrement designatorStatementIncrement) {
        common_designatorStatementIncDec(designatorStatementIncrement.getDesignator().obj, Code.add);
    }

    @Override
    public void visit(DesignatorStatementDecrement designatorStatementDecrement) {
        common_designatorStatementIncDec(designatorStatementDecrement.getDesignator().obj, Code.sub);
    }

}
