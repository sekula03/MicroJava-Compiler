// generated with ast extension for cup
// version 0.8
// 12/1/2026 2:52:55


package rs.ac.bg.etf.pp1.ast;

public class GlobalMethodDeclarationNoParams extends GlobalMethodDeclaration {

    private GlobalMethodHeader GlobalMethodHeader;
    private VarDeclarationList VarDeclarationList;
    private StatementList StatementList;

    public GlobalMethodDeclarationNoParams (GlobalMethodHeader GlobalMethodHeader, VarDeclarationList VarDeclarationList, StatementList StatementList) {
        this.GlobalMethodHeader=GlobalMethodHeader;
        if(GlobalMethodHeader!=null) GlobalMethodHeader.setParent(this);
        this.VarDeclarationList=VarDeclarationList;
        if(VarDeclarationList!=null) VarDeclarationList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public GlobalMethodHeader getGlobalMethodHeader() {
        return GlobalMethodHeader;
    }

    public void setGlobalMethodHeader(GlobalMethodHeader GlobalMethodHeader) {
        this.GlobalMethodHeader=GlobalMethodHeader;
    }

    public VarDeclarationList getVarDeclarationList() {
        return VarDeclarationList;
    }

    public void setVarDeclarationList(VarDeclarationList VarDeclarationList) {
        this.VarDeclarationList=VarDeclarationList;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalMethodHeader!=null) GlobalMethodHeader.accept(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalMethodHeader!=null) GlobalMethodHeader.traverseTopDown(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalMethodHeader!=null) GlobalMethodHeader.traverseBottomUp(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalMethodDeclarationNoParams(\n");

        if(GlobalMethodHeader!=null)
            buffer.append(GlobalMethodHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclarationList!=null)
            buffer.append(VarDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalMethodDeclarationNoParams]");
        return buffer.toString();
    }
}
