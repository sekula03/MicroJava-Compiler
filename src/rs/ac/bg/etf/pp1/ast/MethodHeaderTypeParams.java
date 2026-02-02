// generated with ast extension for cup
// version 0.8
// 1/1/2026 22:44:20


package rs.ac.bg.etf.pp1.ast;

public class MethodHeaderTypeParams extends MethodHeader {

    private Type Type;
    private MethodName MethodName;
    private FormalParamsList FormalParamsList;

    public MethodHeaderTypeParams (Type Type, MethodName MethodName, FormalParamsList FormalParamsList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.FormalParamsList=FormalParamsList;
        if(FormalParamsList!=null) FormalParamsList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public FormalParamsList getFormalParamsList() {
        return FormalParamsList;
    }

    public void setFormalParamsList(FormalParamsList FormalParamsList) {
        this.FormalParamsList=FormalParamsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(MethodName!=null) MethodName.accept(visitor);
        if(FormalParamsList!=null) FormalParamsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(FormalParamsList!=null) FormalParamsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(FormalParamsList!=null) FormalParamsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodHeaderTypeParams(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParamsList!=null)
            buffer.append(FormalParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodHeaderTypeParams]");
        return buffer.toString();
    }
}
