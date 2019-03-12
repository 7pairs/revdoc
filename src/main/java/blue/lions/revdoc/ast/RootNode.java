package blue.lions.revdoc.ast;

public class RootNode extends ParentNode {

    private String title;

    public RootNode(String title) {
        this.title = title;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
