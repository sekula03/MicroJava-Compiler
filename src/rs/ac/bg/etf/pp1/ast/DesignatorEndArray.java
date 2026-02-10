// generated with ast extension for cup
// version 0.8
// 10/1/2026 0:52:31


package rs.ac.bg.etf.pp1.ast;

public class DesignatorEndArray extends Designator {

    private EndArrayName EndArrayName;
    private Expression Expression;

    public DesignatorEndArray (EndArrayName EndArrayName, Expression Expression) {
        this.EndArrayName=EndArrayName;
        if(EndArrayName!=null) EndArrayName.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public EndArrayName getEndArrayName() {
        return EndArrayName;
    }

    public void setEndArrayName(EndArrayName EndArrayName) {
        this.EndArrayName=EndArrayName;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EndArrayName!=null) EndArrayName.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EndArrayName!=null) EndArrayName.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EndArrayName!=null) EndArrayName.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorEndArray(\n");

        if(EndArrayName!=null)
            buffer.append(EndArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorEndArray]");
        return buffer.toString();
    }
}
