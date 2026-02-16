// generated with ast extension for cup
// version 0.8
// 16/1/2026 23:10:56


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassDeclarationNoMethodList extends AbstractClassDeclaration {

    private AbstractClassName AbstractClassName;
    private FieldDeclarationList FieldDeclarationList;

    public AbstractClassDeclarationNoMethodList (AbstractClassName AbstractClassName, FieldDeclarationList FieldDeclarationList) {
        this.AbstractClassName=AbstractClassName;
        if(AbstractClassName!=null) AbstractClassName.setParent(this);
        this.FieldDeclarationList=FieldDeclarationList;
        if(FieldDeclarationList!=null) FieldDeclarationList.setParent(this);
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassName!=null) AbstractClassName.accept(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassName!=null) AbstractClassName.traverseTopDown(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassName!=null) AbstractClassName.traverseBottomUp(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassDeclarationNoMethodList(\n");

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

        buffer.append(tab);
        buffer.append(") [AbstractClassDeclarationNoMethodList]");
        return buffer.toString();
    }
}
