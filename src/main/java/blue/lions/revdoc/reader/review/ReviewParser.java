package blue.lions.revdoc.reader.review;

import blue.lions.revdoc.ast.Heading1Node;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.RootNode;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
class ReviewParser extends BaseParser<Object> {

    Rule Root() {
        return Sequence(
            push(new RootNode()),
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
            OneOrMore(NormalCharacter()),
            push(new Heading1Node(match())),
            Newline()
        );
    }

    Rule Paragraph() {
        return Sequence(
            OneOrMore(
                OneOrMore(NormalCharacter()),
                Newline()
            ),
            push(new ParagraphNode(match())),
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
