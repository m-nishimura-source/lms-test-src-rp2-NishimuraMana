package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		webDriver.get("http://localhost:8080/lms/");

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		// 機能プルダウンの要素を指定してクリック
		WebElement functionElement = webDriver.findElement(By.xpath("//a[contains(text(), '機能')]"));
		functionElement.click();

		// ヘルプリンクが表示されるまで最大5秒間待機
		visibilityTimeout(By.linkText("ヘルプ"), 5);

		// ヘルプリンクの要素を指定してクリック
		WebElement helpElement = webDriver.findElement(By.linkText("ヘルプ"));
		helpElement.click();

		// ヘルプ画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), 'ヘルプ')]"), 5);

		// 	ヘルプ画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		// よくある質問リンクの要素を指定してクリック
		WebElement faqElement = webDriver.findElement(By.linkText("よくある質問"));
		faqElement.click();

		// 開いている全ウィンドウのハンドルを取得し、最新のウィンドウに切り替える
		for (String handle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(handle);
		}

		// よくある質問画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), 'よくある質問')]"), 5);

		// 	よくある質問画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		// カテゴリ検索リンクの要素を指定してクリック
		WebElement faqElement = webDriver.findElement(By.linkText("【研修関係】"));
		faqElement.click();

		// 検索結果が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//dl[contains(@id, 'question-h')]//dt"), 5);

		// 期待する質問文のリストを定義
		String[] question = { "キャンセル料・途中退校について",
				"研修の申し込みはどのようにすれば良いですか？" };

		// 質問文の要素を指定
		List<WebElement> questionElements = webDriver
				.findElements(By.xpath("//dl[contains(@id, 'question-h')]//dt/span[2]"));

		// 期待する質問文リストと同じ質問文が表示されているか検証
		for (int i = 0; i < questionElements.size(); i++) {
			String fullText = questionElements.get(i).getText();
			assertEquals(fullText, question[i]);
		}

		// エビデンス取得
		scrollTo("150");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		// 質問文の要素を指定してクリック
		List<WebElement> questionElements = webDriver
				.findElements(By.xpath("//dl[contains(@id, 'question-h')]//dt"));
		questionElements.get(1).click();

		// 回答文が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("(//dl[contains(@id, 'question-h')])[2]//dd"), 5);

		// 期待する回答文を定義
		String answer = "営業担当がいる場合は、営業担当までご連絡ください。 申し込み方法についてご案内させていただきます。 なお、弊社営業営業がいない場合は、東京ITスクール運営事務局までご連絡いただけると幸いです。";

		// 回答文の要素を指定して検証
		WebElement answerElement = webDriver
				.findElement(By.xpath("(//dl[contains(@id, 'question-h')])[2]//dd/span[2]"));
		assertEquals(answerElement.getText(), answer);

		// エビデンス取得
		scrollTo("200");
		getEvidence(new Object() {
		});
	}

}
