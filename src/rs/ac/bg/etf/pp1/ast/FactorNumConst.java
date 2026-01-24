// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class FactorNumConst extends Factor {

    private Sign Sign;
    private Integer N2;

    public FactorNumConst (Sign Sign, Integer N2) {
        this.Sign=Sign;
        if(Sign!=null) Sign.setParent(this);
        this.N2=N2;
    }

    public Sign getSign() {
        return Sign;
    }

    public void setSign(Sign Sign) {
        this.Sign=Sign;
    }

    public Integer getN2() {
        return N2;
    }

    public void setN2(Integer N2) {
        this.N2=N2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Sign!=null) Sign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Sign!=null) Sign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Sign!=null) Sign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNumConst(\n");

        if(Sign!=null)
            buffer.append(Sign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+N2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNumConst]");
        return buffer.toString();
    }
}
