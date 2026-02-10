// generated with ast extension for cup
// version 0.8
// 10/1/2026 12:56:38


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFieldArray extends Designator {

    private FieldArrayName FieldArrayName;
    private Expression Expression;

    public DesignatorFieldArray (FieldArrayName FieldArrayName, Expression Expression) {
        this.FieldArrayName=FieldArrayName;
        if(FieldArrayName!=null) FieldArrayName.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public FieldArrayName getFieldArrayName() {
        return FieldArrayName;
    }

    public void setFieldArrayName(FieldArrayName FieldArrayName) {
        this.FieldArrayName=FieldArrayName;
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
        if(FieldArrayName!=null) FieldArrayName.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FieldArrayName!=null) FieldArrayName.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FieldArrayName!=null) FieldArrayName.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFieldArray(\n");

        if(FieldArrayName!=null)
            buffer.append(FieldArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFieldArray]");
        return buffer.toString();
    }
}
