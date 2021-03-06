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

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * コマンドライン引数を格納するクラス。
 */
public class Arguments {

    /* 入力ディレクトリパス */
    @Option(name = "-i", aliases = "--input")
    private String inputDirectoryPath;

    /* 出力ファイルパス */
    @Option(name = "-o", aliases = "--output")
    private String outputFilePath;

    /* テンプレートファイルパス */
    @Option(name = "-t", aliases = "--template")
    private String templateFilePath;

    /* コンストラクタ(隠蔽) */
    private Arguments() {}

    /**
     * コマンドライン引数をパースし、結果を {@code Arguments} オブジェクトに変換する。
     *
     * @param args コマンドライン引数
     * @return パース結果
     */
    public static Arguments parse(String... args) {
        // パース結果格納用
        Arguments arguments = new Arguments();

        // コマンドライン引数をパースする
        CmdLineParser cmdLineParser = new CmdLineParser(arguments);
        try {
            cmdLineParser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }

        // パース結果を返す
        return arguments;
    }

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
