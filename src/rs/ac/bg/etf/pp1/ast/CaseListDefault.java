// generated with ast extension for cup
// version 0.8
// 16/1/2026 23:10:56


package rs.ac.bg.etf.pp1.ast;

public class CaseListDefault extends CaseList {

    private CaseList CaseList;
    private DefaultCase DefaultCase;

    public CaseListDefault (CaseList CaseList, DefaultCase DefaultCase) {
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
        this.DefaultCase=DefaultCase;
        if(DefaultCase!=null) DefaultCase.setParent(this);
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public DefaultCase getDefaultCase() {
        return DefaultCase;
    }

    public void setDefaultCase(DefaultCase DefaultCase) {
        this.DefaultCase=DefaultCase;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CaseList!=null) CaseList.accept(visitor);
        if(DefaultCase!=null) DefaultCase.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
        if(DefaultCase!=null) DefaultCase.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        if(DefaultCase!=null) DefaultCase.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseListDefault(\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DefaultCase!=null)
            buffer.append(DefaultCase.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseListDefault]");
        return buffer.toString();
    }
}
