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
import blue.lions.revdoc.ast.FootnoteIDNode;
import blue.lions.revdoc.ast.FootnoteNode;
import blue.lions.revdoc.ast.FrontMatterNode;
import blue.lions.revdoc.ast.HeadingNode;
import blue.lions.revdoc.ast.InnerParagraphNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.OrderedListItemNode;
import blue.lions.revdoc.ast.OrderedListNode;
import blue.lions.revdoc.ast.ParagraphNode;
import blue.lions.revdoc.ast.PartNode;
import blue.lions.revdoc.ast.RootNode;
import blue.lions.revdoc.ast.TextNode;
import blue.lions.revdoc.ast.UnorderedListItemNode;
import blue.lions.revdoc.ast.UnorderedListNode;
import blue.lions.revdoc.ast.Visitor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFootnote;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象構文木を辿ってWordファイルを出力するVisitor。
 */
public class WordVisitor implements Visitor {

    /* 処理中のWordドキュメント */
    private XWPFDocument document;

    /* 処理中の段落 */
    private XWPFParagraph paragraph;

    /* 処理中のラン */
    private XWPFRun run;

    /* 解析済みの脚注 */
    private Map<String, XWPFFootnote> footnotes;

    /**
     * {@code WordVisitor} オブジェクトを構築する。
     *
     * @param document Wordドキュメント
     */
    public WordVisitor(XWPFDocument document) {
        // フィールドを初期化する
        this.document = document;
    }

    /** {@inheritDoc} */
    @Override
    public void visit(RootNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(FrontMatterNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(BodyMatterNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(AppendixNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(BackMatterNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(PartNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(ChapterNode node) {
        // 脚注を初期化する
        footnotes = new HashMap<>();

        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(HeadingNode node) {
        // 見出しを出力する
        XWPFParagraph paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setBold(true);
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(FootnoteNode node) {
        // 脚注を作成する
        XWPFFootnote footnote = document.createFootnote();
        paragraph = footnote.createParagraph();
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
        footnotes.put(node.getId(), footnote);
    }

    /** {@inheritDoc} */
    @Override
    public void visit(UnorderedListNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(UnorderedListItemNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            paragraph = document.createParagraph();
            paragraph.setNumID(BigInteger.valueOf(2));
            paragraph.getCTP().getPPr().getNumPr().addNewIlvl().setVal(BigInteger.valueOf(node.getLevel() - 1));
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(OrderedListNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(OrderedListItemNode node) {
        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            paragraph = document.createParagraph();
            paragraph.setNumID(BigInteger.valueOf(2));
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(ParagraphNode node) {
        // 段落を出力する
        paragraph = document.createParagraph();
        for (Node child : node.getChildren()) {
            run = paragraph.createRun();
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(InnerParagraphNode node) {
        // 段落を出力する
        for (Node child : node.getChildren()) {
            run = paragraph.createRun();
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(FootnoteIDNode node) {
        // 脚注を関連付ける
        paragraph.addFootnoteReference(footnotes.get(node.getId()));
    }

    /** {@inheritDoc} */
    @Override
    public void visit(TextNode node) {
        // テキストの内容を出力する
        run.setText(node.getText());
    }
}
