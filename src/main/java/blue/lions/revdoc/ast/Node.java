package blue.lions.revdoc.ast;

public abstract class Node {

    public abstract void accept(Visitor visitor);
}
