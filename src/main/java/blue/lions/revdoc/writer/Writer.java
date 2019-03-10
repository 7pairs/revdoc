package blue.lions.revdoc.writer;

import blue.lions.revdoc.Arguments;
import blue.lions.revdoc.ast.Node;
import blue.lions.revdoc.writer.word.WordWriter;

public abstract class Writer {

    public static Writer getInstance(Arguments arguments) {
        return new WordWriter(arguments);
    }

    public abstract void run(Node ast);
}
