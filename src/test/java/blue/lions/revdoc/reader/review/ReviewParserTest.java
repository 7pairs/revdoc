/*
 * Copyright 2019 HASEBA, Junya
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

//import static org.assertj.core.api.Assertions.*;
//
//import blue.lions.revdoc.ast.old.ChapterNode;
//import blue.lions.revdoc.ast.old.ColumnNode;
//import blue.lions.revdoc.ast.old.FootnoteIDNode;
//import blue.lions.revdoc.ast.old.FootnoteNode;
//import blue.lions.revdoc.ast.old.HeadingNode;
//import blue.lions.revdoc.ast.old.ImageNode;
//import blue.lions.revdoc.ast.old.InnerParagraphNode;
//import blue.lions.revdoc.ast.old.LinkNode;
//import blue.lions.revdoc.ast.old.OrderedListItemNode;
//import blue.lions.revdoc.ast.old.OrderedListNode;
//import blue.lions.revdoc.ast.old.ParagraphNode;
//import blue.lions.revdoc.ast.ParentNode;
//import blue.lions.revdoc.ast.old.TextNode;
//import blue.lions.revdoc.ast.old.UnorderedListItemNode;
//import blue.lions.revdoc.ast.old.UnorderedListNode;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.parboiled.Parboiled;
//import org.parboiled.parserunners.ReportingParseRunner;
//import org.parboiled.support.ParsingResult;
//
//import java.math.BigDecimal;

public class ReviewParserTest {

//    @Test
//    @DisplayName("ReviewParser : パースを実行したとき : 戻り値のトップが ChapterNode であること")
//    public void ReviewParser_parse_TopOfReturnValueIsChapterNode() {
//        final Class EXPECTED = ChapterNode.class;
//
//        String review = "ドーモ、世界=サン。ニンジャスレイヤーです。";
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue).isInstanceOf(EXPECTED);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Column をパースしたとき : ColumnNode に変換されること")
//    public void ReviewParser_parseColumn_convertColumnNode() {
//        final Class EXPECTED_CLASS = ColumnNode.class;
//        final String EXPECTED_TITLE = "走れメロス";
//        final String EXPECTED_TEXT1 = "メロスは激怒した。必ず、かの邪智暴虐の王を除かなければならぬと決意した。";
//        final String EXPECTED_TEXT2
//            = "メロスには政治がわからぬ。メロスは、村の牧人である。笛を吹き、羊と遊んで暮して来た。" +
//              "けれども邪悪に対しては、人一倍に敏感であった。";
//
//        String review = String.format(
//            "===[column] %s\n\n%s\n\n%s\n\n===[/column]\n",
//            EXPECTED_TITLE,
//            EXPECTED_TEXT1,
//            EXPECTED_TEXT2
//        );
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        ColumnNode columnNode = (ColumnNode) result.resultValue.getChildren().get(0);
//        assertThat(columnNode.getTitle()).isEqualTo(EXPECTED_TITLE);
//        assertThat(columnNode.getChildren().size()).isEqualTo(2);
//
//        ParagraphNode paragraphNode1 = (ParagraphNode) columnNode.getChildren().get(0);
//        TextNode textNode1 = (TextNode) paragraphNode1.getChildren().get(0);
//        ParagraphNode paragraphNode2 = (ParagraphNode) columnNode.getChildren().get(1);
//        TextNode textNode2 = (TextNode) paragraphNode2.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Heading1 をパースしたとき : HeadingNode に変換されること")
//    public void ReviewParser_parseHeading1_convertHeadingNode() {
//        final Class EXPECTED_CLASS = HeadingNode.class;
//        final int EXPECTED_LEVEL = 1;
//        final String EXPECTED_TEXT1 = "見出し1";
//        final String EXPECTED_TEXT2 = "見出し2";
//        final String EXPECTED_ID = "heading_id";
//
//        String review = String.format("= %s\n\n={%s} %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
//        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode1.getId()).isBlank();
//
//        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
//
//        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
//        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);
//
//        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Heading2 をパースしたとき : HeadingNode に変換されること")
//    public void ReviewParser_parseHeading2_convertHeadingNode() {
//        final Class EXPECTED_CLASS = HeadingNode.class;
//        final int EXPECTED_LEVEL = 2;
//        final String EXPECTED_TEXT1 = "見出し1";
//        final String EXPECTED_TEXT2 = "見出し2";
//        final String EXPECTED_ID = "heading_id";
//
//        String review = String.format("== %s\n\n=={%s} %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
//        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode1.getId()).isBlank();
//
//        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
//
//        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
//        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);
//
//        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Heading3 をパースしたとき : HeadingNode に変換されること")
//    public void ReviewParser_parseHeading3_convertHeadingNode() {
//        final Class EXPECTED_CLASS = HeadingNode.class;
//        final int EXPECTED_LEVEL = 3;
//        final String EXPECTED_TEXT1 = "見出し1";
//        final String EXPECTED_TEXT2 = "見出し2";
//        final String EXPECTED_ID = "heading_id";
//
//        String review = String.format("=== %s\n\n==={%s} %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
//        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode1.getId()).isBlank();
//
//        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
//
//        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
//        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);
//
//        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Heading4 をパースしたとき : HeadingNode に変換されること")
//    public void ReviewParser_parseHeading4_convertHeadingNode() {
//        final Class EXPECTED_CLASS = HeadingNode.class;
//        final int EXPECTED_LEVEL = 4;
//        final String EXPECTED_TEXT1 = "見出し1";
//        final String EXPECTED_TEXT2 = "見出し2";
//        final String EXPECTED_ID = "heading_id";
//
//        String review = String.format("==== %s\n\n===={%s} %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
//        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode1.getId()).isBlank();
//
//        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
//
//        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
//        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);
//
//        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Heading5 をパースしたとき : HeadingNode に変換されること")
//    public void ReviewParser_parseHeading5_convertHeadingNode() {
//        final Class EXPECTED_CLASS = HeadingNode.class;
//        final int EXPECTED_LEVEL = 5;
//        final String EXPECTED_TEXT1 = "見出し1";
//        final String EXPECTED_TEXT2 = "見出し2";
//        final String EXPECTED_ID = "heading_id";
//
//        String review = String.format("===== %s\n\n====={%s} %s\n", EXPECTED_TEXT1, EXPECTED_ID, EXPECTED_TEXT2);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode1 = (HeadingNode) result.resultValue.getChildren().get(0);
//        assertThat(headingNode1.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode1.getId()).isBlank();
//
//        TextNode textNode1 = (TextNode) headingNode1.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
//
//        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//
//        HeadingNode headingNode2 = (HeadingNode) result.resultValue.getChildren().get(1);
//        assertThat(headingNode2.getLevel()).isEqualTo(EXPECTED_LEVEL);
//        assertThat(headingNode2.getId()).isEqualTo(EXPECTED_ID);
//
//        TextNode textNode2 = (TextNode) headingNode2.getChildren().get(0);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Image をパースしたとき : ImageNode に変換されること")
//    public void ReviewParser_parseImage_convertImageNode() {
//        final Class EXPECTED_CLASS = ImageNode.class;
//        final String EXPECTED_ID = "image_id";
//        final String EXPECTED_CAPTION = "画像の説明";
//        final BigDecimal EXPECTED_SCALE = new BigDecimal("0.5");
//
//        String review = String.format(
//            "//image[%s][%s][scale=%s]{\n//}",
//            EXPECTED_ID,
//            EXPECTED_CAPTION,
//            EXPECTED_SCALE.toString()
//        );
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        ImageNode imageNode = (ImageNode) result.resultValue.getChildren().get(0);
//        assertThat(imageNode.getId()).isEqualTo(EXPECTED_ID);
//        assertThat(imageNode.getCaption()).isEqualTo(EXPECTED_CAPTION);
//        assertThat(imageNode.getScale()).isEqualTo(EXPECTED_SCALE);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Footnote をパースしたとき : FootnoteNode に変換されること")
//    public void ReviewParser_parseFootnote_convertFootnoteNode() {
//        final Class EXPECTED_CLASS = FootnoteNode.class;
//        final String EXPECTED_ID1 = "footnote1";
//        final String EXPECTED_TEXT1 = "ひとつ目の脚注です。";
//        final String EXPECTED_ID2 = "footnote2";
//        final String EXPECTED_TEXT2 = "ふたつ目の脚注です。";
//
//        String review = String.format(
//            "//footnote[%s][%s]\n//footnote[%s][%s]\n",
//            EXPECTED_ID1,
//            EXPECTED_TEXT1,
//            EXPECTED_ID2,
//            EXPECTED_TEXT2
//        );
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        FootnoteNode footnoteNode1 = (FootnoteNode) result.resultValue.getChildren().get(0);
//        assertThat(footnoteNode1.getId()).isEqualTo(EXPECTED_ID2);
//
//        InnerParagraphNode innerParagraphNode1 = (InnerParagraphNode) footnoteNode1.getChildren().get(0);
//        TextNode textNode1 = (TextNode) innerParagraphNode1.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT2);
//
//        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//
//        FootnoteNode footnoteNode2 = (FootnoteNode) result.resultValue.getChildren().get(1);
//        assertThat(footnoteNode2.getId()).isEqualTo(EXPECTED_ID1);
//
//        InnerParagraphNode innerParagraphNode2 = (InnerParagraphNode) footnoteNode2.getChildren().get(0);
//        TextNode textNode2 = (TextNode) innerParagraphNode2.getChildren().get(0);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT1);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : UnorderedList をパースしたとき : UnorderedListNode に変換されること")
//    public void ReviewParser_parseUnorderedList_convertUnorderedListNode() {
//        final Class EXPECTED_CLASS = UnorderedListNode.class;
//        final String EXPECTED_ITEM1 = "山川 穂高";
//        final String EXPECTED_SUB_ITEM = "2018年 パ・リーグMVP";
//        final String EXPECTED_ITEM2 = "多和田 真三郎";
//        final String EXPECTED_ITEM3 = "外崎 修汰";
//
//        String review = String.format(
//            " * %s\n ** %s\n * %s\n * %s\n",
//            EXPECTED_ITEM1,
//            EXPECTED_SUB_ITEM,
//            EXPECTED_ITEM2,
//            EXPECTED_ITEM3
//        );
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        UnorderedListNode unorderedListNode = (UnorderedListNode) result.resultValue.getChildren().get(0);
//        assertThat(unorderedListNode.getChildren().size()).isEqualTo(4);
//
//        UnorderedListItemNode unorderedListItemNode1 = (UnorderedListItemNode) unorderedListNode.getChildren().get(0);
//        UnorderedListItemNode unorderedListItemNode2 = (UnorderedListItemNode) unorderedListNode.getChildren().get(1);
//        UnorderedListItemNode unorderedListItemNode3 = (UnorderedListItemNode) unorderedListNode.getChildren().get(2);
//        UnorderedListItemNode unorderedListItemNode4 = (UnorderedListItemNode) unorderedListNode.getChildren().get(3);
//        assertThat(unorderedListItemNode1.getLevel()).isEqualTo(1);
//        assertThat(unorderedListItemNode2.getLevel()).isEqualTo(2);
//        assertThat(unorderedListItemNode3.getLevel()).isEqualTo(1);
//        assertThat(unorderedListItemNode4.getLevel()).isEqualTo(1);
//
//        InnerParagraphNode innerParagraphNode1 = (InnerParagraphNode) unorderedListItemNode1.getChildren().get(0);
//        TextNode textNode1 = (TextNode) innerParagraphNode1.getChildren().get(0);
//        InnerParagraphNode innerParagraphNode2 = (InnerParagraphNode) unorderedListItemNode2.getChildren().get(0);
//        TextNode textNode2 = (TextNode) innerParagraphNode2.getChildren().get(0);
//        InnerParagraphNode innerParagraphNode3 = (InnerParagraphNode) unorderedListItemNode3.getChildren().get(0);
//        TextNode textNode3 = (TextNode) innerParagraphNode3.getChildren().get(0);
//        InnerParagraphNode innerParagraphNode4 = (InnerParagraphNode) unorderedListItemNode4.getChildren().get(0);
//        TextNode textNode4 = (TextNode) innerParagraphNode4.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_ITEM1);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_SUB_ITEM);
//        assertThat(textNode3.getText()).isEqualTo(EXPECTED_ITEM2);
//        assertThat(textNode4.getText()).isEqualTo(EXPECTED_ITEM3);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : OrderedList をパースしたとき : OrderedListNode に変換されること")
//    public void ReviewParser_parseOrderedList_convertOrderedListNode() {
//        final Class EXPECTED_CLASS = OrderedListNode.class;
//        final String EXPECTED_ITEM1 = "金子 侑司";
//        final String EXPECTED_ITEM2 = "源田 壮亮";
//        final String EXPECTED_ITEM3 = "秋山 翔吾";
//        final String EXPECTED_ITEM4 = "山川 穂高";
//
//        String review = String.format(
//            " 1. %s\n 2. %s\n 3. %s\n 4. %s\n",
//            EXPECTED_ITEM1,
//            EXPECTED_ITEM2,
//            EXPECTED_ITEM3,
//            EXPECTED_ITEM4
//        );
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        OrderedListNode orderedListNode = (OrderedListNode) result.resultValue.getChildren().get(0);
//        assertThat(orderedListNode.getChildren().size()).isEqualTo(4);
//
//        OrderedListItemNode orderedListItemNode1 = (OrderedListItemNode) orderedListNode.getChildren().get(0);
//        InnerParagraphNode innerParagraphNode1 = (InnerParagraphNode) orderedListItemNode1.getChildren().get(0);
//        TextNode textNode1 = (TextNode) innerParagraphNode1.getChildren().get(0);
//        OrderedListItemNode orderedListItemNode2 = (OrderedListItemNode) orderedListNode.getChildren().get(1);
//        InnerParagraphNode innerParagraphNode2 = (InnerParagraphNode) orderedListItemNode2.getChildren().get(0);
//        TextNode textNode2 = (TextNode) innerParagraphNode2.getChildren().get(0);
//        OrderedListItemNode orderedListItemNode3 = (OrderedListItemNode) orderedListNode.getChildren().get(2);
//        InnerParagraphNode innerParagraphNode3 = (InnerParagraphNode) orderedListItemNode3.getChildren().get(0);
//        TextNode textNode3 = (TextNode) innerParagraphNode3.getChildren().get(0);
//        OrderedListItemNode orderedListItemNode4 = (OrderedListItemNode) orderedListNode.getChildren().get(3);
//        InnerParagraphNode innerParagraphNode4 = (InnerParagraphNode) orderedListItemNode4.getChildren().get(0);
//        TextNode textNode4 = (TextNode) innerParagraphNode4.getChildren().get(0);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_ITEM1);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_ITEM2);
//        assertThat(textNode3.getText()).isEqualTo(EXPECTED_ITEM3);
//        assertThat(textNode4.getText()).isEqualTo(EXPECTED_ITEM4);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Paragraph をパースしたとき : ParagraphNode に変換されること")
//    public void ReviewParser_parseParagraph_convertParagraphNode() {
//        final Class EXPECTED_CLASS = ParagraphNode.class;
//        final String EXPECTED_TEXT1 = "吾輩は猫である。";
//        final String EXPECTED_TEXT2 = "名前はまだ無い。";
//        final String EXPECTED_TEXT3 = "どこで生れたかとんと見当がつかぬ。";
//
//        String review = String.format("%s\n%s\n\n%s\n", EXPECTED_TEXT1, EXPECTED_TEXT2, EXPECTED_TEXT3);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        ParagraphNode paragraphNode1 = (ParagraphNode) result.resultValue.getChildren().get(0);
//        TextNode textNode1 = (TextNode) paragraphNode1.getChildren().get(0);
//        TextNode textNode2 = (TextNode) paragraphNode1.getChildren().get(1);
//        assertThat(textNode1.getText()).isEqualTo(EXPECTED_TEXT1);
//        assertThat(textNode2.getText()).isEqualTo(EXPECTED_TEXT2);
//
//        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//
//        ParagraphNode paragraphNode2 = (ParagraphNode) result.resultValue.getChildren().get(1);
//        TextNode textNode3 = (TextNode) paragraphNode2.getChildren().get(0);
//        assertThat(textNode3.getText()).isEqualTo(EXPECTED_TEXT3);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : FootnoteID をパースしたとき : FootnoteIDNode に変換されること")
//    public void ReviewParser_parseFootnoteID_convertFootnoteIDNode() {
//        final Class EXPECTED_CLASS = FootnoteIDNode.class;
//        final String EXPECTED_ID1 = "fn1";
//        final String EXPECTED_ID2 = "fn2";
//
//        String review = String.format("パグリアルーロ@<fn>{%s}とシアンフロッコ@<fn>{%s}", EXPECTED_ID1, EXPECTED_ID2);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        ParagraphNode paragraphNode = (ParagraphNode) result.resultValue.getChildren().get(0);
//        assertThat(paragraphNode.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
//        assertThat(paragraphNode.getChildren().get(3)).isInstanceOf(EXPECTED_CLASS);
//
//        FootnoteIDNode footnoteIDNode1 = (FootnoteIDNode) paragraphNode.getChildren().get(1);
//        FootnoteIDNode footnoteIDNode2 = (FootnoteIDNode) paragraphNode.getChildren().get(3);
//        assertThat(footnoteIDNode1.getId()).isEqualTo(EXPECTED_ID1);
//        assertThat(footnoteIDNode2.getId()).isEqualTo(EXPECTED_ID2);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Link をパースしたとき : LinkNode に変換されること")
//    public void ReviewParser_parseLink_convertLinkNode() {
//        final Class EXPECTED_CLASS = LinkNode.class;
//        final String EXPECTED_URL1 = "https://github.com/7pairs/revdoc";
//        final String EXPECTED_URL2 = "http://lions.blue/";
//        final String EXPECTED_LABEL = "ねことくまとへび";
//
//        String review = String.format(
//            "@<href>{%s}\n\n@<href>{%s, %s}",
//            EXPECTED_URL1,
//            EXPECTED_URL2,
//            EXPECTED_LABEL
//        );
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        ParagraphNode paragraphNode1 = (ParagraphNode) result.resultValue.getChildren().get(0);
//        ParagraphNode paragraphNode2 = (ParagraphNode) result.resultValue.getChildren().get(1);
//        assertThat(paragraphNode1.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//        assertThat(paragraphNode2.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        LinkNode linkNode1 = (LinkNode) paragraphNode1.getChildren().get(0);
//        LinkNode linkNode2 = (LinkNode) paragraphNode2.getChildren().get(0);
//        assertThat(linkNode1.getUrl()).isEqualTo(EXPECTED_URL1);
//        assertThat(linkNode1.getLabel()).isEqualTo(EXPECTED_URL1);
//        assertThat(linkNode2.getUrl()).isEqualTo(EXPECTED_URL2);
//        assertThat(linkNode2.getLabel()).isEqualTo(EXPECTED_LABEL);
//    }
//
//    @Test
//    @DisplayName("ReviewParser : Comment をパースしたとき : 無視されること")
//    public void ReviewParser_parseComment_beIgnored() {
//        final Class EXPECTED_CLASS = ParagraphNode.class;
//        final String EXPECTED_TEXT = "スシを食べすぎるな";
//
//        String review = String.format("#@# コメント\n%s\n#@#\n", EXPECTED_TEXT);
//        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
//        ParsingResult<ParentNode> result
//            = new ReportingParseRunner<ParentNode>(reviewParser.Chapter("")).run(review);
//
//        assertThat(result.resultValue.getChildren().size()).isEqualTo(1);
//        assertThat(result.resultValue.getChildren().get(0)).isInstanceOf(EXPECTED_CLASS);
//
//        ParagraphNode paragraphNode = (ParagraphNode) result.resultValue.getChildren().get(0);
//        TextNode textNode = (TextNode) paragraphNode.getChildren().get(0);
//        assertThat(textNode.getText()).isEqualTo(EXPECTED_TEXT);
//    }
}
