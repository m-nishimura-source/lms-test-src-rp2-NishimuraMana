package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

	JavascriptExecutor js = (JavascriptExecutor) webDriver;

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
	void test01() {

		// トップページにアクセス
		goTo("http://localhost:8080/lms/");

		// ログイン画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {

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

		// 「ようこそ受講生○○さん」が表示されるまで最大5秒間待機
		By welcomeBy = By.xpath("//*[contains(text(), 'ようこそ')]");
		visibilityTimeout(welcomeBy, 5);

		// 「ようこそ受講生○○さん」の要素を指定して検証
		WebElement welcomeElement = webDriver.findElement(welcomeBy);
		assertTrue(welcomeElement.getText().contains("ようこそ"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {

		// 「同意します」のチェックボックスの要素を指定して選択
		WebElement consentElement = webDriver.findElement(By.xpath("//input[@type='checkbox']"));
		assertFalse(consentElement.isSelected());
		consentElement.click();

		// 「次へ」ボタンの要素を指定してクリック
		WebElement nextElement = webDriver.findElement(By.cssSelector("button[type='submit']"));
		nextElement.click();

		// パスワード変更画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), 'パスワード変更')]"), 5);

		// パスワード変更画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/password/changePassword", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {

		// 各パスワードの要素を指定して空文字検証
		WebElement currentPasswordElement = webDriver.findElement(By.id("currentPassword"));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys("");
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("");
		WebElement passwordConfirmElement = webDriver.findElement(By.id("passwordConfirm"));
		passwordConfirmElement.clear();
		passwordConfirmElement.sendKeys("");

		// 「変更」ボタンの要素を指定して検証
		WebElement changeButtonElement = webDriver.findElement(By.xpath("//button[text()='変更']"));
		changeButtonElement.click();

		// 「パスワード変更の確認」モーダルダイアログが表示されるまで最大5秒間待機
		By modalBy = By.id("div-modal");
		visibilityTimeout(modalBy, 5);

		// 「パスワード変更の確認」モーダルダイアログの要素を指定して検証
		WebElement modalElement = webDriver.findElement(modalBy);
		assertTrue(modalElement.isDisplayed());

		// 「パスワード変更の確認」モーダルダイアログの「変更」ボタンの要素を指定して検証
		js.executeScript("document.getElementById('upd-form').submit();");

		// エラーメッセージが表示されるまで最大5秒間待機
		visibilityTimeout(By.cssSelector("span.error"), 5);

		// パスワード変更画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/password/changePassword/change", webDriver.getCurrentUrl());

		// 各パスワードのエラーメッセージの要素を指定して表示と内容を検証
		WebElement currentErrorElement = webDriver.findElement(
				By.xpath("//input[@id='currentPassword']/following-sibling::ul//span[contains(@class, 'error')]"));
		WebElement passwordErrorElement = webDriver.findElement(
				By.xpath("//input[@id='password']/following-sibling::ul//span[contains(@class, 'error')]"));
		WebElement confirmErrorElement = webDriver.findElement(
				By.xpath("//input[@id='passwordConfirm']/following-sibling::ul//span[contains(@class, 'error')]"));
		assertTrue(currentErrorElement.isDisplayed());
		assertTrue(passwordErrorElement.isDisplayed());
		assertTrue(confirmErrorElement.isDisplayed());

		assertEquals("現在のパスワードは必須です。", currentErrorElement.getText());
		String passwordErrorText = passwordErrorElement.getText();
		assertTrue(passwordErrorText.contains("「パスワード」には半角英数字のみ使用可能です"));
		assertTrue(passwordErrorText.contains("パスワードは必須です。"));
		assertEquals("確認パスワードは必須です。", confirmErrorElement.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {

		// 各パスワードの要素を指定して空文字検証
		WebElement currentPasswordElement = webDriver.findElement(By.id("currentPassword"));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys("StudentAA01");
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("aaaaaaaaaaaaaaaaaaaA1");
		WebElement passwordConfirmElement = webDriver.findElement(By.id("passwordConfirm"));
		passwordConfirmElement.clear();
		passwordConfirmElement.sendKeys("aaaaaaaaaaaaaaaaaaaA1");

		// 「変更」ボタンの要素を指定して検証
		WebElement changeButtonElement = webDriver.findElement(By.xpath("//button[text()='変更']"));
		changeButtonElement.click();

		// 「パスワード変更の確認」モーダルダイアログが表示されるまで最大5秒間待機
		By modalBy = By.id("div-modal");
		visibilityTimeout(modalBy, 5);

		// 「パスワード変更の確認」モーダルダイアログの要素を指定して検証
		WebElement modalElement = webDriver.findElement(modalBy);
		assertTrue(modalElement.isDisplayed());

		// 「パスワード変更の確認」モーダルダイアログの「変更」ボタンの要素を指定して検証
		js.executeScript("document.getElementById('upd-form').submit();");

		// エラーメッセージが表示されるまで最大5秒間待機
		visibilityTimeout(By.cssSelector("span.error"), 5);

		// パスワード変更画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/password/changePassword/change", webDriver.getCurrentUrl());

		// 各パスワードのエラーメッセージの要素を指定して表示と内容を検証
		WebElement passwordErrorElement = webDriver.findElement(
				By.xpath("//input[@id='password']/following-sibling::ul//span[contains(@class, 'error')]"));
		assertTrue(passwordErrorElement.isDisplayed());

		assertEquals("パスワードの長さが最大値(20)を超えています。", passwordErrorElement.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {

		// 各パスワードの要素を指定して空文字検証
		WebElement currentPasswordElement = webDriver.findElement(By.id("currentPassword"));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys("StudentAA01");
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("aaaaaaaa");
		WebElement passwordConfirmElement = webDriver.findElement(By.id("passwordConfirm"));
		passwordConfirmElement.clear();
		passwordConfirmElement.sendKeys("aaaaaaaa");

		// 「変更」ボタンの要素を指定して検証
		WebElement changeButtonElement = webDriver.findElement(By.xpath("//button[text()='変更']"));
		changeButtonElement.click();

		// 「パスワード変更の確認」モーダルダイアログが表示されるまで最大5秒間待機
		By modalBy = By.id("div-modal");
		visibilityTimeout(modalBy, 5);

		// 「パスワード変更の確認」モーダルダイアログの要素を指定して検証
		WebElement modalElement = webDriver.findElement(modalBy);
		assertTrue(modalElement.isDisplayed());

		// 「パスワード変更の確認」モーダルダイアログの「変更」ボタンの要素を指定して検証
		js.executeScript("document.getElementById('upd-form').submit();");

		// エラーメッセージが表示されるまで最大5秒間待機
		visibilityTimeout(By.cssSelector("span.error"), 5);

		// パスワード変更画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/password/changePassword/change", webDriver.getCurrentUrl());

		// 各パスワードのエラーメッセージの要素を指定して表示と内容を検証
		WebElement passwordErrorElement = webDriver.findElement(
				By.xpath("//input[@id='password']/following-sibling::ul//span[contains(@class, 'error')]"));
		assertTrue(passwordErrorElement.isDisplayed());

		String passwordErrorText = passwordErrorElement.getText();
		assertTrue(passwordErrorText.contains("「パスワード」には半角英数字のみ使用可能です"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {

		// 各パスワードの要素を指定して空文字検証
		WebElement currentPasswordElement = webDriver.findElement(By.id("currentPassword"));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys("StudentAA01");
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("ItTest2026");
		WebElement passwordConfirmElement = webDriver.findElement(By.id("passwordConfirm"));
		passwordConfirmElement.clear();
		passwordConfirmElement.sendKeys("ItTest2026error");

		// 「変更」ボタンの要素を指定して検証
		WebElement changeButtonElement = webDriver.findElement(By.xpath("//button[text()='変更']"));
		changeButtonElement.click();

		// 「パスワード変更の確認」モーダルダイアログが表示されるまで最大5秒間待機
		By modalBy = By.id("div-modal");
		visibilityTimeout(modalBy, 5);

		// 「パスワード変更の確認」モーダルダイアログの要素を指定して検証
		WebElement modalElement = webDriver.findElement(modalBy);
		assertTrue(modalElement.isDisplayed());

		// 「パスワード変更の確認」モーダルダイアログの「変更」ボタンの要素を指定して検証
		js.executeScript("document.getElementById('upd-form').submit();");

		// エラーメッセージが表示されるまで最大5秒間待機
		visibilityTimeout(By.cssSelector("span.error"), 5);

		// パスワード変更画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/password/changePassword/change", webDriver.getCurrentUrl());

		// 各パスワードのエラーメッセージの要素を指定して表示と内容を検証
		WebElement passwordErrorElement = webDriver.findElement(
				By.xpath("//input[@id='password']/following-sibling::ul//span[contains(@class, 'error')]"));
		assertTrue(passwordErrorElement.isDisplayed());

		assertEquals("パスワードと確認パスワードが一致しません。", passwordErrorElement.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
