// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class DeclarationListVar extends DeclarationList {

    private DeclarationList DeclarationList;
    private VarDeclaration VarDeclaration;

    public DeclarationListVar (DeclarationList DeclarationList, VarDeclaration VarDeclaration) {
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
        this.VarDeclaration=VarDeclaration;
        if(VarDeclaration!=null) VarDeclaration.setParent(this);
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public VarDeclaration getVarDeclaration() {
        return VarDeclaration;
    }

    public void setVarDeclaration(VarDeclaration VarDeclaration) {
        this.VarDeclaration=VarDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.accept(visitor);
        if(VarDeclaration!=null) VarDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
        if(VarDeclaration!=null) VarDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        if(VarDeclaration!=null) VarDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationListVar(\n");

        if(DeclarationList!=null)
            buffer.append(DeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclaration!=null)
            buffer.append(VarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationListVar]");
        return buffer.toString();
    }
}
