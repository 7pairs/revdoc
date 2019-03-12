/*
 * Copyright 2019 Jun-ya HASEBA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blue.lions.revdoc.reader.review;

import blue.lions.revdoc.ast.DocumentNode;
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.TextNode;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/*
 * Re:VIEWパーサー。
 */
@BuildParseTree
class ReviewParser extends BaseParser<Object> {

    /*
     * Document <- Block *
     */
    Rule Document() {
        return Sequence(
            push(new DocumentNode()),
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
            push(new HeadingNode((TextNode) pop())),
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
