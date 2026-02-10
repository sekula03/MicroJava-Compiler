package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class SemanticAnalyzer extends VisitorAdaptor {

    Logger log = Logger.getLogger(getClass());
    private boolean errorDetected = false;

    public void report_error(String message, SyntaxNode info) {
        errorDetected = true;
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0 : info.getLine();
        if (line != 0) msg.append(" na liniji ").append(line);
        log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0 : info.getLine();
        if (line != 0) msg.append(" na liniji ").append(line);
        log.info(msg.toString());
    }

    public boolean passed() {
        return !errorDetected;
    }

    // =================================================================================================================

    private Obj obj_program, obj_method;
    private Struct struct_type, struct_class;
    private final Struct boolType;
    private boolean main = false, return_statement;
    private int globalVariables;

    public SemanticAnalyzer() {
        Tab.init();
        boolType = new Struct(Struct.Bool);
        Obj bool = Tab.insert(Obj.Type, "bool", boolType);
        bool.setAdr(-1);
        bool.setLevel(-1);
        for (Obj obj: Tab.chrObj.getLocalSymbols())
            obj.setFpPos(1);
        for (Obj obj: Tab.ordObj.getLocalSymbols())
            obj.setFpPos(1);
        for (Obj obj: Tab.lenObj.getLocalSymbols())
            obj.setFpPos(1);
    }

    public int getGlobalVariables() {
        return globalVariables;
    }

    @Override
    public void visit(Program program) {
        globalVariables = Tab.currentScope().getnVars();
        Tab.chainLocalSymbols(obj_program);
        Tab.closeScope();
        if (!main)
            report_error("Nedostaje metoda main", null);
    }

    @Override
    public void visit(ProgramName programName) {
        obj_program = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
        Tab.openScope();
    }

    @Override
    public void visit(Type type) {
        Obj definedType = Tab.find(type.getI1());
        if (definedType == Tab.noObj || definedType.getKind() != Obj.Type) {
            report_error("Nepostojeci tip: " + type.getI1(), type);
            type.struct = struct_type = Tab.noType;
        }
        else
            type.struct = struct_type = definedType.getType();
    }

    // global consts

    private void common_const(SyntaxNode sn, String name, Struct type, int value) {
        Obj con = Tab.find(name);
        if (con != Tab.noObj)
            report_error("Visestruka definicija konstante: " + name, sn);
        else if (type != struct_type)
            report_error("Nekompatibilna vrednost konstante: " + name, sn);
        else {
            con = Tab.insert(Obj.Con, name, struct_type);
            con.setAdr(value);
        }
    }

    @Override
    public void visit(ConstNum constNum) {
        common_const(constNum, constNum.getI1(), Tab.intType, constNum.getN2());
    }

    @Override
    public void visit(ConstChar constChar) {
        common_const(constChar, constChar.getI1(), Tab.charType, constChar.getC2());
    }

    @Override
    public void visit(ConstBool constBool) {
        common_const(constBool, constBool.getI1(), boolType, constBool.getB2() ? 1 : 0);
    }

    // global vars

    private void common_globalVar(SyntaxNode sn, String name, Struct type) {
        Obj var = Tab.find(name);
        if (var != Tab.noObj)
            report_error("Visestruka definicija promenljive: " + name, sn);
        else
            Tab.insert(Obj.Var, name, type);
    }

    @Override
    public void visit(GlobalVarSingle globalVarSingle) {
        common_globalVar(globalVarSingle, globalVarSingle.getI1(), struct_type);
    }

    @Override
    public void visit(GlobalVarArray globalVarArray) {
        common_globalVar(globalVarArray, globalVarArray.getI1(), new Struct(Struct.Array, struct_type));
    }

    // global methods

    private void common_globalMethodHeader(GlobalMethodHeader sn, String name, Struct type) {
        Obj meth = Tab.find(name);
        if (meth != Tab.noObj)
            report_error("Visestruka definicija metode: " + name, sn);
        sn.obj = obj_method = Tab.insert(Obj.Meth, name, type);
        return_statement = false;
        Tab.openScope();
    }

    @Override
    public void visit(GlobalMethodHeaderType globalMethodHeaderType) {
        if (globalMethodHeaderType.getI2().equals("main"))
            report_error("Nevalidan potpis medote main", globalMethodHeaderType);
        common_globalMethodHeader(globalMethodHeaderType, globalMethodHeaderType.getI2(), struct_type);
    }

    @Override
    public void visit(GlobalMethodHeaderVoid globalMethodHeaderVoid) {
        common_globalMethodHeader(globalMethodHeaderVoid, globalMethodHeaderVoid.getI1(), Tab.noType);
    }

    private void common_globalMethodDeclaration(SyntaxNode sn) {
        if (!return_statement && obj_method.getType() != Tab.noType)
            report_error("Nedostaje return iskaz", sn);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(GlobalMethodDeclarationParams globalMethodDeclarationParams) {
        if (obj_method.getName().equals("main"))
            report_error("Nevalidan potpis medote main", globalMethodDeclarationParams);
        common_globalMethodDeclaration(globalMethodDeclarationParams);
    }

    @Override
    public void visit(GlobalMethodDeclarationNoParams globalMethodDeclarationNoParams) {
        if (obj_method.getName().equals("main"))
            main = true;
        common_globalMethodDeclaration(globalMethodDeclarationNoParams);
    }

    // local vars

    private void common_var(SyntaxNode sn, String name, Struct type) {
        Obj var = Tab.currentScope().findSymbol(name);
        if (var != null)
            report_error("Visestruka definicija promenljive: " + name, sn);
        else
            Tab.insert(Obj.Var, name, type);
    }

    @Override
    public void visit(VarSingle varSingle) {
        common_var(varSingle, varSingle.getI1(), struct_type);
    }

    @Override
    public void visit(VarArray varArray) {
        common_var(varArray, varArray.getI1(), new Struct(Struct.Array, struct_type));
    }

    // formal params

    private void common_formalParam(SyntaxNode sn, String name, Struct type) {
        Obj fp = Tab.currentScope().findSymbol(name);
        if (fp != null)
            report_error("Visestruka definicija formalnog parametra: " + name, sn);
        else {
            fp = Tab.insert(Obj.Var, name, type);
            obj_method.setLevel(obj_method.getLevel() + 1);
            fp.setFpPos(obj_method.getLevel());
        }
    }

    @Override
    public void visit(FormalParamSingle formalParamSingle) {
        common_formalParam(formalParamSingle, formalParamSingle.getI2(), struct_type);
    }

    @Override
    public void visit(FormalParamArray formalParamArray) {
        common_formalParam(formalParamArray, formalParamArray.getI2(), new Struct(Struct.Array, struct_type));
    }

    // enums

    private int next_enum;
    private final HashSet<Integer> enum_values = new HashSet<>();

    @Override
    public void visit(EnumName enumName) {
        Obj en = Tab.find(enumName.getI1());
        if (en != Tab.noObj)
            report_error("Visestruka definicija nabrajanja: " + enumName.getI1(), enumName);
        struct_class = new Struct(Struct.Enum);
        next_enum = 0;
        Tab.insert(Obj.Type, enumName.getI1(), struct_class);
        Tab.openScope();
    }

    @Override
    public void visit(EnumDeclaration enumDeclaration) {
        Tab.chainLocalSymbols(struct_class);
        Tab.closeScope();
        enum_values.clear();
        struct_class = null;
    }

    private void common_enumConst(SyntaxNode sn, String name, int value) {
        Obj en = Tab.currentScope().findSymbol(name);
        if (en != null)
            report_error("Visestruka definicija konstante nabrajanja: " + name, sn);
        else if (enum_values.contains(value))
            report_error("Ponovljena vrednost konstante nabrajanja: " + name + " = " + value, sn);
        else {
            en = Tab.insert(Obj.Con, name, struct_class);
            en.setAdr(value);
            enum_values.add(value);
            next_enum = value + 1;
        }
    }

    @Override
    public void visit(EnumConstAssign enumConstAssign) {
        common_enumConst(enumConstAssign, enumConstAssign.getI1(), enumConstAssign.getN2());
    }

    @Override
    public void visit(EnumConstNoAssign enumConstNoAssign) {
        common_enumConst(enumConstNoAssign, enumConstNoAssign.getI1(), next_enum);
    }

    // classes

    private boolean assignableTo(Struct src, Struct dst) {
        if (src == null || dst == null || (src == Tab.noType ^ dst == Tab.noType)) return false;
        if (src.compatibleWith(dst) || (src.getKind() == Struct.Enum && dst == Tab.intType)) return true;
        if (src.getKind() == Struct.Array && dst.getKind() == Struct.Array &&
                src.getElemType() != Tab.noType && dst.getElemType() == Tab.noType) return true;
        if ((src.getKind() != Struct.Class && src.getKind() != Struct.Interface)
                || (dst.getKind() != Struct.Class && dst.getKind() != Struct.Interface)) return false;
        while (src.getElemType() != Tab.noType) {
            if (src.getElemType() == dst) return true;
            src = src.getElemType();
        }
        return false;
    }

    private void common_className(SyntaxNode sn, String name, Struct type, int classtype) {
        Obj cls = Tab.find(name);
        if (cls != Tab.noObj)
            report_error("Visestruka definicija klase: " + name, sn);
        struct_class = new Struct(classtype);
        Tab.insert(Obj.Type, name, struct_class);
        Tab.openScope();
        struct_class.setElementType(type);
    }

    @Override
    public void visit(ClassNameNoExtends classNameNoExtends) {
        common_className(classNameNoExtends, classNameNoExtends.getI1(), Tab.noType, Struct.Class);
        classNameNoExtends.struct = struct_class;
        Tab.insert(Obj.Fld, "#VFTP", Tab.intType);
    }

    @Override
    public void visit(ClassNameExtends classNameExtends) {
        if (struct_type.getKind() != Struct.Class && struct_type.getKind() != Struct.Interface)
            report_error("Nepostojeca natklasa: " + struct_type, classNameExtends);
        common_className(classNameExtends, classNameExtends.getI1(), struct_type, Struct.Class);
        classNameExtends.struct = struct_class;
        for (Obj o: struct_class.getElemType().getMembers()) {
            if (o.getKind() == Obj.Fld)
                Tab.insert(Obj.Fld, o.getName(), o.getType());
        }
    }

    @Override
    public void visit(AbstractClassNameNoExtends abstractClassNameNoExtends) {
        common_className(abstractClassNameNoExtends, abstractClassNameNoExtends.getI1(), Tab.noType, Struct.Interface);
        abstractClassNameNoExtends.struct = struct_class;
        Tab.insert(Obj.Fld, "#VFTP", Tab.intType);
    }

    @Override
    public void visit(AbstractClassNameExtends abstractClassNameExtends) {
        if (struct_type.getKind() != Struct.Class && struct_type.getKind() != Struct.Interface)
            report_error("Nepostojeca natklasa: " + struct_type, abstractClassNameExtends);
        common_className(abstractClassNameExtends, abstractClassNameExtends.getI1(), struct_type, Struct.Interface);
        abstractClassNameExtends.struct = struct_class;
        for (Obj o: struct_class.getElemType().getMembers()) {
            if (o.getKind() == Obj.Fld)
                Tab.insert(Obj.Fld, o.getName(), o.getType());
        }
    }

    private void common_classDeclaration() {
        for (Obj superMember: struct_class.getElemType().getMembers()) {
            if (superMember.getKind() == Obj.Meth && Tab.currentScope().findSymbol(superMember.getName()) == null) {
                if (struct_class.getKind() == Struct.Class && superMember.getAdr() == Obj.NO_VALUE) {
                    report_error("Neredefinisana apstraktna metoda: " + superMember.getName(), null);
                    break;
                }
                Obj new_meth = Tab.insert(Obj.Meth, superMember.getName(), superMember.getType());
                new_meth.setLevel(superMember.getLevel());
                new_meth.setAdr(superMember.getAdr());
                Tab.openScope();
                for (Obj var: superMember.getLocalSymbols()) {
                    Obj new_var = Tab.insert(Obj.Var, var.getName(), var.getName().equals("this") ? struct_class : var.getType());
                    new_var.setFpPos(var.getFpPos());
                }
                Tab.chainLocalSymbols(new_meth);
                Tab.closeScope();
            }
        }
        Tab.chainLocalSymbols(struct_class);
        Tab.closeScope();
        struct_class = null;
    }

    @Override
    public void visit(ClassDeclarationMethodList classDeclarationMethodList) {
        common_classDeclaration();
    }

    @Override
    public void visit(ClassDeclarationNoMethodList classDeclarationNoMethodList) {
        common_classDeclaration();
    }

    @Override
    public void visit(AbstractClassDeclarationMethodList abstractClassDeclarationMethodList) {
        common_classDeclaration();
    }

    @Override
    public void visit(AbstractClassDeclarationNoMethodList abstractClassDeclarationNoMethodList) {
        common_classDeclaration();
    }

    // class fields

    private void common_field(SyntaxNode sn, String name, Struct type) {
        Obj fld = Tab.currentScope().findSymbol(name);
        if (fld != null) {
            report_error("Visestruka definicija polja: " + name, sn);
            return;
        }
        for (Obj sym: struct_class.getElemType().getMembers()) {
            if (sym.getName().equals(name)) {
                report_error("Visestruka definicija polja: " + name, sn);
                return;
            }
        }
        Tab.insert(Obj.Fld, name, type);
    }

    @Override
    public void visit(FieldSingle fieldSingle) {
        common_field(fieldSingle, fieldSingle.getI1(), struct_type);
    }

    @Override
    public void visit(FieldArray fieldArray) {
        common_field(fieldArray, fieldArray.getI1(), new Struct(Struct.Array, struct_type));
    }

    // class methods

    private void common_methodHeader(MethodHeader sn, String name, Struct type) {
        Obj meth = Tab.currentScope().findSymbol(name);
        if (meth != null)
            report_error("Visestruka definicija metode: " + name, sn);
        sn.obj = obj_method = Tab.insert(Obj.Meth, name, type);
        return_statement = false;
        Tab.openScope();
        Obj fp = Tab.insert(Obj.Var, "this", struct_class);
        obj_method.setLevel(1);
        fp.setFpPos(1);
    }

    @Override
    public void visit(MethodHeaderType methodHeaderType) {
        common_methodHeader(methodHeaderType, methodHeaderType.getI2(), struct_type);
    }

    @Override
    public void visit(MethodHeaderVoid methodHeaderVoid) {
        common_methodHeader(methodHeaderVoid, methodHeaderVoid.getI1(), Tab.noType);
    }

    private Obj findParamByPos(Obj method, int pos) {
        for (Obj sym : method.getLocalSymbols()) {
            if (sym.getFpPos() == pos) {
                return sym;
            }
        }
        return Tab.noObj;
    }

    private void common_methodDeclaration(SyntaxNode sn) {
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
        if (obj_method.getAdr() != Obj.NO_VALUE && !return_statement && obj_method.getType() != Tab.noType)
            report_error("Nedostaje return iskaz", sn);
        else {
            Obj super_method = null;
            for (Obj meth : struct_class.getElemType().getMembers()) {
                if (meth.getKind() == Obj.Meth && meth.getName().equals(obj_method.getName())) {
                    super_method = meth;
                    break;
                }
            }
            if (super_method != null) {
                if (!assignableTo(obj_method.getType(), super_method.getType()) && super_method.getLevel() == obj_method.getLevel()) {
                    report_error("Pogresna redefinicija metode: " + obj_method.getName(), sn);
                    return;
                }
                for (int i = 2; i < obj_method.getLevel(); i++) {
                    Obj currentP = findParamByPos(obj_method, i);
                    Obj superP = findParamByPos(super_method, i);
                    if (!assignableTo(superP.getType(), currentP.getType())) {
                        report_error("Pogresna redefinicija metode: " + obj_method.getName(), sn);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void visit(MethodDeclarationParams methodDeclarationParams) {
        common_methodDeclaration(methodDeclarationParams);
    }

    @Override
    public void visit(MethodDeclarationNoParams methodDeclarationNoParams) {
        common_methodDeclaration(methodDeclarationNoParams);
    }

    @Override
    public void visit(AbstractMethodDeclarationParams abstractMethodDeclarationParams) {
        obj_method.setAdr(Obj.NO_VALUE);
        common_methodDeclaration(abstractMethodDeclarationParams);
    }

    @Override
    public void visit(AbstractMethodDeclarationNoParams abstractMethodDeclarationNoParams) {
        obj_method.setAdr(Obj.NO_VALUE);
        common_methodDeclaration(abstractMethodDeclarationNoParams);
    }

    // factors

    private void common_factorVarFunctionCall(Factor f, Sign sign, Struct type) {
        if (sign instanceof SignEmpty || assignableTo(type, Tab.intType))
            f.struct = type;
        else {
            report_error("Negiranje ne-int tipa", f);
            f.struct = Tab.noType;
        }
    }

    private void common_VarFldElemCon(SyntaxNode sn, Obj d) {
        if (d.getKind() == Obj.Var) {
            if (d.getLevel() == 0)
                report_info("Pristup globalnoj promenljivoj: " + d.getName(), sn);
            else if (d.getFpPos() == 0)
                report_info("Pristup lokalnoj promenjivoj: "  + d.getName(), sn);
            else
                report_info("Pristup formalnom parametru: "  + d.getName(), sn);
        }
        else if (d.getKind() == Obj.Fld)
            report_info("Pristup klasnom polju: " + d.getName(), sn);
        else if (d.getKind() == Obj.Con && d.getType().getKind() == Struct.Enum)
            report_info("Pristup simbolickoj konstanti: " + d.getName(), sn);
        else if (d.getKind() == Obj.Elem)
            report_info("Pristup elementu niza: " + d.getName(), sn);
    }

    @Override
    public void visit(FactorVariable factorVariable) {
        Obj d = factorVariable.getDesignator().obj;
        if (d.getKind() != Obj.Var && d.getKind() != Obj.Fld && d.getKind() != Obj.Con && d.getKind() != Obj.Elem) {
            report_error("Nepostojeca promenljiva/konstanta " + d.getName(), factorVariable);
            factorVariable.struct = Tab.noType;
            return;
        }
        common_VarFldElemCon(factorVariable, d);
        common_factorVarFunctionCall(
                factorVariable,
                factorVariable.getSign(),
                factorVariable.getDesignator().obj.getType()
        );
    }

    private int common_functionCall(SyntaxNode sn, Designator designator) {
        Obj d = designator.obj;
        int diff;
        if (designator instanceof DesignatorEndVar && struct_class == null || d == Tab.chrObj || d == Tab.ordObj || d == Tab.lenObj) {
            diff = 1;
            report_info("Poziv globalne funkcije " + d.getName(), sn);
        }
        else {
            diff = 2;
            report_info("Poziv klasne metode " + d.getName(), sn);
        }
        return diff;
    }

    private void common_factorFunctionCall(Factor f, Designator designator, Sign sign) {
        Obj d = designator.obj;
        if (d.getKind() != Obj.Meth) {
            report_error("Nepostojeca metoda " + d.getName(), f);
            f.struct = Tab.noType;
            return;
        }
        int diff = common_functionCall(f, designator);
        ArrayList<Struct> curr = actual_param_types.pop();
        if (curr.size() != d.getLevel() - (diff - 1)) {
            report_error("Nepoklapanje broja argumenata poziva metode " + d.getName(), f);
            return;
        }
        for (int i = 0; i < curr.size(); i++) {
            if (!assignableTo(curr.get(i), findParamByPos(d, i+diff).getType())) {
                report_error("Nepoklapanje tipova argumenata poziva metode " + d.getName(), f);
                f.struct = Tab.noType;
                return;
            }
        }
        common_factorVarFunctionCall(f, sign, d.getType());
    }

    @Override
    public void visit(FactorFunctionCallWithArgs factorFunctionCallWithArgs) {
        common_factorFunctionCall(
                factorFunctionCallWithArgs,
                factorFunctionCallWithArgs.getDesignator(),
                factorFunctionCallWithArgs.getSign()
        );
    }

    @Override
    public void visit(FactorFunctionCallNoArgs factorFunctionCallNoArgs) {
        actual_param_types.push(new ArrayList<>());
        common_factorFunctionCall(
                factorFunctionCallNoArgs,
                factorFunctionCallNoArgs.getDesignator(),
                factorFunctionCallNoArgs.getSign()
        );
    }

    @Override
    public void visit(FactorNested factorNested) {
        common_factorVarFunctionCall(
                factorNested,
                factorNested.getSign(),
                factorNested.getExpression().struct
        );
    }

    @Override
    public void visit(FactorNumConst factorNumConst) {
        factorNumConst.struct = Tab.intType;
    }

    @Override
    public void visit(FactorBoolConst factorBoolConst) {
        factorBoolConst.struct = boolType;
    }

    @Override
    public void visit(FactorCharConst factorCharConst) {
        factorCharConst.struct = Tab.charType;
    }

    @Override
    public void visit(FactorNewVar factorNewVar) {
        if (struct_type.getKind() == Struct.Class) {
            report_info("Kreiranje objekta klase", factorNewVar);
            factorNewVar.struct = struct_type;
        }
        else  {
            report_error("Koriscen ne-klasni tip pri pozivu new", factorNewVar);
            factorNewVar.struct = Tab.noType;
        }
    }

    @Override
    public void visit(FactorNewArray factorNewArray) {
        if (assignableTo(factorNewArray.getExpression().struct, Tab.intType))
            factorNewArray.struct = new Struct(Struct.Array, struct_type);
        else {
            report_error("Indeksiranje ne-int tipom", factorNewArray);
            factorNewArray.struct = Tab.noType;
        }
    }

    // factor lists

    @Override
    public void visit(FactorListMulOp factorListMulOp) {
        if (assignableTo(factorListMulOp.getFactor().struct, Tab.intType) && assignableTo(factorListMulOp.getFactorList().struct, Tab.intType))
            factorListMulOp.struct = Tab.intType;
        else {
            report_error("Nekompatibilni tipovi za MulOp", factorListMulOp);
            factorListMulOp.struct = Tab.noType;
        }
    }

    @Override
    public void visit(FactorListNoMulOp factorListNoMulOp) {
        factorListNoMulOp.struct = factorListNoMulOp.getFactor().struct;
    }

    // terms

    @Override
    public void visit(Term term) {
        term.struct = term.getFactorList().struct;
    }

    // term lists

    @Override
    public void visit(TermListAddOp termListAddOp) {
        if (assignableTo(termListAddOp.getTerm().struct, Tab.intType) && assignableTo(termListAddOp.getTermList().struct, Tab.intType))
            termListAddOp.struct = Tab.intType;
        else {
            report_error("Nekompatibilni tipovi za AddOp", termListAddOp);
            termListAddOp.struct = Tab.noType;
        }
    }

    @Override
    public void visit(TermListNoAddOp termListNoAddOp) {
        termListNoAddOp.struct = termListNoAddOp.getTerm().struct;
    }

    // expressions

    @Override
    public void visit(ArithmeticExpression arithmeticExpression) {
        arithmeticExpression.struct = arithmeticExpression.getTermList().struct;
    }

    @Override
    public void visit(ExpressionArithmetic expressionArithmetic) {
        expressionArithmetic.struct = expressionArithmetic.getArithmeticExpression().struct;
    }

    @Override
    public void visit(ExpressionTernary expressionTernary) {
        Struct t1 = expressionTernary.getExpression().struct;
        Struct t2 = expressionTernary.getExpression1().struct;
        if (t1.equals(t2))
            expressionTernary.struct = t1;
        else {
            report_error("Nepoklapanje tipova grana ternarnog operatora", expressionTernary);
            expressionTernary.struct = Tab.noType;
        }
    }

    // designators

    private Obj common_designatorField(String name, Obj d) {
        if (d.getKind() == Obj.Meth)
            return null;
        for (Obj obj: d.getType().getMembers()) {
            if (obj.getName().equals(name))
                return obj;
        }
        return null;
    }

    @Override
    public void visit(DesignatorFieldVar designatorFieldVar) {
        Obj prev = designatorFieldVar.getDesignator().obj, field;
        String field_name = designatorFieldVar.getI2();
        if (prev.getName().equals("this") && struct_class != null) {
            field = Tab.currentScope().getOuter().findSymbol(field_name);
            if (field == null) {
                for (Obj obj : struct_class.getElemType().getMembers()) {
                    if (obj.getName().equals(field_name)) {
                        field = obj;
                        break;
                    }
                }
            }
        }
        else
            field = common_designatorField(field_name, prev);
        if (field != null)
            designatorFieldVar.obj = field;
        else {
            report_error("Nepostojece polje: " + field_name, designatorFieldVar);
            designatorFieldVar.obj = Tab.noObj;
        }
    }

    @Override
    public void visit(DesignatorFieldArray designatorFieldArray) {
        Obj obj = designatorFieldArray.getFieldArrayName().obj;
        if (obj != Tab.noObj) {
            if (!assignableTo(designatorFieldArray.getExpression().struct, Tab.intType)) {
                report_error("Indeksiranje ne-int tipom", designatorFieldArray);
                designatorFieldArray.obj = Tab.noObj;
            }
            else
                designatorFieldArray.obj = new Obj(Obj.Elem, obj.getName() + "[]", obj.getType().getElemType());
        }
        else
            designatorFieldArray.obj = Tab.noObj;
    }

    @Override
    public void visit(FieldArrayName fieldArrayName) {
        Obj field = common_designatorField(fieldArrayName.getI2(), fieldArrayName.getDesignator().obj);
        if (field != null && field.getType().getKind() == Struct.Array)
            fieldArrayName.obj = field;
        else {
            report_error("Nepostojece polje nizovskog tipa: " + fieldArrayName.getI2(), fieldArrayName);
            fieldArrayName.obj = Tab.noObj;
        }
    }

    @Override
    public void visit(DesignatorLength designatorLength) {
        Obj obj = designatorLength.getDesignator().obj;
        if (obj.getType().getKind() != Struct.Array) {
            report_error("Pristup length polju ne-nizovske promenljive", designatorLength);
            designatorLength.obj = Tab.noObj;
        }
        else
            designatorLength.obj = new Obj(Obj.Con, obj.getName() + ".length", Tab.intType);
    }

    @Override
    public void visit(DesignatorEndVar designatorEndVar) {
        String name = designatorEndVar.getI1();
        Obj obj = null;
        if (struct_class != null) {
            obj = Tab.currentScope().findSymbol(name);
            if (obj == null)
                obj = Tab.currentScope().getOuter().findSymbol(name);
            if (obj == null) {
                for (Obj x : struct_class.getElemType().getMembers()) {
                    if (x.getName().equals(name)) {
                        obj = x;
                        break;
                    }
                }
            }
        }
        if (obj == null)
            obj = Tab.find(name);
        if (obj == Tab.noObj || (obj.getKind() != Obj.Var && obj.getKind() != Obj.Con &&
                obj.getKind() != Obj.Meth && obj.getKind() != Obj.Fld && obj.getType().getKind() != Struct.Enum)) {
            report_error("Neadekvatan identifikator " + designatorEndVar.getI1(), designatorEndVar);
            designatorEndVar.obj = Tab.noObj;
        }
        else
            designatorEndVar.obj = obj;
    }

    @Override
    public void visit(DesignatorEndArray designatorEndArray) {
        Obj obj = designatorEndArray.getEndArrayName().obj;
        if (obj == Tab.noObj)
            designatorEndArray.obj = Tab.noObj;
        else if (!assignableTo(designatorEndArray.getExpression().struct, Tab.intType)) {
            report_error("Indeksiranje ne-int tipom", designatorEndArray);
            designatorEndArray.obj = Tab.noObj;
        }
        else
            designatorEndArray.obj = new Obj(Obj.Elem, obj.getName() + "[]", obj.getType().getElemType());
    }

    @Override
    public void visit(EndArrayName endArrayName) {
        endArrayName.obj = Tab.find(endArrayName.getI1());
        if (endArrayName.obj == Tab.noObj || endArrayName.obj.getType().getKind() != Struct.Array)
            report_error("Nepostojeci niz: " + endArrayName.getI1(), endArrayName);
    }

    // designator statements

    @Override
    public void visit(DesignatorStatementAssign designatorStatementAssign) {
        Obj d = designatorStatementAssign.getDesignator().obj;
        if (d.getKind() != Obj.Var && d.getKind() != Obj.Elem && d.getKind() != Obj.Fld)
            report_error("Nevalidan identifikator sa leve strane", designatorStatementAssign);
        else if (!assignableTo(designatorStatementAssign.getExpression().struct, d.getType()))
            report_error("Nekompatibilni tipovi za dodelu vrednosti", designatorStatementAssign);
        else
            common_VarFldElemCon(designatorStatementAssign, d);
    }

    private void common_designatorStatementFunctionCall(SyntaxNode sn, Designator designator) {
        Obj d = designator.obj;
        if (d.getKind() != Obj.Meth)
            report_error("Nepostojeca metoda " + d.getName(), sn);
        else {
            int diff = common_functionCall(sn, designator);
            ArrayList<Struct> curr = actual_param_types.pop();
            if (curr.size() != d.getLevel() - (diff - 1)) {
                report_error("Nepoklapanje broja argumenata poziva metode " + d.getName(), sn);
                return;
            }
            for (int i = 0; i < curr.size(); i++) {
                if (!assignableTo(curr.get(i), findParamByPos(d, i+diff).getType()))
                    report_error("Nepoklapanje tipova argumenata poziva metode " + d.getName(), sn);
            }
        }
    }

    @Override
    public void visit(DesignatorStatementFunctionCallParams designatorStatementFunctionCallParams) {
        common_designatorStatementFunctionCall(
                designatorStatementFunctionCallParams,
                designatorStatementFunctionCallParams.getDesignator()
        );
    }

    @Override
    public void visit(DesignatorStatementFunctionCallNoParams designatorStatementFunctionCallNoParams) {
        actual_param_types.push(new ArrayList<>());
        common_designatorStatementFunctionCall(
                designatorStatementFunctionCallNoParams,
                designatorStatementFunctionCallNoParams.getDesignator()
        );
    }

    private void common_designatorStatementIncDec(SyntaxNode sn, Obj d, String op) {
        if (d.getKind() != Obj.Var && d.getKind() != Obj.Elem && d.getKind() != Obj.Fld)
            report_error("Nevalidan identifikator sa leve strane", sn);
        else if (d.getType() != Tab.intType)
            report_error(op + " ne-int tipa", sn);
        else
            common_VarFldElemCon(sn, d);
    }

    @Override
    public void visit(DesignatorStatementIncrement designatorStatementIncrement) {
        common_designatorStatementIncDec(
                designatorStatementIncrement,
                designatorStatementIncrement.getDesignator().obj,
                "Intekemtiranje"
        );
    }

    @Override
    public void visit(DesignatorStatementDecrement designatorStatementDecrement) {
        common_designatorStatementIncDec(
                designatorStatementDecrement,
                designatorStatementDecrement.getDesignator().obj,
                "Dekrementiranje"
        );
    }

    // statements

    @Override
    public void visit(StatementRead statementRead) {
        Obj d = statementRead.getDesignator().obj;
        if (d.getKind() != Obj.Var && d.getKind() != Obj.Elem && d.getKind() != Obj.Fld)
            report_error("Nevalidan identifikator za read", statementRead);
        else if (d.getType() != Tab.intType && d.getType() != Tab.charType && d.getType() != boolType)
            report_error("Argument read-a mora biti int, char ili bool", statementRead);
        else
            common_VarFldElemCon(statementRead, d);
    }

    @Override
    public void visit(StatementPrintNoNumConst statementPrintNoNumConst) {
        Struct s = statementPrintNoNumConst.getExpression().struct;
        if (!assignableTo(s, Tab.intType) && s != Tab.charType && s != boolType)
            report_error("Argument print-a mora biti int, char ili bool", statementPrintNoNumConst);
    }

    @Override
    public void visit(StatementPrintNumConst statementPrintNumConst) {
        Struct s = statementPrintNumConst.getExpression().struct;
        if (!assignableTo(s, Tab.intType) && s != Tab.charType && s != boolType)
            report_error("Argument print-a mora biti int, char ili bool", statementPrintNumConst);
    }

    private int for_count = 0;

    @Override
    public void visit(ForStart forStart) {
        for_count++;
    }

    @Override
    public void visit(StatementFor statementFor) {
        for_count--;
    }

    private final Stack<HashSet<Integer>> case_values = new Stack<>();

    @Override
    public void visit(SwitchStart switchStart) {
        case_values.push(new HashSet<>());
    }

    @Override
    public void visit(StatementSwitch statementSwitch) {
        if (!assignableTo(statementSwitch.getExpression().struct, Tab.intType))
            report_error("Zaglavlje switch-a mora sadrzati int tip", statementSwitch);
        case_values.pop();
    }

    private int case_count = 0;

    @Override
    public void visit(CaseStart caseStart) {
        int n = caseStart.getN1();
        if (case_values.peek().contains(n))
            report_error("Ponovljena vrednost konstante u case-u: " + n, caseStart);
        else
            case_values.peek().add(n);
        case_count++;
    }

    @Override
    public void visit(Case _case) {
        case_count--;
    }

    @Override
    public void visit(StatementBreak statementBreak) {
        if (for_count == 0 && case_count == 0)
            report_error("Break bez okruzujuceg for-a ili case-a", statementBreak);
    }

    @Override
    public void visit(StatementContinue statementContinue) {
        if (for_count == 0)
            report_error("Continue bez okruzujuceg for-a", statementContinue);
    }

    private void common_returnDeclaration(SyntaxNode sn, Struct expression) {
        if (assignableTo(expression, obj_method.getType()))
            return_statement = true;
        else
            report_error("Nekompatibilan tip return iskaza", sn);
    }

    @Override
    public void visit(StatementReturnExpression statementReturnExpression) {
        common_returnDeclaration(
                statementReturnExpression,
                statementReturnExpression.getExpression().struct
        );
    }

    @Override
    public void visit(StatementReturnNoExpression statementReturnNoExpression) {
        common_returnDeclaration(
                statementReturnNoExpression,
                Tab.noType
        );
    }

    // condition factor

    @Override
    public void visit(ConditionFactorNoRelOp conditionFactorNoRelOp) {
        if (conditionFactorNoRelOp.getArithmeticExpression().struct != boolType)
            report_error("Uslovni izraz mora biti bool tipa", conditionFactorNoRelOp);
    }

    @Override
    public void visit(ConditionFactorRelOp conditionFactorRelOp) {
        Struct e1 = conditionFactorRelOp.getArithmeticExpression().struct;
        Struct e2 = conditionFactorRelOp.getArithmeticExpression1().struct;
        RelOp relOp = conditionFactorRelOp.getRelOp();
        if (e1 == boolType || e2 == boolType)
            report_error("Bool tipovi se ne mogu porediti", conditionFactorRelOp);
        else if (!e1.compatibleWith(e2))
            report_error("Nekompatibilni tipovi za poredjenje", conditionFactorRelOp);
        else if (e1.isRefType() && e1.isRefType() && !(relOp instanceof RelOpEqual || relOp instanceof RelOpNotEqual))
            report_error("Tipovi referenci se mogu porediti samo na jednakost i nejednakost", conditionFactorRelOp);
    }

    // actual params

    private final Stack<ArrayList<Struct>> actual_param_types = new Stack<>();

    @Override
    public void visit(ActualParamsListSingle actualParamsListSingle) {
        actual_param_types.push(new ArrayList<>());
        actual_param_types.peek().add(actualParamsListSingle.getExpression().struct);
    }

    @Override
    public void visit(ActualParamsListMultiple actualParamsListMultiple) {
        actual_param_types.peek().add(actualParamsListMultiple.getExpression().struct);
    }
}
