// generated with ast extension for cup
// version 0.8
// 10/1/2026 0:52:31


package rs.ac.bg.etf.pp1.ast;

public class ExpressionTernary extends Expression {

    private Condition Condition;
    private Expression Expression;
    private SkipTernary SkipTernary;
    private Expression Expression1;

    public ExpressionTernary (Condition Condition, Expression Expression, SkipTernary SkipTernary, Expression Expression1) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.SkipTernary=SkipTernary;
        if(SkipTernary!=null) SkipTernary.setParent(this);
        this.Expression1=Expression1;
        if(Expression1!=null) Expression1.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public SkipTernary getSkipTernary() {
        return SkipTernary;
    }

    public void setSkipTernary(SkipTernary SkipTernary) {
        this.SkipTernary=SkipTernary;
    }

    public Expression getExpression1() {
        return Expression1;
    }

    public void setExpression1(Expression Expression1) {
        this.Expression1=Expression1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
        if(SkipTernary!=null) SkipTernary.accept(visitor);
        if(Expression1!=null) Expression1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(SkipTernary!=null) SkipTernary.traverseTopDown(visitor);
        if(Expression1!=null) Expression1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(SkipTernary!=null) SkipTernary.traverseBottomUp(visitor);
        if(Expression1!=null) Expression1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExpressionTernary(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SkipTernary!=null)
            buffer.append(SkipTernary.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression1!=null)
            buffer.append(Expression1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExpressionTernary]");
        return buffer.toString();
    }
}
