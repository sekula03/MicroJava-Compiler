// generated with ast extension for cup
// version 0.8
// 4/1/2026 22:6:35


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private String I1;
    private Expression Expression;
    private SuffixList SuffixList;

    public DesignatorArray (String I1, Expression Expression, SuffixList SuffixList) {
        this.I1=I1;
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.SuffixList=SuffixList;
        if(SuffixList!=null) SuffixList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public SuffixList getSuffixList() {
        return SuffixList;
    }

    public void setSuffixList(SuffixList SuffixList) {
        this.SuffixList=SuffixList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expression!=null) Expression.accept(visitor);
        if(SuffixList!=null) SuffixList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(SuffixList!=null) SuffixList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(SuffixList!=null) SuffixList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArray(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SuffixList!=null)
            buffer.append(SuffixList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
