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
package blue.lions.revdoc.writer;

import static org.assertj.core.api.Assertions.*;

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.writer.word.WordWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WriterTest {

    @Test
    @DisplayName("getInstance : 条件なし : WordWriterを返すこと")
    public void getInstance_None_ReturnWordWriter() {
        final Class EXPECTED = WordWriter.class;

        Arguments arguments = Arguments.parse();
        Writer writer = Writer.getInstance(arguments);

        assertThat(writer).isInstanceOf(EXPECTED);
    }
}
