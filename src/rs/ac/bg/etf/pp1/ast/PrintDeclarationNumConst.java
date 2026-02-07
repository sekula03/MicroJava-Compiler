// generated with ast extension for cup
// version 0.8
// 6/1/2026 23:19:47


package rs.ac.bg.etf.pp1.ast;

public class PrintDeclarationNumConst extends PrintDeclaration {

    private Integer N1;

    public PrintDeclarationNumConst (Integer N1) {
        this.N1=N1;
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintDeclarationNumConst(\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintDeclarationNumConst]");
        return buffer.toString();
    }
}
