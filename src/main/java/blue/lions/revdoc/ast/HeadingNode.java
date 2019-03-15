package blue.lions.revdoc.ast;

public class HeadingNode extends ParentNode {

    private int level;

    private String id;

    public HeadingNode(int level, Node child, String id) {
        this.level = level;
        appendChild(child);
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public String getId() {
        return id;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
