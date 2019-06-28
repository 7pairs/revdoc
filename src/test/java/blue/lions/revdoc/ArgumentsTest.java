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
package blue.lions.revdoc;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Arguments")
public class ArgumentsTest {

    @Nested
    @DisplayName("parse()")
    public class ParseTest {

        @Test
        @DisplayName("不正なオプションを指定したとき - 空のArgumentsオブジェクトを返すこと")
        public void shouldReturnEmptyArguments_whenCalledWithInvalidOption() {
            String[] args = new String[] {"--invalid", "invalid"};
            Arguments arguments = Arguments.parse(args);

            assertThat(arguments).isNotNull();
            assertThat(arguments.getInputDirectoryPath()).isNull();
            assertThat(arguments.getOutputFilePath()).isNull();
            assertThat(arguments.getTemplateFilePath()).isNull();
        }
    }

    @Nested
    @DisplayName("getInputDirectoryPath()")
    public class GetInputDirectoryPathTest {

        @Test
        @DisplayName("parse()に-iオプションを指定したとき - オプション値を返すこと")
        public void shouldReturnOptionValue_whenParseIsCalledWithIOption() {
            final String EXPECTED = "./articles";

            String[] args = new String[] {"-i", EXPECTED};
            Arguments arguments = Arguments.parse(args);

            assertThat(arguments.getInputDirectoryPath()).isEqualTo(EXPECTED);
        }

        @Test
        @DisplayName("parse()に--inputオプションを指定したとき - オプション値を返すこと")
        public void shouldReturnOptionValue_whenParseIsCalledWithInputOption() {
            final String EXPECTED = "./articles";

            String[] args = new String[] {"--input", EXPECTED};
            Arguments arguments = Arguments.parse(args);

            assertThat(arguments.getInputDirectoryPath()).isEqualTo(EXPECTED);
        }
    }

    @Nested
    @DisplayName("getOutputFilePath()")
    public class GetOutputFilePathTest {

        @Test
        @DisplayName("parse()に-oオプションを指定したとき - オプション値を返すこと")
        public void shouldReturnOptionValue_whenParseIsCalledWithOOption() {
            final String EXPECTED = "./output.docx";

            String[] args = new String[] {"-o", EXPECTED};
            Arguments arguments = Arguments.parse(args);

            assertThat(arguments.getOutputFilePath()).isEqualTo(EXPECTED);
        }

        @Test
        @DisplayName("parse()に--outputオプションを指定したとき - オプション値を返すこと")
        public void shouldReturnOptionValue_whenParseIsCalledWithOutputOption() {
            final String EXPECTED = "./output.docx";

            String[] args = new String[] {"--output", EXPECTED};
            Arguments arguments = Arguments.parse(args);

            assertThat(arguments.getOutputFilePath()).isEqualTo(EXPECTED);
        }
    }

    @Nested
    @DisplayName("getTemplateFilePath()")
    public class GetTemplateFilePathTest {

        @Test
        @DisplayName("parse()に-tオプションを指定したとき - オプション値を返すこと")
        public void shouldReturnOptionValue_whenParseIsCalledWithTOption() {
            final String EXPECTED = "./template.docx";

            String[] args = new String[] {"-t", EXPECTED};
            Arguments arguments = Arguments.parse(args);

            assertThat(arguments.getTemplateFilePath()).isEqualTo(EXPECTED);
        }

        @Test
        @DisplayName("parse()に--templateオプションを指定したとき - オプション値を返すこと")
        public void shouldReturnOptionValue_whenParseIsCalledWithTemplateOption() {
            final String EXPECTED = "./template.docx";

            String[] args = new String[] {"--template", EXPECTED};
            Arguments arguments = Arguments.parse(args);

            assertThat(arguments.getTemplateFilePath()).isEqualTo(EXPECTED);
        }
    }
}
