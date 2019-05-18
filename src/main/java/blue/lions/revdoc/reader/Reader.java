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
package blue.lions.revdoc.reader;

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.reader.review.ReviewReader;

/**
 * Readerのベースクラス。
 */
public abstract class Reader {

    // region 静的メソッド

    /**
     * コマンドライン引数に対応したReaderを取得する。
     *
     * 現時点では引数にかかわらず {@code ReviewReader} を固定で返す。
     *
     * @param arguments コマンドライン引数
     * @return Reader
     */
    public static Reader getInstance(Arguments arguments) {
        // ReviewReaderを返す
        return new ReviewReader(arguments);
    }

    // endregion

    // region 抽象メソッド

    /**
     * 原稿データを抽象構文木に変換する。
     *
     * @return 抽象構文木
     */
    public abstract Node run();

    // endregion
}
