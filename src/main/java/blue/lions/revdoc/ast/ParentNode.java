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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 子ノードを持つノードのベースクラス。
 */
public abstract class ParentNode extends Node {

    /* 子ノード */
    private List<Node> children = new ArrayList<>();

    /**
     * 子ノードを取得する。
     *
     * @return 子ノード
     */
    public List<Node> getChildren() {
        // 子ノードを返す
        return Collections.unmodifiableList(children);
    }

    /**
     * 子ノードを追加する。
     *
     * @param child 子ノード
     */
    public void appendChild(Node child) {
        // 子ノードを追加する
        children.add(child);
    }

    /**
     * 子ノードを指定された場所に追加する。
     *
     * @param index 追加場所
     * @param child 子ノード
     */
    public void appendChild(int index, Node child) {
        // 子ノードを追加する
        children.add(index, child);
    }

    /**
     * 複数の子ノードを追加する。
     *
     * @param children 子ノード
     */
    public void appendChildren(List<Node> children) {
        // 子ノードを追加する
        this.children.addAll(children);
    }
}
