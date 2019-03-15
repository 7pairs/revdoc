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

import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.reader.Reader;
import blue.lions.revdoc.writer.Writer;

/**
 * revdocのメインクラス。
 */
public class RevDoc {

    /**
     * revdocのエントリーポイント。
     *
     * @param args コマンドライン引数
     */
    public static void main(String... args) {
        // コマンドライン引数をパースする
        Arguments arguments = Arguments.parse(args);

        // 原稿ファイルを解析する
        Reader reader = Reader.getInstance(arguments);
        Node ast = reader.run();

        // 原稿ファイルの内容を出力する
        Writer writer = Writer.getInstance(arguments);
        writer.run(ast);
    }
}
