package blue.lions.revdoc.ast;

public class BodyMatterNode extends ParentNode {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
