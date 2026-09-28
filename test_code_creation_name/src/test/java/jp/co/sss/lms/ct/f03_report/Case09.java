package jp.co.sss.lms.ct.f03_report;

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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		// ログインID、パスワードのid属性の要素を指定
		WebElement idElement = webDriver.findElement(By.id("loginId"));
		WebElement passElement = webDriver.findElement(By.id("password"));

		// 初回ログイン済みのユーザー情報をキー入力
		idElement.clear();
		idElement.sendKeys("StudentAA01");
		passElement.clear();
		passElement.sendKeys("ItTest2026");

		// ログインボタンの要素を指定してクリック
		WebElement buttonElement = webDriver.findElement(By.xpath("//input[@value='ログイン']"));
		buttonElement.click();

		// コース詳細画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.cssSelector("ol.breadcrumb li.active"), 5);

		// コース詳細画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		// 「ようこそ○○さん」の要素を指定してクリック
		WebElement welcomeElement = webDriver.findElement(By.xpath("//small[contains(text(), 'ようこそ')]"));
		welcomeElement.click();

		// ユーザー詳細画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), 'ユーザー詳細')]"), 5);

		// ユーザー詳細画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/user/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		scrollTo("300");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 「修正する」ボタンの要素を指定してクリック
		By dateBy = By.xpath(
				"//tr[td[1][contains(text(), '2022年10月2日(日)')] and td[2][contains(text(), '週報【デモ】')]]//form[contains(@action, '/report/regist')]//input[@value='修正する']");
		WebElement dateElement = webDriver.findElement(dateBy);
		dateElement.click();

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// レポート登録画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		// エビデンス取得
		scrollTo("500");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		// 学習項目の要素を指定し、キー入力
		WebElement itemElement = webDriver.findElement(By.id("intFieldName_0"));
		itemElement.clear();
		itemElement.sendKeys("");

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		js.executeScript("arguments[0].click();", submitElement);

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// エラー表示の要素を指定して検証
		WebElement errorElement = webDriver.findElement(By.id("intFieldName_0"));
		assertTrue(errorElement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		// 学習項目の要素を指定し、キー入力
		WebElement itemElement = webDriver.findElement(By.id("intFieldName_0"));
		itemElement.clear();
		itemElement.sendKeys("ITリテラシー①");

		// 理解度の要素を指定して選択
		WebElement levelElement = webDriver.findElement(By.id("intFieldValue_0"));
		Select select = new Select(levelElement);
		select.selectByValue("");

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		js.executeScript("arguments[0].click();", submitElement);

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// エラー表示の要素を指定して検証
		WebElement errorElement = webDriver.findElement(By.id("intFieldValue_0"));
		assertTrue(errorElement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		// 理解度の要素を指定して選択
		WebElement levelElement = webDriver.findElement(By.id("intFieldValue_0"));
		Select select = new Select(levelElement);
		select.selectByValue("2");

		// 目標の達成度の要素を指定し、キー入力
		WebElement achievementElement = webDriver.findElement(By.id("content_0"));
		achievementElement.clear();
		achievementElement.sendKeys("あ");

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		js.executeScript("arguments[0].click();", submitElement);

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// エラー表示の要素を指定して検証
		WebElement errorElement = webDriver.findElement(By.id("content_0"));
		assertTrue(errorElement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		// 目標の達成度の要素を指定し、キー入力
		WebElement achievementElement = webDriver.findElement(By.id("content_0"));
		achievementElement.clear();
		achievementElement.sendKeys("11");

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		js.executeScript("arguments[0].click();", submitElement);

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// エラー表示の要素を指定して検証
		WebElement errorElement = webDriver.findElement(By.id("content_0"));
		assertTrue(errorElement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		// 目標の達成度の要素を指定し、キー入力
		WebElement achievementElement = webDriver.findElement(By.id("content_0"));
		achievementElement.clear();
		achievementElement.sendKeys("");

		// 所感の要素を指定し、キー入力
		WebElement impressionElement = webDriver.findElement(By.id("content_1"));
		impressionElement.clear();
		impressionElement.sendKeys("");

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		js.executeScript("arguments[0].click();", submitElement);

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// 目標の達成度のエラー表示の要素を指定して検証
		WebElement achievementErrorElement = webDriver.findElement(By.id("content_0"));
		assertTrue(achievementErrorElement.getAttribute("class").contains("errorInput"));

		// 所感のエラー表示の要素を指定して検証
		WebElement impressionErrorElement = webDriver.findElement(By.id("content_1"));
		assertTrue(impressionErrorElement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		scrollTo("300");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		// 目標の達成度の要素を指定し、キー入力
		WebElement achievementElement = webDriver.findElement(By.id("content_0"));
		achievementElement.clear();
		achievementElement.sendKeys("5");

		// 2000文字超の文字列を定義
		String overLengthText = "あ".repeat(2001);

		// 所感の要素を指定し、キー入力
		WebElement impressionElement = webDriver.findElement(By.id("content_1"));
		impressionElement.clear();
		impressionElement.sendKeys(overLengthText);

		// 一週間の振り返りの要素を指定し、キー入力
		WebElement reviewElement = webDriver.findElement(By.id("content_2"));
		reviewElement.clear();
		reviewElement.sendKeys(overLengthText);

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		js.executeScript("arguments[0].click();", submitElement);

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// 所感のエラー表示の要素を指定して検証
		WebElement impressionErrorElement = webDriver.findElement(By.id("content_1"));
		assertTrue(impressionErrorElement.getAttribute("class").contains("errorInput"));

		// 一週間の振り返りのエラー表示の要素を指定して検証
		WebElement reviewErrorElement = webDriver.findElement(By.id("content_2"));
		assertTrue(reviewErrorElement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		scrollTo("500");
		getEvidence(new Object() {
		});
	}

}
