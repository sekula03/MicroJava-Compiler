package rs.ac.bg.etf.pp1;

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
    private boolean main = false;


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

    // globalne konstante

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

    // globalne promenljive

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

    // globalne metode

    private void common_globalMethodHeader(SyntaxNode sn, String name, Struct type) {
        Obj meth = Tab.find(name);
        if (meth != Tab.noObj)
            report_error("Visestruka definicija metode: " + name, sn);
        obj_method = Tab.insert(Obj.Meth, name, type);
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
        if (obj_method.getName().equals("main"))
            report_error("Nevalidan potpis medote main", globalMethodDeclarationParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(GlobalMethodDeclarationNoParams globalMethodDeclarationNoParams) {
        if (obj_method.getName().equals("main"))
            main = true;
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    // lokalne promenljive

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

    // formalni parametri

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

    // enumi

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

    // klase

    private static boolean assignableTo(Struct src, Struct dst) {
        if (src == null || dst == null) return false;
        if (src.compatibleWith(dst)) return true;
        if (src.getKind() != Struct.Class || dst.getKind() != Struct.Class) return false;
        while (src.getElemType() != null) {
            if (src.getElemType().equals(dst)) return true;
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
        common_className(classNameNoExtends, classNameNoExtends.getI1(), null, Struct.Class);
    }

    @Override
    public void visit(ClassNameExtends classNameExtends) {
        if (struct_type.getKind() != Struct.Class)
            report_error("Nepostojeca natklasa: " + struct_type, classNameExtends);
        common_className(classNameExtends, classNameExtends.getI1(), struct_type, Struct.Class);
        for (Obj o: struct_class.getElemType().getMembers()) {
            if (o.getKind() == Obj.Fld)
                Tab.insert(Obj.Fld, o.getName(), o.getType());
        }
    }

    @Override
    public void visit(AbstractClassNameNoExtends abstractClassNameNoExtends) {
        common_className(abstractClassNameNoExtends, abstractClassNameNoExtends.getI1(), null, Struct.Interface);
    }

    @Override
    public void visit(AbstractClassNameExtends abstractClassNameExtends) {
        if (struct_type.getKind() != Struct.Class)
            report_error("Nepostojeca natklasa: " + struct_type, abstractClassNameExtends);
        common_className(abstractClassNameExtends, abstractClassNameExtends.getI1(), struct_type, Struct.Interface);
        for (Obj o: struct_class.getElemType().getMembers()) {
            if (o.getKind() == Obj.Fld)
                Tab.insert(Obj.Fld, o.getName(), o.getType());
        }
    }

    private void common_classDeclaration() {
        if (struct_class.getElemType() == null) return;
        for (Obj superMember: struct_class.getElemType().getMembers()) {
            if (superMember.getKind() == Obj.Meth && Tab.currentScope().findSymbol(superMember.getName()) == null) {
                Obj new_meth = Tab.insert(Obj.Meth, superMember.getName(), superMember.getType());
                new_meth.setLevel(superMember.getLevel());
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

    // klasna polja

    private void common_field(SyntaxNode sn, String name, Struct type) {
        Obj fld = Tab.currentScope().findSymbol(name);
        if (fld != null) {
            report_error("Visestruka definicija polja: " + name, sn);
            return;
        }
        if (struct_class.getElemType() != null) {
            for (Obj sym: struct_class.getElemType().getMembers()) {
                if (sym.getName().equals(name)) {
                    report_error("Visestruka definicija polja: " + name, sn);
                    return;
                }
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

    // klasne metode

    private void common_methodHeader(SyntaxNode sn, String name, Struct type) {
        Obj meth = Tab.currentScope().findSymbol(name);
        if (meth != null)
            report_error("Visestruka definicija metode: " + name, sn);
        obj_method = Tab.insert(Obj.Meth,name, type);
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
        return null;
    }

    private void common_methodDeclaration(SyntaxNode sn) {
        if (struct_class.getElemType() == null) return;
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
                if (superP == null || currentP == null || !assignableTo(superP.getType(), currentP.getType()))
                    break;
            }
            return;
        }
        report_error("Pogresna redefinicija metode: " + obj_method.getName(), sn);
    }

    @Override
    public void visit(MethodDeclarationParams methodDeclarationParams) {
        common_methodDeclaration(methodDeclarationParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(MethodDeclarationNoParams methodDeclarationNoParams) {
        common_methodDeclaration(methodDeclarationNoParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(AbstractMethodDeclarationParams abstractMethodDeclarationParams) {
        common_methodDeclaration(abstractMethodDeclarationParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(AbstractMethodDeclarationNoParams abstractMethodDeclarationNoParams) {
        common_methodDeclaration(abstractMethodDeclarationNoParams);
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

}
