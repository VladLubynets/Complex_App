package pages.newHomePage;

public enum NamesOfPostsList {

    THE_LATEST_POSTS("The latest posts"),
    THE_LATEST_POSTS_FROM_THOSE_YOU_FOLLOW("The Latest From Those You Follow");

    private final String name;

    NamesOfPostsList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
