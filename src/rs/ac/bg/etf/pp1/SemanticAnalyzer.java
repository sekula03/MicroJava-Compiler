package rs.ac.bg.etf.pp1;

import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.HashSet;

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
    private boolean main = false, return_statement;


    @Override
    public void visit(Program program) {
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
            struct_type = Tab.noType;
        }
        else
            struct_type = definedType.getType();
    }

    // global consts

    private void common_const(SyntaxNode sn, String name, Struct type, int value) {
        Obj con = Tab.find(name);
        if (con != Tab.noObj)
            report_error("Visestruka definicija konstante: " + name, sn);
        else if (!type.equals(struct_type))
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
        common_const(constBool, constBool.getI1(), Tab.find("bool").getType(), constBool.getB2() ? 1 : 0);
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

    private void common_globalMethodHeader(SyntaxNode sn, String name, Struct type) {
        Obj meth = Tab.find(name);
        if (meth != Tab.noObj)
            report_error("Visestruka definicija metode: " + name, sn);
        obj_method = Tab.insert(Obj.Meth, name, type);
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

    @Override
    public void visit(GlobalMethodDeclarationParams globalMethodDeclarationParams) {
        if (!return_statement && obj_method.getType() != Tab.noType)
            report_error("Nedostaje return iskaz", globalMethodDeclarationParams);
        if (obj_method.getName().equals("main"))
            report_error("Nevalidan potpis medote main", globalMethodDeclarationParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(GlobalMethodDeclarationNoParams globalMethodDeclarationNoParams) {
        if (!return_statement && obj_method.getType() != Tab.noType)
            report_error("Nedostaje return iskaz", globalMethodDeclarationNoParams);
        if (obj_method.getName().equals("main"))
            main = true;
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
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
            fp.setFpPos(obj_method.getLevel());
            obj_method.setLevel(obj_method.getLevel() + 1);
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
    private HashSet<Integer> enum_vals;

    @Override
    public void visit(EnumName enumName) {
        Obj en = Tab.find(enumName.getI1());
        if (en != Tab.noObj)
            report_error("Visestruka definicija nabrajanja: " + enumName.getI1(), enumName);
        struct_class = new Struct(Struct.Enum);
        next_enum = 0;
        enum_vals = new HashSet<>();
        Tab.insert(Obj.Type, enumName.getI1(), struct_class);
        Tab.openScope();
    }

    @Override
    public void visit(EnumDeclaration enumDeclaration) {
        Tab.chainLocalSymbols(struct_class);
        Tab.closeScope();
        enum_vals = null;
    }

    private void common_enumConst(SyntaxNode sn, String name, int value) {
        Obj en = Tab.currentScope().findSymbol(name);
        if (en != null)
            report_error("Visestruka definicija konstante nabrajanja: " + name, sn);
        else if (enum_vals.contains(value))
            report_error("Ponovljena vrednost konstante nabrajanja: " + name + " = " + value, sn);
        else {
            en = Tab.insert(Obj.Con, name, Tab.intType);
            en.setAdr(value);
            enum_vals.add(value);
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
        if (src == null || dst == null || src == Tab.noType || dst == Tab.noType) return false;
        if (src.compatibleWith(dst)) return true;
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
    }

    @Override
    public void visit(ClassNameExtends classNameExtends) {
        if (struct_type.getKind() != Struct.Class && struct_type.getKind() != Struct.Interface)
            report_error("Nepostojeca natklasa: " + struct_type, classNameExtends);
        common_className(classNameExtends, classNameExtends.getI1(), struct_type, Struct.Class);
        for (Obj o: struct_class.getElemType().getMembers()) {
            if (o.getKind() == Obj.Fld)
                Tab.insert(Obj.Fld, o.getName(), o.getType());
        }
    }

    @Override
    public void visit(AbstractClassNameNoExtends abstractClassNameNoExtends) {
        common_className(abstractClassNameNoExtends, abstractClassNameNoExtends.getI1(), Tab.noType, Struct.Interface);
    }

    @Override
    public void visit(AbstractClassNameExtends abstractClassNameExtends) {
        if (struct_type.getKind() != Struct.Class && struct_type.getKind() != Struct.Interface)
            report_error("Nepostojeca natklasa: " + struct_type, abstractClassNameExtends);
        common_className(abstractClassNameExtends, abstractClassNameExtends.getI1(), struct_type, Struct.Interface);
        for (Obj o: struct_class.getElemType().getMembers()) {
            if (o.getKind() == Obj.Fld)
                Tab.insert(Obj.Fld, o.getName(), o.getType());
        }
    }

    private void common_classDeclaration() {
        for (Obj superMember: struct_class.getElemType().getMembers()) {
            if (superMember.getKind() == Obj.Meth && Tab.currentScope().findSymbol(superMember.getName()) == null) {
                if (struct_class.getKind() == Struct.Class && superMember.getAdr() == Obj.NO_VALUE)
                    report_error("Neredefinisana apstraktna metoda: " + superMember.getName(), null);
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
    }

    @Override
    public void visit(ClassDeclarationMethodList classDeclarationMethodList) {
        common_classDeclaration();
        Tab.chainLocalSymbols(struct_class);
        Tab.closeScope();
    }

    @Override
    public void visit(ClassDeclarationNoMethodList classDeclarationNoMethodList) {
        common_classDeclaration();
        Tab.chainLocalSymbols(struct_class);
        Tab.closeScope();
    }

    @Override
    public void visit(AbstractClassDeclarationMethodList abstractClassDeclarationMethodList) {
        common_classDeclaration();
        Tab.chainLocalSymbols(struct_class);
        Tab.closeScope();
    }

    @Override
    public void visit(AbstractClassDeclarationNoMethodList abstractClassDeclarationNoMethodList) {
        common_classDeclaration();
        Tab.chainLocalSymbols(struct_class);
        Tab.closeScope();
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

    private void common_methodHeader(SyntaxNode sn, String name, Struct type) {
        Obj meth = Tab.currentScope().findSymbol(name);
        if (meth != null)
            report_error("Visestruka definicija metode: " + name, sn);
        obj_method = Tab.insert(Obj.Meth, name, type);
        return_statement = false;
        Tab.openScope();
        Obj fp = Tab.insert(Obj.Var, "this", struct_class);
        obj_method.setLevel(1);
        fp.setFpPos(0);
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
        Obj super_method = null;
        for (Obj meth: struct_class.getElemType().getMembers()) {
            if (meth.getKind() == Obj.Meth && meth.getName().equals(obj_method.getName())) {
                super_method = meth;
                break;
            }
        }
        if (super_method == null) return;
        if (assignableTo(obj_method.getType(), super_method.getType()) && super_method.getLevel() == obj_method.getLevel()) {
            for (int i = 1; i <= obj_method.getLevel(); i++) {
                Obj currentP = findParamByPos(obj_method, i);
                Obj superP = findParamByPos(super_method, i);
                if (!assignableTo(superP.getType(), currentP.getType()))
                    break;
            }
            return;
        }
        report_error("Pogresna redefinicija metode: " + obj_method.getName(), sn);
    }

    @Override
    public void visit(MethodDeclarationParams methodDeclarationParams) {
        if (!return_statement && obj_method.getType() != Tab.noType)
            report_error("Nedostaje return iskaz", methodDeclarationParams);
        else
            common_methodDeclaration(methodDeclarationParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(MethodDeclarationNoParams methodDeclarationNoParams) {
        if (!return_statement && obj_method.getType() != Tab.noType)
            report_error("Nedostaje return iskaz", methodDeclarationNoParams);
        else
            common_methodDeclaration(methodDeclarationNoParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(AbstractMethodDeclarationParams abstractMethodDeclarationParams) {
        common_methodDeclaration(abstractMethodDeclarationParams);
        obj_method.setAdr(Obj.NO_VALUE);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(AbstractMethodDeclarationNoParams abstractMethodDeclarationNoParams) {
        common_methodDeclaration(abstractMethodDeclarationNoParams);
        obj_method.setAdr(Obj.NO_VALUE);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    // factors

    private Struct common_factor(SyntaxNode sn, Sign sign, Struct type) {
        if (sign instanceof SignEmpty)
            return type;
        if (type == Tab.intType)
            return Tab.intType;
        report_error("Negiranje ne-int tipa", sn);
        return Tab.noType;
    }

    @Override
    public void visit(FactorVariable factorVariable) {
        int kind = factorVariable.getDesignator().obj.getKind();
        if (kind != Obj.Var && kind != Obj.Fld && kind != Obj.Con && kind != Obj.Elem) {
            report_error("Nepostojeca promenljiva/konstanta " + factorVariable.getDesignator().obj.getName(), factorVariable);
            factorVariable.struct = Tab.noType;
        }
        else
            factorVariable.struct = common_factor(
                    factorVariable,
                    factorVariable.getSign(),
                    factorVariable.getDesignator().obj.getType()
            );
    }

    @Override
    public void visit(FactorFunctionCallWithArgs factorFunctionCallWithArgs) {
        if (factorFunctionCallWithArgs.getDesignator().obj.getKind() != Obj.Meth) {
            report_error("Nepostojeca metoda " + factorFunctionCallWithArgs.getDesignator().obj.getName(), factorFunctionCallWithArgs);
            factorFunctionCallWithArgs.struct = Tab.noType;
        }
        else
            factorFunctionCallWithArgs.struct = common_factor(
                    factorFunctionCallWithArgs,
                    factorFunctionCallWithArgs.getSign(),
                    factorFunctionCallWithArgs.getDesignator().obj.getType()
            );
    }

    @Override
    public void visit(FactorFunctionCallNoArgs factorFunctionCallNoArgs) {
        if (factorFunctionCallNoArgs.getDesignator().obj.getKind() != Obj.Meth) {
            report_error("Nepostojeca metoda " + factorFunctionCallNoArgs.getDesignator().obj.getName(), factorFunctionCallNoArgs);
            factorFunctionCallNoArgs.struct = Tab.noType;
        }
        else
            factorFunctionCallNoArgs.struct = common_factor(
                    factorFunctionCallNoArgs,
                    factorFunctionCallNoArgs.getSign(),
                    factorFunctionCallNoArgs.getDesignator().obj.getType()
            );
    }

    @Override
    public void visit(FactorNested factorNested) {
        factorNested.struct = common_factor(
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
        factorBoolConst.struct = Tab.find("bool").getType();
    }

    @Override
    public void visit(FactorCharConst factorCharConst) {
        factorCharConst.struct = Tab.charType;
    }

    @Override
    public void visit(FactorNewVar factorNewVar) {
        if (struct_type.getKind() == Struct.Class)
            factorNewVar.struct = struct_type;
        else  {
            report_error("Koriscen ne-klasni tip pri pozivu new", factorNewVar);
            factorNewVar.struct = Tab.noType;
        }
    }

    @Override
    public void visit(FactorNewArray factorNewArray) {
        if (factorNewArray.getExpression().struct == Tab.intType)
            factorNewArray.struct = new Struct(Struct.Array, struct_type);
        else {
            report_error("Indeksiranje ne-int tipom", factorNewArray);
            factorNewArray.struct = Tab.noType;
        }
    }

    // factor lists

    @Override
    public void visit(FactorListMulOp factorListMulOp) {
        if (factorListMulOp.getFactor().struct == Tab.intType && factorListMulOp.getFactorList().struct == Tab.intType)
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
        if (termListAddOp.getTerm().struct == Tab.intType && termListAddOp.getTermList().struct == Tab.intType)
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
        Obj field = common_designatorField(designatorFieldVar.getI2(), designatorFieldVar.getDesignator().obj);
        if (field != null)
            designatorFieldVar.obj = field;
        else {
            report_error("Nepostojece polje: " + designatorFieldVar.getI2(), designatorFieldVar);
            designatorFieldVar.obj = Tab.noObj;
        }
    }

    @Override
    public void visit(DesignatorFieldArray designatorFieldArray) {
        Obj field = common_designatorField(designatorFieldArray.getI2(), designatorFieldArray.getDesignator().obj);
        if (field != null && field.getKind() == Obj.Fld && field.getType().getKind() == Struct.Array) {
            if (designatorFieldArray.getExpression().struct != Tab.intType) {
                report_error("Indeksiranje ne-int tipom", designatorFieldArray);
                designatorFieldArray.obj = Tab.noObj;
            }
            else
                designatorFieldArray.obj = new Obj(Obj.Elem, field.getName() + "[]", field.getType().getElemType());
        }
        else {
            report_error("Nepostojece polje nizovskog tipa: " + designatorFieldArray.getI2(), designatorFieldArray);
            designatorFieldArray.obj = Tab.noObj;
        }
    }

    @Override
    public void visit(DesignatorLength designatorLength) {
        Obj obj = designatorLength.getDesignator().obj;
        if (obj.getKind() == Obj.Meth || obj.getType().getKind() != Struct.Array) {
            report_error("Pristup length polju ne-nizovske promenljive", designatorLength);
            designatorLength.obj = Tab.noObj;
        }
        else
            designatorLength.obj = new Obj(Obj.Con, obj.getName() + ".length", Tab.intType);
    }

    @Override
    public void visit(DesignatorEndVar designatorEndVar) {
        Obj obj = Tab.find(designatorEndVar.getI1());
        if (obj == Tab.noObj || (obj.getKind() != Obj.Var &&
                obj.getKind() != Obj.Con && obj.getKind() != Obj.Meth && obj.getType().getKind() != Struct.Enum)) {
            report_error("Neadekvatan identifikator " + designatorEndVar.getI1(), designatorEndVar);
            designatorEndVar.obj = Tab.noObj;
        }
        else
            designatorEndVar.obj = obj;
    }

    @Override
    public void visit(DesignatorEndArray designatorEndArray) {
        Obj obj = Tab.find(designatorEndArray.getI1());
        if (obj == Tab.noObj || obj.getKind() != Obj.Var || obj.getType().getKind() != Struct.Array) {
            report_error("Nepostojeci niz " + designatorEndArray.getI1(), designatorEndArray);
            designatorEndArray.obj = Tab.noObj;
        }
        else if (designatorEndArray.getExpression().struct != Tab.intType) {
            report_error("Indeksiranje ne-int tipom", designatorEndArray);
            designatorEndArray.obj = Tab.noObj;
        }
        else
            designatorEndArray.obj = new Obj(Obj.Elem, obj.getName() + "[]", obj.getType().getElemType());
    }

    // designator statements

    @Override
    public void visit(DesignatorStatementAssign designatorStatementAssign) {
        Struct e = designatorStatementAssign.getExpression().struct;
        Obj d = designatorStatementAssign.getDesignator().obj;
        if (d.getKind() != Obj.Var && d.getKind() != Obj.Elem && d.getKind() != Obj.Fld)
            report_error("Nevalidan identifikator sa leve strane", designatorStatementAssign);
        else if (!assignableTo(e, d.getType()))
            report_error("Nekompatibilni tipovi za dodelu vrednosti", designatorStatementAssign);
    }

    private void common_designatorStatementFunctionCall(SyntaxNode sn, Obj d) {
        if (d.getKind() != Obj.Meth)
            report_error("Nepostojeca metoda " + d.getName(), sn);
    }

    @Override
    public void visit(DesignatorStatementFunctionCallParams designatorStatementFunctionCallParams) {
        common_designatorStatementFunctionCall(
                designatorStatementFunctionCallParams,
                designatorStatementFunctionCallParams.getDesignator().obj
        );
    }

    @Override
    public void visit(DesignatorStatementFunctionCallNoParams designatorStatementFunctionCallNoParams) {
        common_designatorStatementFunctionCall(
                designatorStatementFunctionCallNoParams,
                designatorStatementFunctionCallNoParams.getDesignator().obj
        );
    }

    private void common_designatorStatementIncDec(SyntaxNode sn, Obj d, String op) {
        if (d.getKind() != Obj.Var && d.getKind() != Obj.Elem && d.getKind() != Obj.Fld)
            report_error("Nevalidan identifikator sa leve strane", sn);
        else if (d.getType() != Tab.intType) {
            report_error(op + " ne-int tipa", sn);
        }
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
        else if (d.getType() != Tab.intType && d.getType() != Tab.charType && d.getType() != Tab.find("bool").getType()) {
            report_error("Argument read-a mora biti int, char ili bool", statementRead);
        }
    }

    // prints

    private void common_printDeclaration(SyntaxNode sn, Struct s) {
        if (s != Tab.intType && s != Tab.charType && s != Tab.find("bool").getType())
            report_error("Argument print-a mora biti int, char ili bool", sn);
    }

    @Override
    public void visit(PrintDeclarationNumConst printDeclarationNumConst) {
        common_printDeclaration(
                printDeclarationNumConst,
                printDeclarationNumConst.getExpression().struct
        );
    }

    @Override
    public void visit(PrintDeclarationNoNumConst printDeclarationNoNumConst) {
        common_printDeclaration(
                printDeclarationNoNumConst,
                printDeclarationNoNumConst.getExpression().struct
        );
    }

    // returns

    private void common_returnDeclaration(SyntaxNode sn, Struct expression) {
        if ((expression == Tab.noType && obj_method.getType() == Tab.noType) || assignableTo(expression, obj_method.getType()))
            return_statement = true;
        else
            report_error("Nekompatibilan tip return iskaza", sn);
    }

    @Override
    public void visit(ReturnDeclarationExpression returnDeclarationExpression) {
        common_returnDeclaration(
                returnDeclarationExpression,
                returnDeclarationExpression.getExpression().struct
        );
    }

    @Override
    public void visit(ReturnDeclarationNoExpression returnDeclarationNoExpression) {
        common_returnDeclaration(
                returnDeclarationNoExpression,
                Tab.noType
        );
    }

}
