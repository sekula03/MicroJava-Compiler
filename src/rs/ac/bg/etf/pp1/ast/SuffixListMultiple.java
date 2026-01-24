// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class SuffixListMultiple extends SuffixList {

    private SuffixList SuffixList;
    private Suffix Suffix;

    public SuffixListMultiple (SuffixList SuffixList, Suffix Suffix) {
        this.SuffixList=SuffixList;
        if(SuffixList!=null) SuffixList.setParent(this);
        this.Suffix=Suffix;
        if(Suffix!=null) Suffix.setParent(this);
    }

    public SuffixList getSuffixList() {
        return SuffixList;
    }

    public void setSuffixList(SuffixList SuffixList) {
        this.SuffixList=SuffixList;
    }

    public Suffix getSuffix() {
        return Suffix;
    }

    public void setSuffix(Suffix Suffix) {
        this.Suffix=Suffix;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SuffixList!=null) SuffixList.accept(visitor);
        if(Suffix!=null) Suffix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SuffixList!=null) SuffixList.traverseTopDown(visitor);
        if(Suffix!=null) Suffix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SuffixList!=null) SuffixList.traverseBottomUp(visitor);
        if(Suffix!=null) Suffix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SuffixListMultiple(\n");

        if(SuffixList!=null)
            buffer.append(SuffixList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Suffix!=null)
            buffer.append(Suffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SuffixListMultiple]");
        return buffer.toString();
    }
}
