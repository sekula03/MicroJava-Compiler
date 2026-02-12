// generated with ast extension for cup
// version 0.8
// 12/1/2026 2:52:55


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassDeclarationMethodList extends AbstractClassDeclaration {

    private AbstractClassName AbstractClassName;
    private FieldDeclarationList FieldDeclarationList;
    private AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList;

    public AbstractClassDeclarationMethodList (AbstractClassName AbstractClassName, FieldDeclarationList FieldDeclarationList, AbstractClassMethodDeclarationList AbstractClassMethodDeclarationList) {
        this.AbstractClassName=AbstractClassName;
        if(AbstractClassName!=null) AbstractClassName.setParent(this);
        this.FieldDeclarationList=FieldDeclarationList;
        if(FieldDeclarationList!=null) FieldDeclarationList.setParent(this);
        this.AbstractClassMethodDeclarationList=AbstractClassMethodDeclarationList;
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.setParent(this);
    }

    public AbstractClassName getAbstractClassName() {
        return AbstractClassName;
    }

    public void setAbstractClassName(AbstractClassName AbstractClassName) {
        this.AbstractClassName=AbstractClassName;
    }

    public FieldDeclarationList getFieldDeclarationList() {
        return FieldDeclarationList;
    }

    public void setFieldDeclarationList(FieldDeclarationList FieldDeclarationList) {
        this.FieldDeclarationList=FieldDeclarationList;
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
        if(AbstractClassName!=null) AbstractClassName.accept(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.accept(visitor);
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassName!=null) AbstractClassName.traverseTopDown(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseTopDown(visitor);
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassName!=null) AbstractClassName.traverseBottomUp(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseBottomUp(visitor);
        if(AbstractClassMethodDeclarationList!=null) AbstractClassMethodDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassDeclarationMethodList(\n");

        if(AbstractClassName!=null)
            buffer.append(AbstractClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDeclarationList!=null)
            buffer.append(FieldDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractClassMethodDeclarationList!=null)
            buffer.append(AbstractClassMethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassDeclarationMethodList]");
        return buffer.toString();
    }
}
