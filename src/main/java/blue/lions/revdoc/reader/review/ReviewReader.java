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
package blue.lions.revdoc.reader.review;

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.RootNode;
import blue.lions.revdoc.reader.Reader;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Re:VIEWフォーマットのファイルを読み込むリーダー。
 */
public class ReviewReader extends Reader {

    /* 入力ディレクトリパス */
    private String inputDirectoryPath;

    /**
     * {@code ReviewReader} オブジェクトを構築する。
     *
     * @param arguments コマンドライン引数
     */
    public ReviewReader(Arguments arguments) {
        // フィールドを初期化する
        inputDirectoryPath = arguments.getInputDirectoryPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node run() {
        // config.ymlを読み込む
        Config config = new Config(readFile(Paths.get(inputDirectoryPath, "config.yml")));

        // catalog.ymlを読み込む
        Catalog catalog = new Catalog(readFile(Paths.get(inputDirectoryPath, "catalog.yml")));

        // ルートノードを構築する
        RootNode rootNode = new RootNode(config.getBookTitle());


        String input = "= 見出し\n\n1行目\n2行目\n\n2段落目\n";
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<Node> result = new ReportingParseRunner<Node>(reviewParser.Root()).run(input);

        return rootNode;
    }

    private String readFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
