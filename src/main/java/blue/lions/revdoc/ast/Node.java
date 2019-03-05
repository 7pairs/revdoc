package blue.lions.revdoc.ast;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {

    private List<Node> children;

    public Node() {
        children = new ArrayList<>();
    }

    public void appendChild(Node node) {
        children.add(node);
    }

    public void export(XWPFDocument document) {
        for (Node node : children) {
            node.export(document);
        }
    }
}
