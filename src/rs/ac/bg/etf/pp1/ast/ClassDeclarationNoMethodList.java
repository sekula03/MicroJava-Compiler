// generated with ast extension for cup
// version 0.8
// 6/1/2026 23:19:47


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclarationNoMethodList extends ClassDeclaration {

    private ClassName ClassName;
    private FieldDeclarationList FieldDeclarationList;

    public ClassDeclarationNoMethodList (ClassName ClassName, FieldDeclarationList FieldDeclarationList) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.FieldDeclarationList=FieldDeclarationList;
        if(FieldDeclarationList!=null) FieldDeclarationList.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
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
        if(ClassName!=null) ClassName.accept(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclarationNoMethodList(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDeclarationList!=null)
            buffer.append(FieldDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclarationNoMethodList]");
        return buffer.toString();
    }
}
