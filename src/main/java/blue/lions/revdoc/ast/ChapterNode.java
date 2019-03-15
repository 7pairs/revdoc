package blue.lions.revdoc.ast;

public class ChapterNode extends ParentNode {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
