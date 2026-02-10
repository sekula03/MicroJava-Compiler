// generated with ast extension for cup
// version 0.8
// 10/1/2026 0:52:31


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclarationMethodList extends ClassDeclaration {

    private ClassName ClassName;
    private FieldDeclarationList FieldDeclarationList;
    private MethodDeclarationList MethodDeclarationList;

    public ClassDeclarationMethodList (ClassName ClassName, FieldDeclarationList FieldDeclarationList, MethodDeclarationList MethodDeclarationList) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.FieldDeclarationList=FieldDeclarationList;
        if(FieldDeclarationList!=null) FieldDeclarationList.setParent(this);
        this.MethodDeclarationList=MethodDeclarationList;
        if(MethodDeclarationList!=null) MethodDeclarationList.setParent(this);
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

    public MethodDeclarationList getMethodDeclarationList() {
        return MethodDeclarationList;
    }

    public void setMethodDeclarationList(MethodDeclarationList MethodDeclarationList) {
        this.MethodDeclarationList=MethodDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.accept(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseTopDown(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(FieldDeclarationList!=null) FieldDeclarationList.traverseBottomUp(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclarationMethodList(\n");

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

        if(MethodDeclarationList!=null)
            buffer.append(MethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclarationMethodList]");
        return buffer.toString();
    }
}
