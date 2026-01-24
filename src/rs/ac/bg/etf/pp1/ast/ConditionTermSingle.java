// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class ConditionTermSingle extends ConditionTerm {

    private ConditionFact ConditionFact;

    public ConditionTermSingle (ConditionFact ConditionFact) {
        this.ConditionFact=ConditionFact;
        if(ConditionFact!=null) ConditionFact.setParent(this);
    }

    public ConditionFact getConditionFact() {
        return ConditionFact;
    }

    public void setConditionFact(ConditionFact ConditionFact) {
        this.ConditionFact=ConditionFact;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionFact!=null) ConditionFact.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionFact!=null) ConditionFact.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionFact!=null) ConditionFact.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionTermSingle(\n");

        if(ConditionFact!=null)
            buffer.append(ConditionFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionTermSingle]");
        return buffer.toString();
    }
}
