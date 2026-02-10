// generated with ast extension for cup
// version 0.8
// 10/1/2026 0:52:31


package rs.ac.bg.etf.pp1.ast;

public class AbstractMethodDeclarationNoParams extends AbstractMethodDeclaration {

    private MethodHeader MethodHeader;

    public AbstractMethodDeclarationNoParams (MethodHeader MethodHeader) {
        this.MethodHeader=MethodHeader;
        if(MethodHeader!=null) MethodHeader.setParent(this);
    }

    public MethodHeader getMethodHeader() {
        return MethodHeader;
    }

    public void setMethodHeader(MethodHeader MethodHeader) {
        this.MethodHeader=MethodHeader;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodHeader!=null) MethodHeader.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodHeader!=null) MethodHeader.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodHeader!=null) MethodHeader.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractMethodDeclarationNoParams(\n");

        if(MethodHeader!=null)
            buffer.append(MethodHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractMethodDeclarationNoParams]");
        return buffer.toString();
    }
}
