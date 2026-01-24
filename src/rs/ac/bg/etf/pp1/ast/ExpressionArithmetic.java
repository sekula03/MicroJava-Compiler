// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class ExpressionArithmetic extends Expression {

    private ArithmeticExpression ArithmeticExpression;

    public ExpressionArithmetic (ArithmeticExpression ArithmeticExpression) {
        this.ArithmeticExpression=ArithmeticExpression;
        if(ArithmeticExpression!=null) ArithmeticExpression.setParent(this);
    }

    public ArithmeticExpression getArithmeticExpression() {
        return ArithmeticExpression;
    }

    public void setArithmeticExpression(ArithmeticExpression ArithmeticExpression) {
        this.ArithmeticExpression=ArithmeticExpression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArithmeticExpression!=null) ArithmeticExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArithmeticExpression!=null) ArithmeticExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArithmeticExpression!=null) ArithmeticExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExpressionArithmetic(\n");

        if(ArithmeticExpression!=null)
            buffer.append(ArithmeticExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExpressionArithmetic]");
        return buffer.toString();
    }
}
