// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class DeclarationListClass extends DeclarationList {

    private DeclarationList DeclarationList;
    private ClassDeclaration ClassDeclaration;

    public DeclarationListClass (DeclarationList DeclarationList, ClassDeclaration ClassDeclaration) {
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
        this.ClassDeclaration=ClassDeclaration;
        if(ClassDeclaration!=null) ClassDeclaration.setParent(this);
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public ClassDeclaration getClassDeclaration() {
        return ClassDeclaration;
    }

    public void setClassDeclaration(ClassDeclaration ClassDeclaration) {
        this.ClassDeclaration=ClassDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.accept(visitor);
        if(ClassDeclaration!=null) ClassDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
        if(ClassDeclaration!=null) ClassDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        if(ClassDeclaration!=null) ClassDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationListClass(\n");

        if(DeclarationList!=null)
            buffer.append(DeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassDeclaration!=null)
            buffer.append(ClassDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationListClass]");
        return buffer.toString();
    }
}
