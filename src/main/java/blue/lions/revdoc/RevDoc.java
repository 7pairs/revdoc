/*
 * Copyright 2019 HASEBA, Junya
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

import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.reader.Reader;
import blue.lions.revdoc.writer.Writer;

/**
 * RevDocのメインクラス。
 */
public class RevDoc {

    // region - Static methods.

    /**
     * RevDocのエントリーポイント。
     *
     * @param args コマンドライン引数
     */
    public static void main(String... args) {
        // コマンドライン引数をパースする
        Arguments arguments = Arguments.parse(args);

        // 原稿データを抽象構文木に変換する
        Reader reader = Reader.getInstance(arguments);
        Node ast = reader.read();

        // 抽象構文木の内容を出力する
        Writer writer = Writer.getInstance(arguments);
        writer.write(ast);
    }

    // endregion
}
