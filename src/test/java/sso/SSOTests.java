/*
 * Copyright 2021 Automate The Planet Ltd.
 * Author: Anton Angelov
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sso;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SSOTests {
    private WebDriver driver;
    private String googleTestEmail;
    private String googleTestPass;
    private String fbTestEmail;
    private String fbTestPass;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        googleTestEmail = System.getenv("googleTestEmail");
        googleTestPass = System.getenv("googleTestPass");
        fbTestEmail = System.getenv("googleTestEmail");
        fbTestPass = System.getenv("googleTestPass");
    }

    @BeforeEach
    public void testInit() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void testCleanup() {
        driver.quit();
    }

    @Test
    public void loginSuccessfully_usingGoogleSSO_notAlreadyLoggedInInGoogleAccount() {
        driver.navigate().to("https://localhost:3000/");

        var ssoTab = driver.findElement(By.xpath("//a[text()='SSO']"));
        ssoTab.click();

        var loginWithGoogleButton = driver.findElement(By.xpath("//a[text()='Login with Google']"));
        loginWithGoogleButton.click();

        var emailInput = driver.findElement(By.id("identifierId"));
        emailInput.sendKeys(googleTestEmail);

        var nextButton = driver.findElement(By.xpath("//span[text()='Next']"));
        nextButton.click();

        var passwordInput = driver.findElement(By.xpath("//a[text()='Enter your password']"));
        passwordInput.sendKeys(googleTestPass);

        nextButton = driver.findElement(By.xpath("//span[text()='Next']"));
        nextButton.click();

        var userName = driver.findElement(By.id("username"));

        Assertions.assertEquals("John Smith", userName.getText());

        var logoutButton = driver.findElement(By.xpath("//a[text()='Logout']"));
        logoutButton.click();
    }

    @Test
    public void loginSuccessfully_usingFacebookSSO_notAlreadyLoggedInInFacebookAccount() {
        driver.navigate().to("https://localhost:3000/");

        var ssoTab = driver.findElement(By.xpath("//a[text()='SSO']"));
        ssoTab.click();

        var loginWithFacebookButton = driver.findElement(By.xpath("//a[text()='Login with Facebook']"));
        loginWithFacebookButton.click();

        var allowAllCookies = driver.findElement(By.xpath("//button[text()='Allow all cookies']"));
        allowAllCookies.click();

        var emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys(fbTestEmail);

        var passwordInput = driver.findElement(By.id("pass"));
        passwordInput.sendKeys(fbTestPass);

        var loginButton = driver.findElement(By.id("loginbutton"));
        loginButton.click();


        var userName = driver.findElement(By.id("username"));

        Assertions.assertEquals("John Smith", userName.getText());

        var logoutButton = driver.findElement(By.xpath("//a[text()='Logout']"));
        logoutButton.click();
    }














}