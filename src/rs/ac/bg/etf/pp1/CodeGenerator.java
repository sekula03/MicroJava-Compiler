package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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
        common_methodDeclaration(Tab.noType);
    }

    private final HashMap<Struct, Integer> VFTPs = new HashMap<>();

    private final ArrayList<Obj> classMethods = new ArrayList<>();

    private int staticDataOffset = Code.dataSize;

    private Struct current_class = null;

    // method headers

    private void common_methodHeader(Obj obj) {
        obj.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(obj.getLevel());
        Code.put(obj.getLocalSymbols().size());
        nestedSwitchesStack.push(0);
    }

    @Override
    public void visit(MethodHeaderVoid methodHeaderVoid) {
        if (methodHeaderVoid.obj.getAdr() != -1)
            common_methodHeader(methodHeaderVoid.obj);
    }

    @Override
    public void visit(MethodHeaderType methodHeaderType) {
        if (methodHeaderType.obj.getAdr() != -1)
            common_methodHeader(methodHeaderType.obj);
    }

    @Override
    public void visit(GlobalMethodHeaderVoid globalMethodHeaderVoid) {
        if (globalMethodHeaderVoid.getI1().equals("main")) {
            mainPC = Code.pc;
            for (Obj method: classMethods) {
                if (method == null) {
                    Code.loadConst(-2);
                    Code.put(Code.putstatic);
                    Code.put2(Code.dataSize++);
                    continue;
                }
                String name = method.getName();
                for (int i = 0; i < name.length(); i++) {
                    Code.loadConst(name.charAt(i));
                    Code.put(Code.putstatic);
                    Code.put2(Code.dataSize++);
                }
                Code.loadConst(-1);
                Code.put(Code.putstatic);
                Code.put2(Code.dataSize++);
                Code.loadConst(method.getAdr());
                Code.put(Code.putstatic);
                Code.put2(Code.dataSize++);
            }
        }
        common_methodHeader(globalMethodHeaderVoid.obj);
    }

    @Override
    public void visit(GlobalMethodHeaderType globalMethodHeaderType) {
        common_methodHeader(globalMethodHeaderType.obj);
    }

    // method declarations

    private void common_methodDeclaration(Struct retType) {
        if (retType == Tab.noType) {
            clearSwitches();
            Code.put(Code.exit);
            Code.put(Code.return_);
        }
        else {
            Code.put(Code.trap);
            Code.put(1);
        }
    }

    @Override
    public void visit(MethodDeclarationNoParams methodDeclarationNoParams) {
        common_methodDeclaration(methodDeclarationNoParams.getMethodHeader().obj.getType());
    }

    @Override
    public void visit(MethodDeclarationParams methodDeclarationParams) {
        common_methodDeclaration(methodDeclarationParams.getMethodHeader().obj.getType());
    }

    @Override
    public void visit(GlobalMethodDeclarationNoParams globalMethodDeclarationNoParams) {
        common_methodDeclaration(globalMethodDeclarationNoParams.getGlobalMethodHeader().obj.getType());
    }

    @Override
    public void visit(GlobalMethodDeclarationParams globalMethodDeclarationParams) {
        common_methodDeclaration(globalMethodDeclarationParams.getGlobalMethodHeader().obj.getType());
    }

    // class names

    @Override
    public void visit(ClassNameNoExtends classNameNoExtends) {
        VFTPs.put(classNameNoExtends.struct, staticDataOffset);
        current_class = classNameNoExtends.struct;
    }

    @Override
    public void visit(ClassNameExtends classNameExtends) {
        VFTPs.put(classNameExtends.struct, staticDataOffset);
        current_class = classNameExtends.struct;
    }

    @Override
    public void visit(AbstractClassNameNoExtends abstractClassNameNoExtends) {
        current_class = abstractClassNameNoExtends.struct;
    }

    @Override
    public void visit(AbstractClassNameExtends abstractClassNameExtends) {
        current_class = abstractClassNameExtends.struct;
    }

    // class declarations

    private void common_classDeclaration(Struct s) {
        for (Obj method: s.getMembers()) {
            if (method.getKind() == Obj.Meth) {
                staticDataOffset += method.getName().length() + 2;
                classMethods.add(method);
                if (method.getAdr() == 0) {
                    Obj superMethod = s.getElemType().getMembersTable().searchKey(method.getName());
                    method.setAdr(superMethod.getAdr());
                }
            }
        }
        staticDataOffset++;
        classMethods.add(null);
        current_class = null;
    }

    @Override
    public void visit(ClassDeclarationNoMethodList classDeclarationNoMethodList) {
        common_classDeclaration(classDeclarationNoMethodList.getClassName().struct);
    }

    @Override
    public void visit(ClassDeclarationMethodList classDeclarationMethodList) {
        common_classDeclaration(classDeclarationMethodList.getClassName().struct);
    }

    @Override
    public void visit(AbstractClassDeclarationNoMethodList abstractClassDeclarationNoMethodList) {
        current_class = null;
    }

    @Override
    public void visit(AbstractClassDeclarationMethodList abstractClassDeclarationMethodList) {
        current_class = null;
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
        designators.pop();
    }

    @Override
    public void visit(Return _return) {
        clearSwitches();
    }

    @Override
    public void visit(StatementReturnNoExpression statementReturn) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override
    public void visit(StatementReturnExpression statementReturnExpression) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override
    public void visit(StatementIf statementIf) {
        Code.fixup(conds.pop());
    }

    private final Stack<int[]> forStack = new Stack<>();

    @Override
    public void visit(StatementFor statementFor) {
        while (!continueStack.peek().isEmpty()) Code.fixup(continueStack.peek().pop());
        continueStack.pop();
        Code.putJump(forStack.pop()[1]);
        if (!conds.isEmpty()) Code.fixup(conds.pop());
        while (!breakStack.peek().isEmpty()) Code.fixup(breakStack.peek().pop());
        breakStack.pop();
        nestedSwitchesStack.pop();
    }

    @Override
    public void visit(ForStart forStart) {
        forStack.push(new int[2]);
        breakStack.push(new Stack<>());
        continueStack.push(new Stack<>());
        nestedSwitchesStack.push(0);
    }

    @Override
    public void visit(ForInitNotEmpty forInitNotEmpty) {
        forStack.peek()[0] = Code.pc;
    }

    @Override
    public void visit(ForInitEmpty forInitEmpty) {
        forStack.peek()[0] = Code.pc;
    }

    @Override
    public void visit(ForCondNotEmpty forCondNotEmpty) {
        Code.putJump(0);
        forStack.peek()[1] = Code.pc;
    }

    @Override
    public void visit(ForCondEmpty forCondEmpty) {
        Code.putJump(0);
        forStack.peek()[1] = Code.pc;
    }

    @Override
    public void visit(ForActNotEmpty forActNotEmpty) {
        Code.putJump(forStack.peek()[0]);
        Code.fixup(forStack.peek()[1] - 2);
    }

    @Override
    public void visit(ForActEmpty forActEmpty) {
        Code.putJump(forStack.peek()[0]);
        Code.fixup(forStack.peek()[1] - 2);
    }

    private final Stack<Stack<Integer>> switchStack = new Stack<>();

    private final Stack<Integer> nestedSwitchesStack = new Stack<>();

    private void clearSwitches() {
        while (!nestedSwitchesStack.isEmpty()) {
            for (int i = 0; i < nestedSwitchesStack.pop(); i++) Code.put(Code.pop);
        }
    }

    @Override
    public void visit(StatementSwitch statementSwitch) {
        while(!switchStack.peek().isEmpty()) Code.fixup(switchStack.peek().pop());
        switchStack.pop();
        while (!breakStack.peek().isEmpty()) Code.fixup(breakStack.peek().pop());
        breakStack.pop();
        Code.put(Code.pop);
        nestedSwitchesStack.push(nestedSwitchesStack.pop() - 1);
    }

    @Override
    public void visit(SwitchStart switchStart) {
        switchStack.push(new Stack<>());
        breakStack.push(new Stack<>());
        nestedSwitchesStack.push(nestedSwitchesStack.pop() + 1);
    }

    @Override
    public void visit(CaseStart caseStart) {
        Code.put(Code.dup);
        Code.loadConst(caseStart.getN1());
        Code.putFalseJump(Code.eq, 0);
        if (!switchStack.peek().isEmpty()) Code.fixup(switchStack.peek().pop());
        switchStack.peek().push(Code.pc - 2);
    }

    @Override
    public void visit(Case _case) {
        Code.putJump(0);
        Code.fixup(switchStack.peek().pop());
        switchStack.peek().push(Code.pc - 2);
    }

    private final Stack<Stack<Integer>> breakStack = new Stack<>();

    @Override
    public void visit(StatementBreak statementBreak) {
        Code.putJump(0);
        breakStack.peek().push(Code.pc - 2);
    }

    private final Stack<Stack<Integer>> continueStack = new Stack<>();

    @Override
    public void visit(StatementContinue statementContinue) {
        for (int i = 0; i < nestedSwitchesStack.peek(); i++) Code.put(Code.pop);
        Code.putJump(0);
        continueStack.peek().push(Code.pc - 2);
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
        Obj obj = factorVariable.getDesignator().obj;
        if (obj.getName().contains(".length"))
            Code.put(Code.arraylength);
        else
            Code.load(obj);
        if (factorVariable.getSign() instanceof SignMinus)
            Code.put(Code.neg);
        designators.pop();
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
        Code.put(Code.dup);
        Code.loadConst(VFTPs.get(factorNewVar.getType().struct));
        Code.put(Code.putfield);
        Code.put2(0);
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

    private final Stack<ArrayList<Obj>> designators = new Stack<>();

    @Override
    public void visit(DesignatorFieldVar designatorFieldVar) {
        Obj obj = designatorFieldVar.getDesignator().obj;
        if (obj.getKind() != Obj.Type) Code.load(obj);
        designators.peek().add(obj);
    }

    @Override
    public void visit(FieldArrayName fieldArrayName) {
        Code.load(fieldArrayName.getDesignator().obj);
        Code.load(fieldArrayName.obj);
        designators.peek().add(fieldArrayName.getDesignator().obj);
        designators.peek().add(fieldArrayName.obj);
    }

    @Override
    public void visit(DesignatorLength designatorLength) {
        Code.load(designatorLength.getDesignator().obj);
    }

    private void common_designatorEnd(Obj obj) {
        designators.push(new ArrayList<>());
        if (obj.getKind() == Obj.Fld ||
                (obj.getKind() == Obj.Meth && current_class != null && obj != Tab.chrObj && obj != Tab.ordObj && obj != Tab.lenObj)) {
            Code.put(Code.load_n);
            Obj this_var = new Obj(Obj.Var, "this", Tab.noType);
            this_var.setLevel(1);
            this_var.setAdr(0);
            designators.peek().add(this_var);
        }
    }

    @Override
    public void visit(EndArrayName endArrayName) {
        common_designatorEnd(endArrayName.obj);
        Code.load(endArrayName.obj);
        designators.peek().add(endArrayName.obj);
    }

    @Override
    public void visit(DesignatorEndVar designatorEndVar) {
        common_designatorEnd(designatorEndVar.obj);
    }

    // designator statements

    @Override
    public void visit(DesignatorStatementAssign designatorStatementAssign) {
        Code.store(designatorStatementAssign.getDesignator().obj);
        designators.pop();
    }

    private void common_FunctionCall(Obj obj) {
        ArrayList<Obj> d = designators.pop();
        if (d.isEmpty()) {
            int offset = obj.getAdr() - Code.pc;
            Code.put(Code.call);
            Code.put2(offset);
        }
        else {
            for (Obj value : d) Code.load(value);
            Code.put(Code.getfield);
            Code.put2(0);
            Code.put(Code.invokevirtual);
            for (int i = 0; i < obj.getName().length(); i++)
                Code.put4(obj.getName().charAt(i));
            Code.put4(-1);
        }
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
        designators.pop();
    }

    @Override
    public void visit(DesignatorStatementIncrement designatorStatementIncrement) {
        common_designatorStatementIncDec(designatorStatementIncrement.getDesignator().obj, Code.add);
    }

    @Override
    public void visit(DesignatorStatementDecrement designatorStatementDecrement) {
        common_designatorStatementIncDec(designatorStatementDecrement.getDesignator().obj, Code.sub);
    }

    // condition facts

    private final Stack<Integer> condFacts = new Stack<>(), condTerms = new Stack<>(), conds = new Stack<>();

    @Override
    public void visit(ConditionFactorNoRelOp conditionFactorNoRelOp) {
        Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0);
        condFacts.push(Code.pc - 2);
    }

    @Override
    public void visit(ConditionFactorRelOp conditionFactorRelOp) {
        RelOp relOp = conditionFactorRelOp.getRelOp();
        if (relOp instanceof RelOpEqual) Code.putFalseJump(Code.eq, 0);
        else if (relOp instanceof RelOpNotEqual) Code.putFalseJump(Code.ne, 0);
        else if (relOp instanceof RelOpLess) Code.putFalseJump(Code.lt, 0);
        else if (relOp instanceof RelOpLessEqual) Code.putFalseJump(Code.le, 0);
        else if (relOp instanceof RelOpGreater) Code.putFalseJump(Code.gt, 0);
        else Code.putFalseJump(Code.ge, 0);
        condFacts.push(Code.pc - 2);
    }

    // condition term

    @Override
    public void visit(ConditionTermMultiple conditionTermMultiple) {
        if (!(conditionTermMultiple.getParent() instanceof ConditionTermMultiple)) {
            Code.putJump(0);
            condTerms.push(Code.pc - 2);
            while (!condFacts.isEmpty()) Code.fixup(condFacts.pop());
        }
    }

    @Override
    public void visit(ConditionTermSingle conditionTermSingle) {
        if (!(conditionTermSingle.getParent() instanceof ConditionTermMultiple)) {
            Code.putJump(0);
            condTerms.push(Code.pc - 2);
            while (!condFacts.isEmpty()) Code.fixup(condFacts.pop());
        }
    }

    // condition

    @Override
    public void visit(ConditionMultiple conditionMultiple) {
        if (!(conditionMultiple.getParent() instanceof ConditionMultiple)) {
            Code.putJump(0);
            conds.push(Code.pc - 2);
            while (!condTerms.isEmpty()) Code.fixup(condTerms.pop());
        }
    }

    @Override
    public void visit(ConditionSingle conditionSingle) {
        if (!(conditionSingle.getParent() instanceof ConditionMultiple)) {
            Code.putJump(0);
            conds.push(Code.pc - 2);
            while (!condTerms.isEmpty()) Code.fixup(condTerms.pop());
        }
    }

    // skips

    @Override
    public void visit(SkipElse skipElse) {
        Code.putJump(0);
        Code.fixup(conds.pop());
        conds.push(Code.pc - 2);
    }

    @Override
    public void visit(SkipTernary skipTernary) {
        Code.putJump(0);
        Code.fixup(conds.pop());
        conds.push(Code.pc - 2);
    }

    @Override
    public void visit(ExpressionTernary expressionTernary) {
        Code.fixup(conds.pop());
    }

}
