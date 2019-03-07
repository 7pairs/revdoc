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
public class CommandLineArguments {

    /* 入力ディレクトリパス */
    @Option(name = "-i", aliases = "--input")
    private String inputDirectoryPath;

    /* 出力ファイルパス */
    @Option(name = "-o", aliases = "--output")
    private String outputFilePath;

    /* テンプレートファイルパス */
    @Option(name = "-t", aliases = "--template")
    private String templateFilePath;

    /**
     * 入力ディレクトリパスを取得する。
     *
     * @return 入力ディレクトリパス
     */
    public String getInputDirectoryPath() {
        // 入力ディレクトリパスを返す
        return inputDirectoryPath;
    }

    /**
     * 出力ファイルパスを取得する。
     *
     * @return 出力ファイルパス
     */
    public String getOutputFilePath() {
        // 出力ファイルパスを返す
        return outputFilePath;
    }

    /**
     * テンプレートファイルパスを取得する。
     *
     * @return テンプレートファイルパス
     */
    public String getTemplateFilePath() {
        // テンプレートファイルパスを返す
        return templateFilePath;
    }
}
