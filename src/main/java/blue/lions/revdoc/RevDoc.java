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
import blue.lions.revdoc.parser.ReviewParser;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * revdocのメインクラス。
 */
public class RevDoc {

    /**
     * メイン処理。
     *
     * @param args コマンドライン引数
     */
    public static void main(String... args) {
        // コマンドライン引数をパースする
        Arguments arguments = parseArgument(args);

        Config config = parseConfig(arguments.getInputDirectory());

        try {
            String input = "= 見出し\n\n1行目\n2行目\n\n2段落目\n";
            ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
            ParsingResult<Node> result = new ReportingParseRunner<Node>(reviewParser.Root()).run(input);

            XWPFDocument document = new XWPFDocument();
            result.resultValue.export(document);
            FileOutputStream fout = new FileOutputStream("~/test.docx");
            document.write(fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * コマンドライン引数をパースして @{code Arguments} に格納する。
     *
     * @param args コマンドライン引数
     * @return コマンドライン引数オブジェクト
     */
    private static Arguments parseArgument(String... args) {
        Arguments arguments = new Arguments();
        CmdLineParser cmdLineParser = new CmdLineParser(arguments);
        try {
            cmdLineParser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
        return arguments;
    }

    private static Config parseConfig(String configPath) {
        return new Config();
    }
}
