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
package blue.lions.revdoc.writer.word;

import blue.lions.revdoc.ast.AppendixNode;
import blue.lions.revdoc.ast.BackMatterNode;
import blue.lions.revdoc.ast.BodyMatterNode;
import blue.lions.revdoc.ast.ChapterNode;
import blue.lions.revdoc.ast.FrontMatterNode;
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.ParentNode;
import blue.lions.revdoc.ast.PartNode;
import blue.lions.revdoc.ast.RootNode;
import blue.lions.revdoc.ast.TextNode;
import blue.lions.revdoc.ast.Visitor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * 抽象構文木を辿ってWordファイルを出力するVisitor。
 */
public class WordVisitor implements Visitor {

    /* Wordドキュメント */
    private XWPFDocument document;

    /**
     * {@code WordVisitor} オブジェクトを構築する。
     *
     * @param document Wordドキュメント
     */
    public WordVisitor(XWPFDocument document) {
        // フィールドを初期化する
        this.document = document;
    }

    /**
     * 抽象構文木を辿り、それぞれのノードに対する処理を実行する。
     *
     * @param node 抽象構文木
     */
    public void accept(Node node) {
        // 指定されたノードに対する処理を実行する
        node.accept(this);

        // 子ノードに対する処理を実行する
        if (node instanceof ParentNode) {
            for (Node child : ((ParentNode) node).getChildren()) {
                accept(child);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(RootNode node) {

    }

    /** {@inheritDoc} */
    @Override
    public void visit(FrontMatterNode node) {

    }

    /** {@inheritDoc} */
    @Override
    public void visit(BodyMatterNode node) {

    }

    /** {@inheritDoc} */
    @Override
    public void visit(AppendixNode node) {

    }

    /** {@inheritDoc} */
    @Override
    public void visit(BackMatterNode node) {

    }

    /** {@inheritDoc} */
    @Override
    public void visit(PartNode node) {

    }

    /** {@inheritDoc} */
    @Override
    public void visit(ChapterNode node) {

    }

    /** {@inheritDoc} */
    @Override
    public void visit(HeadingNode node) {
        // 見出しを出力する
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(((TextNode) node.getChildren().get(0)).getText());
        // ToDo: 見出しのスタイルを適用する
    }

    /** {@inheritDoc} */
    @Override
    public void visit(ParagraphNode node) {
        // 段落を出力する
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(((TextNode) node.getChildren().get(0)).getText());
    }

    /** {@inheritDoc} */
    @Override
    public void visit(TextNode node) {
        // TextNodeの内容は親ノードで出力する
    }
}
