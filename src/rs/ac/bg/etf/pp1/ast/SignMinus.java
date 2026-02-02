// generated with ast extension for cup
// version 0.8
// 1/1/2026 22:44:20


package rs.ac.bg.etf.pp1.ast;

public class SignMinus extends Sign {

    public SignMinus () {
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
        buffer.append("SignMinus(\n");

        buffer.append(tab);
        buffer.append(") [SignMinus]");
        return buffer.toString();
    }
}
