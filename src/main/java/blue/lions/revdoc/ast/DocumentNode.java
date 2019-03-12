package blue.lions.revdoc.ast;

public class DocumentNode extends ParentNode {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
