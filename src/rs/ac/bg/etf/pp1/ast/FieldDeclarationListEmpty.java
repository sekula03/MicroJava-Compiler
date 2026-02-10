// generated with ast extension for cup
// version 0.8
// 10/1/2026 12:56:38


package rs.ac.bg.etf.pp1.ast;

public class FieldDeclarationListEmpty extends FieldDeclarationList {

    public FieldDeclarationListEmpty () {
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
        buffer.append("FieldDeclarationListEmpty(\n");

        buffer.append(tab);
        buffer.append(") [FieldDeclarationListEmpty]");
        return buffer.toString();
    }
}
