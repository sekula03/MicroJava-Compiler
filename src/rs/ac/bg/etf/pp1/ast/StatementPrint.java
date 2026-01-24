// generated with ast extension for cup
// version 0.8
// 23/0/2026 18:51:6


package rs.ac.bg.etf.pp1.ast;

public class StatementPrint extends Statement {

    private PrintDeclaration PrintDeclaration;

    public StatementPrint (PrintDeclaration PrintDeclaration) {
        this.PrintDeclaration=PrintDeclaration;
        if(PrintDeclaration!=null) PrintDeclaration.setParent(this);
    }

    public PrintDeclaration getPrintDeclaration() {
        return PrintDeclaration;
    }

    public void setPrintDeclaration(PrintDeclaration PrintDeclaration) {
        this.PrintDeclaration=PrintDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PrintDeclaration!=null) PrintDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrintDeclaration!=null) PrintDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrintDeclaration!=null) PrintDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrint(\n");

        if(PrintDeclaration!=null)
            buffer.append(PrintDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrint]");
        return buffer.toString();
    }
}
