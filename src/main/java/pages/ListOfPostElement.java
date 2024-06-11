package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ListOfPostElement extends ActionWithElements {

    WebElement workingListOfPosts;
    String nameOfPostsList;

    private By inner_postTitleLocator = By.tagName("strong");
    private By inner_userInfoLocator = By.xpath(".//div[@class='text-muted small']");


    public ListOfPostElement(WebDriver webDriver, NamesOfPostsList nameOfPostsList) {
        super(webDriver);
        this.nameOfPostsList = nameOfPostsList.getName();
        try {
            workingListOfPosts = new WebDriverWait(webDriver, Duration.ofSeconds(4)).until(
                    w -> webDriver.findElement(By.xpath("//h3[text()='" + nameOfPostsList.getName() + "']/following-sibling::div"))
            );
        } catch (TimeoutException e) {
            workingListOfPosts = null;
        }
    }


    public WebElement getElementWithHeader() {
        return webDriver.findElement(By.xpath("//h2[text()='" + nameOfPostsList + "']"));
    }

    private List<WebElement> getPostItems() {
        return workingListOfPosts.findElements(By.cssSelector("div.list-group a.list-group-item"));
    }


    public void checkIstLatestPostsListGroupVisible() {
        checkElementDisplayed(workingListOfPosts);
    }

    public void checkIsFollowingPostsListGroupVisible() {
        checkElementDisplayed(workingListOfPosts);
    }

    public void checkIsFollowingPostsListGroupNonVisible() {
        if (workingListOfPosts != null) {
            checkElementNotDisplayed(workingListOfPosts);
        }
    }

    public void checkNumberOfPosts(int expectedNumber) {
        Assert.assertEquals("The number of posts is not " + expectedNumber + ".", getPostItems().size(), expectedNumber);
    }

    public void checkPostStructure() {
        List<WebElement> postItems = getPostItems();
        for (int i = 0; i < postItems.size(); i++) {
            WebElement postItem = postItems.get(i);
            String postItemText = postItem.getText().trim();

            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);

            String actualPostTitle = postTitleElement.getText().trim();
            String actualUserInfoText = userInfoElement.getText().trim();


            Assert.assertFalse("Post title is empty.", actualPostTitle.isEmpty()); // check that post title is not empty
            Assert.assertTrue("User info does not contain 'by'.", actualUserInfoText.contains("by"));


            boolean containsDate = actualUserInfoText.matches(".*\\d{1,2}/\\d{1,2}/\\d{4}.*"); // check that user info contains date in the format 'MM/dd/yyyy'
            Assert.assertTrue("User info does not contain date in the format 'MM/dd/yyyy'.", containsDate);


            String expectedPostNumberPrefix = (i + 1) + "."; // check that post number starts with the correct number
            Assert.assertTrue("Post number does not start with the correct number: " + expectedPostNumberPrefix,
                    postItemText.startsWith(expectedPostNumberPrefix));
        }
    }

    public List<WebElement> getPostListWithName(String title) { // get the list of posts with the specified title
        String postTitleLocator = String.format(".//*[text()='%s']", title);
        return workingListOfPosts.findElements(By.xpath(
                String.format(postTitleLocator, title
                )));
    }

    public ListOfPostElement checkNumberOfPostWithTitleIsPresent(String title, int expectedCount) {  // check the number of posts with the specified title
        Assert.assertEquals(" Count of posts with title " + title, expectedCount, getPostListWithName(title).size());
        return this;
    }

    public void clickOnPostWithTitle(String title) { // click on the post with the specified title
        clickOnElement(getPostListWithName(title).get(0));
    }


    public void verifyActualDateExistsInPost(String postTitle) { // check if the  actual date is present in the post with the specified title
        LocalDate currentDate = LocalDate.now(); // get the current date
        System.out.println(currentDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy"); // format the date
        System.out.println(formatter.format(currentDate));
        String expectedDate = currentDate.format(formatter);
        System.out.println(expectedDate);
        System.out.println(postTitle);

        boolean dateFound = false;

        for (WebElement postItem : getPostItems()) {
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            if (postTitleElement.getText().trim().equals(postTitle)) {
                WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
                String actualUserInfoText = userInfoElement.getText().trim();

                if (actualUserInfoText.contains(expectedDate)) {
                    dateFound = true;
                    break;
                }
            }
        }
        Assert.assertTrue("Date '" + expectedDate + "' is not found in the post with title '" + postTitle + "'.", dateFound);
    }

    // check if the post with the specified title is present in the list and choose should it be there or not
    public void verifyPostInLists(String postTitle, boolean shouldBeInList) {
        boolean isPostPresent = isPostPresent(postTitle);

        if (shouldBeInList) {
            Assert.assertTrue("Post titled '" + postTitle + "' should be present in '" + nameOfPostsList + "'.", isPostPresent);
        } else {
            Assert.assertFalse("Post titled '" + postTitle + "' should not be present in '" + nameOfPostsList + "'.", isPostPresent);
        }
    }

    public boolean isPostPresent(String postTitle) {
        for (WebElement postItem : getPostItems()) {
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            if (postTitleElement.getText().trim().equals(postTitle)) {
                return true;
            }
        }
        return false;
    }

    public int getPostPositionWithTitle(String title) {// get the position of the post with the specified title
        List<WebElement> postItems = getPostItems();
        for (int i = 0; i < postItems.size(); i++) {
            WebElement postItem = postItems.get(i);
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            String actualPostTitle = postTitleElement.getText().trim();
            if (actualPostTitle.equals(title)) {
                int position = i + 1;
                System.out.println("Post with title '" + title + "' found at position: " + position);
                return position;
            }
        }
        System.out.println("Post with title '" + title + "' not found.");
        return -1;
    }

    public void checkPostPositionWithTitle(String title, int expectedPosition) {
        int currentPosition = getPostPositionWithTitle(title); // get the current position of the post
        // check that the current position is equal to the expected position
        Assert.assertEquals("Position of post '" + title + "'", expectedPosition, currentPosition);
    }

    // Method to extract dates from all posts as strings
    private List<String> extractDatesFromAllPosts(List<WebElement> postItems) {
        List<String> dates = new ArrayList<>();

        for (WebElement postItem : postItems) {
            WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
            String actualUserInfoText = userInfoElement.getText().trim();

            // Split the user info text into parts to find the date
            String[] parts = actualUserInfoText.split(" ");
            for (String part : parts) {
                // Check if the part matches the date format "M/d/yyyy"
                if (part.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                    dates.add(part); // Add the date string to the list
                    break; // Stop looking for the date after finding it
                }
            }
        }

        return dates; // Return the list of extracted date strings
    }

    // Method to convert date strings to LocalDate objects
    private List<LocalDate> convertStringsToLocalDates(List<String> dateStrings) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        List<LocalDate> localDates = new ArrayList<>();

        for (String dateString : dateStrings) {
            try {
                LocalDate date = LocalDate.parse(dateString, formatter); // Convert the string to LocalDate
                localDates.add(date); // Add the LocalDate to the list
            } catch (Exception e) {
                e.printStackTrace(); // Print an error if the date format is incorrect
            }
        }

        return localDates; // Return the list of LocalDate objects
    }

    // Method to check if post dates are in descending order
    public void checkPostDatesInDescendingOrder() {
        List<WebElement> postItems = getPostItems();
        List<String> dateStrings = extractDatesFromAllPosts(postItems); // Extract dates as strings
        List<LocalDate> dates = convertStringsToLocalDates(dateStrings); // Convert strings to LocalDate

        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate currentDate = dates.get(i);
            LocalDate nextDate = dates.get(i + 1);
            // Check that the next date is not after the current date (descending order)
            Assert.assertTrue("Dates are not in descending order. Found " + nextDate + " after " + currentDate,
                    !nextDate.isAfter(currentDate));
        }
    }

    // Method to check if the top N posts from a specified user are in descending order by date
    public void checkTopPostsFromUserInDescendingOrder(String username, int Nposts) {
        List<WebElement> postItems = getPostItems();
        List<WebElement> topNPostItems = new ArrayList<>();
        int count = 0;

        // Collect the top N posts from the specified user
        for (WebElement postItem : postItems) {
            if (count >= Nposts) {
                break; // Stop if we have collected N posts
            }

            WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
            String actualUserInfoText = userInfoElement.getText().trim();

            // Check if the post is from the specified user
            if (actualUserInfoText.contains(username)) {
                topNPostItems.add(postItem); // Add the post to the list
                count++;
            }
        }

        // Verify we have the expected number of posts from the user
        Assert.assertEquals("Expected " + Nposts + " posts from user " + username + " but found " + count, Nposts, count);

        // Extract dates from the top N posts
        List<String> dateStrings = extractDatesFromAllPosts(topNPostItems); // Extract dates as strings
        List<LocalDate> dates = convertStringsToLocalDates(dateStrings); // Convert strings to LocalDate

        // Verify the dates are in descending order
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate currentDate = dates.get(i);
            LocalDate nextDate = dates.get(i + 1);
            // Check that the next date is not after the current date (descending order)
            Assert.assertTrue("Dates are not in descending order. Found " + nextDate + " after " + currentDate,
                    !nextDate.isAfter(currentDate));
        }
    }
}