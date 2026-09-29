package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	JavascriptExecutor js = (JavascriptExecutor) webDriver;

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 「詳細」ボタンの要素を指定してクリック
		By dateBy = By.xpath("//td[text()='2022年10月2日(日)']/ancestor::tr//input[@value='詳細']");
		WebElement dateElement = webDriver.findElement(dateBy);
		dateElement.click();

		// セクション詳細画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//ol[@class='breadcrumb']/li[@class='active' and text()='セクション詳細']"), 5);

		// セクション詳細画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {

		// 詳細ボタンの要素を指定してクリック
		WebElement buttonElement = webDriver.findElement(By.xpath("//input[@value='詳細']"));
		buttonElement.click();

		// 試験開始画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/exam/start", webDriver.getCurrentUrl());

		List<WebElement> buttons = webDriver.findElements(
				By.xpath("//input[@value='試験を開始する']"));

		System.out.println("試験開始ボタンの件数：" + buttons.size());

		// 試験開始画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//input[@value='試験を開始する']"), 5);

		// 試験開始画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/exam/start", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {

		// 「試験を開始する」ボタンの要素を指定してクリック
		WebElement startButtonElement = webDriver.findElement(By.xpath("//input[@value='試験を開始する']"));
		startButtonElement.click();

		// 試験問題画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//input[@value='確認画面へ進む']"), 5);

		// 試験問題画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/exam/question", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {

		// 「確認画面へ進む」ボタンの要素を指定してクリック
		WebElement confirmButtonElement = webDriver.findElement(By.xpath("//input[@value='確認画面へ進む']"));
		js.executeScript("arguments[0].click();", confirmButtonElement);

		// 試験回答確認画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.id("sendButton"), 5);

		// 試験回答確認画面確認が表示されているか検証
		assertEquals("http://localhost:8080/lms/exam/answerCheck", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {

		// 3秒待機
		Thread.sleep(3000);

		// 「回答を送信する」ボタンの要素を指定してクリック
		WebElement sendButtonElement = webDriver.findElement(By.id("sendButton"));
		js.executeScript("arguments[0].click();", sendButtonElement);

		// 回答送信ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 回答送信ダイアログを検証して、OKボタンをクリック
		assertEquals("回答を送信します。よろしいですか？", alert.getText());
		alert.accept();

		// 試験実施日時を設定
		Case13.date = new Date();

		// 試験結果画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2/small[contains(text(), 'あなたのスコア')]"), 5);

		// 試験結果画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/exam/result", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {

		// 「戻る」ボタンの要素を指定してクリック
		WebElement returnButtonElement = webDriver.findElement(By.xpath("//input[@value='戻る']"));
		js.executeScript("arguments[0].click();", returnButtonElement);

		// 試験開始画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//input[@value='試験を開始する']"), 5);

		// 試験開始画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/exam/start", webDriver.getCurrentUrl());

		// 試験実施日時をフォーマット変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
		String examDate = sdf.format(Case13.date);

		// 試験結果の要素を指定して検証
		WebElement examElement = webDriver
				.findElement(By.xpath("//table[@class='table']//tr[td[4]='" + examDate + "']"));
		String point = examElement.findElement(By.xpath("./td[2]")).getText();
		assertEquals("0.0点", point);

		// エビデンス取得
		scrollBy("300");
		getEvidence(new Object() {
		});
	}

}
