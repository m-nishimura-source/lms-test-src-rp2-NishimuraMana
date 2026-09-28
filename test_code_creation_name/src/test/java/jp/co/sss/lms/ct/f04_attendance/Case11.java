package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

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
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {

		// 前日の出勤時の要素を指定して選択
		WebElement beforeStartHourElement = webDriver.findElement(By.id("startHour0"));
		Select dropdown = new Select(beforeStartHourElement);
		dropdown.selectByVisibleText("09");

		// 前日の出勤分の要素を指定して選択
		WebElement beforeStartMinuteElement = webDriver.findElement(By.id("startMinute0"));
		dropdown = new Select(beforeStartMinuteElement);
		dropdown.selectByVisibleText("00");

		// 前日の退勤時の要素を指定して選択
		WebElement beforeEndHourElement = webDriver.findElement(By.id("endHour0"));
		dropdown = new Select(beforeEndHourElement);
		dropdown.selectByVisibleText("18");

		// 前日の退勤分の要素を指定して選択
		WebElement beforeEndMinuteElement = webDriver.findElement(By.id("endMinute0"));
		dropdown = new Select(beforeEndMinuteElement);
		dropdown.selectByVisibleText("00");

		// 当日の出勤時の要素を指定して選択
		WebElement todayStartHourElement = webDriver.findElement(By.id("startHour1"));
		dropdown = new Select(todayStartHourElement);
		dropdown.selectByVisibleText("09");

		// 当日の出勤分の要素を指定して選択
		WebElement todayStartMinuteElement = webDriver.findElement(By.id("startMinute1"));
		dropdown = new Select(todayStartMinuteElement);
		dropdown.selectByVisibleText("00");

		// 当日の退勤時の要素を指定して選択
		WebElement todayEndHourElement = webDriver.findElement(By.id("endHour1"));
		dropdown = new Select(todayEndHourElement);
		dropdown.selectByVisibleText("18");

		// 当日の退勤分の要素を指定して選択
		WebElement todayEndMinuteElement = webDriver.findElement(By.id("endMinute1"));
		dropdown = new Select(todayEndMinuteElement);
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

		// 前日の出勤時間の要素を指定して、想定時間と一致するか検証
		WebElement beforeStartTimeElement = webDriver
				.findElement(By.xpath("//tbody[@class='db']/tr[td[1]='2026年9月27日(日)']"));
		assertEquals("09:00", beforeStartTimeElement.findElement(By.xpath("./td[3]")).getText());

		// 前日の退勤時間の要素を指定して、想定時間と一致するか検証
		WebElement beforeEndTimeElement = webDriver
				.findElement(By.xpath("//tbody[@class='db']/tr[td[1]='2026年9月27日(日)']"));
		assertEquals("18:00", beforeEndTimeElement.findElement(By.xpath("./td[4]")).getText());

		// 当日の出勤時間の要素を指定して、想定時間と一致するか検証
		WebElement todayStartTimeElement = webDriver
				.findElement(By.xpath("//tbody//tr[contains(@class, 'info')]/td[3]"));
		assertEquals("09:00", todayStartTimeElement.getText());

		// 当日の退勤時間の要素を指定して、想定時間と一致するか検証
		WebElement todayEndTimeElement = webDriver
				.findElement(By.xpath("//tbody//tr[contains(@class, 'info')]/td[4]"));
		assertEquals("18:00", todayEndTimeElement.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
