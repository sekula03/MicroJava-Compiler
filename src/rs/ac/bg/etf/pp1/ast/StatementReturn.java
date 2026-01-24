// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class StatementReturn extends Statement {

    private ReturnDeclaration ReturnDeclaration;

    public StatementReturn (ReturnDeclaration ReturnDeclaration) {
        this.ReturnDeclaration=ReturnDeclaration;
        if(ReturnDeclaration!=null) ReturnDeclaration.setParent(this);
    }

    public ReturnDeclaration getReturnDeclaration() {
        return ReturnDeclaration;
    }

    public void setReturnDeclaration(ReturnDeclaration ReturnDeclaration) {
        this.ReturnDeclaration=ReturnDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnDeclaration!=null) ReturnDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnDeclaration!=null) ReturnDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnDeclaration!=null) ReturnDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementReturn(\n");

        if(ReturnDeclaration!=null)
            buffer.append(ReturnDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementReturn]");
        return buffer.toString();
    }
}
