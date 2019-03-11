package blue.lions.revdoc.ast;

public class ParagraphNode extends ParentNode {

    public ParagraphNode(TextNode textNode) {
        super(textNode);
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
