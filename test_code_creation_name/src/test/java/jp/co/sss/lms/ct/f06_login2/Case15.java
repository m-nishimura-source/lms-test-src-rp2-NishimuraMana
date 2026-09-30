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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

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
		By welcomeBy = By.xpath("//small[contains(text(), 'ようこそ')]");
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
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {

		// 「同意します」のチェックボックスの要素を指定して、チェックが入っていないことを検証
		WebElement consentElement = webDriver.findElement(By.cssSelector("input[type='checkbox'][name='securityFlg']"));
		assertFalse(consentElement.isSelected());

		// 「次へ」ボタンの要素を指定してクリック
		WebElement nextElement = webDriver.findElement(By.cssSelector("button[type='submit']"));
		nextElement.click();

		// エラーメッセージが表示されるまで最大5秒間待機
		By errorBy = By.className("error");
		visibilityTimeout(errorBy, 5);

		// エラーメッセージの要素を指定して表示と内容を検証
		WebElement errorElement = webDriver.findElement(errorBy);
		assertTrue(errorElement.isDisplayed());
		assertEquals("セキュリティ規約への同意は必須です。", errorElement.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});

	}

}
