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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CatalogTest {

    @Test
    @DisplayName("getPredef() : YAML に PREDEF が存在するとき : 対応する値を返すこと")
    public void getPredef_ExistsPredef_ReturnValue() {
        final String EXPECTED1 = "front_matter1.re";
        final String EXPECTED2 = "front_matter2.re";

        String yamlString = String.format(
            "PREDEF:\n" +
            "  - %s\n" +
            "  - %s\n",
            EXPECTED1,
            EXPECTED2
        );
        Catalog catalog = new Catalog(yamlString);

        assertThat(catalog.getPredef().size()).isEqualTo(2);
        assertThat(catalog.getPredef().get(0)).isEqualTo(EXPECTED1);
        assertThat(catalog.getPredef().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getChapters() : CHAPS が部を持つとき : 対応する値を返すこと")
    public void getChapters_ChapsHasParts_ReturnValue() {
        final String EXPECTED_PART1 = "part1.re";
        final String EXPECTED_CHAPTER1 = "chapter1.re";
        final String EXPECTED_CHAPTER2 = "chapter2.re";
        final String EXPECTED_PART2 = "part2.re";
        final String EXPECTED_CHAPTER3 = "chapter3.re";

        String yamlString = String.format(
            "CHAPS:\n" +
            "  - %s:\n" +
            "    - %s\n" +
            "    - %s\n" +
            "  - %s:\n" +
            "    - %s\n",
            EXPECTED_PART1,
            EXPECTED_CHAPTER1,
            EXPECTED_CHAPTER2,
            EXPECTED_PART2,
            EXPECTED_CHAPTER3
        );
        Catalog catalog = new Catalog(yamlString);

        assertThat(catalog.getChapters().size()).isEqualTo(2);
        assertThat(catalog.getChapters().get(0).getFileName()).isEqualTo(EXPECTED_PART1);
        assertThat(catalog.getChapters().get(0).getChapterFileNames().size()).isEqualTo(2);
        assertThat(catalog.getChapters().get(0).getChapterFileNames().get(0)).isEqualTo(EXPECTED_CHAPTER1);
        assertThat(catalog.getChapters().get(0).getChapterFileNames().get(1)).isEqualTo(EXPECTED_CHAPTER2);
        assertThat(catalog.getChapters().get(1).getFileName()).isEqualTo(EXPECTED_PART2);
        assertThat(catalog.getChapters().get(1).getChapterFileNames().size()).isEqualTo(1);
        assertThat(catalog.getChapters().get(1).getChapterFileNames().get(0)).isEqualTo(EXPECTED_CHAPTER3);
    }

    @Test
    @DisplayName("getChapters() : CHAPS が部を持たないとき : 対応する値を返すこと")
    public void getChapters_ChapsDoesNotHaveParts_ReturnValue() {
        final String EXPECTED1 = "chapter1.re";
        final String EXPECTED2 = "chapter2.re";

        String yamlString = String.format(
            "CHAPS:\n" +
            "  - %s\n" +
            "  - %s\n",
            EXPECTED1,
            EXPECTED2
        );
        Catalog catalog = new Catalog(yamlString);

        assertThat(catalog.getChapters().size()).isEqualTo(1);
        assertThat(catalog.getChapters().get(0).getFileName()).isBlank();
        assertThat(catalog.getChapters().get(0).getChapterFileNames().size()).isEqualTo(2);
        assertThat(catalog.getChapters().get(0).getChapterFileNames().get(0)).isEqualTo(EXPECTED1);
        assertThat(catalog.getChapters().get(0).getChapterFileNames().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getAppendix() : YAML に APPENDIX が存在するとき : 対応する値を返すこと")
    public void getAppendix_ExistsAppendix_ReturnValue() {
        final String EXPECTED1 = "appendix1.re";
        final String EXPECTED2 = "appendix2.re";

        String yamlString = String.format(
            "APPENDIX:\n" +
            "  - %s\n" +
            "  - %s\n",
            EXPECTED1,
            EXPECTED2
        );
        Catalog catalog = new Catalog(yamlString);

        assertThat(catalog.getAppendix().size()).isEqualTo(2);
        assertThat(catalog.getAppendix().get(0)).isEqualTo(EXPECTED1);
        assertThat(catalog.getAppendix().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getPostdef() : YAML に POSTDEF が存在するとき : 対応する値を返すこと")
    public void getPostdef_ExistsPostdef_ReturnValue() {
        final String EXPECTED1 = "back_matter1.re";
        final String EXPECTED2 = "back_matter2.re";

        String yamlString = String.format(
            "POSTDEF:\n" +
            "  - %s\n" +
            "  - %s\n",
            EXPECTED1,
            EXPECTED2
        );
        Catalog catalog = new Catalog(yamlString);

        assertThat(catalog.getPostdef().size()).isEqualTo(2);
        assertThat(catalog.getPostdef().get(0)).isEqualTo(EXPECTED1);
        assertThat(catalog.getPostdef().get(1)).isEqualTo(EXPECTED2);
    }
}
