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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 「詳細」ボタンの要素を指定してクリック
		By dateBy = By.xpath("//td[text()='2022年10月5日(水)']/ancestor::tr//input[@value='詳細']");
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
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//input[@value='日報【デモ】を提出する']"));
		submitElement.click();

		// レポート登録画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '日報【デモ】')]"), 5);

		// レポート登録画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {

		// 期待するレポート文を定義
		String text = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ１２３４５６７８９"
				+ "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"
				+ "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをん"
				+ "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン"
				+ "一二三四五六七八九十①②③④⑤⑥⑦⑧⑨⑩!-/:-@¥[-`{-~]*$";

		// 報告内容のテキストボックスの要素を取得し、キー入力
		WebElement reportElement = webDriver.findElement(By.id("content_0"));
		reportElement.clear();
		reportElement.sendKeys(text);

		// 提出ボタンの要素を指定してクリック
		WebElement submitElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submitElement.click();

		// セクション詳細画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//ol[@class='breadcrumb']/li[@class='active' and text()='セクション詳細']"), 5);

		// セクション詳細画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/section/detail?sectionId=3", webDriver.getCurrentUrl());

		// 確認ボタンの要素を指定して検証
		WebElement checkElement = webDriver
				.findElement(By.xpath("//form[contains(@action, '/report/regist')]//input[@type='submit']"));
		assertEquals(checkElement.getAttribute("value"), "提出済み日報【デモ】を確認する");

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
