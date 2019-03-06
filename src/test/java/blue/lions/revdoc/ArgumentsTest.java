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
package blue.lions.revdoc;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class ArgumentsTest {

    @Test
    @DisplayName("getInputDirectory() : -i オプションを指定したとき、指定した値が取得できること。")
    public void getInputDirectory_SpecifyIOption_ReturnOptionValue() {
        final String EXPECTED = "./articles";

        String[] args = new String[] {"-i", EXPECTED};
        Arguments arguments = parseArguments(args);

        assertThat(arguments.getInputDirectory()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getInputDirectory() : --input オプションを指定したとき、指定した値が取得できること。")
    public void getInputDirectory_SpecifyInputOption_ReturnOptionValue() {
        final String EXPECTED = "./articles";

        String[] args = new String[] {"--input", EXPECTED};
        Arguments arguments = parseArguments(args);

        assertThat(arguments.getInputDirectory()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getOutputFile() : -o オプションを指定したとき、指定した値が取得できること。")
    public void getOutputFile_SpecifyOOption_ReturnOptionValue() {
        final String EXPECTED = "output.docx";

        String[] args = new String[] {"-o", EXPECTED};
        Arguments arguments = parseArguments(args);

        assertThat(arguments.getOutputFile()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getOutputFile() : --output オプションを指定したとき、指定した値が取得できること。")
    public void getOutputFile_SpecifyOutputOption_ReturnOptionValue() {
        final String EXPECTED = "output.docx";

        String[] args = new String[] {"--output", EXPECTED};
        Arguments arguments = parseArguments(args);

        assertThat(arguments.getOutputFile()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getTemplateFile() : -t オプションを指定したとき、指定した値が取得できること。")
    public void getTemplateFile_SpecifyTOption_ReturnOptionValue() {
        final String EXPECTED = "template.docx";

        String[] args = new String[] {"-t", EXPECTED};
        Arguments arguments = parseArguments(args);

        assertThat(arguments.getTemplateFile()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getTemplateFile() : --template オプションを指定したとき、指定した値が取得できること。")
    public void getTemplateFile_SpecifyTemplateOption_ReturnOptionValue() {
        final String EXPECTED = "template.docx";

        String[] args = new String[] {"--template", EXPECTED};
        Arguments arguments = parseArguments(args);

        assertThat(arguments.getTemplateFile()).isEqualTo(EXPECTED);
    }

    /*
     * コマンドライン引数をパースし、結果を {@code Arguments} に変換する。
     *
     * @param args コマンドライン引数
     * @return パース結果
     */
    private Arguments parseArguments(String... args) {
        // パース結果格納用
        Arguments arguments = new Arguments();

        // コマンドライン引数をパースする
        CmdLineParser cmdLineParser = new CmdLineParser(arguments);
        try {
            cmdLineParser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }

        // パース結果を返す
        return arguments;
    }
}
