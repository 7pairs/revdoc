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

import blue.lions.revdoc.ast.Node;
import org.junit.jupiter.api.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class ReviewParserTest {

    @Test
    public void test() {
        String review = "=[test] 見出し1\n==見出し2\n本文1\n本文2\n\n本文3\n";

        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<Node> result = new ReportingParseRunner<Node>(reviewParser.Chapter()).run(review);
        review = "";
    }
}
