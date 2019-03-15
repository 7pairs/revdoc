package blue.lions.revdoc.writer.word;

import blue.lions.revdoc.ast.AppendixNode;
import blue.lions.revdoc.ast.BackMatterNode;
import blue.lions.revdoc.ast.BodyMatterNode;
import blue.lions.revdoc.ast.ChapterNode;
import blue.lions.revdoc.ast.FrontMatterNode;
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.PartNode;
import blue.lions.revdoc.ast.RootNode;
import blue.lions.revdoc.ast.TextNode;
import blue.lions.revdoc.ast.Visitor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordVisitor implements Visitor {

    XWPFDocument document;

    public WordVisitor(XWPFDocument document) {
        this.document = document;
    }

    public void accept(Node node) {
        node.accept(this);
        if (node instanceof ParentNode) {
            for (Node child : ((ParentNode) node).getChildren()) {
                accept(child);
            }
        }
    }

    @Override
    public void visit(RootNode node) {

    }

    @Override
    public void visit(FrontMatterNode node) {

    }

    @Override
    public void visit(BodyMatterNode node) {

    }

    @Override
    public void visit(AppendixNode node) {

    }

    @Override
    public void visit(BackMatterNode node) {

    }

    @Override
    public void visit(PartNode node) {

    }

    @Override
    public void visit(ChapterNode node) {

    }

    @Override
    public void visit(HeadingNode node) {

    }

    @Override
    public void visit(ParagraphNode node) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(((TextNode)node.getChildren().get(0)).getText());
    }

    @Override
    public void visit(TextNode node) {

    }
}
