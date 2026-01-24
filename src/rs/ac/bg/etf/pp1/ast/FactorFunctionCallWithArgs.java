// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class FactorFunctionCallWithArgs extends Factor {

    private Sign Sign;
    private Designator Designator;
    private ActualParamsList ActualParamsList;

    public FactorFunctionCallWithArgs (Sign Sign, Designator Designator, ActualParamsList ActualParamsList) {
        this.Sign=Sign;
        if(Sign!=null) Sign.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActualParamsList=ActualParamsList;
        if(ActualParamsList!=null) ActualParamsList.setParent(this);
    }

    public Sign getSign() {
        return Sign;
    }

    public void setSign(Sign Sign) {
        this.Sign=Sign;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActualParamsList getActualParamsList() {
        return ActualParamsList;
    }

    public void setActualParamsList(ActualParamsList ActualParamsList) {
        this.ActualParamsList=ActualParamsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Sign!=null) Sign.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
        if(ActualParamsList!=null) ActualParamsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Sign!=null) Sign.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActualParamsList!=null) ActualParamsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Sign!=null) Sign.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActualParamsList!=null) ActualParamsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorFunctionCallWithArgs(\n");

        if(Sign!=null)
            buffer.append(Sign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualParamsList!=null)
            buffer.append(ActualParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorFunctionCallWithArgs]");
        return buffer.toString();
    }
}
