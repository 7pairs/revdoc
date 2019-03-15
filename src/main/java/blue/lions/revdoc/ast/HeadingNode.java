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
 * 見出しを表現するノード。
 */
public class HeadingNode extends ParentNode {

    /* 見出しレベル */
    private int level;

    /* ID */
    private String id;

    /**
     * {@code HeadingNode} オブジェクトを構築する。
     *
     * @param level 見出しレベル
     * @param child 子ノード
     * @param id ID
     */
    public HeadingNode(int level, Node child, String id) {
        // フィールドを初期化する
        this.level = level;
        appendChild(child);
        this.id = id;
    }

    /**
     * 見出しレベルを取得する。
     *
     * @return 見出しレベル
     */
    public int getLevel() {
        // 見出しレベルを返す
        return level;
    }

    /**
     * IDを取得する。
     *
     * @return ID
     */
    public String getId() {
        // IDを返す
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        // HeadingNodeに対する処理を実行する
        visitor.visit(this);
    }
}
