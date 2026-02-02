// generated with ast extension for cup
// version 0.8
// 1/1/2026 22:44:20


package rs.ac.bg.etf.pp1.ast;

public class MethodHeaderVoidNoParams extends MethodHeader {

    private MethodName MethodName;

    public MethodHeaderVoidNoParams (MethodName MethodName) {
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodName!=null) MethodName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodHeaderVoidNoParams(\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodHeaderVoidNoParams]");
        return buffer.toString();
    }
}
