package blue.lions.revdoc.parser;

import blue.lions.revdoc.ast.Heading1Node;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.RootNode;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class ReviewParser extends BaseParser<Object> {

    public Rule Root() {
        return Sequence(
            push(new RootNode()),
            ZeroOrMore(Block(), appendChild())
        );
    }

    public Rule Block() {
        return Sequence(
            ZeroOrMore(BlankLine()),
            FirstOf(Heading1(), Paragraph())
        );
    }

    public Rule Heading1() {
        return Sequence(
            "= ",
            OneOrMore(NormalCharacter()),
            push(new Heading1Node(match())),
            Newline()
        );
    }

    public Rule Paragraph() {
        return Sequence(
            OneOrMore(
                OneOrMore(NormalCharacter()),
                Newline()
            ),
            push(new ParagraphNode(match())),
            Test(FirstOf(OneOrMore(BlankLine()), EOI))
        );
    }

    public Rule NormalCharacter() {
        return Sequence(
            TestNot(' '),
            TestNot(AnyOf("\n\r")),
            ANY
        );
    }

    public Rule Newline() {
        return FirstOf('\n', Sequence('\r', Optional('\n')));
    }

    public Rule BlankLine() {
        return Sequence(
            ZeroOrMore(' '),
            Newline()
        );
    }

    public boolean appendChild() {
        Node child = (Node) pop();
        Node parent = (Node) peek();
        parent.appendChild(child);
        return true;
    }
}
