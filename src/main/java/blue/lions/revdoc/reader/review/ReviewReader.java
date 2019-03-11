package blue.lions.revdoc.reader.review;

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.reader.Reader;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class ReviewReader extends Reader {

    private Arguments arguments;

    public ReviewReader(Arguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public Node run() {
        Config config = parseConfig(arguments.getInputDirectoryPath());

        String input = "= 見出し\n\n1行目\n2行目\n\n2段落目\n";
        ReviewParser reviewParser = Parboiled.createParser(ReviewParser.class);
        ParsingResult<Node> result = new ReportingParseRunner<Node>(reviewParser.Root()).run(input);

        return result.resultValue;
    }

    private static Config parseConfig(String configPath) {
        return null;
//        try {
//            String yamlString = Files.readString(Paths.get(configPath));
//            return new Config(yamlString);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Config("");
//        }
    }

}
