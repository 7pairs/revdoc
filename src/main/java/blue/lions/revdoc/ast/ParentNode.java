package blue.lions.revdoc.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentNode extends Node {

    private List<Node> children;

    public ParentNode() {
        children = new ArrayList<>();
    }

    public void appendChild(Node node) {
        children.add(node);
    }

}
