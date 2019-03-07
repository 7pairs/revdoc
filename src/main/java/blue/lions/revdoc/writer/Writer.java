package blue.lions.revdoc.writer;

import blue.lions.revdoc.CommandLineArguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.writer.word.WordWriter;

public abstract class Writer {

    public static Writer getInstance(CommandLineArguments commandLineArguments) {
        return new WordWriter(commandLineArguments);
    }

    public abstract void run(Node ast);
}
