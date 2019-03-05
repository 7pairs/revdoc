package blue.lions.revdoc;

import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.parser.ReviewParser;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RevDoc {

    public static void main(String... args) {
        Arguments arguments = new Arguments();
        CmdLineParser cmdLineParser = new CmdLineParser(arguments);
        try {
            cmdLineParser.parseArgument(args);

            String input = "= 見出し\n\n1行目\n2行目\n\n2段落目\n";
            ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
            ParsingResult<Node> result = new ReportingParseRunner<Node>(reviewParser.Root()).run(input);

            XWPFDocument document = new XWPFDocument();
            result.resultValue.export(document);
            FileOutputStream fout = new FileOutputStream("~/test.docx");
            document.write(fout);
        } catch (CmdLineException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
