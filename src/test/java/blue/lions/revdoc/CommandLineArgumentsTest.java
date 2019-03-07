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

public class CommandLineArgumentsTest {

    @Test
    @DisplayName("getInputDirectoryPath() : -i オプションを指定したとき、オプション値が取得できること。")
    public void getInputDirectoryPath_SpecifyIOption_ReturnOptionValue() {
        final String EXPECTED = "./articles";

        String[] args = new String[] {"-i", EXPECTED};
        CommandLineArguments commandLineArguments = parseCommandLineArguments(args);

        assertThat(commandLineArguments.getInputDirectoryPath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getInputDirectoryPath() : --input オプションを指定したとき、オプション値が取得できること。")
    public void getInputDirectoryPath_SpecifyInputOption_ReturnOptionValue() {
        final String EXPECTED = "./articles";

        String[] args = new String[] {"--input", EXPECTED};
        CommandLineArguments commandLineArguments = parseCommandLineArguments(args);

        assertThat(commandLineArguments.getInputDirectoryPath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getOutputFilePath() : -o オプションを指定したとき、オプション値が取得できること。")
    public void getOutputFilePath_SpecifyOOption_ReturnOptionValue() {
        final String EXPECTED = "./output.docx";

        String[] args = new String[] {"-o", EXPECTED};
        CommandLineArguments commandLineArguments = parseCommandLineArguments(args);

        assertThat(commandLineArguments.getOutputFilePath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getOutputFilePath() : --output オプションを指定したとき、オプション値が取得できること。")
    public void getOutputFilePath_SpecifyOutputOption_ReturnOptionValue() {
        final String EXPECTED = "./output.docx";

        String[] args = new String[] {"--output", EXPECTED};
        CommandLineArguments commandLineArguments = parseCommandLineArguments(args);

        assertThat(commandLineArguments.getOutputFilePath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getTemplateFilePath() : -t オプションを指定したとき、オプション値が取得できること。")
    public void getTemplateFilePath_SpecifyTOption_ReturnOptionValue() {
        final String EXPECTED = "./template.docx";

        String[] args = new String[] {"-t", EXPECTED};
        CommandLineArguments commandLineArguments = parseCommandLineArguments(args);

        assertThat(commandLineArguments.getTemplateFilePath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getTemplateFilePath() : --template オプションを指定したとき、オプション値が取得できること。")
    public void getTemplateFilePath_SpecifyTemplateOption_ReturnOptionValue() {
        final String EXPECTED = "./template.docx";

        String[] args = new String[] {"--template", EXPECTED};
        CommandLineArguments commandLineArguments = parseCommandLineArguments(args);

        assertThat(commandLineArguments.getTemplateFilePath()).isEqualTo(EXPECTED);
    }

    /*
     * コマンドライン引数をパースし、結果を {@code CommandLineArguments} に変換する。
     *
     * @param args コマンドライン引数
     * @return パース結果
     */
    private CommandLineArguments parseCommandLineArguments(String... args) {
        // パース結果格納用
        CommandLineArguments commandLineArguments = new CommandLineArguments();

        // コマンドライン引数をパースする
        CmdLineParser cmdLineParser = new CmdLineParser(commandLineArguments);
        try {
            cmdLineParser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }

        // パース結果を返す
        return commandLineArguments;
    }
}
