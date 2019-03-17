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
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.TextNode;
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
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(0)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT1);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getId()).isBlank();
        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(1)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT2);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getId()).isEqualTo(EXPECTED_ID);
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
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(0)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT1);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getId()).isBlank();
        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(1)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT2);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getId()).isEqualTo(EXPECTED_ID);
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
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(0)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT1);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getId()).isBlank();
        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(1)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT2);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getId()).isEqualTo(EXPECTED_ID);
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
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(0)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT1);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getId()).isBlank();
        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(1)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT2);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getId()).isEqualTo(EXPECTED_ID);
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
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(0)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT1);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(0)).getId()).isBlank();
        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getLevel()).isEqualTo(EXPECTED_LEVEL);
        assertThat(((TextNode) ((HeadingNode) result.resultValue.getChildren().get(1)).getChildren().get(0)).getText())
            .isEqualTo(EXPECTED_TEXT2);
        assertThat(((HeadingNode) result.resultValue.getChildren().get(1)).getId()).isEqualTo(EXPECTED_ID);
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
        assertThat(
            ((TextNode) ((ParagraphNode) result.resultValue.getChildren().get(0)).getChildren().get(0)).getText()
        ).isEqualTo(EXPECTED_TEXT1);
        assertThat(
            ((TextNode) ((ParagraphNode) result.resultValue.getChildren().get(0)).getChildren().get(1)).getText()
        ).isEqualTo(EXPECTED_TEXT2);
        assertThat(result.resultValue.getChildren().get(1)).isInstanceOf(EXPECTED_CLASS);
        assertThat(
            ((TextNode) ((ParagraphNode) result.resultValue.getChildren().get(1)).getChildren().get(0)).getText()
        ).isEqualTo(EXPECTED_TEXT3);
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
        assertThat(
            ((TextNode) ((ParagraphNode) result.resultValue.getChildren().get(0)).getChildren().get(0)).getText()
        ).isEqualTo(EXPECTED_TEXT);
    }
}
