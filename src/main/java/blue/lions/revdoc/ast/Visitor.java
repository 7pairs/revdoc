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
 * 抽象構文木を辿るVisitorのインターフェイス。
 */
public interface Visitor {

    /**
     * {@code RootNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(RootNode node);

    /**
     * {@code FrontMatterNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(FrontMatterNode node);

    /**
     * {@code BodyMatterNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(BodyMatterNode node);

    /**
     * {@code AppendixNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(AppendixNode node);

    /**
     * {@code BackMatterNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(BackMatterNode node);

    /**
     * {@code PartNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(PartNode node);

    /**
     * {@code ChapterNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(ChapterNode node);

    /**
     * {@code HeadingNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(HeadingNode node);

    /**
     * {@code ParagraphNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(ParagraphNode node);

    /**
     * {@code TextNode} に対する処理を実行する。
     *
     * @param node 処理対象のノード
     */
    void visit(TextNode node);
}
