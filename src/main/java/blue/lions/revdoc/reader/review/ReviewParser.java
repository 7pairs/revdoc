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
     *               ( !InlineElement [^\r\n] )+
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
     * pop() の結果を代入先の型で取得する。
     *
     * @return pop() の結果
     */
    <T> T popAs() {
        // 代入先の型で返す
        return (T) pop();
    }
}
