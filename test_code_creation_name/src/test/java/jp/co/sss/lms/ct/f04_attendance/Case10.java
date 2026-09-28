package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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

		// 	勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {

		// 出勤時間の想定時間を定義
		String startExpectedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		// 出勤ボタンの要素を指定してクリック
		WebElement startButtonElement = webDriver.findElement(By.xpath("//input[@value='出勤']"));
		startButtonElement.click();

		// 出勤確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 出勤確認ダイアログを検証して、OKボタンをクリック
		assertEquals("打刻します。よろしいですか？", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 	勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/detail", webDriver.getCurrentUrl());

		// 当日の出勤時間の要素を指定して、想定時間と一致するか検証
		WebElement startTimeElement = webDriver.findElement(By.xpath("//tbody//tr[contains(@class, 'info')]/td[3]"));
		assertEquals(startExpectedTime, startTimeElement.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

		// 退勤時間の想定時間を定義
		String endExpectedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		// 退勤ボタンの要素を指定してクリック
		WebElement endButtonElement = webDriver.findElement(By.xpath("//input[@value='退勤']"));
		endButtonElement.click();

		// 退勤確認ダイアログの要素を指定して、表示されるまで最大5秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// 退勤確認ダイアログを検証して、OKボタンをクリック
		assertEquals("打刻します。よろしいですか？", alert.getText());
		alert.accept();

		// 勤怠管理画面が表示されるまで最大5秒間待機
		visibilityTimeout(By.xpath("//h2[contains(text(), '勤怠管理')]"), 5);

		// 	勤怠管理画面が表示されているか検証
		assertEquals("http://localhost:8080/lms/attendance/detail", webDriver.getCurrentUrl());

		// 当日の出勤時間の要素を指定して、想定時間と一致するか検証
		WebElement endTimeElement = webDriver.findElement(By.xpath("//tbody//tr[contains(@class, 'info')]/td[4]"));
		assertEquals(endExpectedTime, endTimeElement.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
