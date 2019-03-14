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
package blue.lions.revdoc.writer.word;

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.writer.Writer;

/**
 * Wordファイルを扱うライター。
 */
public class WordWriter extends Writer {

    /* テンプレートファイルパス */
    private String templateFilePath;

    /* 出力ファイルパス */
    private String outputFilePath;

    /**
     * {@code WordWriter} オブジェクトを構築する。
     *
     * @param arguments コマンドライン引数
     */
    public WordWriter(Arguments arguments) {
        // フィールドを初期化する
        this.templateFilePath = arguments.getTemplateFilePath();
        this.outputFilePath = arguments.getOutputFilePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(Node ast) {
        WordVisitor wordVisitor = new WordVisitor(templateFilePath, outputFilePath);
        wordVisitor.accept(ast);
    }
}
