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

import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.Visitor;

import java.math.BigDecimal;

/**
 * 画像を表現するノード。
 */
public class ImageNode extends Node {

    /* ID */
    private String id;

    /* 見出し */
    private String caption;

    /* 比率 */
    private BigDecimal scale;

    /**
     * {@code ImageNode} オブジェクトを構築する。
     *
     * @param id ID
     * @param caption 見出し
     * @param scale 比率
     */
    public ImageNode(String id, String caption, String scale) {
        // フィールドを初期化する
        this.id = id;
        this.caption = caption;

        // 比率を初期化する
        try {
            this.scale = new BigDecimal(scale);
        } catch (NumberFormatException e) {
            this.scale = new BigDecimal("1.0");
        }
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

    /**
     * 見出しを取得する。
     *
     * @return 見出し
     */
    public String getCaption() {
        // 見出しを返す
        return caption;
    }

    /**
     * 比率を取得する。
     *
     * @return 比率
     */
    public BigDecimal getScale() {
        // 比率を返す
        return scale;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        // ImageNodeに対する処理を実行する
        visitor.visit(this);
    }
}
