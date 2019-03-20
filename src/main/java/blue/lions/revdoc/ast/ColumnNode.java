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
package blue.lions.revdoc.ast;

/**
 * コラムを表現するノード。
 */
public class ColumnNode extends ParentNode {

    /* タイトル */
    private String title;

    /**
     * {@code ColumnNode} オブジェクトを構築する。
     *
     * @param title タイトル
     */
    public ColumnNode(String title) {
        // フィールドを初期化する
        this.title = title;
    }

    /**
     * タイトルを取得する。
     *
     * @return タイトル
     */
    public String getTitle() {
        // タイトルを返す
        return title;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        // ColumnNodeに対する処理を実行する
        visitor.visit(this);
    }
}
