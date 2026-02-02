package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

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
    private Struct struct_type;
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

    @Override
    public void visit(ConstNum constNum) {
        Obj con = Tab.find(constNum.getI1());
        if (con != Tab.noObj)
            report_error("Visestruka definicija konstante: " + constNum.getI1(), constNum);
        else if (!Tab.intType.assignableTo(struct_type))
            report_error("Nekompatibilna vrednost konstante: " + constNum.getI1(), constNum);
        else {
            con = Tab.insert(Obj.Con, constNum.getI1(), struct_type);
            con.setAdr(constNum.getN2());
        }
    }

    @Override
    public void visit(ConstChar constChar) {
        Obj con = Tab.find(constChar.getI1());
        if (con != Tab.noObj)
            report_error("Visestruka definicija konstante: " + constChar.getI1(), constChar);
        else if (!Tab.charType.assignableTo(struct_type))
            report_error("Nekompatibilna vrednost konstante: " + constChar.getI1(), constChar);
        else{
            con = Tab.insert(Obj.Con, constChar.getI1(), struct_type);
            con.setAdr(constChar.getC2());
        }
    }

    @Override
    public void visit(ConstBool constBool) {
        Obj con = Tab.find(constBool.getI1());
        if (con != Tab.noObj)
            report_error("Visestruka definicija konstante: " + constBool.getI1(), constBool);
        else if (!Tab.find("bool").getType().assignableTo(struct_type))
            report_error("Nekompatibilna vrednost konstante: " + constBool.getI1(), constBool);
        else{
            con = Tab.insert(Obj.Con, constBool.getI1(), struct_type);
            con.setAdr(constBool.getB2() ? 1 : 0);
        }
    }

    // globalne promenljive

    @Override
    public void visit(GlobalVarSingle globalVarSingle) {
        Obj var = Tab.find(globalVarSingle.getI1());
        if (var != Tab.noObj)
            report_error("Visestruka definicija promenljive: " + globalVarSingle.getI1(), globalVarSingle);
        else
            Tab.insert(Obj.Var, globalVarSingle.getI1(), struct_type);
    }

    @Override
    public void visit(GlobalVarArray globalVarArray) {
        Obj var = Tab.find(globalVarArray.getI1());
        if (var != Tab.noObj)
            report_error("Visestruka definicija promenljive: " + globalVarArray.getI1(), globalVarArray);
        else
            Tab.insert(Obj.Var, globalVarArray.getI1(), new Struct(Struct.Array, struct_type));
    }

    // globalne metode

    @Override
    public void visit(GlobalMethodHeaderType globalMethodHeaderType) {
        Obj meth = Tab.find(globalMethodHeaderType.getI2());
        if (meth != Tab.noObj)
            report_error("Visestruka definicija metode: " + globalMethodHeaderType.getI2(), globalMethodHeaderType);
        else if (globalMethodHeaderType.getI2().equals("main"))
            report_error("Nevalidan potpis medote main", globalMethodHeaderType);
        obj_method = Tab.insert(Obj.Meth, globalMethodHeaderType.getI2(), struct_type);
        Tab.openScope();
    }

    @Override
    public void visit(GlobalMethodHeaderVoid globalMethodHeaderVoid) {
        Obj meth = Tab.find(globalMethodHeaderVoid.getI1());
        if (meth != Tab.noObj)
            report_error("Visestruka definicija metode: " + globalMethodHeaderVoid.getI1(), globalMethodHeaderVoid);
        obj_method = Tab.insert(Obj.Meth, globalMethodHeaderVoid.getI1(), Tab.noType);
        Tab.openScope();
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

    @Override
    public void visit(VarSingle varSingle) {
        Obj var = Tab.currentScope().findSymbol(varSingle.getI1());
        if (var != null)
            report_error("Visestruka definicija promenljive: " + varSingle.getI1(), varSingle);
        else
            Tab.insert(Obj.Var, varSingle.getI1(), struct_type);
    }

    @Override
    public void visit(VarArray varArray) {
        Obj var = Tab.currentScope().findSymbol(varArray.getI1());
        if (var != null)
            report_error("Visestruka definicija promenljive: " + varArray.getI1(), varArray);
        else
            Tab.insert(Obj.Var, varArray.getI1(), new Struct(Struct.Array, struct_type));
    }

    // formalni parametri

    @Override
    public void visit(FormalParamSingle formalParamSingle) {
        Obj fp = Tab.currentScope().findSymbol(formalParamSingle.getI2());
        if (fp != null)
            report_error("Visestruka definicija formalnog parametra: " + formalParamSingle.getI2(), formalParamSingle);
        else {
            fp = Tab.insert(Obj.Var, formalParamSingle.getI2(), struct_type);
            fp.setFpPos(1);
            obj_method.setLevel(obj_method.getLevel() + 1);
        }
    }

    @Override
    public void visit(FormalParamArray formalParamArray) {
        Obj fp = Tab.currentScope().findSymbol(formalParamArray.getI2());
        if (fp != null)
            report_error("Visestruka definicija formalnog parametra: " + formalParamArray.getI2(), formalParamArray);
        else {
            fp = Tab.insert(Obj.Var, formalParamArray.getI2(), new Struct(Struct.Array, struct_type));
            fp.setFpPos(1);
            obj_method.setLevel(obj_method.getLevel() + 1);
        }
    }

    // lokalne metode

    @Override
    public void visit(MethodHeaderType methodHeaderType) {
        Obj meth = Tab.currentScope().findSymbol(methodHeaderType.getI2());
        if (meth != null)
            report_error("Visestruka definicija metode: " + methodHeaderType.getI2(), methodHeaderType);
        obj_method = Tab.insert(Obj.Meth, methodHeaderType.getI2(), struct_type);
        Tab.openScope();
    }

    @Override
    public void visit(MethodHeaderVoid methodHeaderVoid) {
        Obj meth = Tab.currentScope().findSymbol(methodHeaderVoid.getI1());
        if (meth != null)
            report_error("Visestruka definicija metode: " + methodHeaderVoid.getI1(), methodHeaderVoid);
        obj_method = Tab.insert(Obj.Meth, methodHeaderVoid.getI1(), Tab.noType);
        Tab.openScope();
    }

    @Override
    public void visit(MethodDeclarationParams methodDeclarationParams) {
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }

    @Override
    public void visit(MethodDeclarationNoParams methodDeclarationNoParams) {
        Tab.chainLocalSymbols(obj_method);
        Tab.closeScope();
    }
}
