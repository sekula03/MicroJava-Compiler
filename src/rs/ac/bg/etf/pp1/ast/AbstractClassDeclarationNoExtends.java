// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassDeclarationNoExtends extends AbstractClassDeclaration {

    private String I1;
    private AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList;

    public AbstractClassDeclarationNoExtends (String I1, AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList) {
        this.I1=I1;
        this.AbstractClassMethodDeclarationList=AbstractClassMethodDeclarationList;
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public AbstractClassMethodDeclarationList getAbstractClassMethodDeclarationList() {
        return AbstractClassMethodDeclarationList;
    }

    public void setAbstractClassMethodDeclarationList(AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList) {
        this.AbstractClassMethodDeclarationList=AbstractClassMethodDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassDeclarationNoExtends(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(AbstractClassMethodDeclarationList!=null)
            buffer.append(AbstractClassMethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassDeclarationNoExtends]");
        return buffer.toString();
    }
}
