/*
 * Copyright 2019 HASEBA, Junya
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

import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 * catalog.ymlの情報を格納するクラス。
 */
class Catalog {

    // region - Inner classes.

    /*
     * 部の情報を格納するクラス。
     */
    static class Part {

        // region - Fields.

        /* ファイル名 */
        private String fileName;

        /* 章のファイル名 */
        private List<String> chapterFileNames;

        // endregion

        // region - Constructors.

        /*
         * Partオブジェクトを生成する。
         *
         * @param fileName ファイル名
         * @param chapterFileNames 章のファイル名
         */
        Part(String fileName, List<String> chapterFileNames) {
            // フィールドを初期化する
            this.fileName = fileName;
            this.chapterFileNames = chapterFileNames;
        }

        // endregion

        // region Getters.

        /*
         * ファイル名を取得する。
         *
         * @return ファイル名
         */
        String getFileName() {
            // ファイル名を返す
            return fileName;
        }

        /*
         * 章のファイル名を取得する。
         *
         * @return 章のファイル名
         */
        List<String> getChapterFileNames() {
            // 章のファイル名を返す
            return Collections.unmodifiableList(chapterFileNames);
        }

        // endregion
    }

    // endregion

    // region - Fields.

    /* 前付 */
    private List<String> predef;

    /* 本編 */
    private List<Part> chapters;

    /* 付録 */
    private List<String> appendix;

    /* 後付 */
    private List<String> postdef;

    // endregion

    // region - Constructors.

    /*
     * Catalogオブジェクトを生成する。
     *
     * @param yamlString YAML文字列
     */
    Catalog(String yamlString) {
        // YAML文字列をパースする
        Yaml yaml = new Yaml();
        Map<String, Object> catalog = yaml.load(yamlString);

        // フィールドを初期化する
        this.predef = (List<String>) catalog.getOrDefault("PREDEF", Collections.emptyList());
        this.chapters = parseChapters((List) catalog.get("CHAPS"));
        this.appendix = (List<String>) catalog.getOrDefault("APPENDIX", Collections.emptyList());
        this.postdef = (List<String>) catalog.getOrDefault("POSTDEF", Collections.emptyList());
    }

    // endregion

    // region - Getters.

    /*
     * 前付を取得する。
     *
     * @return 前付
     */
    List<String> getPredef() {
        // 前付を返す
        return Collections.unmodifiableList(predef);
    }

    /*
     * 本編を取得する。
     *
     * @return 本編
     */
    List<Part> getChapters() {
        // 本編を返す
        return Collections.unmodifiableList(chapters);
    }

    /*
     * 付録を取得する。
     *
     * @return 付録
     */
    List<String> getAppendix() {
        // 付録を返す
        return Collections.unmodifiableList(appendix);
    }

    /*
     * 後付を取得する。
     *
     * @return 後付
     */
    List<String> getPostdef() {
        // 後付を返す
        return Collections.unmodifiableList(postdef);
    }

    // endregion

    // region - Private methods.

    /*
     * 本編情報をパースし、結果をList<Part>に変換する。
     *
     * @param chaps 本編情報
     * @return パース結果
     */
    private List<Part> parseChapters(List chaps) {
        // nullが指定された場合は空リストを返す
        if (chaps == null) {
            return Collections.emptyList();
        }

        // パース結果格納用
        List<Part> chapters = new ArrayList<>();

        // 本編情報をパースする
        Object firstChap = chaps.get(0);
        if (firstChap instanceof Map) {
            // 部がある場合
            for (Map<String, List<String>> part : (List<Map<String, List<String>>>) chaps) {
                for (Map.Entry<String, List<String>> entry : part.entrySet()) {
                    chapters.add(new Part(entry.getKey(), entry.getValue()));
                }
            }
        } else if (firstChap instanceof String) {
            // 部がない場合
            chapters.add(new Part("", chaps));
        }

        // パース結果を返す
        return chapters;
    }

    // endregion
}
