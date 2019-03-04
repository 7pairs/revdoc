package blue.lions.revdoc;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class RevDoc {

    public static void main(String... args) {
        Arguments arguments = new Arguments();
        CmdLineParser parser = new CmdLineParser(arguments);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }

    private static class Arguments {

        @Option(name = "-i")
        private String inputDirectory;

        @Option(name = "-o")
        private String outputFile;

        @Option(name = "-t")
        private String templateFile;

        public String getInputDirectory() {
            return inputDirectory;
        }

        public String getOutputFile() {
            return outputFile;
        }

        public String getTemplateFile() {
            return templateFile;
        }
    }
}
