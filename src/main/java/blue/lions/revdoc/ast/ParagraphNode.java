package blue.lions.revdoc.ast;

public class ParagraphNode extends ParentNode {

    public ParagraphNode(Node child) {
        appendChild(child);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
