package blue.lions.revdoc.ast;

public class SingleLineCommentNode extends Node {

    private String text;

    public SingleLineCommentNode(String text) {
        this.text = text;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
