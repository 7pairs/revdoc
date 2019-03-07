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

public class ReviewConfigTest {

    @Test
    @DisplayName("getBookName() : YAML のキーに bookname が存在するとき、対応する値が取得できること。")
    public void getBookName_ExistsBookname_ReturnValue() {
        final String EXPECTED = "hobopy";

        String yaml = String.format("bookname: %s", EXPECTED);
        ReviewConfig reviewConfig = new ReviewConfig(yaml);

        assertThat(reviewConfig.getBookName()).isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("getBookTitle() : YAML のキーに booktitle が存在するとき、対応する値が取得できること。")
    public void getBookTitle_ExistsBooktitle_ReturnValue() {
        final String EXPECTED = "ほぼPythonだけでサーバーレスアプリをつくろう";

        String yaml = String.format("booktitle: %s", EXPECTED);
        ReviewConfig reviewConfig = new ReviewConfig(yaml);

        assertThat(reviewConfig.getBookTitle()).isEqualTo(EXPECTED);
    }
}
