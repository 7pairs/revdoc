package blue.lions.revdoc.reader.review;

import blue.lions.revdoc.ast.ChapterNode;
import blue.lions.revdoc.ast.Heading1Node;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.TextNode;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
class ReviewParser extends BaseParser<Object> {

    Rule Root() {
        return Sequence(
            push(new ChapterNode()),
            ZeroOrMore(Block(), appendChild())
        );
    }

    Rule Block() {
        return Sequence(
            ZeroOrMore(BlankLine()),
            FirstOf(Heading1(), Paragraph())
        );
    }

    Rule Heading1() {
        return Sequence(
            "= ",
            Text(),
            push(new Heading1Node((TextNode) pop())),
            Newline()
        );
    }

    Rule Paragraph() {
        return Sequence(
            Text(),
            push(new ParagraphNode((TextNode) pop())),
            Test(FirstOf(OneOrMore(BlankLine()), EOI))
        );
    }

    Rule NormalCharacter() {
        return Sequence(
            TestNot(' '),
            TestNot(AnyOf("\n\r")),
            ANY
        );
    }

    Rule Text() {
        return Sequence(
            OneOrMore(OneOrMore(NormalCharacter()), Newline()),
            push(new TextNode(match()))
        );
    }

    Rule Newline() {
        return FirstOf('\n', Sequence('\r', Optional('\n')));
    }

    Rule BlankLine() {
        return Sequence(
            ZeroOrMore(' '),
            Newline()
        );
    }

    boolean appendChild() {
        Node child = (Node) pop();
        ParentNode parent = (ParentNode) peek();
        parent.appendChild(child);
        return true;
    }
}
