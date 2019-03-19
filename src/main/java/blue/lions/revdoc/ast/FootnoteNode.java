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
 * 脚注を表現するノード。
 */
public class FootnoteNode extends ParentNode {

    /* ID */
    private String id;

    /**
     * {@code FootnoteNode} を構築する。
     *
     * @param id 脚注ID
     * @param innerParagraphNode 脚注内容
     */
    public FootnoteNode(String id, InnerParagraphNode innerParagraphNode) {
        // フィールドを初期化する
        this.id = id;
        appendChild(innerParagraphNode);
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
        // FootnoteNodeに対する処理を実行する
        visitor.visit(this);
    }
}
