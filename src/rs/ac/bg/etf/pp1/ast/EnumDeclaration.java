// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclaration implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private EnumConstList EnumConstList;

    public EnumDeclaration (String I1, EnumConstList EnumConstList) {
        this.I1=I1;
        this.EnumConstList=EnumConstList;
        if(EnumConstList!=null) EnumConstList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public EnumConstList getEnumConstList() {
        return EnumConstList;
    }

    public void setEnumConstList(EnumConstList EnumConstList) {
        this.EnumConstList=EnumConstList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumConstList!=null) EnumConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumConstList!=null) EnumConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumConstList!=null) EnumConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclaration(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(EnumConstList!=null)
            buffer.append(EnumConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDeclaration]");
        return buffer.toString();
    }
}
