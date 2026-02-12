// generated with ast extension for cup
// version 0.8
// 12/1/2026 0:49:14


package rs.ac.bg.etf.pp1.ast;

public class ElseNotEmpty extends Else {

    private SkipElse SkipElse;
    private Statement Statement;

    public ElseNotEmpty (SkipElse SkipElse, Statement Statement) {
        this.SkipElse=SkipElse;
        if(SkipElse!=null) SkipElse.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public SkipElse getSkipElse() {
        return SkipElse;
    }

    public void setSkipElse(SkipElse SkipElse) {
        this.SkipElse=SkipElse;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SkipElse!=null) SkipElse.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SkipElse!=null) SkipElse.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SkipElse!=null) SkipElse.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ElseNotEmpty(\n");

        if(SkipElse!=null)
            buffer.append(SkipElse.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ElseNotEmpty]");
        return buffer.toString();
    }
}
