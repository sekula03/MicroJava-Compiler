// generated with ast extension for cup
// version 0.8
// 12/1/2026 0:49:14


package rs.ac.bg.etf.pp1.ast;

public class FieldDeclarationListMore extends FieldDeclarationList {

    private FieldDeclarationList FieldDeclarationList;
    private FieldDeclaration FieldDeclaration;

    public FieldDeclarationListMore (FieldDeclarationList FieldDeclarationList, FieldDeclaration FieldDeclaration) {
        this.FieldDeclarationList=FieldDeclarationList;
        if(FieldDeclarationList!=null) FieldDeclarationList.setParent(this);
        this.FieldDeclaration=FieldDeclaration;
        if(FieldDeclaration!=null) FieldDeclaration.setParent(this);
    }

    public FieldDeclarationList getFieldDeclarationList() {
        return FieldDeclarationList;
    }

    public void setFieldDeclarationList(FieldDeclarationList FieldDeclarationList) {
        this.FieldDeclarationList=FieldDeclarationList;
    }

    public FieldDeclaration getFieldDeclaration() {
        return FieldDeclaration;
    }

    public void setFieldDeclaration(FieldDeclaration FieldDeclaration) {
        this.FieldDeclaration=FieldDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FieldDeclarationList!=null) FieldDeclarationList.accept(visitor);
        if(FieldDeclaration!=null) FieldDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseTopDown(visitor);
        if(FieldDeclaration!=null) FieldDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseBottomUp(visitor);
        if(FieldDeclaration!=null) FieldDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FieldDeclarationListMore(\n");

        if(FieldDeclarationList!=null)
            buffer.append(FieldDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDeclaration!=null)
            buffer.append(FieldDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FieldDeclarationListMore]");
        return buffer.toString();
    }
}
