package blue.lions.revdoc.reader;

import blue.lions.revdoc.CommandLineArguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.reader.review.ReviewReader;

public abstract class Reader {

    public static Reader getInstance(CommandLineArguments commandLineArguments) {
        return new ReviewReader(commandLineArguments);
    }

    public abstract Node run();
}
