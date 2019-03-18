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
import blue.lions.revdoc.ast.FootnoteIDNode;
import blue.lions.revdoc.ast.FootnoteNode;
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.SingleLineParagraphNode;
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
    Rule Chapter() {
        return Sequence(
            push(new ChapterNode()),
            ZeroOrMore(FirstOf(BlankLine(), Comment())),
            ZeroOrMore(Block(), appendChild(), ZeroOrMore(FirstOf(BlankLine(), Comment())))
        );
    }

    /*
     * Block <- (Heading5 / Heading4 / Heading3 / Heading2 / Heading1 / Footnote / UnorderedList / Paragraph)
     *
     * 各要素はRule内で結果Nodeをpushすること。
     */
    Rule Block() {
        return FirstOf(
            Heading5(),
            Heading4(),
            Heading3(),
            Heading2(),
            Heading1(),
            Footnote(),
            UnorderedList(),
            Paragraph()
        );
    }

    /*
     * IsNotBlock <- !'#@#' !' *' !'//footnote'
     */
    Rule IsNotBlock() {
        return Sequence(
            TestNot("#@#"),
            TestNot(" *"),
            TestNot("//footnote")
        );
    }

    /*
     * Heading5 <- '=====' ('[' LimitedText(']') ']')? Space* Text NewLine?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading5() {
        return Heading(5);
    }

    /*
     * Heading4 <- '====' ('[' LimitedText(']') ']')? Space* Text NewLine?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading4() {
        return Heading(4);
    }

    /*
     * Heading3 <- '===' ('[' LimitedText(']') ']')? Space* Text NewLine?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading3() {
        return Heading(3);
    }

    /*
     * Heading2 <- '==' ('[' LimitedText(']') ']')? Space* Text NewLine?
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading2() {
        return Heading(2);
    }

    /*
     * Heading1 <- '=' ('[' LimitedText(']') ']')? Space* Text NewLine?
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
            Optional(FirstOf(Sequence("[", LimitedText(TestNot("]")), "]"), push(""))),
            ZeroOrMore(Space()),
            Text(),
            push(new HeadingNode(level, new TextNode(popAs()), popAs())),
            Optional(NewLine())
        );
    }

    /*
     * Footnote <- '//footnote[' LimitedText(!']') '][' SingleLineParagraph(!']') ']' NewLine?
     */
    Rule Footnote() {
        return Sequence(
            "//footnote[",
            LimitedText(TestNot("]")),
            "][",
            SingleLineParagraph(TestNot("]")),
            swap(),
            push(new FootnoteNode(popAs(), popAs())),
            "]",
            Optional(NewLine())
        );
    }

    /*
     * UnorderedList <- UnorderedListItemNode+ NewLine?
     */
    Rule UnorderedList() {
        return Sequence(
            push(new UnorderedListNode()),
            OneOrMore(UnorderedListItem(), appendChild()),
            Optional(NewLine())
        );
    }

    /*
     * UnorderedListItemNode <- '*'+ Space? SingleLineParagraph(&ANY) NewLine?
     */
    Rule UnorderedListItem() {
        return Sequence(
            " ",
            OneOrMore("*"),
            push(match()),
            Optional(Space()),
            SingleLineParagraph(Test(ANY)),
            swap(),
            push(new UnorderedListItemNode(((String) pop()).length(), popAs())),
            Optional(NewLine())
        );
    }

    /*
     * Paragraph <- ((Inline / Text) NewLine?)+
     */
    Rule Paragraph() {
        return Sequence(
            push(new ParagraphNode()),
            OneOrMore(FirstOf(
                Sequence(Inline(), appendChild()),
                Sequence(Text(), push(new TextNode(popAs())), appendChild())
            ), Optional(NewLine()))
        );
    }

    /*
     * SingleLineParagraph <- (Inline / Text)+ NewLine?
     */
    Rule SingleLineParagraph(Rule rule) {
        return Sequence(
            push(new SingleLineParagraphNode()),
            OneOrMore(FirstOf(
                Sequence(Inline(), appendChild()),
                Sequence(LimitedText(rule), push(new TextNode(popAs())), appendChild())
            )),
            Optional(NewLine())
        );
    }

    /*
     * Inline <- FootnoteID
     */
    Rule Inline() {
        return FootnoteID();
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
     * Comment <- '#@#' Text? NewLine?
     *
     * Textがpushした文字列は破棄する。
     */
    Rule Comment() {
        return Sequence(
            "#@#",
            Optional(Sequence(Text(), drop())),
            Optional(NewLine())
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
     * NormalCharacter <- !NewLine ANY
     */
    Rule NormalCharacter() {
        return Sequence(
            TestNot(NewLine()),
            ANY
        );
    }

    /*
     * Space <- (' ' / '\t')+
     */
    Rule Space() {
        return OneOrMore(
            FirstOf(" ", "\t")
        );
    }

    /*
     * NewLine <- '\n' / ('\r' '\n'?)
     */
    Rule NewLine() {
        return FirstOf(
            "\n",
            Sequence("\r", Optional("\n"))
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
