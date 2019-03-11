package blue.lions.revdoc.ast;

public class RootNode extends ParentNode {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
