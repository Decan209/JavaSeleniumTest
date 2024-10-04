package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserRegistrationTest {
    public static void main(String[] args) {
        // Cấu hình WebDriver
        System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            // Tạo đối tượng WebDriverWait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Test case 1: Đăng ký với email không hợp lệ
            System.out.println("Test case 1: Đăng ký với email không hợp lệ");
            driver.get("http://127.0.0.1:5500/page/register/index.html");
            registerUser(driver, wait, "invalid-email", "ValidPass123", "0123456789", "Nguyen Van A");
            assertErrorMessage(driver, wait, "emailError", "Email không hợp lệ.");

            // Test case 2: Đăng ký với mật khẩu yếu
            System.out.println("Test case 2: Đăng ký với mật khẩu không đủ mạnh");
            driver.get("http://127.0.0.1:5500/page/register/index.html");
            registerUser(driver, wait, "validemail@example.com", "123", "0123456789", "Nguyen Van B");
            assertErrorMessage(driver, wait, "passwordError", "Mật khẩu phải có ít nhất 8 ký tự.");

            // Test case 3: Đăng ký với số điện thoại không hợp lệ
            System.out.println("Test case 3: Đăng ký với số điện thoại không hợp lệ");
            driver.get("http://127.0.0.1:5500/page/register/index.html");
            registerUser(driver, wait, "validemail@example.com", "ValidPass123", "123", "Nguyen Van C");
            assertErrorMessage(driver, wait, "phoneError", "Số điện thoại không hợp lệ.");

            // Test case 4: Đăng ký với họ tên không hợp lệ
            System.out.println("Test case 4: Đăng ký với họ tên không hợp lệ");
            driver.get("http://127.0.0.1:5500/page/register/index.html");
            registerUser(driver, wait, "validemail@example.com", "ValidPass123", "0123456789", "Nguyen@VanD");
            assertErrorMessage(driver, wait, "fullNameError", "Họ tên không được chứa ký tự đặc biệt hoặc số.");

            // Test case 5: Đăng ký thành công với dữ liệu hợp lệ
            System.out.println("Test case 5: Đăng ký thành công với dữ liệu hợp lệ");
            driver.get("http://127.0.0.1:5500/page/register/index.html");
            registerUser(driver, wait, "newuser@example.com", "StrongPassword123", "0123456789", "Nguyen Van E");

            // Kiểm tra thông báo thành công
            assertSuccessMessage(driver, wait, "Đăng ký thành công!"); // Kiểm tra thông báo thành công

        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }

    private static void registerUser(WebDriver driver, WebDriverWait wait, String email, String password, String phone, String fullName) {
        // Điền thông tin vào biểu mẫu đăng ký
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        driver.findElement(By.id("email")).sendKeys(email);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        driver.findElement(By.id("password")).sendKeys(password);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
        driver.findElement(By.id("phone")).sendKeys(phone);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fullName")));
        driver.findElement(By.id("fullName")).sendKeys(fullName);
        
        // Nhấn nút đăng ký
        driver.findElement(By.className("btn-submit")).click();
        
        // Chờ một thời gian để thông báo thành công hiển thị (nếu cần thiết)
        try {
            Thread.sleep(2000); // Điều chỉnh thời gian nếu cần thiết
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void assertErrorMessage(WebDriver driver, WebDriverWait wait, String errorId, String expectedMessage) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(errorId)));
        WebElement errorElement = driver.findElement(By.id(errorId));
        if (errorElement.isDisplayed() && errorElement.getText().equals(expectedMessage)) {
            System.out.println("Thông báo lỗi hiển thị như mong đợi: " + expectedMessage);
        } else {
            System.out.println("Thông báo lỗi mong đợi không hiển thị.");
        }
    }

    private static void assertSuccessMessage(WebDriver driver, WebDriverWait wait, String expectedMessage) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
            WebElement successElement = driver.findElement(By.id("successMessage"));
            if (successElement.isDisplayed() && successElement.getText().equals(expectedMessage)) {
                System.out.println("Thông báo thành công hiển thị như mong đợi: " + expectedMessage);
            } else {
                System.out.println("Thông báo thành công mong đợi không hiển thị.");
            }
        } catch (Exception e) {
            System.out.println("Không tìm thấy thông báo thành công: " + expectedMessage);
        }
    }
}
