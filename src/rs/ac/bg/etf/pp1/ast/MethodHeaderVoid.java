// generated with ast extension for cup
// version 0.8
// 6/1/2026 23:19:47


package rs.ac.bg.etf.pp1.ast;

public class MethodHeaderVoid extends MethodHeader {

    private String I1;

    public MethodHeaderVoid (String I1) {
        this.I1=I1;
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodHeaderVoid(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodHeaderVoid]");
        return buffer.toString();
    }
}
