/*
 * Copyright 2019 HASEBA Junya
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

import blue.lions.revdoc.ast.old.AppendixNode;
import blue.lions.revdoc.ast.old.BackMatterNode;
import blue.lions.revdoc.ast.old.BodyMatterNode;
import blue.lions.revdoc.ast.old.ChapterNode;
import blue.lions.revdoc.ast.old.ColumnNode;
import blue.lions.revdoc.ast.old.FootnoteIDNode;
import blue.lions.revdoc.ast.old.FootnoteNode;
import blue.lions.revdoc.ast.old.FrontMatterNode;
import blue.lions.revdoc.ast.old.HeadingNode;
import blue.lions.revdoc.ast.old.ImageNode;
import blue.lions.revdoc.ast.old.InnerParagraphNode;
import blue.lions.revdoc.ast.old.LinkNode;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.ast.old.OrderedListItemNode;
import blue.lions.revdoc.ast.old.OrderedListNode;
import blue.lions.revdoc.ast.old.ParagraphNode;
import blue.lions.revdoc.ast.old.PartNode;
import blue.lions.revdoc.ast.old.RootNode;
import blue.lions.revdoc.ast.old.TextNode;
import blue.lions.revdoc.ast.old.UnorderedListItemNode;
import blue.lions.revdoc.ast.old.UnorderedListNode;
import blue.lions.revdoc.ast.Visitor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFootnote;
import org.apache.poi.xwpf.usermodel.XWPFFootnotes;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象構文木を辿ってWordファイルを出力するVisitor。
 */
public class WordVisitor implements Visitor {

    /**
     * 画像フォーマット情報を格納するクラス。
     */
    public static class ImageFormat {

        /* 拡張子 */
        private String extension;

        /* 画像タイプ */
        private int pictureType;

        /**
         * {@code ImageFormat} オブジェクトを構築する。
         *
         * @param extension 拡張子
         * @param pictureType 画像タイプ
         */
        public ImageFormat(String extension, int pictureType) {
            // フィールドを初期化する
            this.extension = extension;
            this.pictureType = pictureType;
        }

        /**
         * 拡張子を取得する。
         *
         * @return 拡張子
         */
        public String getExtension() {
            // 拡張子を返す
            return extension;
        }

        /**
         * 画像タイプを取得する。
         *
         * @return 画像タイプ
         */
        public int getPictureType() {
            // 画像タイプを返す
            return pictureType;
        }
    }

    /* 対応画像フォーマット */
    private static final List<ImageFormat> IMAGE_FORMATS;

    /* 入力ディレクトリパス */
    private String inputDirectoryPath;

    /* 処理中のWordドキュメント */
    private XWPFDocument document;

    /* 処理中の章ID */
    private String chapterID;

    /* 処理中の段落 */
    private XWPFParagraph paragraph;

    /* 処理中のラン */
    private XWPFRun run;

    /* 解析済みの脚注 */
    private XWPFFootnotes footnotes;

    /* 脚注ID→インデックスマッピング用 */
    private Map<String, Integer> footnoteIndices;

    /* 脚注解析中フラグ */
    private boolean isFootnote = false;

    // 初期化処理
    static {
        IMAGE_FORMATS = new ArrayList<>();
        IMAGE_FORMATS.add(new ImageFormat("png", XWPFDocument.PICTURE_TYPE_PNG));
        IMAGE_FORMATS.add(new ImageFormat("jpg", XWPFDocument.PICTURE_TYPE_JPEG));
        IMAGE_FORMATS.add(new ImageFormat("jpeg", XWPFDocument.PICTURE_TYPE_JPEG));
    }

    /**
     * {@code WordVisitor} オブジェクトを構築する。
     *
     * @param document Wordドキュメント
     * @param inputDirectoryPath 入力ディレクトリパス
     */
    public WordVisitor(XWPFDocument document, String inputDirectoryPath) {
        // フィールドを初期化する
        this.document = document;
        this.inputDirectoryPath = inputDirectoryPath;
    }

    /** {@inheritDoc} */
    @Override
    public void visit(RootNode node) {
        // ドキュメントの初期処理
        footnotes = document.createFootnotes();

        // 子ノードを取得する
        List<Node> children = node.getChildren();

        // 前付の処理を実行する
        children.get(0).accept(this);

        // 本文の処理を実行する
        children.get(1).accept(this);

        // 付録の処理を実行する
        children.get(2).accept(this);

        // 後付の処理を実行する
        children.get(3).accept(this);
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
        // IDを更新する
        chapterID = node.getId();

        // 脚注を初期化する
        footnoteIndices = new HashMap<>();

        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    public void visit(ColumnNode node) {
        // 見出しを出力する
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setBold(true);
        run.setText(node.getTitle());

        // 子ノードを辿って処理を実行する
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(HeadingNode node) {
        // 見出しを出力する
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setBold(true);
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(ImageNode node) {
        // 画像を挿入する
        for (ImageFormat imageFormat : IMAGE_FORMATS) {
            String imageFileName = node.getId() + "." + imageFormat.getExtension();
            Path imageFilePath = Paths.get(inputDirectoryPath, "images", chapterID, imageFileName);
            if (Files.exists(imageFilePath)) {
                double ratio;
                try (InputStream inputStream = Files.newInputStream(imageFilePath)) {
                    BufferedImage bufferedImage = ImageIO.read(inputStream);
                    ratio = (double) bufferedImage.getHeight() / bufferedImage.getWidth();
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                paragraph = document.createParagraph();
                run = paragraph.createRun();
                try (InputStream inputStream = Files.newInputStream(imageFilePath)) {
                    run.addPicture(
                        inputStream,
                        imageFormat.getPictureType(),
                        node.getCaption(),
                        Units.toEMU(400),
                        Units.toEMU(400 * ratio)
                    );
                    return;
                } catch (InvalidFormatException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(FootnoteNode node) {
        // 脚注の処理を開始
        isFootnote = true;

        // 脚注を作成する
        XWPFFootnote footnote = footnotes.createFootnote();
        paragraph = footnote.createParagraph();
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
        footnoteIndices.put(node.getId(), footnotes.getFootnotesList().size() - 1);

        // 脚注の処理を終了
        isFootnote = false;
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
        paragraph.addFootnoteReference(footnotes.getFootnotesList().get(footnoteIndices.get(node.getId())));
    }

    /** {@inheritDoc} */
    @Override
    public void visit(LinkNode node) {
        // リンクを出力する
        try {
            PackagePart part = isFootnote ? footnotes.getPackagePart() : document.getPackagePart();
            String id = part.addExternalRelationship(node.getUrl(), XWPFRelation.HYPERLINK.getRelation()).getId();
            CTHyperlink hyperlink = paragraph.getCTP().addNewHyperlink();
            hyperlink.setId(id);
            hyperlink.addNewR();
            XWPFHyperlinkRun run = new XWPFHyperlinkRun(hyperlink, hyperlink.getRArray(0), paragraph);
            run.setText(node.getLabel());
        } catch (Exception e) {
            run.setText(node.getLabel());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visit(TextNode node) {
        // テキストの内容を出力する
        run.setText(node.getText());
    }
}
