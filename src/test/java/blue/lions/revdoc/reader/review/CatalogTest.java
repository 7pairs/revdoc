/*
 * Copyright 2019 HASEBA Junya
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Catalog")
public class CatalogTest {

    @Nested
    @DisplayName("getPredef()")
    public class GetPredefTest {

        @Test
        @DisplayName("YAMLにPREDEFが存在するとき - 対応する値を返すこと")
        public void shouldReturnCorrespondingValue_whenYamlHasPredef() {
            final String EXPECTED1 = "front_matter1.re";
            final String EXPECTED2 = "front_matter2.re";

            String yamlString = String.format(
                "PREDEF:\n" +
                "  - %s\n" +
                "  - %s\n",
                EXPECTED1, EXPECTED2
            );
            Catalog catalog = new Catalog(yamlString);

            List<String> predef = catalog.getPredef();
            assertThat(predef.size()).isEqualTo(2);
            assertThat(predef.get(0)).isEqualTo(EXPECTED1);
            assertThat(predef.get(1)).isEqualTo(EXPECTED2);
        }
    }

    @Nested
    @DisplayName("getChapters()")
    public class GetChaptersTest {

        @Test
        @DisplayName("CHAPSが部を持つとき - 対応する値を返すこと")
        public void shouldReturnCorrespondingValue_whenChapsHasParts() {
            final String EXPECTED_PART1 = "part1.re";
            final String EXPECTED_PART2 = "part2.re";
            final String EXPECTED_CHAPTER1 = "chapter1.re";
            final String EXPECTED_CHAPTER2 = "chapter2.re";
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

            List<Catalog.Part> chapters = catalog.getChapters();
            assertThat(chapters.size()).isEqualTo(2);

            Catalog.Part part1 = chapters.get(0);
            assertThat(part1.getFileName()).isEqualTo(EXPECTED_PART1);

            List<String> chapterFileNames1 = part1.getChapterFileNames();
            assertThat(chapterFileNames1.size()).isEqualTo(2);
            assertThat(chapterFileNames1.get(0)).isEqualTo(EXPECTED_CHAPTER1);
            assertThat(chapterFileNames1.get(1)).isEqualTo(EXPECTED_CHAPTER2);

            Catalog.Part part2 = chapters.get(1);
            assertThat(part2.getFileName()).isEqualTo(EXPECTED_PART2);

            List<String> chapterFileNames2 = part2.getChapterFileNames();
            assertThat(chapterFileNames2.size()).isEqualTo(1);
            assertThat(chapterFileNames2.get(0)).isEqualTo(EXPECTED_CHAPTER3);
        }

        @Test
        @DisplayName("CHAPSが部を持たないとき - 対応する値を返すこと")
        public void shouldReturnCorrespondingValue_whenChapsDoesNotHaveParts() {
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

            List<Catalog.Part> chapters = catalog.getChapters();
            assertThat(chapters.size()).isEqualTo(1);

            Catalog.Part part = chapters.get(0);
            assertThat(part.getFileName()).isBlank();

            List<String> chapterFileNames = part.getChapterFileNames();
            assertThat(chapterFileNames.size()).isEqualTo(2);
            assertThat(chapterFileNames.get(0)).isEqualTo(EXPECTED1);
            assertThat(chapterFileNames.get(1)).isEqualTo(EXPECTED2);
        }
    }

    @Nested
    @DisplayName("getAppendix()")
    public class GetAppendixTest {

        @Test
        @DisplayName("YAMLにAPPENDIXが存在するとき - 対応する値を返すこと")
        public void shouldReturnCorrespondingValue_whenYamlHasAppendix() {
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

            List<String> appendix = catalog.getAppendix();
            assertThat(appendix.size()).isEqualTo(2);
            assertThat(appendix.get(0)).isEqualTo(EXPECTED1);
            assertThat(appendix.get(1)).isEqualTo(EXPECTED2);
        }
    }

    @Nested
    @DisplayName("getPostdef()")
    public class GetPostdefTest {

        @Test
        @DisplayName("YAMLにPOSTDEFが存在するとき - 対応する値を返すこと")
        public void shouldReturnCorrespondingValue_whenYamlHasPostdef() {
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

            List<String> postdef = catalog.getPostdef();
            assertThat(postdef.size()).isEqualTo(2);
            assertThat(postdef.get(0)).isEqualTo(EXPECTED1);
            assertThat(postdef.get(1)).isEqualTo(EXPECTED2);
        }
    }
}
