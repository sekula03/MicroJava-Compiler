// generated with ast extension for cup
// version 0.8
// 2/1/2026 23:8:48


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassDeclarationExtendsNoMethodList extends AbstractClassDeclaration {

    private AbstractClassName AbstractClassName;
    private Type Type;
    private VarDeclarationList VarDeclarationList;

    public AbstractClassDeclarationExtendsNoMethodList (AbstractClassName AbstractClassName, Type Type, VarDeclarationList VarDeclarationList) {
        this.AbstractClassName=AbstractClassName;
        if(AbstractClassName!=null) AbstractClassName.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDeclarationList=VarDeclarationList;
        if(VarDeclarationList!=null) VarDeclarationList.setParent(this);
    }

    public AbstractClassName getAbstractClassName() {
        return AbstractClassName;
    }

    public void setAbstractClassName(AbstractClassName AbstractClassName) {
        this.AbstractClassName=AbstractClassName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDeclarationList getVarDeclarationList() {
        return VarDeclarationList;
    }

    public void setVarDeclarationList(VarDeclarationList VarDeclarationList) {
        this.VarDeclarationList=VarDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassName!=null) AbstractClassName.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassName!=null) AbstractClassName.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassName!=null) AbstractClassName.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassDeclarationExtendsNoMethodList(\n");

        if(AbstractClassName!=null)
            buffer.append(AbstractClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclarationList!=null)
            buffer.append(VarDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassDeclarationExtendsNoMethodList]");
        return buffer.toString();
    }
}
