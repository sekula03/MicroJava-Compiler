// generated with ast extension for cup
// version 0.8
// 10/1/2026 12:56:38


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclarationNoParams extends MethodDeclaration {

    private MethodHeader MethodHeader;
    private VarDeclarationList VarDeclarationList;
    private StatementList StatementList;

    public MethodDeclarationNoParams (MethodHeader MethodHeader, VarDeclarationList VarDeclarationList, StatementList StatementList) {
        this.MethodHeader=MethodHeader;
        if(MethodHeader!=null) MethodHeader.setParent(this);
        this.VarDeclarationList=VarDeclarationList;
        if(VarDeclarationList!=null) VarDeclarationList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodHeader getMethodHeader() {
        return MethodHeader;
    }

    public void setMethodHeader(MethodHeader MethodHeader) {
        this.MethodHeader=MethodHeader;
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
        if(MethodHeader!=null) MethodHeader.accept(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodHeader!=null) MethodHeader.traverseTopDown(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodHeader!=null) MethodHeader.traverseBottomUp(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclarationNoParams(\n");

        if(MethodHeader!=null)
            buffer.append(MethodHeader.toString("  "+tab));
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
        buffer.append(") [MethodDeclarationNoParams]");
        return buffer.toString();
    }
}
