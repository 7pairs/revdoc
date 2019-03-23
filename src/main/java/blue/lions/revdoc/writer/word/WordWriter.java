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
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Wordファイルを扱うWriter。
 */
public class WordWriter extends Writer {

    /* 入力ディレクトリパス */
    private String inputDirectoryPath;

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
        this.inputDirectoryPath = arguments.getInputDirectoryPath();
        this.templateFilePath = arguments.getTemplateFilePath();
        this.outputFilePath = arguments.getOutputFilePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(Node ast) {
        // Wordファイルを出力する
        try (
            InputStream inputStream = Files.newInputStream(Paths.get(templateFilePath));
            XWPFDocument document = new XWPFDocument(inputStream);
            OutputStream outputStream = Files.newOutputStream(Paths.get(outputFilePath))
        ) {
            WordVisitor wordVisitor = new WordVisitor(document, inputDirectoryPath);
            ast.accept(wordVisitor);
            document.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
