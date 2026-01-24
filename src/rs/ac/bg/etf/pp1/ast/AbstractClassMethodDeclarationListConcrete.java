// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassMethodDeclarationListConcrete extends AbstractClassMethodDeclarationList {

    private AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList;
    private MethodDeclaration MethodDeclaration;

    public AbstractClassMethodDeclarationListConcrete (AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList, MethodDeclaration MethodDeclaration) {
        this.AbstractClassMethodDeclarationList=AbstractClassMethodDeclarationList;
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.setParent(this);
        this.MethodDeclaration=MethodDeclaration;
        if(MethodDeclaration!=null) MethodDeclaration.setParent(this);
    }

    public AbstractClassMethodDeclarationList getAbstractClassMethodDeclarationList() {
        return AbstractClassMethodDeclarationList;
    }

    public void setAbstractClassMethodDeclarationList(AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList) {
        this.AbstractClassMethodDeclarationList=AbstractClassMethodDeclarationList;
    }

    public MethodDeclaration getMethodDeclaration() {
        return MethodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration MethodDeclaration) {
        this.MethodDeclaration=MethodDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.accept(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseTopDown(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseBottomUp(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassMethodDeclarationListConcrete(\n");

        if(AbstractClassMethodDeclarationList!=null)
            buffer.append(AbstractClassMethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclaration!=null)
            buffer.append(MethodDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassMethodDeclarationListConcrete]");
        return buffer.toString();
    }
}
