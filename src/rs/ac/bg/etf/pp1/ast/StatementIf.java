// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class StatementIf extends Statement {

    private IfDeclaration IfDeclaration;

    public StatementIf (IfDeclaration IfDeclaration) {
        this.IfDeclaration=IfDeclaration;
        if(IfDeclaration!=null) IfDeclaration.setParent(this);
    }

    public IfDeclaration getIfDeclaration() {
        return IfDeclaration;
    }

    public void setIfDeclaration(IfDeclaration IfDeclaration) {
        this.IfDeclaration=IfDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfDeclaration!=null) IfDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfDeclaration!=null) IfDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfDeclaration!=null) IfDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementIf(\n");

        if(IfDeclaration!=null)
            buffer.append(IfDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementIf]");
        return buffer.toString();
    }
}
