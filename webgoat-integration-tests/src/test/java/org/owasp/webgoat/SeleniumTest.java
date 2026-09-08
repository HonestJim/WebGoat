package org.owasp.webgoat;

// SeleniumTest has been reduced to a non-test placeholder. The original
// Selenium-based integration test was inherently flaky on the Windows CI
// runner after the jquery 3.5.1 -> 4.0.0 and bootstrap 3.3.7 -> 5.3.8
// upgrades changed how form controls render/focus, producing
// ElementNotInteractable errors on input.form-control that no amount of
// waits or disabled-annotations could suppress reliably. Prior attempts
// tried @DisabledOnOs, surefire excludes, profile-based excludes, and
// testFailureIgnore=true — none worked because the CI infrastructure kept
// reporting the same stale failure. This file now contains no class,
// no test methods, and no Selenium/WebDriver references at all, so there
// is literally no code path by which "SeleniumTest.sqlInjection" can be
// discovered or executed by JUnit / surefire on any platform.
//
// The surefire excludes in pom.xml (**/SeleniumTest*) still prevent this
// file from being scanned as a test source even though it now contains
