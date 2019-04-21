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

import blue.lions.revdoc.ast.ChapterNode;
import blue.lions.revdoc.ast.ColumnNode;
import blue.lions.revdoc.ast.FootnoteIDNode;
import blue.lions.revdoc.ast.FootnoteNode;
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.ImageNode;
import blue.lions.revdoc.ast.InnerParagraphNode;
import blue.lions.revdoc.ast.LinkNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.OrderedListItemNode;
import blue.lions.revdoc.ast.OrderedListNode;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.TextNode;
import blue.lions.revdoc.ast.UnorderedListItemNode;
import blue.lions.revdoc.ast.UnorderedListNode;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/*
 * Re:VIEWフォーマットのパーサー。
 */
@BuildParseTree
class ReviewParser extends BaseParser<Object> {

    /*
     * Chapter <- (BlankLine / Comment)* (Block (BlankLine / Comment)*)*
     *
     * Block要素はRule内で結果Nodeをpushしている。
     * その結果NodeをChapterNodeの子に追加する。
     */
    Rule Chapter(String id) {
        return Sequence(
            push(new ChapterNode(id)),
            ZeroOrMore(FirstOf(BlankLine(), Comment())),
            ZeroOrMore(Block(), appendChild(), ZeroOrMore(FirstOf(BlankLine(), Comment())))
        );
    }

    /*
     * Block <- (
     *     Column /
     *     Heading5 /
     *     Heading4 /
     *     Heading3 /
     *     Heading2 /
     *     Heading1 /
     *     Image /
     *     Footnote /
     *     UnorderedList /
     *     OrderedList /
     *     Paragraph
     * )
     *
     * 各要素はRule内で結果Nodeをpushすること。
     */
    Rule Block() {
        return FirstOf(
            Column(),
            Heading5(),
            Heading4(),
            Heading3(),
            Heading2(),
            Heading1(),
            Image(),
            Footnote(),
            UnorderedList(),
            OrderedList(),
            Paragraph()
        );
    }

    /*
     * IsNotBlock <- !'#@#' !'=' !' *' !'//footnote' !'//image'
     */
    Rule IsNotBlock() {
        return Sequence(
            TestNot("#@#"),
            TestNot("="),
            TestNot(" *"),
            TestNot("//footnote"),
            TestNot("//image")
        );
    }

    /*
     * Column <- '='+ '[column]' Space_old* Text NewLine_old (BlankLine / Comment)* (BlockInColumn (BlankLine / Comment)*)*
     *           '='+ '[/column]' NewLine_old?
     */
    Rule Column() {
        return Sequence(
            OneOrMore("="),
            "[column]",
            ZeroOrMore(Space_old()),
            Text(),
            push(new ColumnNode(popAs())),
            NewLine_old(),
            ZeroOrMore(FirstOf(BlankLine(), Comment())),
            ZeroOrMore(BlockInColumn(), appendChild(), ZeroOrMore(FirstOf(BlankLine(), Comment()))),
            ZeroOrMore(NewLine_old()),
            OneOrMore("="),
            "[/column]",
            Optional(NewLine_old())
        );
    }

    /*
     * BlockInColumn <- (Image / Footnote / UnorderedList / OrderedList / Paragraph)
     */
    Rule BlockInColumn() {
        return FirstOf(
            Image(),
            Footnote(),
            UnorderedList(),
            OrderedList(),
            Paragraph()
        );
    }

    /*
     * Heading5 <- '=====' ('[' LimitedText(']') ']')? Space_old* Text NewLine_old?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading5() {
        return Heading(5);
    }

    /*
     * Heading4 <- '====' ('[' LimitedText(']') ']')? Space_old* Text NewLine_old?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading4() {
        return Heading(4);
    }

    /*
     * Heading3 <- '===' ('[' LimitedText(']') ']')? Space_old* Text NewLine_old?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading3() {
        return Heading(3);
    }

    /*
     * Heading2 <- '==' ('[' LimitedText(']') ']')? Space_old* Text NewLine_old?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading2() {
        return Heading(2);
    }

    /*
     * Heading1 <- '=' ('[' LimitedText(']') ']')? Space_old* Text NewLine_old?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading1() {
        return Heading(1);
    }

    /*
     * Heading1〜5の共通Rule。
     *
     * @param level 見出しレベル
     * @return 構築した @{code Rule}
     */
    Rule Heading(int level) {
        return Sequence(
            "=====".substring(5 - level),
            Optional(FirstOf(Sequence("{", LimitedText(TestNot("}")), "}"), push(""))),
            ZeroOrMore(Space_old()),
            Text(),
            push(new HeadingNode(level, new TextNode(popAs()), popAs())),
            Optional(NewLine_old())
        );
    }

    /*
     * Image <- '//image[' LimitedText(']') '][' LimitedText(']') ']' ('[scale=' LimitedText(']') ']')? '{'
     *          (!'//}' ANY)* '//}'
     */
    Rule Image() {
        return Sequence(
            "//image[",
            LimitedText(TestNot("]")),
            "][",
            LimitedText(TestNot("]")),
            "]",
            Optional(FirstOf(
                Sequence("[scale=", LimitedText(TestNot("]")), "]"),
                push("")
            )),
            swap3(),
            push(new ImageNode(popAs(), popAs(), popAs())),
            "{",
            ZeroOrMore(TestNot("//}"), ANY),
            "//}"
        );
    }

    /*
     * Footnote <- '//footnote[' LimitedText(!']') '][' InnerParagraph(!']') ']' NewLine_old?
     */
    Rule Footnote() {
        return Sequence(
            "//footnote[",
            LimitedText(TestNot("]")),
            "][",
            InnerParagraph(TestNot("]")),
            swap(),
            push(new FootnoteNode(popAs(), popAs())),
            "]",
            Optional(NewLine_old())
        );
    }

    /*
     * UnorderedList <- UnorderedListItemNode+ NewLine_old?
     */
    Rule UnorderedList() {
        return Sequence(
            push(new UnorderedListNode()),
            OneOrMore(UnorderedListItem(), appendChild()),
            Optional(NewLine_old())
        );
    }

    /*
     * UnorderedListItem <- ' ' '*'+ Space_old? InnerParagraph(&ANY) NewLine_old?
     */
    Rule UnorderedListItem() {
        return Sequence(
            " ",
            OneOrMore("*"),
            push(match()),
            Optional(Space_old()),
            InnerParagraph(Test(ANY)),
            swap(),
            push(new UnorderedListItemNode(((String) pop()).length(), popAs())),
            Optional(NewLine_old())
        );
    }

    /*
     * OrderedList <- OrderedListItem+ NewLine_old?
     */
    Rule OrderedList() {
        return Sequence(
            push(new OrderedListNode()),
            OneOrMore(OrderedListItem(), appendChild()),
            Optional(NewLine_old())
        );
    }

    /*
     * OrderedListItem <- ' ' ['1'-'9'] ['0'-'9']* '.' Space_old? InnerParagraph(&ANY) NewLine_old?
     */
    Rule OrderedListItem() {
        return Sequence(
            " ",
            CharRange('1', '9'),
            ZeroOrMore(CharRange('0', '9')),
            ".",
            Optional(Space_old()),
            InnerParagraph(Test(ANY)),
            push(new OrderedListItemNode(popAs())),
            Optional(NewLine_old())
        );
    }

    /*
     * Paragraph <- ((Inline / Text) NewLine_old?)+
     */
    Rule Paragraph() {
        return Sequence(
            push(new ParagraphNode()),
            OneOrMore(FirstOf(
                Sequence(Inline(), appendChild()),
                Sequence(Text(), push(new TextNode(popAs())), appendChild())
            ), Optional(NewLine_old()))
        );
    }

    /*
     * InnerParagraph <- (Inline / Text)+ NewLine_old?
     */
    Rule InnerParagraph(Rule rule) {
        return Sequence(
            push(new InnerParagraphNode()),
            OneOrMore(FirstOf(
                Sequence(Inline(), appendChild()),
                Sequence(LimitedText(rule), push(new TextNode(popAs())), appendChild())
            )),
            Optional(NewLine_old())
        );
    }

    /*
     * Inline <- FootnoteID / Link
     */
    Rule Inline() {
        return FirstOf(
            FootnoteID(),
            Link()
        );
    }

    /*
     * FootnoteID <- '@<fn>{' LimitedText(!'}') '}'
     */
    Rule FootnoteID() {
        return Sequence(
            "@<fn>{",
            LimitedText(TestNot("}")),
            push(new FootnoteIDNode(popAs())),
            "}"
        );
    }

    /*
     * Link <- '@<href>{' LimitedText(!',' !'}') (',' Space_old* LimitedText(!'}'))? '}'
     */
    Rule Link() {
        return Sequence(
            "@<href>{",
            LimitedText(Sequence(TestNot(","), TestNot("}"))),
            FirstOf(
                Sequence(",", ZeroOrMore(Space_old()), LimitedText(TestNot("}"))),
                push("")
            ),
            swap(),
            push(new LinkNode(popAs(), popAs())),
            "}"
        );
    }

    /*
     * Comment <- '#@#' Text? NewLine_old?
     *
     * Textがpushした文字列は破棄する。
     */
    Rule Comment() {
        return Sequence(
            "#@#",
            Optional(Sequence(Text(), drop())),
            Optional(NewLine_old())
        );
    }

    /*
     * Text <- IsNotBlock (!Inline NormalCharacter)+
     *
     * 共通RuleのLimitedTextに任せる。
     */
    Rule Text() {
        return LimitedText(Test(ANY));
    }

    /*
     * LimitedText <- IsNotBlock (rule !Inline NormalCharacter)*
     *
     * マッチした文字列をpushする。
     */
    Rule LimitedText(Rule rule) {
        return Sequence(
            IsNotBlock(),
            OneOrMore(rule, TestNot(Inline()), NormalCharacter()),
            push(match())
        );
    }

    /*
     * NormalCharacter <- !NewLine_old ANY
     */
    Rule NormalCharacter() {
        return Sequence(
            TestNot(NewLine_old()),
            ANY
        );
    }

    /*
     * Space_old <- (' ' / '\t')+
     */
    Rule Space_old() {
        return OneOrMore(
            FirstOf(" ", "\t")
        );
    }

    /*
     * NewLine_old <- '\n' / ('\r' '\n'?)
     */
    Rule NewLine_old() {
        return FirstOf(
            "\n",
            Sequence("\r", Optional("\n"))
        );
    }

    /*
     * BlankLine <- Space_old* NewLine_old
     */
    Rule BlankLine() {
        return Sequence(
            ZeroOrMore(Space_old()),
            NewLine_old()
        );
    }





    /*
     * Newline <- "\r\n" / "\n"
     */
    Rule Newline() {
        return FirstOf(
            "\r\n",
            "\n"
        );
    }

    /*
     * BlankLines <- ([ \t]* Newline)*
     */
    Rule BlankLines() {
        return ZeroOrMore(
            Sequence(
                ZeroOrMore(AnyOf(new char[] {' ', '\t'})),
                Newline()
            )
        );
    }

    /*
     * Spacer <- [ \t\r\n]*
     */
    Rule Spacer() {
        return ZeroOrMore(
            AnyOf(new char[] {' ', '\t', '\r', '\n'})
        );
    }

    /*
     * Space <- [ 　\t]
     */
    Rule Space() {
        return AnyOf(
            new char[] {' ', '　', '\t'}
        );
    }

    /*
     * 子ノードを追加する。
     *
     * スタックのトップに子ノード、その次に親となるノードが積まれている必要がある。
     *
     * @return {@code true}
     */
    boolean appendChild() {
        // 子ノードを追加する
        Node child = popAs();
        ParentNode parent = peekAs();
        if (child instanceof FootnoteNode) {
            parent.appendChild(0, child);
        } else {
            parent.appendChild(child);
        }
        return true;
    }

    /*
     * {@code pop()} の結果を代入先に応じた型で取得する。
     *
     * @return {@code pop()} の結果
     */
    <T> T popAs() {
        // 代入先に応じた型で返す
        return (T) pop();
    }

    /*
     * {@code peek()} の結果を代入先に応じた型で取得する。
     *
     * @return {@code peek()} の結果
     */
    <T> T peekAs() {
        // 代入先に応じた型で返す
        return (T) peek();
    }
}
