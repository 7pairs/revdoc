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

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.reader.review.ReviewReader;

/**
 * すべてのReaderのベースクラス。
 */
public abstract class Reader {

    // region - Static methods.

    /**
     * コマンドライン引数に対応した {@code Reader} を取得する。
     *
     * @param arguments コマンドライン引数
     * @return 対応するReader
     */
    public static Reader getInstance(Arguments arguments) {
        // 現時点では無条件でReviewReaderを返す
        return new ReviewReader(arguments);
    }

    // endregion

    // region - Abstract methods.

    /**
     * 原稿データを抽象構文木に変換する。
     *
     * @return 抽象構文木
     */
    public abstract Node read();

    // endregion
}
