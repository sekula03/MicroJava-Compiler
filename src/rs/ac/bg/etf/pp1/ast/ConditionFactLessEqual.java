// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class ConditionFactLessEqual extends ConditionFact {

    private ArithmeticExpression ArithmeticExpression;
    private ArithmeticExpression ArithmeticExpression1;

    public ConditionFactLessEqual (ArithmeticExpression ArithmeticExpression, ArithmeticExpression ArithmeticExpression1) {
        this.ArithmeticExpression=ArithmeticExpression;
        if(ArithmeticExpression!=null) ArithmeticExpression.setParent(this);
        this.ArithmeticExpression1=ArithmeticExpression1;
        if(ArithmeticExpression1!=null) ArithmeticExpression1.setParent(this);
    }

    public ArithmeticExpression getArithmeticExpression() {
        return ArithmeticExpression;
    }

    public void setArithmeticExpression(ArithmeticExpression ArithmeticExpression) {
        this.ArithmeticExpression=ArithmeticExpression;
    }

    public ArithmeticExpression getArithmeticExpression1() {
        return ArithmeticExpression1;
    }

    public void setArithmeticExpression1(ArithmeticExpression ArithmeticExpression1) {
        this.ArithmeticExpression1=ArithmeticExpression1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArithmeticExpression!=null) ArithmeticExpression.accept(visitor);
        if(ArithmeticExpression1!=null) ArithmeticExpression1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArithmeticExpression!=null) ArithmeticExpression.traverseTopDown(visitor);
        if(ArithmeticExpression1!=null) ArithmeticExpression1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArithmeticExpression!=null) ArithmeticExpression.traverseBottomUp(visitor);
        if(ArithmeticExpression1!=null) ArithmeticExpression1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionFactLessEqual(\n");

        if(ArithmeticExpression!=null)
            buffer.append(ArithmeticExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ArithmeticExpression1!=null)
            buffer.append(ArithmeticExpression1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionFactLessEqual]");
        return buffer.toString();
    }
}
