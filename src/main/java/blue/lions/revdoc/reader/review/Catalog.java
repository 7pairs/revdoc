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

import java.util.List;
import java.util.Map;

/*
 * catalog.ymlの情報を格納するクラス。
 */
class Catalog {

    /* 前付 */
    private List<String> predef;

    /* 章 */
    private List<List<String>> chapters;

    /* 付録 */
    private List<String> appendix;

    /* 後付 */
    private List<String> postdef;

    /*
     * {@code Catalog} オブジェクトを構築する。
     *
     * @param yamlString YAML文字列
     */
    Catalog(String yamlString) {
        // YAMLをパースする
        Yaml yaml = new Yaml();
        Map catalog = yaml.loadAs(yamlString, Map.class);

        // フィールドを初期化する
        predef = (List<String>) catalog.get("PREDEF");
        chapters = (List<List<String>>) catalog.get("CHAPS");
        appendix = (List<String>) catalog.get("APPENDIX");
        postdef = (List<String>) catalog.get("POSTDEF");
    }
}
