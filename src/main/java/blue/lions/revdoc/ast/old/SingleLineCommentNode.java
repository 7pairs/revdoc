package blue.lions.revdoc.ast.old;

import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.Visitor;

public class SingleLineCommentNode extends Node {

    private String text;

    public SingleLineCommentNode(String text) {
        this.text = text;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
