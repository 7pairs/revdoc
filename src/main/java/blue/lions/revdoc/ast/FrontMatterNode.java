package blue.lions.revdoc.ast;

public class FrontMatterNode extends ParentNode {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
