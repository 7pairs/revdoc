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
package blue.lions.revdoc.reader;

import static org.assertj.core.api.Assertions.*;

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.reader.review.ReviewReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Reader")
public class ReaderTest {

    @Nested
    @DisplayName("getInstance()")
    public class GetInstanceTest {

        @Test
        @DisplayName("条件なし - ReviewReaderを返すこと")
        public void shouldReturnReviewReader() {
            final Class EXPECTED = ReviewReader.class;

            Arguments arguments = Arguments.parse();
            Reader reader = Reader.getInstance(arguments);

            assertThat(reader).isInstanceOf(EXPECTED);
        }
    }
}
