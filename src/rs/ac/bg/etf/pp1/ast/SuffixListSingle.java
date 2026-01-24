// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class SuffixListSingle extends SuffixList {

    private Suffix Suffix;

    public SuffixListSingle (Suffix Suffix) {
        this.Suffix=Suffix;
        if(Suffix!=null) Suffix.setParent(this);
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
        if(Suffix!=null) Suffix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Suffix!=null) Suffix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Suffix!=null) Suffix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SuffixListSingle(\n");

        if(Suffix!=null)
            buffer.append(Suffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SuffixListSingle]");
        return buffer.toString();
    }
}
