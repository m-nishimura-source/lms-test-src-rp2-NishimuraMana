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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
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
		scrollTo("150");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 確認ボタンの要素を指定してクリック
		WebElement checkElement = webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']"));
		checkElement.click();

		// レポート登録詳細画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// レポート登録画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		// エビデンス取得
		scrollTo("300");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		// 期待するレポート文を定義
		String text = "報告内容を修正しました。";

		// 所感のテキストボックスの要素を取得し、キー入力
		WebElement reportElement = webDriver.findElement(By.id("content_1"));
		reportElement.clear();
		reportElement.sendKeys(text);

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submitElement.click();

		// セクション詳細画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//ol[@class='breadcrumb']/li[@class='active' and text()='セクション詳細']"), 5);

		// セクション詳細画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/section/detail?sectionId=2", webDriver.getCurrentUrl());

		// エビデンス取得
		scrollTo("150");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		// 「詳細」ボタンの要素を指定してクリック
		By dateBy = By.xpath(
				"//tr[td[1][contains(text(), '2022年10月2日(日)')] and td[2][contains(text(), '週報【デモ】')]]//form[contains(@action, '/report/detail')]//input[@value='詳細']");
		WebElement dateElement = webDriver.findElement(dateBy);
		dateElement.click();

		// レポート詳細画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '週報【デモ】')]"), 5);

		// レポート詳細画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/report/detail", webDriver.getCurrentUrl());

		// 所感のテキスト要素を指定して検証
		WebElement commentElement = webDriver.findElement(By.xpath("//th[text()='所感']/following-sibling::td"));
		assertEquals(commentElement.getText(), "報告内容を修正しました。");

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
