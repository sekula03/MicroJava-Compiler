// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class ActualParamsListMultiple extends ActualParamsList {

    private ActualParamsList ActualParamsList;
    private Expression Expression;

    public ActualParamsListMultiple (ActualParamsList ActualParamsList, Expression Expression) {
        this.ActualParamsList=ActualParamsList;
        if(ActualParamsList!=null) ActualParamsList.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public ActualParamsList getActualParamsList() {
        return ActualParamsList;
    }

    public void setActualParamsList(ActualParamsList ActualParamsList) {
        this.ActualParamsList=ActualParamsList;
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
        if(ActualParamsList!=null) ActualParamsList.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParamsList!=null) ActualParamsList.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParamsList!=null) ActualParamsList.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParamsListMultiple(\n");

        if(ActualParamsList!=null)
            buffer.append(ActualParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParamsListMultiple]");
        return buffer.toString();
    }
}
