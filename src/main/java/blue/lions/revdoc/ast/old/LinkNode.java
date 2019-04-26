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
package blue.lions.revdoc.ast.old;

import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.Visitor;

/**
 * リンクを表現するノード。
 */
public class LinkNode extends ParentNode {

    /* URL */
    private String url;

    /* ラベル */
    private String label;

    /**
     * {@code LinkNode} オブジェクトを構築する。
     *
     * @param url URL
     * @param label ラベル
     */
    public LinkNode(String url, String label) {
        // フィールドを初期化する
        this.url = url;
        this.label = label.length() > 0 ? label : url;
    }

    /**
     * URLを取得する。
     *
     * @return URL
     */
    public String getUrl() {
        // URLを返す
        return url;
    }

    /**
     * ラベルを取得する。
     *
     * @return ラベル
     */
    public String getLabel() {
        // ラベルを返す
        return label;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        // LinkNodeに対する処理を実行する
        visitor.visit(this);
    }
}
