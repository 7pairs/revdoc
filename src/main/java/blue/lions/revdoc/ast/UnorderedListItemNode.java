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
 * 番号なしリストの要素を表現するノード。
 */
public class UnorderedListItemNode extends ParentNode {

    /* ネストレベル */
    private int level;

    /**
     * {@code UnorderedListItemNode} を構築する。
     *
     * @param level ネストレベル
     * @param textNode 要素内容
     */
    public UnorderedListItemNode(int level, TextNode textNode) {
        // フィールドを初期化する
        this.level = level;
        appendChild(textNode);
    }

    /**
     * ネストレベルを取得する。
     *
     * @return ネストレベル
     */
    public int getLevel() {
        // ネストレベルを返す
        return level;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        // UnorderedListItemNodeに対する処理を実行する
        visitor.visit(this);
    }
}
