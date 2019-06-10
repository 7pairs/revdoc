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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * config.ymlの情報を格納するクラス。
 */
class Config {

    // region - Inner Classes.

    /*
     * 発行年月の情報を格納するクラス。
     */
    static class HistoryItem {

        // region - Fields.

        /* 日付 */
        private LocalDate date;

        /* バージョン */
        private String version;

        // endregion

        // region - Constructors.

        /*
         * HistoryItemオブジェクトを生成する。
         *
         * @param date 日付
         * @param version バージョン
         */
        HistoryItem(LocalDate date, String version) {
            // フィールドを初期化する
            this.date = date;
            this.version = version;
        }

        // endregion

        // region - Getters.

        /*
         * 日付を取得する。
         *
         * @return 日付
         */
        LocalDate getDate() {
            // 日付を返す
            return date;
        }

        /*
         * バージョンを取得する。
         *
         * @return バージョン
         */
        String getVersion() {
            // バージョンを返す
            return version;
        }

        // endregion
    }

    // endregion

    // region - Fields.

    /* 名称 */
    private String bookName;

    /* 書名 */
    private String bookTitle;

    /* 著者 */
    private List<String> authors;

    /* 表紙 */
    private List<String> covers;

    /* 日付 */
    private LocalDate date;

    /* 発行年月 */
    private List<List<HistoryItem>> history;

    /* 権利表記 */
    private String rights;

    // endregion

    // region - Constructors.

    /*
     * Configオブジェクトを生成する。
     *
     * @param yamlString YAML文字列
     */
    Config(String yamlString) {
        // YAMLをパースする
        Yaml yaml = new Yaml();
        Map<String, Object> config = yaml.load(yamlString);

        // フィールドを初期化する
        bookName = (String) config.get("bookname");
        bookTitle = (String) config.get("booktitle");
        authors = parsePeople(config.get("aut"));
        covers = parsePeople(config.get("cov"));
        date = convertToLocalDate(config.get("date"));
        history = parseHistory((List<List>) config.get("history"));
        rights = (String) config.get("rights");
    }

    // endregion

    // region Getters.

    /*
     * 名称を取得する。
     *
     * @return 名称
     */
    String getBookName() {
        // 名称を返す
        return bookName;
    }

    /*
     * 書名を取得する。
     *
     * @return 書名
     */
    String getBookTitle() {
        // 書名を返す
        return bookTitle;
    }

    /*
     * 著者を取得する。
     *
     * @return 著者
     */
    List<String> getAuthors() {
        // 著者を返す
        return Collections.unmodifiableList(authors);
    }

    /*
     * 表紙を取得する。
     *
     * @return 表紙
     */
    List<String> getCovers() {
        // 表紙を返す
        return Collections.unmodifiableList(covers);
    }

    /*
     * 日付を取得する。
     *
     * @return 日付
     */
    LocalDate getDate() {
        // 日付を返す
        return date;
    }

    /*
     * 発行年月を取得する。
     *
     * @return 発行年月
     */
    List<List<HistoryItem>> getHistory() {
        // 発行年月を返す
        return Collections.unmodifiableList(history);
    }

    /*
     * 権利表記を取得する。
     *
     * @return 権利表記
     */
    String getRights() {
        // 権利表記を返す
        return rights;
    }

    // endregion

    // region - Private Methods.

    /*
     * 人物情報をパースし、結果をList<String>に変換する。
     *
     * 以下の6パターンに対応している。
     *   1. "著者 太郎"
     *   2. 著者 太郎
     *   3. ["著者 太郎", "著者 次郎", ...]
     *   4. [著者 太郎, 著者 次郎, ...]
     *   5. [{name: "著者 太郎", file-as: "チョシャ タロウ"}, {name: "著者 次郎", file-as: "チョシャ ジロウ"}, ...]
     *   6. [{name: 著者 太郎, file-as: チョシャ タロウ}, {name: 著者 次郎, file-as: チョシャ ジロウ}, ...]
     *
     * @param data 人物情報
     * @return パース結果
     */
    private List<String> parsePeople(Object data) {
        // nullが指定された場合は空リストを返す
        if (data == null) {
            return Collections.emptyList();
        }

        // パース結果格納用
        List<String> people = new ArrayList<>();

        // 人物情報をパースする
        if (data instanceof String) {
            // パターン1, 2の場合
            people.add((String) data);
        } else if (data instanceof List) {
            for (Object person : (List) data) {
                if (person instanceof String) {
                    // パターン3, 4の場合
                    people.add((String) person);
                } else if (person instanceof Map) {
                    // パターン5, 6の場合
                    String name = ((Map<String, String>) person).get("name");
                    if (name != null) {
                        people.add(name);
                    }
                }
            }
        }

        // パース結果を返す
        return people;
    }

    /*
     * 日付をLocalDateに変換する。
     *
     * @param date 変換元のDateもしくはString
     * @return 変換結果
     */
    private LocalDate convertToLocalDate(Object date) {
        // 日付をLocalDateに変換する
        if (date instanceof Date) {
            return Instant.ofEpochMilli(((Date) date).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        } else if (date instanceof String) {
            return LocalDate.parse((String) date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        // 変換できなかった場合はnullを返す
        return null;
    }

    /*
     * 発行年月をパースし、結果をList<List<HistoryItem>>に変換する。
     *
     * 以下の4パターンに対応している。
     *   1. [["2018-10-08", "2018-12-30", ...], ["2019-04-14", ...], ...]
     *   2. [[2018-10-08, 2018-12-30, ...], [2019-04-14, ...], ...]
     *   3. [["2018-10-08 技術書典5", "2018-12-30 C95", ...], ["2019-04-14 技術書典6", ...], ...]
     *   4. [[2018-10-08 技術書典5, 2018-12-30 C95, ...], [2019-04-14 技術書典6, ...], ...]
     *
     * @param history 発行年月
     * @return パース結果
     */
    private List<List<HistoryItem>> parseHistory(List<List> history) {
        // nullが指定された場合は空リストを返す
        if (history == null) {
            return Collections.emptyList();
        }

        // パース結果格納用
        List<List<HistoryItem>> historyItems = new ArrayList<>();

        // 発行年月をパースする
        for (List edition : history) {
            List<HistoryItem> editionItems = new ArrayList<>();
            for (Object printing : edition) {
                if (printing instanceof Date) {
                    // パターン2の場合
                    editionItems.add(new HistoryItem(convertToLocalDate(printing), ""));
                } else if (printing instanceof String) {
                    // パターン1, 3, 4の場合
                    String[] elements = ((String) printing).split(" ", 2);
                    editionItems.add(new HistoryItem(
                        convertToLocalDate(elements[0]),
                        elements.length == 2 ? elements[1] : ""
                    ));
                }
            }
            historyItems.add(editionItems);
        }

        // パース結果を返す
        return historyItems;
    }

    // endregion
}
