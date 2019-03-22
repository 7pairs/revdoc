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
 * 章を表現するノード。
 */
public class ChapterNode extends ParentNode {

    /* ID */
    private String id;

    /**
     * {@code ChapterNode} オブジェクトを構築する。
     *
     * @param id ID
     */
    public ChapterNode(String id) {
        // フィールドを初期化する
        this.id = id;
    }

    /**
     * IDを取得する。
     *
     * @return ID
     */
    public String getId() {
        // ファイル名を返す
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        // ChapterNodeに対する処理を実行する
        visitor.visit(this);
    }
}
