// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class MethodHeaderVoid extends MethodHeader {

    private String I1;
    private FormalParamsList FormalParamsList;

    public MethodHeaderVoid (String I1, FormalParamsList FormalParamsList) {
        this.I1=I1;
        this.FormalParamsList=FormalParamsList;
        if(FormalParamsList!=null) FormalParamsList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
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
        if(FormalParamsList!=null) FormalParamsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParamsList!=null) FormalParamsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParamsList!=null) FormalParamsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodHeaderVoid(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(FormalParamsList!=null)
            buffer.append(FormalParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodHeaderVoid]");
        return buffer.toString();
    }
}
