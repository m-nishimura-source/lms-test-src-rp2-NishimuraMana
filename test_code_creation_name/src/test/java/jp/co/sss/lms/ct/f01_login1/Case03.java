package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() throws IOException {
		// TODO ここに追加

		// トップページにアクセス
		webDriver.get("http://localhost:8080/lms/");

		// ログイン画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		// 開いたページのキャプチャを取得する
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

		//evidenceフォルダに保存
		Files.copy(file.toPath(), Paths.get("./evidence/Case03_1.png"), StandardCopyOption.REPLACE_EXISTING);
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws IOException {
		// TODO ここに追加

		// ログインID、パスワードのid属性の要素を指定
		WebElement idElement = webDriver.findElement(By.id("loginId"));
		WebElement passElement = webDriver.findElement(By.id("password"));

		// ユーザー情報をキー入力
		idElement.clear();
		idElement.sendKeys("StudentAA01");
		passElement.clear();
		passElement.sendKeys("StudentAA01");

		// ログインボタンの要素を指定してクリック
		WebElement buttonElement = webDriver.findElement(By.xpath("//input[@value='ログイン']"));
		buttonElement.click();

		// 「ようこそ受講生○○さん」の要素を指定して検証
		WebElement welcomeElement = webDriver.findElement(By.xpath("//small[contains(text(), 'ようこそ')]\""));
		assertTrue(welcomeElement.getText().contains("ようこそ"));
		
		// 「同意します」のチェックボックスの要素を指定
		WebElement consentElement = webDriver.findElement(By.name("securityFlg"));

		// 開いたページのキャプチャを取得する
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		//evidenceフォルダに保存
		Files.copy(file.toPath(), Paths.get("./evidence/Case03_2.png"), StandardCopyOption.REPLACE_EXISTING);

	}

}
