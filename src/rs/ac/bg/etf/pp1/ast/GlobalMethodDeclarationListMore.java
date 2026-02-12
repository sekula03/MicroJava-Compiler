// generated with ast extension for cup
// version 0.8
// 12/1/2026 2:52:55


package rs.ac.bg.etf.pp1.ast;

public class GlobalMethodDeclarationListMore extends GlobalMethodDeclarationList {

    private GlobalMethodDeclarationList GlobalMethodDeclarationList;
    private GlobalMethodDeclaration GlobalMethodDeclaration;

    public GlobalMethodDeclarationListMore (GlobalMethodDeclarationList GlobalMethodDeclarationList, GlobalMethodDeclaration GlobalMethodDeclaration) {
        this.GlobalMethodDeclarationList=GlobalMethodDeclarationList;
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.setParent(this);
        this.GlobalMethodDeclaration=GlobalMethodDeclaration;
        if(GlobalMethodDeclaration!=null) GlobalMethodDeclaration.setParent(this);
    }

    public GlobalMethodDeclarationList getGlobalMethodDeclarationList() {
        return GlobalMethodDeclarationList;
    }

    public void setGlobalMethodDeclarationList(GlobalMethodDeclarationList GlobalMethodDeclarationList) {
        this.GlobalMethodDeclarationList=GlobalMethodDeclarationList;
    }

    public GlobalMethodDeclaration getGlobalMethodDeclaration() {
        return GlobalMethodDeclaration;
    }

    public void setGlobalMethodDeclaration(GlobalMethodDeclaration GlobalMethodDeclaration) {
        this.GlobalMethodDeclaration=GlobalMethodDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.accept(visitor);
        if(GlobalMethodDeclaration!=null) GlobalMethodDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.traverseTopDown(visitor);
        if(GlobalMethodDeclaration!=null) GlobalMethodDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.traverseBottomUp(visitor);
        if(GlobalMethodDeclaration!=null) GlobalMethodDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalMethodDeclarationListMore(\n");

        if(GlobalMethodDeclarationList!=null)
            buffer.append(GlobalMethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalMethodDeclaration!=null)
            buffer.append(GlobalMethodDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalMethodDeclarationListMore]");
        return buffer.toString();
    }
}
