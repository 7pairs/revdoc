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

import org.kohsuke.args4j.Option;

/**
 * コマンドライン引数を格納するクラス。
 */
public class Arguments {

    /* 入力ディレクトリ名 */
    @Option(name = "-i", aliases = "--input")
    private String inputDirectory;

    /* 出力ファイル名 */
    @Option(name = "-o", aliases = "--output")
    private String outputFile;

    /* テンプレートファイル名 */
    @Option(name = "-t", aliases = "template")
    private String templateFile;

    /**
     * 入力ディレクトリ名を取得する。
     *
     * @return 入力ディレクトリ名
     */
    public String getInputDirectory() {
        // 入力ディレクトリ名を返す
        return inputDirectory;
    }

    /**
     * 出力ファイル名を取得する。
     *
     * @return 出力ファイル名
     */
    public String getOutputFile() {
        // 出力ファイル名を返す
        return outputFile;
    }

    /**
     * テンプレートファイル名を取得する。
     *
     * @return テンプレートファイル名
     */
    public String getTemplateFile() {
        // テンプレートファイル名を返す
        return templateFile;
    }
}
