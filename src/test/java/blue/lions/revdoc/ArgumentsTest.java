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

public class ArgumentsTest {

    @Test
    @DisplayName("parse() : 不正なオプションを指定したとき : 空の Arguments オブジェクトを返すこと")
    public void parse_SpecifyInvalidOption_ReturnEmptyArguments() {
        String[] args = new String[] {"--invalid", "invalid"};
        Arguments arguments = Arguments.parse(args);

        assertThat(arguments).isNotNull();
        assertThat(arguments.getInputDirectoryPath()).isNull();
        assertThat(arguments.getOutputFilePath()).isNull();
        assertThat(arguments.getTemplateFilePath()).isNull();
    }

    @Test
    @DisplayName("getInputDirectoryPath() : parse() に -i オプションを指定したとき : オプション値を返すこと")
    public void getInputDirectoryPath_SpecifyIOptionForParse_ReturnOptionValue() {
        final String EXPECTED = "./articles";

        String[] args = new String[] {"-i", EXPECTED};
        Arguments arguments = Arguments.parse(args);

        assertThat(arguments.getInputDirectoryPath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getInputDirectoryPath() : parse() に --input オプションを指定したとき : オプション値を返すこと")
    public void getInputDirectoryPath_SpecifyInputOptionForParse_ReturnOptionValue() {
        final String EXPECTED = "./articles";

        String[] args = new String[] {"--input", EXPECTED};
        Arguments arguments = Arguments.parse(args);

        assertThat(arguments.getInputDirectoryPath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getOutputFilePath() : parse() に -o オプションを指定したとき : オプション値を返すこと")
    public void getOutputFilePath_SpecifyOOptionForParse_ReturnOptionValue() {
        final String EXPECTED = "./output.docx";

        String[] args = new String[] {"-o", EXPECTED};
        Arguments arguments = Arguments.parse(args);

        assertThat(arguments.getOutputFilePath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getOutputFilePath() : parse() に --output オプションを指定したとき : オプション値を返すこと")
    public void getOutputFilePath_SpecifyOutputOptionForParse_ReturnOptionValue() {
        final String EXPECTED = "./output.docx";

        String[] args = new String[] {"--output", EXPECTED};
        Arguments arguments = Arguments.parse(args);

        assertThat(arguments.getOutputFilePath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getTemplateFilePath() : parse() に -t オプションを指定したとき : オプション値を返すこと")
    public void getTemplateFilePath_SpecifyTOptionForParse_ReturnOptionValue() {
        final String EXPECTED = "./template.docx";

        String[] args = new String[] {"-t", EXPECTED};
        Arguments arguments = Arguments.parse(args);

        assertThat(arguments.getTemplateFilePath()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getTemplateFilePath() : parse() に --template オプションを指定したとき : オプション値を返すこと")
    public void getTemplateFilePath_SpecifyTemplateOptionForParse_ReturnOptionValue() {
        final String EXPECTED = "./template.docx";

        String[] args = new String[] {"--template", EXPECTED};
        Arguments arguments = Arguments.parse(args);

        assertThat(arguments.getTemplateFilePath()).isEqualTo(EXPECTED);
    }
}
