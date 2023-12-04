package TestData;

public class ColorPalette {

    public enum color {
        WHITE("#ffffff"),
        BLUE("#cce5ff"),
        PINK("#f8d7da"),
        GRAY("#4444"),
        LIGHT_GRAY("#888"),
        LIGHT_BLUE("#80bdff"),
        NAVY_BLUE("#007bff");

        private String color;

        color(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }

    }
}
