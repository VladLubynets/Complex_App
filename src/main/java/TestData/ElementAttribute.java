package TestData;

public enum ElementAttribute {
    VALUE("value"),
    NAME("name"),
    TYPE("type");

    private final String attribute;

    ElementAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}