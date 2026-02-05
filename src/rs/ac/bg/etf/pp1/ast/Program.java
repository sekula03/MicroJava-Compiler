// generated with ast extension for cup
// version 0.8
// 5/1/2026 20:34:6


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ProgramName ProgramName;
    private DeclarationList DeclarationList;
    private GlobalMethodDeclarationList GlobalMethodDeclarationList;

    public Program (ProgramName ProgramName, DeclarationList DeclarationList, GlobalMethodDeclarationList GlobalMethodDeclarationList) {
        this.ProgramName=ProgramName;
        if(ProgramName!=null) ProgramName.setParent(this);
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
        this.GlobalMethodDeclarationList=GlobalMethodDeclarationList;
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.setParent(this);
    }

    public ProgramName getProgramName() {
        return ProgramName;
    }

    public void setProgramName(ProgramName ProgramName) {
        this.ProgramName=ProgramName;
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public GlobalMethodDeclarationList getGlobalMethodDeclarationList() {
        return GlobalMethodDeclarationList;
    }

    public void setGlobalMethodDeclarationList(GlobalMethodDeclarationList GlobalMethodDeclarationList) {
        this.GlobalMethodDeclarationList=GlobalMethodDeclarationList;
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
        if(ProgramName!=null) ProgramName.accept(visitor);
        if(DeclarationList!=null) DeclarationList.accept(visitor);
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramName!=null) ProgramName.traverseTopDown(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramName!=null) ProgramName.traverseBottomUp(visitor);
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        if(GlobalMethodDeclarationList!=null) GlobalMethodDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Program(\n");

        if(ProgramName!=null)
            buffer.append(ProgramName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DeclarationList!=null)
            buffer.append(DeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalMethodDeclarationList!=null)
            buffer.append(GlobalMethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Program]");
        return buffer.toString();
    }
}
