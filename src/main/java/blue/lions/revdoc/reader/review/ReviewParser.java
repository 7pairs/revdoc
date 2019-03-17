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
     * Chapter <- (BlankLine* (Block / Comment))*
     *
     * Block要素はRule内で結果Nodeをpushしている。
     * その結果NodeをChapterNodeの子に追加する。
     */
    Rule Chapter() {
        return Sequence(
            push(new ChapterNode()),
            ZeroOrMore(ZeroOrMore(FirstOf(BlankLine(), Comment())), Block(), appendChild())
        );
    }

    /*
     * Block <- (Heading5 / Heading4 / Heading3 / Heading2 / Heading1 / Paragraph)
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
            Paragraph()
        );
    }

    /*
     * Heading5 <- '=====' ('[' LimitedText(']') ']')? Space* Text NewLine
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading5() {
        return Heading(5);
    }

    /*
     * Heading4 <- '====' ('[' LimitedText(']') ']')? Space* Text NewLine
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading4() {
        return Heading(4);
    }

    /*
     * Heading3 <- '===' ('[' LimitedText(']') ']')? Space* Text NewLine
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading3() {
        return Heading(3);
    }

    /*
     * Heading2 <- '==' ('[' LimitedText(']') ']')? Space* Text NewLine
     *
     * 共通RuleのHeadingに任せる。
     */
    Rule Heading2() {
        return Heading(2);
    }

    /*
     * Heading1 <- '=' ('[' LimitedText(']') ']')? Space* Text NewLine
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
            Optional(FirstOf(Sequence("[", LimitedText("]"), "]"), push(""))),
            ZeroOrMore(Space()),
            Text(),
            push(new HeadingNode(level, new TextNode(popAs()), popAs())),
            NewLine()
        );
    }

    /*
     * Paragraph <- ((Inline / Text) NewLine?)+
     *
     * 複数行にわたる場合は、改行文字を除外して連結する。
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
     * Inline <- FootnoteID
     */
    Rule Inline() {
        return FootnoteID();
    }

    /*
     * FootnoteID <- '@<fn>{' LimitedText('}') '}'
     */
    Rule FootnoteID() {
        return Sequence(
            "@<fn>{",
            LimitedText("}"),
            push(new FootnoteIDNode(popAs())),
            "}"
        );
    }

    /*
     * Comment <- '#@#' Text? NewLine
     *
     * Textがpushした文字列は破棄する。
     */
    Rule Comment() {
        return Sequence(
            "#@#",
            Optional(Text()),
            drop(),
            NewLine()
        );
    }

    /*
     * Text <- !'#@#' (!Inline NormalCharacter)+
     *
     * マッチした文字列をpushする。
     */
    Rule Text() {
        return Sequence(
            TestNot("#@#"),
            OneOrMore(TestNot(Inline()), NormalCharacter()),
            push(match())
        );
    }

    /*
     * LimitedText <- (!exclusion NormalCharacter)*
     *
     * マッチした文字列をpushする。
     */
    Rule LimitedText(String exclusion) {
        return Sequence(
            OneOrMore(TestNot(exclusion), NormalCharacter()),
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
        parent.appendChild(child);
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
