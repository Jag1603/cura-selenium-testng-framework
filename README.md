# CURA Healthcare Selenium TestNG Framework

End-to-end Selenium WebDriver framework for:
https://katalon-demo-cura.herokuapp.com/

## Stack
- Java 17
- Maven
- Selenium WebDriver 4
- WebDriverManager
- TestNG
- Page Object Model
- TestNG DataProvider
- CSV test data
- TestNG Listener
- Retry Analyzer
- Allure Reports
- SLF4J + Logback
- GitHub Actions

## Run

```bash
mvn clean test
```

Headless:

```bash
mvn clean test -Dheadless=true
```

Firefox:

```bash
mvn clean test -Dbrowser=firefox
```

Generate/open Allure locally:

```bash
allure generate allure-results --clean -o allure-report
allure open allure-report
```

## Structure

```text
src/test/java/com/cura
  base
  driver
  listeners
  pages
  retry
  tests
  utils

src/test/resources
  config
  data
  logback.xml

.github/workflows
  cura-tests.yml
```

## Client report

GitHub Actions publishes:
- Allure report
- allure-results
- logs
- screenshots
- surefire reports

The workflow also creates a ZIP artifact for client sharing.

For SMTP email delivery, add repository secrets:
`SMTP_HOST`, `SMTP_PORT`, `SMTP_USERNAME`, `SMTP_PASSWORD`, `CLIENT_EMAIL`.

The framework is designed so credentials are never stored in source control.
