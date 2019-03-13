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
 * Re:VIEWフォーマットのパーサー。
 */
@BuildParseTree
class ReviewParser extends BaseParser<Object> {

    /*
     * Document <- Block*
     */
    Rule Document() {
        return Sequence(
            push(new DocumentNode()),
            ZeroOrMore(Block(), appendChild())
        );
    }

    /*
     * Block <- (Heading5 / Heading4 / Heading3 / Heading2 / Heading1 / Paragraph)
     */
    Rule Block() {
        return FirstOf(
            Heading5(),
            Heading4(),
            Heading3(),
            Heading2(),
            Heading1(),
            Paragraph()
        );
    }

    /*
     * Heading1 <- "=" ("[" Text "]")? Space* Text NewLine
     */
    Rule Heading1() {
        return Sequence(
            "=",
            Optional(FirstOf(Sequence("[", Text(), push(match()), "]"), push(""))),
            ZeroOrMore(Space()),
            Text(),
            push(new HeadingNode(1, (Node) pop(), (String) pop())),
            NewLine()
        );
    }

    /*
     * Heading2 <- "==" ("[" Text "]")? Space* Text NewLine
     */
    Rule Heading2() {
        return Sequence(
            "==",
            Optional(FirstOf(Sequence("[", Text(), push(match()), "]"), push(""))),
            ZeroOrMore(Space()),
            Text(),
            push(new HeadingNode(2, (Node) pop(), (String) pop())),
            NewLine()
        );
    }

    /*
     * Heading3 <- "===" ("[" Text "]")? Space* Text NewLine
     */
    Rule Heading3() {
        return Sequence(
            "===",
            Optional(FirstOf(Sequence("[", Text(), push(match()), "]"), push(""))),
            ZeroOrMore(Space()),
            Text(),
            push(new HeadingNode(3, (Node) pop(), (String) pop())),
            NewLine()
        );
    }

    /*
     * Heading4 <- "====" ("[" Text "]")? Space* Text NewLine
     */
    Rule Heading4() {
        return Sequence(
            "====",
            Optional(FirstOf(Sequence("[", Text(), push(match()), "]"), push(""))),
            ZeroOrMore(Space()),
            Text(),
            push(new HeadingNode(4, (Node) pop(), (String) pop())),
            NewLine()
        );
    }

    /*
     * Heading5 <- "=====" ("[" Text "]")? Space* Text NewLine
     */
    Rule Heading5() {
        return Sequence(
            "=====",
            Optional(FirstOf(Sequence("[", Text(), push(match()), "]"), push(""))),
            ZeroOrMore(Space()),
            Text(),
            push(new HeadingNode(5, (Node) pop(), (String) pop())),
            NewLine()
        );
    }

    /*
     * Paragraph <- Text &((BlankLine / EOI)+)
     */
    Rule Paragraph() {
        return Sequence(
            Text(),
            push(new ParagraphNode((Node) pop())),
            Test(OneOrMore(FirstOf(BlankLine(), EOI)))
        );
    }

    /*
     * Text <- NormalCharacter+
     */
    Rule Text(String... s) {
        return Sequence(
            OneOrMore(NormalCharacter()),
            push(new TextNode(match()))
        );
    }

    /*
     * NormalCharacter <- !NewLine ANY
     */
    Rule NormalCharacter() {
        return Sequence(
            TestNot(NewLine()),
            ANY
        );
    }

    /*
     * Space <- (" " / "\t")+
     */
    Rule Space() {
        return OneOrMore(
            FirstOf(" ", "\t")
        );
    }

    /*
     * NewLine <- "\n" / ("\r" "\n"?)
     */
    Rule NewLine() {
        return FirstOf(
            '\n',
            Sequence('\r', Optional('\n'))
        );
    }

    /*
     * BlankLine <- Space* NewLine
     */
    Rule BlankLine() {
        return Sequence(
            ZeroOrMore(Space()),
            NewLine()
        );
    }

    /*
     * 親ノードに子ノードを追加する。
     *
     * スタックのトップに子ノード、その次に親ノードが積まれている必要がある。
     *
     * @return {@code true}
     */
    boolean appendChild() {
        // 親ノードに子ノードを追加する
        Node child = (Node) pop();
        ParentNode parent = (ParentNode) peek();
        parent.appendChild(child);
        return true;
    }
}
