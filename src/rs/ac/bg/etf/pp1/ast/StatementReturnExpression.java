// generated with ast extension for cup
// version 0.8
// 12/1/2026 2:52:55


package rs.ac.bg.etf.pp1.ast;

public class StatementReturnExpression extends Statement {

    private Return Return;
    private Expression Expression;

    public StatementReturnExpression (Return Return, Expression Expression) {
        this.Return=Return;
        if(Return!=null) Return.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public Return getReturn() {
        return Return;
    }

    public void setReturn(Return Return) {
        this.Return=Return;
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
        if(Return!=null) Return.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Return!=null) Return.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Return!=null) Return.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementReturnExpression(\n");

        if(Return!=null)
            buffer.append(Return.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementReturnExpression]");
        return buffer.toString();
    }
}
