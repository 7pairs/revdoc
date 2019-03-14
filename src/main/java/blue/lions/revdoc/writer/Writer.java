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

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.writer.word.WordWriter;

/**
 * すべてのライターのベースクラス。
 */
public abstract class Writer {

    /**
     * コマンドライン引数に対応したライターを取得する。
     *
     * 現時点では {@code WordWriter} を固定で返している。
     *
     * @param arguments コマンドライン引数
     * @return ライター
     */
    public static Writer getInstance(Arguments arguments) {
        // WordWriterを返す
        return new WordWriter(arguments);
    }

    /**
     * 抽象構文木を解析し、印刷用ファイルを出力する。
     *
     * @param ast 抽象構文木
     */
    public abstract void run(Node ast);
}
