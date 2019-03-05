package blue.lions.revdoc;

import org.kohsuke.args4j.Option;

public class Arguments {

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
