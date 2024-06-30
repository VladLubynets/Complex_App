package pages;

public enum NamesOfPostsList {

    THE_LATEST_POSTS("The latest posts"),
    THE_LATEST_POSTS_FROM_THOSE_YOU_FOLLOW("Latest posts from those you follow");

    private final String name;

    NamesOfPostsList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}