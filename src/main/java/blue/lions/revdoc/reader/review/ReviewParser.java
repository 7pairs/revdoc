/*
 * Copyright 2019 HASEBA, Junya
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
package blue.lions.revdoc.reader.review;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/*
 * Re:VIEW文書のパーサークラス。
 */
@BuildParseTree
class ReviewParser extends BaseParser<Object> {

    /*
     * Start = Spacer Chapters? Spacer
     */
    Rule Start() {
        return Sequence(
            Spacer(),
            Optional(Chapters()),
            Spacer()
        );
    }

    /*
     * Chapters = Chapter Chapters?
     */
    Rule Chapters() {
        return Sequence(
            Chapter(),
            Optional(Chapters())
        );
    }

    /*
     * Chapter = SingleLineComments? Headline Contents?
     */
    Rule Chapter() {
        return Sequence(
            Optional(SingleLineComments()),
            Headline(),
            Optional(Contents())
        );
    }

    /*
     * Headline = "="+ BraceArg? Space* SingleLineContent BlankLines
     */
    Rule Headline() {
        return Sequence(
            OneOrMore("="),
            Optional(BraceArg()),
            ZeroOrMore(Space()),
            SingleLineContent(),
            BlankLines()
        );
    }

    /*
     * Contents = &. Content Contents? BlankLines
     */
    Rule Contents() {
        return Sequence(
            Test(ANY),
            Content(),
            Optional(Contents()),
            BlankLines()
        );
    }

    /*
     * Content = SingleLineComment / BlockElement / UList / OList / DList / Paragraph / Column
     */
    Rule Content() {
        return FirstOf(
            SingleLineComment(),
            BlockElement(),
            UList(),
            OList(),
            DList(),
            Paragraph(),
            Column()
        );
    }

    /*
     * Paragraph = !"=" ParagraphSubs BlankLines
     */
    Rule Paragraph() {
        return Sequence(
            TestNot("="),
            ParagraphSubs(),
            BlankLines()
        );
    }

    /*
     * ParagraphSubs = ParagraphSub ParagraphSubs?
     */
    Rule ParagraphSubs() {
        return Sequence(
            ParagraphSub(),
            Optional(ParagraphSubs())
        );
    }

    /*
     * ParagraphSub = InlineElement Newline? / ContentText Newline?
     */
    Rule ParagraphSub() {
        return FirstOf(
            Sequence(InlineElement(), Optional(Newline())),
            Sequence(ContentText(), Optional(Newline()))
        );
    }

    /*
     * ContentText = !Newline !Headline !SingleLineComment !BlockElement !UList !OList !DList
     *               (!InlineElement [^\r\n])+
     */
    Rule ContentText() {
        return Sequence(
            TestNot(Newline()),
            TestNot(Headline()),
            TestNot(SingleLineComment()),
            TestNot(BlockElement()),
            TestNot(UList()),
            TestNot(OList()),
            TestNot(DList()),
            OneOrMore(TestNot(InlineElement()), NoneOf(new char[] {'\r', '\n'}))
        );
    }

    /*
     * BlockElement = "//" LowerAlphabet+ BracketArg* "{" BlankLines BlockElementContents? "//}" BlankLines
     *              / "//" LowerAlphabet+ BracketArg* BlankLines
     */
    Rule BlockElement() {
        return FirstOf(
            Sequence("//", OneOrMore(LowerAlphabet()), ZeroOrMore(BracketArg()), "{", BlankLines(),
                Optional(BlockElementContents()), "//}", BlankLines()),
            Sequence("//", OneOrMore(LowerAlphabet()), ZeroOrMore(BracketArg()), BlankLines())
        );
    }

    /*
     * InlineElement = "@<" [^>\r\n]+ ">" "{" InlineElementContents? "}"
     */
    Rule InlineElement() {
        return Sequence(
            "@<",
            OneOrMore(NoneOf(new char[] {'>', '\r', '\n'})),
            ">",
            "{",
            Optional(InlineElementContents()),
            "}"
        );
    }

    /*
     * Column = ColumnHeadline ColumnContents? ColumnTerminator?
     */
    Rule Column() {
        return Sequence(
            ColumnHeadline(),
            Optional(ColumnContents()),
            Optional(ColumnTerminator())
        );
    }

    /*
     * ColumnHeadline = "="+ "[column]" Space* SingleLineContent BlankLines
     */
    Rule ColumnHeadline() {
        return Sequence(
            OneOrMore("="),
            "[column]",
            ZeroOrMore(Space()),
            SingleLineContent(),
            BlankLines()
        );
    }

    /*
     * ColumnContents = &. ColumnContent ColumnContents? BlankLines
     */
    Rule ColumnContents() {
        return Sequence(
            Test(ANY),
            ColumnContent(),
            Optional(ColumnContents()),
            BlankLines()
        );
    }

    /*
     * ColumnContent = !ColumnTerminator SingleLineComment
     *               / !ColumnTerminator BlockElement
     *               / !ColumnTerminator UList
     *               / !ColumnTerminator OList
     *               / !ColumnTerminator DList
     *               / !ColumnTerminator Paragraph
     *               / !ColumnTerminator Chapter
     */
    Rule ColumnContent() {
        return FirstOf(
            Sequence(TestNot(ColumnTerminator()), SingleLineComment()),
            Sequence(TestNot(ColumnTerminator()), BlockElement()),
            Sequence(TestNot(ColumnTerminator()), UList()),
            Sequence(TestNot(ColumnTerminator()), OList()),
            Sequence(TestNot(ColumnTerminator()), DList()),
            Sequence(TestNot(ColumnTerminator()), Paragraph()),
            Sequence(TestNot(ColumnTerminator()), Chapter())
        );
    }

    /*
     * ColumnTerminator = "="+ "[/column]" Space* BlankLines
     */
    Rule ColumnTerminator() {
        return Sequence(
            OneOrMore("="),
            "[/column]",
            ZeroOrMore(Space()),
            BlankLines()
        );
    }

    /*
     * BracketArg = "[" BracketArgSubs "]"
     */
    Rule BracketArg() {
        return Sequence(
            "[",
            BracketArgSubs(),
            "]"
        );
    }

    /*
     * BracketArgSubs = BracketArgSub BracketArgSubs?
     */
    Rule BracketArgSubs() {
        return Sequence(
            BracketArgSub(),
            Optional(BracketArgSubs())
        );
    }

    /*
     * BracketArgSub = InlineElement / BracketArgText
     */
    Rule BracketArgSub() {
        return FirstOf(
            InlineElement(),
            BracketArgText()
        );
    }

    /*
     * BracketArgText = (!Newline !InlineElement ("\\\\" / "\\]" / "\\" / !"]" .))+
     */
    Rule BracketArgText() {
        return OneOrMore(
            TestNot(Newline()),
            TestNot(InlineElement()),
            FirstOf("\\\\", "\\]", "\\", Sequence(TestNot("]"), ANY))
        );
    }

    /*
     * BraceArg = "{" ([^\r\n\}\\]+ ("\\\\" / "\\}" / "\\")?)* "}"
     */
    Rule BraceArg() {
        return Sequence(
            "{",
            ZeroOrMore(OneOrMore(NoneOf(new char[] {'\r', '\n', '}', '\\'})),
                Optional(FirstOf("\\\\", "\\}", "\\"))),
            "}"
        );
    }

    /*
     * BlockElementContents = BlockElementContent BlockElementContents? BlankLines
     */
    Rule BlockElementContents() {
        return Sequence(
            BlockElementContent(),
            Optional(BlockElementContents()),
            BlankLines()
        );
    }

    /*
     * BlockElementContent = SingleLineComment / BlockElement / UList / OList / DList / BlockElementParagraph
     */
    Rule BlockElementContent() {
        return FirstOf(
            SingleLineComment(),
            BlockElement(),
            UList(),
            OList(),
            DList(),
            BlockElementParagraph()
        );
    }

    /*
     * BlockElementParagraph = BlockElementParagraphSubs BlankLines
     */
    Rule BlockElementParagraph() {
        return Sequence(
            BlockElementParagraphSubs(),
            BlankLines()
        );
    }

    /*
     * BlockElementParagraphSubs = BlockElementParagraphSub BlockElementParagraphSubs?
     */
    Rule BlockElementParagraphSubs() {
        return Sequence(
            BlockElementParagraphSub(),
            Optional(BlockElementParagraphSubs())
        );
    }

    /*
     * BlockElementParagraphSub = InlineElement / BlockElementContentText
     */
    Rule BlockElementParagraphSub() {
        return FirstOf(
            InlineElement(),
            BlockElementContentText()
        );
    }

    /*
     * BlockElementContentText = (&. !"//}" !SingleLineComment !BlockElement !UList !OList !DList
     *                               (!InlineElement [^\r\n])+ Newline?)+
     */
    Rule BlockElementContentText() {
        return OneOrMore(
            Test(ANY),
            TestNot("//}"),
            TestNot(SingleLineComment()),
            TestNot(BlockElement()),
            TestNot(UList()),
            TestNot(OList()),
            TestNot(DList()),
            OneOrMore(TestNot(InlineElement()), NoneOf(new char[] {'\r', '\n'})),
            Optional(Newline())
        );
    }

    /*
     * InlineElementContents = !"}" InlineElementContent InlineElementContents?
     */
    Rule InlineElementContents() {
        return Sequence(
            TestNot("}"),
            InlineElementContent(),
            Optional(InlineElementContents())
        );
    }

    /*
     * InlineElementContent = InlineElement / InlineElementContentText
     */
    Rule InlineElementContent() {
        return FirstOf(
            InlineElement(),
            InlineElementContentText()
        );
    }

    /*
     * InlineElementContentText = (!InlineElement [^\r\n}])+
     */
    Rule InlineElementContentText() {
        return OneOrMore(
            TestNot(InlineElement()),
            NoneOf(new char[] {'\r', '\n'})
        );
    }

    /*
     * SingleLineContent = ContentInlines (Newline / EOI)
     */
    Rule SingleLineContent() {
        return Sequence(
            ContentInlines(),
            FirstOf(Newline(), EOI)
        );
    }

    /*
     * ContentInlines = ContentInline ContentInlines?
     */
    Rule ContentInlines() {
        return Sequence(
            ContentInline(),
            Optional(ContentInlines())
        );
    }

    /*
     * ContentInline = InlineElement / ContentInlineText
     */
    Rule ContentInline() {
        return FirstOf(
            InlineElement(),
            ContentInlineText()
        );
    }

    /*
     * ContentInlineText = (!InlineElement [^\r\n])+
     */
    Rule ContentInlineText() {
        return OneOrMore(
            TestNot(InlineElement()),
            NoneOf(new char[] {'\r', '\n'})
        );
    }

    /*
     * UList = (UListElement / SingleLineComment) UList? BlankLines
     */
    Rule UList() {
        return Sequence(
            FirstOf(UListElement(), SingleLineComment()),
            Optional(UList()),
            BlankLines()
        );
    }

    /*
     * UListElement = " "+ "*"+ Space* SingleLineContent
     */
    Rule UListElement() {
        return Sequence(
            OneOrMore(" "),
            OneOrMore("*"),
            ZeroOrMore(Space()),
            SingleLineContent()
        );
    }

    /*
     * OList = (OListElement / SingleLineComment) OList? BlankLines
     */
    Rule OList() {
        return Sequence(
            FirstOf(OListElement(), SingleLineComment()),
            Optional(OList()),
            BlankLines()
        );
    }

    /*
     * OListElement = " "+ Digits "." Space* SingleLineContent
     */
    Rule OListElement() {
        return Sequence(
            OneOrMore(" "),
            Digits(),
            ".",
            ZeroOrMore(Space()),
            SingleLineContent()
        );
    }

    /*
     * DList = (DListElement / SingleLineComment) DList? BlankLines
     */
    Rule DList() {
        return Sequence(
            FirstOf(DListElement(), SingleLineComment()),
            Optional(DList()),
            BlankLines()
        );
    }

    /*
     * DListElement = " "* ":" " " Space* SingleLineContent DListElementContents BlankLines
     */
    Rule DListElement() {
        return Sequence(
            ZeroOrMore(" "),
            ":",
            " ",
            ZeroOrMore(Space()),
            SingleLineContent(),
            DListElementContents(),
            BlankLines()
        );
    }

    /*
     * DListElementContents = DListElementContent DListElementContents? BlankLines
     */
    Rule DListElementContents() {
        return Sequence(
            DListElementContent(),
            Optional(DListElementContents()),
            BlankLines()
        );
    }

    /*
     * DListElementContent = [ \t]+ SingleLineContent Newline?
     */
    Rule DListElementContent() {
        return Sequence(
            OneOrMore(AnyOf(new char[] {' ', '\t'})),
            SingleLineContent(),
            Optional(Newline())
        );
    }

    /*
     * SingleLineComments = SingleLineComment SingleLineComments?
     */
    Rule SingleLineComments() {
        return Sequence(
            SingleLineComment(),
            Optional(SingleLineComments())
        );
    }

    /*
     * SingleLineComment = "#@" [^\r\n]* Newline? BlankLines
     */
    Rule SingleLineComment() {
        return Sequence(
            "#@",
            ZeroOrMore(NoneOf(new char[] {'\r', '\n'})),
            Optional(Newline()),
            BlankLines()
        );
    }

    /*
     * Digits = Digit+
     */
    Rule Digits() {
        return OneOrMore(
            Digit()
        );
    }

    /*
     * Digit = [0-9]
     */
    Rule Digit() {
        return CharRange(
            '0',
            '9'
        );
    }

    /*
     * LowerAlphabet = [a-z]
     */
    Rule LowerAlphabet() {
        return CharRange(
            'a',
            'z'
        );
    }

    /*
     * Newline = "\r\n" / "\n"
     */
    Rule Newline() {
        return FirstOf(
            "\r\n",
            "\n"
        );
    }

    /*
     * BlankLines = ([ \t]* Newline)*
     */
    Rule BlankLines() {
        return ZeroOrMore(
            ZeroOrMore(AnyOf(new char[] {' ', '\t'})),
            Newline()
        );
    }

    /*
     * Spacer = [ \t\r\n]*
     */
    Rule Spacer() {
        return ZeroOrMore(
            AnyOf(new char[] {' ', '\t', '\r', '\n'})
        );
    }

    /*
     * Space = [ 　\t]
     */
    Rule Space() {
        return AnyOf(
            new char[] {' ', '　', '\t'}
        );
    }

    /*
     * pop() の結果を代入先の型で取得する。
     *
     * @return pop() の結果
     */
    <T> T popAs() {
        // 代入先の型で返す
        return (T) pop();
    }
}
