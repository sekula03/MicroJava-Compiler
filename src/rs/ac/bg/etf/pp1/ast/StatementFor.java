// generated with ast extension for cup
// version 0.8
// 10/1/2026 0:52:31


package rs.ac.bg.etf.pp1.ast;

public class StatementFor extends Statement {

    private ForStart ForStart;
    private ForInit ForInit;
    private ForCond ForCond;
    private ForAct ForAct;
    private Statement Statement;

    public StatementFor (ForStart ForStart, ForInit ForInit, ForCond ForCond, ForAct ForAct, Statement Statement) {
        this.ForStart=ForStart;
        if(ForStart!=null) ForStart.setParent(this);
        this.ForInit=ForInit;
        if(ForInit!=null) ForInit.setParent(this);
        this.ForCond=ForCond;
        if(ForCond!=null) ForCond.setParent(this);
        this.ForAct=ForAct;
        if(ForAct!=null) ForAct.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForStart getForStart() {
        return ForStart;
    }

    public void setForStart(ForStart ForStart) {
        this.ForStart=ForStart;
    }

    public ForInit getForInit() {
        return ForInit;
    }

    public void setForInit(ForInit ForInit) {
        this.ForInit=ForInit;
    }

    public ForCond getForCond() {
        return ForCond;
    }

    public void setForCond(ForCond ForCond) {
        this.ForCond=ForCond;
    }

    public ForAct getForAct() {
        return ForAct;
    }

    public void setForAct(ForAct ForAct) {
        this.ForAct=ForAct;
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
        if(ForStart!=null) ForStart.accept(visitor);
        if(ForInit!=null) ForInit.accept(visitor);
        if(ForCond!=null) ForCond.accept(visitor);
        if(ForAct!=null) ForAct.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForStart!=null) ForStart.traverseTopDown(visitor);
        if(ForInit!=null) ForInit.traverseTopDown(visitor);
        if(ForCond!=null) ForCond.traverseTopDown(visitor);
        if(ForAct!=null) ForAct.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForStart!=null) ForStart.traverseBottomUp(visitor);
        if(ForInit!=null) ForInit.traverseBottomUp(visitor);
        if(ForCond!=null) ForCond.traverseBottomUp(visitor);
        if(ForAct!=null) ForAct.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementFor(\n");

        if(ForStart!=null)
            buffer.append(ForStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForInit!=null)
            buffer.append(ForInit.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCond!=null)
            buffer.append(ForCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForAct!=null)
            buffer.append(ForAct.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementFor]");
        return buffer.toString();
    }
}
