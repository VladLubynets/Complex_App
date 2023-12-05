package TestData;

public enum ColorPalette {
    WHITE("#ffffff"),
    BLUE("#cce5ff"),
    PINK("#f8d7da"),
    GRAY("#4444"),
    LIGHT_GRAY("#888"),
    LIGHT_BLUE("#80bdff"),
    NAVY_BLUE("#007bff");

    private final String colorCode;

    ColorPalette(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}