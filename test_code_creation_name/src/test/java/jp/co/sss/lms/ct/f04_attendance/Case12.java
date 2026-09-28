package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

	JavascriptExecutor js = (JavascriptExecutor) webDriver;
	Select dropdown;

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

		// 勤怠リンクの要素を指定してクリック
		WebElement attendanceElement = webDriver.findElement(By.xpath("//a[contains(text(), '勤怠')]"));
		attendanceElement.click();

		// 過去日未入力確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 過去日未入力確認ダイアログを検証して、OKボタンをクリック
		assertEquals("過去日の勤怠に未入力があります。", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {

		// 「勤怠情報を直接編集する」リンクの要素を指定してクリック
		WebElement updateAttendanceElement = webDriver.findElement(By.xpath("//a[contains(text(), '勤怠情報を直接編集する')]"));
		updateAttendanceElement.click();

		// 勤怠情報直接変更画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//button[contains(text(), '定時')]"), 5);

		// 勤怠情報直接変更画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {

		// 出勤分の要素を指定して選択
		WebElement startMinuteElement = webDriver.findElement(By.id("startMinute1"));
		dropdown = new Select(startMinuteElement);
		dropdown.selectByVisibleText("");

		// 退勤時の要素を指定して選択
		WebElement endHourElement = webDriver.findElement(By.id("endHour1"));
		dropdown = new Select(endHourElement);
		dropdown.selectByVisibleText("");

		// 更新ボタンの要素を指定してクリック
		WebElement updateButtonElement = webDriver.findElement(By.xpath("//input[@value='更新']"));
		js.executeScript("arguments[0].click();", updateButtonElement);

		// 更新確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 更新確認ダイアログを検証して、OKボタンをクリック
		assertEquals("更新します。よろしいですか？", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		// エラーメッセージのリストを定義
		String[] errorMsgs = { "* 出勤時間が正しく入力されていません。", "* 退勤時間が正しく入力されていません。" };

		// 期待するエラーメッセージの要素を指定
		List<WebElement> errorElements = webDriver.findElements(By.cssSelector("span.help-inline.error"));

		// エラーメッセージリストと同じ文が表示されているか検証
		for (int i = 0; i < errorElements.size(); i++) {
			String fullText = errorElements.get(i).getText();
			assertEquals(fullText, errorMsgs[i]);
		}

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {

		// 出勤時の要素を指定して選択
		WebElement startHourElement = webDriver.findElement(By.id("startHour1"));
		dropdown = new Select(startHourElement);
		dropdown.selectByVisibleText("");

		// 退勤時の要素を指定して選択
		WebElement endHourElement = webDriver.findElement(By.id("endHour1"));
		dropdown = new Select(endHourElement);
		dropdown.selectByVisibleText("18");

		// 更新ボタンの要素を指定してクリック
		WebElement updateButtonElement = webDriver.findElement(By.xpath("//input[@value='更新']"));
		js.executeScript("arguments[0].click();", updateButtonElement);

		// 更新確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 更新確認ダイアログを検証して、OKボタンをクリック
		assertEquals("更新します。よろしいですか？", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		// 期待するエラーメッセージを定義
		String errorMsg = "* 出勤情報がないため退勤情報を入力出来ません。";

		// エラーメッセージの要素を指定
		WebElement errorElement = webDriver.findElement(By.cssSelector("span.help-inline.error"));

		// 期待するエラーメッセージと同じ文が表示されているか検証
		assertEquals(errorElement.getText(), errorMsg);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {

		// 出勤時の要素を指定して選択
		WebElement startHourElement = webDriver.findElement(By.id("startHour1"));
		dropdown = new Select(startHourElement);
		dropdown.selectByVisibleText("18");

		// 出勤分の要素を指定して選択
		WebElement startMinuteElement = webDriver.findElement(By.id("startMinute1"));
		dropdown = new Select(startMinuteElement);
		dropdown.selectByVisibleText("00");

		// 退勤時の要素を指定して選択
		WebElement endHourElement = webDriver.findElement(By.id("endHour1"));
		dropdown = new Select(endHourElement);
		dropdown.selectByVisibleText("09");

		// 退勤時の要素を指定して選択
		WebElement endMinuteElement = webDriver.findElement(By.id("endMinute1"));
		dropdown = new Select(endMinuteElement);
		dropdown.selectByVisibleText("00");

		// 更新ボタンの要素を指定してクリック
		WebElement updateButtonElement = webDriver.findElement(By.xpath("//input[@value='更新']"));
		js.executeScript("arguments[0].click();", updateButtonElement);

		// 更新確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 更新確認ダイアログを検証して、OKボタンをクリック
		assertEquals("更新します。よろしいですか？", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		// 期待するエラーメッセージを定義
		String errorMsg = "* 退勤時刻[1]は出勤時刻[1]より後でなければいけません。";

		// エラーメッセージの要素を指定
		WebElement errorElement = webDriver.findElement(By.cssSelector("span.help-inline.error"));

		// 期待するエラーメッセージと同じ文が表示されているか検証
		assertEquals(errorElement.getText(), errorMsg);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {

		// 出勤時の要素を指定して選択
		WebElement startHourElement = webDriver.findElement(By.id("startHour1"));
		dropdown = new Select(startHourElement);
		dropdown.selectByVisibleText("09");

		// 出勤分の要素を指定して選択
		WebElement startMinuteElement = webDriver.findElement(By.id("startMinute1"));
		dropdown = new Select(startMinuteElement);
		dropdown.selectByVisibleText("00");

		// 退勤時の要素を指定して選択
		WebElement endHourElement = webDriver.findElement(By.id("endHour1"));
		dropdown = new Select(endHourElement);
		dropdown.selectByVisibleText("10");

		// 退勤時の要素を指定して選択
		WebElement endMinuteElement = webDriver.findElement(By.id("endMinute1"));
		dropdown = new Select(endMinuteElement);
		dropdown.selectByVisibleText("00");

		// 中抜け時間の要素を指定して選択
		WebElement blankTimeElement = webDriver.findElement(By.name("attendanceList[1].blankTime"));
		dropdown = new Select(blankTimeElement);
		dropdown.selectByVisibleText("2時間");

		// 更新ボタンの要素を指定してクリック
		WebElement updateButtonElement = webDriver.findElement(By.xpath("//input[@value='更新']"));
		js.executeScript("arguments[0].click();", updateButtonElement);

		// 更新確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 更新確認ダイアログを検証して、OKボタンをクリック
		assertEquals("更新します。よろしいですか？", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		// 期待するエラーメッセージを定義
		String errorMsg = "* 中抜け時間が勤務時間を超えています。";

		// エラーメッセージの要素を指定
		WebElement errorElement = webDriver.findElement(By.cssSelector("span.help-inline.error"));

		// 期待するエラーメッセージと同じ文が表示されているか検証
		assertEquals(errorElement.getText(), errorMsg);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {

		// 100文字超の文字列を定義
		String overLengthText = "あ".repeat(101);

		// 備考の要素を指定し、キー入力
		WebElement noteElement = webDriver.findElement(By.name("attendanceList[1].note"));
		noteElement.clear();
		noteElement.sendKeys(overLengthText);

		// 更新ボタンの要素を指定してクリック
		WebElement updateButtonElement = webDriver.findElement(By.xpath("//input[@value='更新']"));
		js.executeScript("arguments[0].click();", updateButtonElement);

		// 更新確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 更新確認ダイアログを検証して、OKボタンをクリック
		assertEquals("更新します。よろしいですか？", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		// 期待するエラーメッセージを定義
		String errorMsg = "* 備考の長さが最大値(100)を超えています。";

		// エラーメッセージの要素を指定
		WebElement errorElement = webDriver.findElement(By.cssSelector("span.help-inline.error"));

		// 期待するエラーメッセージと同じ文が表示されているか検証
		assertEquals(errorElement.getText(), errorMsg);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
