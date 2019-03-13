package blue.lions.revdoc.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentNode extends Node {

    private List<Node> children = new ArrayList<>();

    public void appendChild(Node node) {
        children.add(node);
    }

    public void appendChildren(List<Node> children) {
        this.children.addAll(children);
    }
}
