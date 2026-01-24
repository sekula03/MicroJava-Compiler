// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class StatementFor extends Statement {

    private ForDeclaration ForDeclaration;

    public StatementFor (ForDeclaration ForDeclaration) {
        this.ForDeclaration=ForDeclaration;
        if(ForDeclaration!=null) ForDeclaration.setParent(this);
    }

    public ForDeclaration getForDeclaration() {
        return ForDeclaration;
    }

    public void setForDeclaration(ForDeclaration ForDeclaration) {
        this.ForDeclaration=ForDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForDeclaration!=null) ForDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDeclaration!=null) ForDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDeclaration!=null) ForDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementFor(\n");

        if(ForDeclaration!=null)
            buffer.append(ForDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementFor]");
        return buffer.toString();
    }
}
