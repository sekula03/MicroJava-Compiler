package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.*;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPC;

    public int getMainPC() {
        return mainPC;
    }

    private final Map<Struct, Integer> VFTPs = new HashMap<>();

    private final ArrayList<Obj> classMethods = new ArrayList<>();

    private int staticDataOffset = Code.dataSize;

    private Struct current_class = null;

    private final Map<Obj, Integer> finalObjFields = new HashMap<>();

    public CodeGenerator(Set<Obj> finalObjs) {
        for (Obj obj: finalObjs)
            finalObjFields.put(obj, 0);
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

    @Override
    public void visit(Program program) {
        Code.dataSize += maxStackSize;
    }

    @Override
    public void visit(ProgramName programName) {
        for (Obj obj: programName.obj.getLocalSymbols()) {
            if (obj.getKind() == Obj.Type && obj.getType().getKind() == Struct.Class) {
                Struct type = obj.getType();
                VFTPs.put(type, staticDataOffset);
                for (Obj meth: type.getMembers()) {
                    if (meth.getKind() == Obj.Meth) {
                        staticDataOffset += meth.getName().length() + 2;
                        classMethods.add(meth);
                    }
                }
                staticDataOffset++;
                classMethods.add(null);
            }
        }
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
        current_class = classNameNoExtends.struct;
    }

    @Override
    public void visit(ClassNameExtends classNameExtends) {
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

    private void common_classDeclaration() {
        for (Obj method: current_class.getMembers()) {
            if (method.getKind() == Obj.Meth && method.getAdr() == 0) {
                Obj superMethod = current_class.getElemType().getMembersTable().searchKey(method.getName());
                method.setAdr(superMethod.getAdr());
            }
        }
        current_class = null;
    }

    @Override
    public void visit(ClassDeclarationNoMethodList classDeclarationNoMethodList) {
        common_classDeclaration();
    }

    @Override
    public void visit(ClassDeclarationMethodList classDeclarationMethodList) {
        common_classDeclaration();
    }

    @Override
    public void visit(AbstractClassDeclarationNoMethodList abstractClassDeclarationNoMethodList) {
        common_classDeclaration();
    }

    @Override
    public void visit(AbstractClassDeclarationMethodList abstractClassDeclarationMethodList) {
        common_classDeclaration();
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
        for (int continueAdr: continueStack.pop()) Code.fixup(continueAdr);
        Code.putJump(forStack.pop()[1]);
        int condAdr = conds.pop();
        if (condAdr != -1) Code.fixup(condAdr);
        for (int breakAdr: breakStack.pop()) Code.fixup(breakAdr);
    }

    @Override
    public void visit(ForStart forStart) {
        forStack.push(new int[2]);
        breakStack.push(new ArrayList<>());
        continueStack.push(new ArrayList<>());
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
        conds.push(-1);
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

    private final Stack<Integer> caseStack = new Stack<>();
    private final Stack<Integer> defaultAddress = new Stack<>();

    @Override
    public void visit(SwitchStart switchStart) {
        caseStack.push(-1);
        breakStack.push(new ArrayList<>());
        defaultAddress.push(-1);
    }

    @Override
    public void visit(StatementSwitch statementSwitch) {
        Code.put(Code.pop);
        int defaultAddr = defaultAddress.pop();
        if (defaultAddr != -1) Code.putJump(defaultAddr);
        int fixAdr = caseStack.pop();
        if (fixAdr != -1) Code.fixup(fixAdr);
        for (int breakAdr: breakStack.pop()) Code.fixup(breakAdr);
    }

    @Override
    public void visit(CaseStart caseStart) {
        Code.put(Code.dup);
        Code.loadConst(caseStart.getN1());
        Code.putFalseJump(Code.eq, 0);
        int fixAdr = caseStack.pop();
        caseStack.push(Code.pc - 2);
        Code.put(Code.pop);
        if (fixAdr != -1) Code.fixup(fixAdr);
    }

    @Override
    public void visit(Case _case) {
        Code.putJump(0);
        Code.fixup(caseStack.pop());
        caseStack.push(Code.pc - 2);
    }

    @Override
    public void visit(DefaultCaseStart defaultCaseStart) {
        Code.putJump(0);
        int fixAdr = caseStack.pop();
        if (fixAdr != -1) Code.fixup(fixAdr);
        caseStack.push(Code.pc - 2);
        defaultAddress.pop();
        defaultAddress.push(Code.pc);
    }

    @Override
    public void visit(DefaultCase defaultCase) {
        Code.putJump(0);
        Code.fixup(caseStack.pop());
        caseStack.push(Code.pc - 2);
    }

    private final Stack<List<Integer>> breakStack = new Stack<>();

    @Override
    public void visit(StatementBreak statementBreak) {
        Code.putJump(0);
        breakStack.peek().add(Code.pc - 2);
    }

    private final Stack<List<Integer>> continueStack = new Stack<>();

    @Override
    public void visit(StatementContinue statementContinue) {
        Code.putJump(0);
        continueStack.peek().add(Code.pc - 2);
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
        int size = factorNewVar.getType().struct.getNumberOfFields() << 2;
        if (finalObj != null) {
            size = size << 1;
            finalObjFields.put(finalObj, factorNewVar.getType().struct.getNumberOfFields());
        }
        Code.put2(size);
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

    @Override
    public void visit(FactorMaxArray factorMaxArray) {
        Code.load(factorMaxArray.getDesignator().obj);
        Code.put(Code.dup);
        Code.put(Code.arraylength);
        Code.loadConst(0);
        Code.putFalseJump(Code.eq, 0);
        Code.put(Code.trap);
        Code.put(2);
        Code.fixup(Code.pc - 4);
        Code.put(Code.dup);
        Code.put(Code.dup);
        Code.put(Code.arraylength);
        Code.loadConst(1);
        Code.put(Code.sub);
        Code.put(Code.aload);
        Code.put(Code.dup2);
        Code.put(Code.pop);
        Code.put(Code.arraylength);
        Code.loadConst(2);
        Code.put(Code.sub);
        int start = Code.pc;
        Code.put(Code.dup);
        Code.loadConst(0);
        Code.putFalseJump(Code.ge, 0);
        int exit = Code.pc - 2;
        // body
        Code.put(Code.dup_x2);
        Code.put(Code.pop);

        Code.put(Code.dup_x2);
        Code.put(Code.pop);

        Code.put(Code.dup_x2);
        Code.put(Code.dup_x1);
        Code.put(Code.pop);

        Code.put(Code.dup_x2);
        Code.put(Code.aload);

        Code.put(Code.dup2);
        Code.put(Code.pop);
        Code.put(Code.dup_x2);
        Code.put(Code.pop);
        Code.put(Code.dup_x1);

        Code.putFalseJump(Code.lt, 0);

        Code.put(Code.dup_x1);
        Code.put(Code.pop);
        Code.fixup(Code.pc - 4);

        Code.put(Code.pop);
        Code.put(Code.dup_x1);
        Code.put(Code.pop);

        Code.loadConst(1);
        Code.put(Code.sub);
        Code.putJump(start);
        Code.fixup(exit);

        Code.put(Code.pop);
        Code.put(Code.dup_x1);
        Code.put(Code.pop);
        Code.put(Code.pop);

        if (factorMaxArray.getSign() instanceof SignMinus)
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

    private int maxStackSize = 0;
    private final Stack<Boolean> virtualCallStack = new Stack<>();

    private void loadVFTP() {
        Code.put(Code.dup);
        Code.put(Code.getfield);
        Code.put2(0);
        Code.put(Code.putstatic);
        Code.put2(staticDataOffset++);
        if (staticDataOffset > maxStackSize) maxStackSize = staticDataOffset;
        virtualCallStack.push(true);
    }

    @Override
    public void visit(DesignatorFieldVar designatorFieldVar) {
        Obj obj = designatorFieldVar.getDesignator().obj;
        if (obj.getKind() != Obj.Type) Code.load(obj);
        if (designatorFieldVar.obj.getKind() == Obj.Meth) loadVFTP();
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
        if (endArrayName.obj.getKind() == Obj.Fld) Code.put(Code.load_n);
        Code.load(endArrayName.obj);
    }

    @Override
    public void visit(DesignatorEndVar designatorEndVar) {
        Obj obj = designatorEndVar.obj;
        if (obj.getKind() == Obj.Fld) Code.put(Code.load_n);
        else if (obj.getKind() == Obj.Meth) {
            if (current_class != null && obj != Tab.chrObj && obj != Tab.ordObj && obj != Tab.lenObj) {
                Code.put(Code.load_n);
                loadVFTP();
            }
            else virtualCallStack.push(false);
        }
    }

    // designator statements

    @Override
    public void visit(DesignatorStatementAssign designatorStatementAssign) {
        Obj obj = designatorStatementAssign.getDesignator().obj;
        if (obj.getKind() == Obj.Fld) {

        }
        Code.store(obj);
        finalObj = null;
    }

    private Obj finalObj = null;

    @Override
    public void visit(Assign assign) {
        Obj obj = ((DesignatorStatementAssign) assign.getParent()).getDesignator().obj;
        if (finalObjFields.containsKey(obj)) finalObj = obj;
    }

    private void common_FunctionCall(Obj obj) {
        if (virtualCallStack.pop()) {
            Code.put(Code.getstatic);
            Code.put2(--staticDataOffset);
            Code.put(Code.invokevirtual);
            for (int i = 0; i < obj.getName().length(); i++)
                Code.put4(obj.getName().charAt(i));
            Code.put4(-1);
        }
        else {
            int offset = obj.getAdr() - Code.pc;
            Code.put(Code.call);
            Code.put2(offset);
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
