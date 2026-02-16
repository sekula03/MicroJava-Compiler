// generated with ast extension for cup
// version 0.8
// 16/1/2026 23:10:56


package rs.ac.bg.etf.pp1.ast;

public class DefaultCase implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private DefaultCaseStart DefaultCaseStart;
    private StatementList StatementList;

    public DefaultCase (DefaultCaseStart DefaultCaseStart, StatementList StatementList) {
        this.DefaultCaseStart=DefaultCaseStart;
        if(DefaultCaseStart!=null) DefaultCaseStart.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public DefaultCaseStart getDefaultCaseStart() {
        return DefaultCaseStart;
    }

    public void setDefaultCaseStart(DefaultCaseStart DefaultCaseStart) {
        this.DefaultCaseStart=DefaultCaseStart;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DefaultCaseStart!=null) DefaultCaseStart.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DefaultCaseStart!=null) DefaultCaseStart.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DefaultCaseStart!=null) DefaultCaseStart.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DefaultCase(\n");

        if(DefaultCaseStart!=null)
            buffer.append(DefaultCaseStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DefaultCase]");
        return buffer.toString();
    }
}
