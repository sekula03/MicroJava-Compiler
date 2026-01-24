// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class DeclarationListEnum extends DeclarationList {

    private DeclarationList DeclarationList;
    private EnumDeclaration EnumDeclaration;

    public DeclarationListEnum (DeclarationList DeclarationList, EnumDeclaration EnumDeclaration) {
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
        this.EnumDeclaration=EnumDeclaration;
        if(EnumDeclaration!=null) EnumDeclaration.setParent(this);
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public EnumDeclaration getEnumDeclaration() {
        return EnumDeclaration;
    }

    public void setEnumDeclaration(EnumDeclaration EnumDeclaration) {
        this.EnumDeclaration=EnumDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.accept(visitor);
        if(EnumDeclaration!=null) EnumDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
        if(EnumDeclaration!=null) EnumDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        if(EnumDeclaration!=null) EnumDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationListEnum(\n");

        if(DeclarationList!=null)
            buffer.append(DeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumDeclaration!=null)
            buffer.append(EnumDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationListEnum]");
        return buffer.toString();
    }
}
