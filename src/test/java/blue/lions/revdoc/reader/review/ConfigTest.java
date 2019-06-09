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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    @DisplayName("getBookName() : bookname の値が引用符に囲まれているとき : 対応する値を返すこと")
    public void getBookName_BooknameValueHasQuotation_ReturnValue() {
        final String EXPECTED = "hobopy";

        String yamlString = String.format("bookname: \"%s\"", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getBookName()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getBookName() : bookname の値が引用符に囲まれていないとき : 対応する値を返すこと")
    public void getBookName_BooknameValueDoesNotHaveQuotation_ReturnValue() {
        final String EXPECTED = "hobopy";

        String yamlString = String.format("bookname: %s", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getBookName()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getBookTitle() : booktitle の値が引用符に囲まれているとき : 対応する値を返すこと")
    public void getBookTitle_BooktitleValueHasQuotation_ReturnValue() {
        final String EXPECTED = "ほぼPythonだけでサーバーレスアプリをつくろう";

        String yamlString = String.format("booktitle: \"%s\"", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getBookTitle()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getBookTitle() : booktitle の値が引用符に囲まれていないとき : 対応する値を返すこと")
    public void getBookTitle_BooktitleValueDoesNotHaveQuotation_ReturnValue() {
        final String EXPECTED = "ほぼPythonだけでサーバーレスアプリをつくろう";

        String yamlString = String.format("booktitle: %s", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getBookTitle()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getAuthors() : aut の値が文字列で引用符に囲まれているとき : 対応する値を返すこと")
    public void getAuthors_AutValueIsStringWithQuotation_ReturnValue() {
        final String EXPECTED = "著者 太郎";

        String yamlString = String.format("aut: \"%s\"", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getAuthors().size()).isEqualTo(1);
        assertThat(config.getAuthors().get(0)).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getAuthors() : aut の値が文字列で引用符に囲まれていないとき : 対応する値を返すこと")
    public void getAuthors_AutValueIsStringWithoutQuotation_ReturnValue() {
        final String EXPECTED = "著者 太郎";

        String yamlString = String.format("aut: %s", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getAuthors().size()).isEqualTo(1);
        assertThat(config.getAuthors().get(0)).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getAuthors() : aut の値がリストで引用符に囲まれているとき : 対応する値を返すこと")
    public void getAuthors_AutValueIsListWithQuotation_ReturnValue() {
        final String EXPECTED1 = "著者 太郎";
        final String EXPECTED2 = "著者 次郎";

        String yamlString = String.format("aut: [\"%s\", \"%s\"]", EXPECTED1, EXPECTED2);
        Config config = new Config(yamlString);

        assertThat(config.getAuthors().size()).isEqualTo(2);
        assertThat(config.getAuthors().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getAuthors().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getAuthors() : aut の値がリストで引用符に囲まれていないとき : 対応する値を返すこと")
    public void getAuthors_AutValueIsListWithoutQuotation_ReturnValue() {
        final String EXPECTED1 = "著者 太郎";
        final String EXPECTED2 = "著者 次郎";

        String yamlString = String.format("aut: [%s, %s]", EXPECTED1, EXPECTED2);
        Config config = new Config(yamlString);

        assertThat(config.getAuthors().size()).isEqualTo(2);
        assertThat(config.getAuthors().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getAuthors().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getAuthors() : aut の値がマップで引用符に囲まれているとき : 対応する値を返すこと")
    public void getAuthors_AutValueIsMapWithQuotation_ReturnValue() {
        final String EXPECTED1 = "著者 太郎";
        final String EXPECTED2 = "著者 次郎";

        String yamlString = String.format(
            "aut: [{name: \"%s\", file-as: \"チョシャ タロウ\"}, {name: \"%s\", file-as: \"チョシャ ジロウ\"}]",
            EXPECTED1,
            EXPECTED2
        );
        Config config = new Config(yamlString);

        assertThat(config.getAuthors().size()).isEqualTo(2);
        assertThat(config.getAuthors().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getAuthors().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getAuthors() : aut の値がマップで引用符に囲まれていないとき : 対応する値を返すこと")
    public void getAuthors_AutValueIsMapWithoutQuotation_ReturnValue() {
        final String EXPECTED1 = "著者 太郎";
        final String EXPECTED2 = "著者 次郎";

        String yamlString = String.format(
            "aut: [{name: %s, file-as: チョシャ タロウ}, {name: %s, file-as: チョシャ ジロウ}]",
            EXPECTED1,
            EXPECTED2
        );
        Config config = new Config(yamlString);

        assertThat(config.getAuthors().size()).isEqualTo(2);
        assertThat(config.getAuthors().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getAuthors().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getCovers() : cov の値が文字列で引用符に囲まれているとき : 対応する値を返すこと")
    public void getCovers_CovValueIsStringWithQuotation_ReturnValue() {
        final String EXPECTED = "表紙 太郎";

        String yamlString = String.format("cov: \"%s\"", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getCovers().size()).isEqualTo(1);
        assertThat(config.getCovers().get(0)).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getCovers() : cov の値が文字列で引用符に囲まれていないとき : 対応する値を返すこと")
    public void getCovers_CovValueIsStringWithoutQuotation_ReturnValue() {
        final String EXPECTED = "表紙 太郎";

        String yamlString = String.format("cov: %s", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getCovers().size()).isEqualTo(1);
        assertThat(config.getCovers().get(0)).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getCovers() : cov の値がリストで引用符に囲まれているとき : 対応する値を返すこと")
    public void getCovers_CovValueIsListWithQuotation_ReturnValue() {
        final String EXPECTED1 = "表紙 太郎";
        final String EXPECTED2 = "表紙 次郎";

        String yamlString = String.format("cov: [\"%s\", \"%s\"]", EXPECTED1, EXPECTED2);
        Config config = new Config(yamlString);

        assertThat(config.getCovers().size()).isEqualTo(2);
        assertThat(config.getCovers().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getCovers().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getCovers() : cov の値がリストで引用符に囲まれていないとき : 対応する値を返すこと")
    public void getCovers_CovValueIsListWithoutQuotation_ReturnValue() {
        final String EXPECTED1 = "表紙 太郎";
        final String EXPECTED2 = "表紙 次郎";

        String yamlString = String.format("cov: [%s, %s]", EXPECTED1, EXPECTED2);
        Config config = new Config(yamlString);

        assertThat(config.getCovers().size()).isEqualTo(2);
        assertThat(config.getCovers().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getCovers().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getCovers() : cov の値がマップで引用符に囲まれているとき : 対応する値を返すこと")
    public void getCovers_CovValueIsMapWithQuotation_ReturnValue() {
        final String EXPECTED1 = "表紙 太郎";
        final String EXPECTED2 = "表紙 次郎";

        String yamlString = String.format(
            "cov: [{name: \"%s\", file-as: \"ヒョウシ タロウ\"}, {name: \"%s\", file-as: \"ヒョウシ ジロウ\"}]",
            EXPECTED1,
            EXPECTED2
        );
        Config config = new Config(yamlString);

        assertThat(config.getCovers().size()).isEqualTo(2);
        assertThat(config.getCovers().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getCovers().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getCovers() : cov の値がマップで引用符に囲まれていないとき : 対応する値を返すこと")
    public void getCovers_CovValueIsMapWithoutQuotation_ReturnValue() {
        final String EXPECTED1 = "表紙 太郎";
        final String EXPECTED2 = "表紙 次郎";

        String yamlString = String.format(
            "cov: [{name: %s, file-as: ヒョウシ タロウ}, {name: %s, file-as: ヒョウシ ジロウ}]",
            EXPECTED1,
            EXPECTED2
        );
        Config config = new Config(yamlString);

        assertThat(config.getCovers().size()).isEqualTo(2);
        assertThat(config.getCovers().get(0)).isEqualTo(EXPECTED1);
        assertThat(config.getCovers().get(1)).isEqualTo(EXPECTED2);
    }

    @Test
    @DisplayName("getDate() : date の値が引用符に囲まれているとき : 対応する値を返すこと")
    public void getDate_DateValueHasQuotation_ReturnValue() {
        final String EXPECTED = "2018-09-30";

        String yamlString = String.format("date: \"%s\"", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getDate()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getDate() : date の値が引用符に囲まれていないとき : 対応する値を返すこと")
    public void getDate_DateValueDoesNotHaveQuotation_ReturnValue() {
        final String EXPECTED = "2018-09-30";

        String yamlString = String.format("date: %s", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getDate()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getHistory() : history の値が日付のみで引用符に囲まれているとき : 対応する値を返すこと")
    public void getHistory_HistoryValueIsOnlyDateWithQuotation_ReturnValue() {
        final String EXPECTED1 = "2018-10-08";
        final String EXPECTED2 = "2018-12-30";
        final String EXPECTED3 = "2019-04-14";

        String yamlString = String.format("history: [[\"%s\", \"%s\"], [\"%s\"]]", EXPECTED1, EXPECTED2, EXPECTED3);
        Config config = new Config(yamlString);

        assertThat(config.getHistory().size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).get(0).getDate()).isEqualTo(EXPECTED1);
        assertThat(config.getHistory().get(0).get(0).getVersion()).isBlank();
        assertThat(config.getHistory().get(0).get(1).getDate()).isEqualTo(EXPECTED2);
        assertThat(config.getHistory().get(0).get(1).getVersion()).isBlank();
        assertThat(config.getHistory().get(1).size()).isEqualTo(1);
        assertThat(config.getHistory().get(1).get(0).getDate()).isEqualTo(EXPECTED3);
        assertThat(config.getHistory().get(1).get(0).getVersion()).isBlank();
    }

    @Test
    @DisplayName("getHistory() : history の値が日付のみで引用符に囲まれていないとき : 対応する値を返すこと")
    public void getHistory_HistoryValueIsOnlyDateWithoutQuotation_ReturnValue() {
        final String EXPECTED1 = "2018-10-08";
        final String EXPECTED2 = "2018-12-30";
        final String EXPECTED3 = "2019-04-14";

        String yamlString = String.format("history: [[%s, %s], [%s]]", EXPECTED1, EXPECTED2, EXPECTED3);
        Config config = new Config(yamlString);

        assertThat(config.getHistory().size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).get(0).getDate()).isEqualTo(EXPECTED1);
        assertThat(config.getHistory().get(0).get(0).getVersion()).isBlank();
        assertThat(config.getHistory().get(0).get(1).getDate()).isEqualTo(EXPECTED2);
        assertThat(config.getHistory().get(0).get(1).getVersion()).isBlank();
        assertThat(config.getHistory().get(1).size()).isEqualTo(1);
        assertThat(config.getHistory().get(1).get(0).getDate()).isEqualTo(EXPECTED3);
        assertThat(config.getHistory().get(1).get(0).getVersion()).isBlank();
    }

    @Test
    @DisplayName("getHistory() : history の値がバージョンを持っていて引用符に囲まれているとき : 対応する値を返すこと")
    public void getHistory_HistoryValueHasVersionWithQuotation_ReturnValue() {
        final String EXPECTED_DATE1 = "2018-10-08";
        final String EXPECTED_VERSION1 = "技術書典5";
        final String EXPECTED_DATE2 = "2018-12-30";
        final String EXPECTED_VERSION2 = "C95";
        final String EXPECTED_DATE3 = "2019-04-14";
        final String EXPECTED_VERSION3 = "技術書典6";

        String yamlString = String.format(
            "history: [[\"%s %s\", \"%s %s\"], [\"%s %s\"]]",
            EXPECTED_DATE1,
            EXPECTED_VERSION1,
            EXPECTED_DATE2,
            EXPECTED_VERSION2,
            EXPECTED_DATE3,
            EXPECTED_VERSION3
        );
        Config config = new Config(yamlString);

        assertThat(config.getHistory().size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).get(0).getDate()).isEqualTo(EXPECTED_DATE1);
        assertThat(config.getHistory().get(0).get(0).getVersion()).isEqualTo(EXPECTED_VERSION1);
        assertThat(config.getHistory().get(0).get(1).getDate()).isEqualTo(EXPECTED_DATE2);
        assertThat(config.getHistory().get(0).get(1).getVersion()).isEqualTo(EXPECTED_VERSION2);
        assertThat(config.getHistory().get(1).size()).isEqualTo(1);
        assertThat(config.getHistory().get(1).get(0).getDate()).isEqualTo(EXPECTED_DATE3);
        assertThat(config.getHistory().get(1).get(0).getVersion()).isEqualTo(EXPECTED_VERSION3);
    }

    @Test
    @DisplayName("getHistory() : history の値がバージョンを持っていて引用符に囲まれていないとき : 対応する値を返すこと")
    public void getHistory_HistoryValueHasVersionWithoutQuotation_ReturnValue() {
        final String EXPECTED_DATE1 = "2018-10-08";
        final String EXPECTED_VERSION1 = "技術書典5";
        final String EXPECTED_DATE2 = "2018-12-30";
        final String EXPECTED_VERSION2 = "C95";
        final String EXPECTED_DATE3 = "2019-04-14";
        final String EXPECTED_VERSION3 = "技術書典6";

        String yamlString = String.format(
            "history: [[%s %s, %s %s], [%s %s]]",
            EXPECTED_DATE1,
            EXPECTED_VERSION1,
            EXPECTED_DATE2,
            EXPECTED_VERSION2,
            EXPECTED_DATE3,
            EXPECTED_VERSION3
        );
        Config config = new Config(yamlString);

        assertThat(config.getHistory().size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).size()).isEqualTo(2);
        assertThat(config.getHistory().get(0).get(0).getDate()).isEqualTo(EXPECTED_DATE1);
        assertThat(config.getHistory().get(0).get(0).getVersion()).isEqualTo(EXPECTED_VERSION1);
        assertThat(config.getHistory().get(0).get(1).getDate()).isEqualTo(EXPECTED_DATE2);
        assertThat(config.getHistory().get(0).get(1).getVersion()).isEqualTo(EXPECTED_VERSION2);
        assertThat(config.getHistory().get(1).size()).isEqualTo(1);
        assertThat(config.getHistory().get(1).get(0).getDate()).isEqualTo(EXPECTED_DATE3);
        assertThat(config.getHistory().get(1).get(0).getVersion()).isEqualTo(EXPECTED_VERSION3);
    }

    @Test
    @DisplayName("getRights() : rights の値が引用符に囲まれているとき : 対応する値を返すこと")
    public void getRights_RightsValueHasQuotation_ReturnValue() {
        final String EXPECTED = "(C) 2018 Thunder Claw";

        String yamlString = String.format("rights: \"%s\"", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getRights()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getRights() : rights の値が引用符に囲まれていないとき : 対応する値を返すこと")
    public void getRights_RightsValueDoesNotHaveQuotation_ReturnValue() {
        final String EXPECTED = "(C) 2018 Thunder Claw";

        String yamlString = String.format("rights: %s", EXPECTED);
        Config config = new Config(yamlString);

        assertThat(config.getRights()).isEqualTo(EXPECTED);
    }
}
