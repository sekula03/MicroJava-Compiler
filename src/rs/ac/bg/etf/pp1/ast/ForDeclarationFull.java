// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class ForDeclarationFull extends ForDeclaration {

    private DesignatorStatement DesignatorStatement;
    private Condition Condition;
    private DesignatorStatement DesignatorStatement1;
    private Statement Statement;

    public ForDeclarationFull (DesignatorStatement DesignatorStatement, Condition Condition, DesignatorStatement DesignatorStatement1, Statement Statement) {
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.DesignatorStatement1=DesignatorStatement1;
        if(DesignatorStatement1!=null) DesignatorStatement1.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public DesignatorStatement getDesignatorStatement1() {
        return DesignatorStatement1;
    }

    public void setDesignatorStatement1(DesignatorStatement DesignatorStatement1) {
        this.DesignatorStatement1=DesignatorStatement1;
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
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(DesignatorStatement1!=null) DesignatorStatement1.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(DesignatorStatement1!=null) DesignatorStatement1.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(DesignatorStatement1!=null) DesignatorStatement1.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForDeclarationFull(\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement1!=null)
            buffer.append(DesignatorStatement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForDeclarationFull]");
        return buffer.toString();
    }
}
