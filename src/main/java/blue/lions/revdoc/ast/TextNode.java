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
 * 文字列を表現するノード。
 */
public class TextNode extends Node {

    /* 文字列 */
    private String text;

    /**
     * {@code TextNode} を構築する。
     *
     * @param text 文字列
     */
    public TextNode(String text) {
        // フィールドを初期化する
        this.text = text;
    }

    /**
     * 文字列を取得する。
     *
     * @return 文字列
     */
    public String getText() {
        // 文字列を返す
        return text;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        // TextNodeに対する処理を実行する
        visitor.visit(this);
    }
}
