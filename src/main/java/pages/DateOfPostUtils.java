package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Utility class for handling dates in posts.
 */
public class DateOfPostUtils {

    private static By inner_postTitleLocator = By.tagName("strong");
    private static By inner_userInfoLocator = By.xpath(".//div[@class='text-muted small']");

    /**
     * Converts a date string to LocalDate format using "M/d/yyyy" pattern.
     */
    public static LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateString, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Checks if a list of LocalDate dates is in descending order.
     */
    public static boolean takeDatesInDescendingOrder(List<LocalDate> dates) {
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate currentDate = dates.get(i);
            LocalDate nextDate = dates.get(i + 1);
            if (nextDate.isAfter(currentDate)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Extracts the date string from a WebElement representing a post.
     */
    public static String extractDateFromPost(WebElement postItem) {
        WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
        String actualUserInfoText = userInfoElement.getText().trim();

        String[] parts = actualUserInfoText.split(" ");
        for (String part : parts) {
            if (part.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                return part;
            }
        }

        return null;
    }

    /**
     * Retrieves the title of a post from the WebElement.
     */
    private static String getPostTitle(WebElement postItem) {
        WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
        return postTitleElement.getText().trim();
    }

    /**
     * Verifies if the dates in the list of posts are in descending order.
     */
    static boolean verifyDatesInDescendingOrder(List<WebElement> postItems) {
        List<LocalDate> dates = new ArrayList<>();

        for (WebElement postItem : postItems) {
            String dateString = DateOfPostUtils.extractDateFromPost(postItem);

            if (dateString == null) {
                System.out.println("Post with title '" + getPostTitle(postItem) + "' has no valid date format.");
                return false;
            }

            LocalDate date = DateOfPostUtils.convertStringToLocalDate(dateString);

            if (date == null) {
                System.out.println("Post with title '" + getPostTitle(postItem) + "' has an invalid date format.");
                return false;
            }

            dates.add(date);
        }

        return DateOfPostUtils.takeDatesInDescendingOrder(dates);
    }

    /**
     * Verifies if the actual date exists in any post with the specified title.
     */
    public static boolean verifyActualDateExistsInPost(List<WebElement> postItems, String postTitle) {
        LocalDate currentDate = LocalDate.now();
        String expectedDate = currentDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        for (WebElement postItem : postItems) {
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            if (postTitleElement.getText().trim().equals(postTitle)) {
                WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
                String actualUserInfoText = userInfoElement.getText().trim();

                if (actualUserInfoText.contains(expectedDate)) {
                    return true;
                }
            }
        }

        return false;
    }
}