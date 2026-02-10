// generated with ast extension for cup
// version 0.8
// 10/1/2026 12:56:38


package rs.ac.bg.etf.pp1.ast;

public class DeclarationListVar extends DeclarationList {

    private DeclarationList DeclarationList;
    private GlobalVarDeclaration GlobalVarDeclaration;

    public DeclarationListVar (DeclarationList DeclarationList, GlobalVarDeclaration GlobalVarDeclaration) {
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
        this.GlobalVarDeclaration=GlobalVarDeclaration;
        if(GlobalVarDeclaration!=null) GlobalVarDeclaration.setParent(this);
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public GlobalVarDeclaration getGlobalVarDeclaration() {
        return GlobalVarDeclaration;
    }

    public void setGlobalVarDeclaration(GlobalVarDeclaration GlobalVarDeclaration) {
        this.GlobalVarDeclaration=GlobalVarDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.accept(visitor);
        if(GlobalVarDeclaration!=null) GlobalVarDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
        if(GlobalVarDeclaration!=null) GlobalVarDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        if(GlobalVarDeclaration!=null) GlobalVarDeclaration.traverseBottomUp(visitor);
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

        if(GlobalVarDeclaration!=null)
            buffer.append(GlobalVarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationListVar]");
        return buffer.toString();
    }
}
