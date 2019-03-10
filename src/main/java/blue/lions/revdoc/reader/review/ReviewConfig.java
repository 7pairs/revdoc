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
class ReviewConfig {

    /*
     * 発行年月の情報を格納するクラス。
     */
    static class HistoryItem {

        /* 日付 */
        private LocalDate date;

        /* バージョン */
        private String version;

        /*
         * {@code HistoryItem} オブジェクトを構築する。
         *
         * @param date 日付
         * @param version バージョン
         */
        HistoryItem(LocalDate date, String version) {
            // フィールドを初期化する
            this.date = date;
            this.version = version;
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
         * バージョンを取得する。
         *
         * @return バージョン
         */
        String getVersion() {
            // バージョンを返す
            return version;
        }
    }

    /* ブック名 */
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

    /*
     * {@code ReviewConfig} オブジェクトを構築する。
     *
     * @param yamlString YAML文字列
     */
    ReviewConfig(String yamlString) {
        // YAMLをパースする
        Yaml yaml = new Yaml();
        Map config = yaml.loadAs(yamlString, Map.class);

        // フィールドを初期化する
        bookName = (String) config.get("bookname");
        bookTitle = (String) config.get("booktitle");
        authors = parsePeople(config.get("aut"));
        covers = parsePeople(config.get("cov"));
        date = dateToLocalDate((Date) config.get("date"));
        history = parseHistory((List<List>) config.get("history"));
        rights = (String) config.get("rights");
    }

    /*
     * ブック名を取得する。
     *
     * @return ブック名
     */
    String getBookName() {
        // ブック名を返す
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

    /*
     * 人物情報をパースし、結果を {@code List<String>} に変換する。
     *
     * 以下のパターンに対応している。
     *   1. "著者 太郎"
     *   2. ["著者 太郎", "著者 次郎", ...]
     *   3. [{name: "著者 太郎", file-as: "チョシャ タロウ"}, {name: "著者 次郎", file-as: "チョシャ ジロウ"}, ...]
     *
     * @param data 人物情報
     * @return パース結果
     */
    private List<String> parsePeople(Object data) {
        // nullが指定された場合はそのまま返す
        if (data == null) {
            return null;
        }

        // パース結果格納用
        List<String> people = new ArrayList<>();

        // 人物情報をパースする
        if (data instanceof String) {
            // パターン1の場合
            people.add((String) data);
        } else if (data instanceof List) {
            for (Object person : (List) data) {
                if (person instanceof String) {
                    // パターン2の場合
                    people.add((String) person);
                } else if (person instanceof Map) {
                    // パターン3の場合
                    String name = (String) ((Map) person).get("name");
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
     * {@code Date} を {@code LocalDate} に変換する。
     *
     * @param date 変換元の {@code Date}
     * @return 変換後の {@code LocalDate}
     */
    private LocalDate dateToLocalDate(Date date) {
        // nullが指定された場合はそのまま返す
        if (date == null) {
            return null;
        }

        // DateをLocalDateに変換する
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /*
     * 発行年月をパースし、結果を {@code List<List<HistoryItem>>} に変換する。
     *
     * 以下のパターンに対応している。
     *   1. [["2018-10-08", "2018-12-30", ...], ["2019-04-14", ...], ...]
     *   2. [["2018-10-08 技術書典5", "2018-12-30 C95", ...], ["2019-04-14 技術書典6", ...], ...]
     *
     * @param history 発行年月
     * @return パース結果
     */
    private List<List<HistoryItem>> parseHistory(List<List> history) {
        // nullが指定された場合はそのまま返す
        if (history == null) {
            return null;
        }

        // パース結果格納用
        List<List<HistoryItem>> historyItems = new ArrayList<>();

        // 発行年月をパースする
        for (List edition : history) {
            List<HistoryItem> editionItems = new ArrayList<>();
            for (Object printing : edition) {
                if (printing instanceof Date) {
                    // パターン1の場合
                    editionItems.add(new HistoryItem(dateToLocalDate((Date) printing), ""));
                } else if (printing instanceof String) {
                    // パターン2の場合
                    String[] elements = ((String) printing).split(" ", 2);
                    editionItems.add(new HistoryItem(
                        LocalDate.parse(elements[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        elements[1]
                    ));
                }
            }
            historyItems.add(editionItems);
        }

        // パース結果を返す
        return historyItems;
    }
}
