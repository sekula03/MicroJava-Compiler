// generated with ast extension for cup
// version 0.8
// 12/1/2026 2:52:55


package rs.ac.bg.etf.pp1.ast;

public class ForActEmpty extends ForAct {

    public ForActEmpty () {
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
        buffer.append("ForActEmpty(\n");

        buffer.append(tab);
        buffer.append(") [ForActEmpty]");
        return buffer.toString();
    }
}
