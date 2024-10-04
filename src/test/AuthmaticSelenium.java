package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthmaticSelenium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// Thiết lập thời gian chờ đợi
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Mở trang đăng nhập React
		driver.get("http://127.0.0.1:5500/page/login/index.html"); // URL ứng dụng React của bạn

		// Tìm trường email và nhập thông tin
		WebElement emailField = driver.findElement(By.xpath("//input[@placeholder='Email']"));
		emailField.sendKeys("admin@gmail.com");

		// Tìm trường mật khẩu và nhập mật khẩu
		WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Mật khẩu']"));
		passwordField.sendKeys("Admin@123");

		// Tìm nút đăng nhập và nhấn
		WebElement loginButton = driver.findElement(By.cssSelector(".main-btn-primary"));
		loginButton.click();

		// Chờ vài giây để trang xử lý
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String expectedUrl = "http://127.0.0.1:5500";
		String actualUrl = driver.getCurrentUrl();

		if (expectedUrl.equals(actualUrl)) {
			System.out.println("Đăng nhập thành công và đã chuyển hướng đến trang chính.");
		} else {
			System.out.println("Đăng nhập thất bại hoặc không chuyển hướng đến trang chính.");
		}

		// Đóng trình duyệt
		driver.quit();
	}

}
