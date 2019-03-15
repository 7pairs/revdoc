package blue.lions.revdoc.ast;

public interface Visitor {

    void visit(RootNode node);

    void visit(FrontMatterNode node);

    void visit(BodyMatterNode node);

    void visit(AppendixNode node);

    void visit(BackMatterNode node);

    void visit(PartNode node);

    void visit(ChapterNode node);

    void visit(HeadingNode node);

    void visit(ParagraphNode node);

    void visit(TextNode node);
}
