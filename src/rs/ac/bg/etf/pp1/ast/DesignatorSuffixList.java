// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorSuffixList extends Designator {

    private String I1;
    private SuffixList SuffixList;

    public DesignatorSuffixList (String I1, SuffixList SuffixList) {
        this.I1=I1;
        this.SuffixList=SuffixList;
        if(SuffixList!=null) SuffixList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
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
        if(SuffixList!=null) SuffixList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SuffixList!=null) SuffixList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SuffixList!=null) SuffixList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorSuffixList(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(SuffixList!=null)
            buffer.append(SuffixList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorSuffixList]");
        return buffer.toString();
    }
}
