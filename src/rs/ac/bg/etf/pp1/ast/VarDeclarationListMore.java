// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationListMore extends VarDeclarationList {

    private VarDeclarationList VarDeclarationList;
    private VarDeclaration VarDeclaration;

    public VarDeclarationListMore (VarDeclarationList VarDeclarationList, VarDeclaration VarDeclaration) {
        this.VarDeclarationList=VarDeclarationList;
        if(VarDeclarationList!=null) VarDeclarationList.setParent(this);
        this.VarDeclaration=VarDeclaration;
        if(VarDeclaration!=null) VarDeclaration.setParent(this);
    }

    public VarDeclarationList getVarDeclarationList() {
        return VarDeclarationList;
    }

    public void setVarDeclarationList(VarDeclarationList VarDeclarationList) {
        this.VarDeclarationList=VarDeclarationList;
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
        if(VarDeclarationList!=null) VarDeclarationList.accept(visitor);
        if(VarDeclaration!=null) VarDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseTopDown(visitor);
        if(VarDeclaration!=null) VarDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclarationList!=null) VarDeclarationList.traverseBottomUp(visitor);
        if(VarDeclaration!=null) VarDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationListMore(\n");

        if(VarDeclarationList!=null)
            buffer.append(VarDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclaration!=null)
            buffer.append(VarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationListMore]");
        return buffer.toString();
    }
}
