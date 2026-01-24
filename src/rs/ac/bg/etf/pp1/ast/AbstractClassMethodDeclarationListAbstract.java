// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassMethodDeclarationListAbstract extends AbstractClassMethodDeclarationList {

    private AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList;
    private AbstractMethodDeclaration AbstractMethodDeclaration;

    public AbstractClassMethodDeclarationListAbstract (AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList, AbstractMethodDeclaration AbstractMethodDeclaration) {
        this.AbstractClassMethodDeclarationList=AbstractClassMethodDeclarationList;
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.setParent(this);
        this.AbstractMethodDeclaration=AbstractMethodDeclaration;
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.setParent(this);
    }

    public AbstractClassMethodDeclarationList getAbstractClassMethodDeclarationList() {
        return AbstractClassMethodDeclarationList;
    }

    public void setAbstractClassMethodDeclarationList(AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList) {
        this.AbstractClassMethodDeclarationList=AbstractClassMethodDeclarationList;
    }

    public AbstractMethodDeclaration getAbstractMethodDeclaration() {
        return AbstractMethodDeclaration;
    }

    public void setAbstractMethodDeclaration(AbstractMethodDeclaration AbstractMethodDeclaration) {
        this.AbstractMethodDeclaration=AbstractMethodDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.accept(visitor);
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseTopDown(visitor);
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseBottomUp(visitor);
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassMethodDeclarationListAbstract(\n");

        if(AbstractClassMethodDeclarationList!=null)
            buffer.append(AbstractClassMethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractMethodDeclaration!=null)
            buffer.append(AbstractMethodDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassMethodDeclarationListAbstract]");
        return buffer.toString();
    }
}
