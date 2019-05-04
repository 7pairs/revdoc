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
import blue.lions.revdoc.ast.old.AppendixNode;
import blue.lions.revdoc.ast.old.BackMatterNode;
import blue.lions.revdoc.ast.old.BodyMatterNode;
import blue.lions.revdoc.ast.old.ChapterNode;
import blue.lions.revdoc.ast.old.FrontMatterNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.old.PartNode;
import blue.lions.revdoc.ast.old.RootNode;
import blue.lions.revdoc.reader.Reader;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Re:VIEW文書を扱うReader。
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

        // 前付ノードを構築する
        FrontMatterNode frontMatterNode = new FrontMatterNode();
        frontMatterNode.appendChildren(parseDocuments(catalog.getPredef()));
        rootNode.appendChild(frontMatterNode);

        // 本文ノードを構築する
        BodyMatterNode bodyMatterNode = new BodyMatterNode();
        for (Catalog.Part part : catalog.getChapters()) {
            PartNode partNode = new PartNode();
            partNode.appendChild(parseChapter(part.getFileName()));
            partNode.appendChildren(parseDocuments(part.getChapterFileNames()));
            bodyMatterNode.appendChild(partNode);
        }
        rootNode.appendChild(bodyMatterNode);

        // 付録ノードを構築する
        AppendixNode appendixNode = new AppendixNode();
        appendixNode.appendChildren(parseDocuments(catalog.getAppendix()));
        rootNode.appendChild(appendixNode);

        // 後付ノードを構築する
        BackMatterNode backMatterNode = new BackMatterNode();
        backMatterNode.appendChildren(parseDocuments(catalog.getPostdef()));
        rootNode.appendChild(backMatterNode);

        // 構築したルートノードを返す
        return rootNode;
    }

    /*
     * テキストファイルを読み込み、内容を文字列として取得する。
     *
     * @param path ファイルパス
     * @return ファイル内容
     */
    private String readFile(Path path) {
        // ファイルを読み込む
        try {
            return Files.lines(path).collect(Collectors.joining(System.getProperty("line.separator")));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * Re:VIEW文書をパースし、結果を抽象構文木に変換する。
     *
     * @param fileName ファイル名
     * @return パース結果
     */
    private ChapterNode parseChapter(String fileName) {
        // Re:VIEWフォーマットのファイルをパースする
        String review = readFile(Paths.get(inputDirectoryPath, fileName));
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<ChapterNode> result = new ReportingParseRunner<ChapterNode>(reviewParser.Start()).run(review);

        // パース結果を返す
        return result.resultValue;
    }

    /*
     * 複数のRe:VIEW文書をパースし、結果を抽象構文木のリストに変換する。
     *
     * @param fileNames ファイル名
     * @return パース結果
     */
    private List<Node> parseDocuments(List<String> fileNames) {
        // パース結果格納用
        List<Node> documents = new ArrayList<>();

        // Re:VIEW文書をパースする
        for (String fileName : fileNames) {
            documents.add(parseChapter(fileName));
        }

        // パース結果を返す
        return documents;
    }
}
