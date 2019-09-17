import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scraper {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "chromedriver");
        
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(capabilities);

        for(int j =59 ;j < 140 ; j++){

        driver.navigate().to("http://results.vtu.ac.in/resultsvitavicbcs_19/index.php");
        ((ChromeDriver) driver).
                findElementByXPath("//*[@id=\"raj\"]/div[1]/div[1]/input")
                .sendKeys("1BG15CS"+String.format("%03d", j ) );

        // Enter the Captcha and Submit
            
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//*[@id=\"dataPrint\"]/div[2]/div/div/div[2]/div[1]/div/img")
                ));

        String usn = ((ChromeDriver) driver)
                .findElementByXPath("//*[@id=\"dataPrint\"]/div[2]/div/div/div[2]/div[1]/div/div/div[1]/div/table/tbody/tr[1]/td[2]")
                .getText();

        usn = usn.substring(1).trim();
        System.out.println(usn);

        String name = ((ChromeDriver) driver)
                .findElementByXPath("//*[@id=\"dataPrint\"]/div[2]/div/div/div[2]/div[1]/div/div/div[1]/div/table/tbody/tr[2]/td[2]")
                .getText();

        name = name.substring(1).trim();
        System.out.println(name);

        List<WebElement> list = driver.findElements(By.className("divTableCell")).subList(4, 36);

        List<Result> resultList = new ArrayList<>();

        for (int i = 0; i < 32; i += 4) {
            List<WebElement> subList = list.subList(i, i + 4);
            
            Result result = new Result();
            result.setSubjectCode(subList.get(0).getText());
            result.setSubjectName(subList.get(1).getText());
            result.setInternalMarks(subList.get(2).getText());
            result.setGrade(subList.get(3).getText());

            resultList.add(result);
        }
        Collections.sort(resultList);
            
        double cgpa = 0;

        for (Result e : resultList) {
            System.out.println(e);
            cgpa += e.getGradePoint() * e.getSubjectCredits();
        }
        System.out.println("CGPA = " + String.format("%.2f", cgpa / 24.0f));

        try {
            FileWriter resultsFile = new FileWriter("results.csv", true);
            resultsFile.write(name + "," + usn + ",");
            for (Result e : resultList) {

                resultsFile.write(e.toString() + ",");
            }
            resultsFile.write(String.format("%.2f", cgpa / 24.0f) + "\n");
            resultsFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.navigate().back();

    }

    }

}
