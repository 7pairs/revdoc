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

import static org.assertj.core.api.Assertions.*;

import blue.lions.revdoc.ast.ChapterNode;
import blue.lions.revdoc.ast.FootnoteIDNode;
import blue.lions.revdoc.ast.FootnoteNode;
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.SingleLineParagraphNode;
import blue.lions.revdoc.ast.TextNode;
import blue.lions.revdoc.ast.UnorderedListItemNode;
import blue.lions.revdoc.ast.UnorderedListNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class ReviewParserTest {

    @Test
    @DisplayName("ReviewParser : パースを実行したとき : 戻り値のトップが ChapterNode であること")
    public void ReviewParser_parse_TopOfReturnValueIsChapterNode() {
        final Class EXPECTED = ChapterNode.class;

        String review = "ドーモ、世界=サン。ニンジャスレイヤーです。";
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue).isInstanceOf(EXPECTED);
    }

    @Test
    @DisplayName("ReviewParser : Heading1 をパースしたとき : HeadingNode に変換されること")
    public void ReviewParser_parseHeading1_convertHeadingNode() {
        final Class EXPECTED_CLASS = HeadingNode.class;
        final int EXPECTED_LEVEL = 1;
        final String EXPECTED_TEXT1 = "見出し1";
        final String EXPECTED_TEXT2 = "見出し2";
        final String EXPECTED_ID = "heading_id";

        String review = String.format("= %s\n\n=[%s] %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode1.getId()).isBlank();

        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);

        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);

        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
    }

    @Test
    @DisplayName("ReviewParser : Heading2 をパースしたとき : HeadingNode に変換されること")
    public void ReviewParser_parseHeading2_convertHeadingNode() {
        final Class EXPECTED_CLASS = HeadingNode.class;
        final int EXPECTED_LEVEL = 2;
        final String EXPECTED_TEXT1 = "見出し1";
        final String EXPECTED_TEXT2 = "見出し2";
        final String EXPECTED_ID = "heading_id";

        String review = String.format("== %s\n\n==[%s] %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode1.getId()).isBlank();

        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);

        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);

        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
    }

    @Test
    @DisplayName("ReviewParser : Heading3 をパースしたとき : HeadingNode に変換されること")
    public void ReviewParser_parseHeading3_convertHeadingNode() {
        final Class EXPECTED_CLASS = HeadingNode.class;
        final int EXPECTED_LEVEL = 3;
        final String EXPECTED_TEXT1 = "見出し1";
        final String EXPECTED_TEXT2 = "見出し2";
        final String EXPECTED_ID = "heading_id";

        String review = String.format("=== %s\n\n===[%s] %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode1.getId()).isBlank();

        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);

        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);

        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
    }

    @Test
    @DisplayName("ReviewParser : Heading4 をパースしたとき : HeadingNode に変換されること")
    public void ReviewParser_parseHeading4_convertHeadingNode() {
        final Class EXPECTED_CLASS = HeadingNode.class;
        final int EXPECTED_LEVEL = 4;
        final String EXPECTED_TEXT1 = "見出し1";
        final String EXPECTED_TEXT2 = "見出し2";
        final String EXPECTED_ID = "heading_id";

        String review = String.format("==== %s\n\n====[%s] %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode1.getId()).isBlank();

        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);

        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);

        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
    }

    @Test
    @DisplayName("ReviewParser : Heading5 をパースしたとき : HeadingNode に変換されること")
    public void ReviewParser_parseHeading5_convertHeadingNode() {
        final Class EXPECTED_CLASS = HeadingNode.class;
        final int EXPECTED_LEVEL = 5;
        final String EXPECTED_TEXT1 = "見出し1";
        final String EXPECTED_TEXT2 = "見出し2";
        final String EXPECTED_ID = "heading_id";

        String review = String.format("===== %s\n\n=====[%s] %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode1.getId()).isBlank();

        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);

        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);

        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);

        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
    }

    @Test
    @DisplayName("ReviewParser : Footnote をパースしたとき : FootnoteNode に変換されること")
    public void ReviewParser_parseFootnote_convertFootnoteNode() {
        final Class EXPECTED_CLASS = FootnoteNode.class;
        final String EXPECTED_ID1 = "footnote1";
        final String EXPECTED_TEXT1 = "ひとつ目の脚注です。";
        final String EXPECTED_ID2 = "footnote2";
        final String EXPECTED_TEXT2 = "ふたつ目の脚注です。";

        String review = String.format(
            "//footnote[%s][%s]\n//footnote[%s][%s]\n",
            EXPECTED_ID1,
            EXPECTED_TEXT1,
            EXPECTED_ID2,
            EXPECTED_TEXT2
        );
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        FootnoteNode footnoteNode1 = (FootnoteNode) result.resultValue.getChildren().get(0);
        assertThat(footnoteNode1.getId()).isEqualTo(EXPECTED_ID2);

        SingleLineParagraphNode singleLineParagraphNode1
            = (SingleLineParagraphNode) footnoteNode1.getChildren().get(0);
        TextNode textNode1 = (TextNode) singleLineParagraphNode1.getChildren().get(0);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT2);

        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);

        FootnoteNode footnoteNode2 = (FootnoteNode) result.resultValue.getChildren().get(1);
        assertThat(footnoteNode2.getId()).isEqualTo(EXPECTED_ID1);

        SingleLineParagraphNode singleLineParagraphNode2
            = (SingleLineParagraphNode) footnoteNode2.getChildren().get(0);
        TextNode textNode2 = (TextNode) singleLineParagraphNode2.getChildren().get(0);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT1);
    }

    @Test
    @DisplayName("ReviewParser : UnorderedList をパースしたとき : UnorderedListNode に変換されること")
    public void ReviewParser_parseUnorderedList_convertUnorderedListNode() {
        final Class EXPECTED_CLASS = UnorderedListNode.class;
        final String EXPECTED_ITEM1 = "山川 穂高";
        final String EXPECTED_SUB_ITEM = "2018年 パ・リーグMVP";
        final String EXPECTED_ITEM2 = "多和田 真三郎";
        final String EXPECTED_ITEM3 = "外崎 修汰";

        String review = String.format(
            " * %s\n ** %s\n * %s\n * %s\n",
            EXPECTED_ITEM1,
            EXPECTED_SUB_ITEM,
            EXPECTED_ITEM2,
            EXPECTED_ITEM3
        );
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        UnorderedListNode unorderedListNode = (UnorderedListNode) result.resultValue.getChildren().get(0);
        assertThat(unorderedListNode.getChildren().size()).isEqualTo(4);

        UnorderedListItemNode unorderedListItemNode1 = (UnorderedListItemNode) unorderedListNode.getChildren().get(0);
        UnorderedListItemNode unorderedListItemNode2 = (UnorderedListItemNode) unorderedListNode.getChildren().get(1);
        UnorderedListItemNode unorderedListItemNode3 = (UnorderedListItemNode) unorderedListNode.getChildren().get(2);
        UnorderedListItemNode unorderedListItemNode4 = (UnorderedListItemNode) unorderedListNode.getChildren().get(3);
        assertThat(unorderedListItemNode1.getLevel()).isEqualTo(1);
        assertThat(unorderedListItemNode2.getLevel()).isEqualTo(2);
        assertThat(unorderedListItemNode3.getLevel()).isEqualTo(1);
        assertThat(unorderedListItemNode4.getLevel()).isEqualTo(1);

        SingleLineParagraphNode singleLineParagraphNode1
            = (SingleLineParagraphNode) unorderedListItemNode1.getChildren().get(0);
        TextNode textNode1 = (TextNode) singleLineParagraphNode1.getChildren().get(0);
        SingleLineParagraphNode singleLineParagraphNode2
            = (SingleLineParagraphNode) unorderedListItemNode2.getChildren().get(0);
        TextNode textNode2 = (TextNode) singleLineParagraphNode2.getChildren().get(0);
        SingleLineParagraphNode singleLineParagraphNode3
            = (SingleLineParagraphNode) unorderedListItemNode3.getChildren().get(0);
        TextNode textNode3 = (TextNode) singleLineParagraphNode3.getChildren().get(0);
        SingleLineParagraphNode singleLineParagraphNode4
            = (SingleLineParagraphNode) unorderedListItemNode4.getChildren().get(0);
        TextNode textNode4 = (TextNode) singleLineParagraphNode4.getChildren().get(0);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_ITEM1);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_SUB_ITEM);
        assertThat(textNode3.getText()).isEqualTo(EXPECTED_ITEM2);
        assertThat(textNode4.getText()).isEqualTo(EXPECTED_ITEM3);
    }

    @Test
    @DisplayName("ReviewParser : Paragraph をパースしたとき : ParagraphNode に変換されること")
    public void ReviewParser_parseParagraph_convertParagraphNode() {
        final Class EXPECTED_CLASS = ParagraphNode.class;
        final String EXPECTED_TEXT1 = "吾輩は猫である。";
        final String EXPECTED_TEXT2 = "名前はまだ無い。";
        final String EXPECTED_TEXT3 = "どこで生れたかとんと見当がつかぬ。";

        String review = String.format("%s\n%s\n\n%s\n", EXPECTED_TEXT1, EXPECTED_TEXT2, EXPECTED_TEXT3);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        ParagraphNode paragraphNode1 = (ParagraphNode) result.resultValue.getChildren().get(0);
        TextNode textNode1 = (TextNode) paragraphNode1.getChildren().get(0);
        TextNode textNode2 = (TextNode) paragraphNode1.getChildren().get(1);
        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);

        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);

        ParagraphNode paragraphNode2 = (ParagraphNode) result.resultValue.getChildren().get(1);
        TextNode textNode3 = (TextNode) paragraphNode2.getChildren().get(0);
        assertThat(textNode3.getText()).isEqualTo(EXPECTED_TEXT3);
    }

    @Test
    @DisplayName("ReviewParser : FootnoteID をパースしたとき : FootnoteIDNode に変換されること")
    public void ReviewParser_parseFootnoteID_convertFootnoteIDNode() {
        final Class EXPECTED_CLASS = FootnoteIDNode.class;
        final String EXPECTED_ID1 = "fn1";
        final String EXPECTED_ID2 = "fn2";

        String review = String.format("パグリアルーロ@<fn>{%s}とシアンフロッコ@<fn>{%s}", EXPECTED_ID1, EXPECTED_ID2);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        ParagraphNode paragraphNode = (ParagraphNode) result.resultValue.getChildren().get(0);
        assertThat(paragraphNode.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
        assertThat(paragraphNode.getChildren().get(3)).isInstanceOf(EXPECTED_CLASS);

        FootnoteIDNode footnoteIDNode1 = (FootnoteIDNode) paragraphNode.getChildren().get(1);
        FootnoteIDNode footnoteIDNode2 = (FootnoteIDNode) paragraphNode.getChildren().get(3);
        assertThat(footnoteIDNode1.getId()).isEqualTo(EXPECTED_ID1);
        assertThat(footnoteIDNode2.getId()).isEqualTo(EXPECTED_ID2);
    }

    @Test
    @DisplayName("ReviewParser : Comment をパースしたとき : 無視されること")
    public void ReviewParser_parseComment_beIgnored() {
        final Class EXPECTED_CLASS = ParagraphNode.class;
        final String EXPECTED_TEXT = "スシを食べすぎるな";

        String review = String.format("#@# コメント\n%s\n#@#\n", EXPECTED_TEXT);
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ParentNode> result = new ReportingParseRunner<ParentNode>(reviewParser.Chapter()).run(review);

        assertThat(result.resultValue.getChildren().size()).isEqualTo(1);
        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);

        ParagraphNode paragraphNode = (ParagraphNode) result.resultValue.getChildren().get(0);
        TextNode textNode = (TextNode) paragraphNode.getChildren().get(0);
        assertThat(textNode.getText()).isEqualTo(EXPECTED_TEXT);
    }
}
