package blue.lions.revdoc.ast;

public class TextNode extends Node {

    private String text;

    public TextNode(String text) {
        this.text = text;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
