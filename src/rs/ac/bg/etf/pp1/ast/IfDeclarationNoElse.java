// generated with ast extension for cup
// version 0.8
// 8/1/2026 2:18:47


package rs.ac.bg.etf.pp1.ast;

public class IfDeclarationNoElse extends IfDeclaration {

    public IfDeclarationNoElse () {
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
        buffer.append("IfDeclarationNoElse(\n");

        buffer.append(tab);
        buffer.append(") [IfDeclarationNoElse]");
        return buffer.toString();
    }
}
