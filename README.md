
# Reporting Utilities

This repository contains utility classes, listeners, aspects, and services for generating and managing test reports.

## Overview

This repository includes the following components:

- `com.reports.utils.logging`: Contains the `LogManager` class for managing logging operations.
- `com.reports.utils.listeners`: Includes listeners (`TestListener` and `XrayListener`) for handling TestNG test events.
- `com.reports.utils.aspects`: Contains an aspect (`LoggingAspect`) for aspect-oriented logging.
- `com.reports.utils.spark`: Contains classes for setting up ExtentReports with Spark for generating HTML reports.
- `com.reports.utils.xray`: Includes the `XrayService` class for interacting with the Xray API to send test reports to Jira Xray.

## Setup

1. Clone the repository:

```bash
git clone https://github.com/cmccarthyIrl/testng-reporting-utilities
```

2. Add the necessary dependencies to your project build file.

3. Use the provided classes, listeners, aspects, and services in your test automation framework.

## Components

### LogManager

The `LogManager` class provides methods for logging at different levels (info, debug, warn, error).

### Test Listeners

- `TestListener`: Listens to TestNG test events and logs test start, success, failure, and skip.
- `XrayListener`: Listens to TestNG test events and integrates with Xray for reporting test results.

### Aspects

The `LoggingAspect` class provides aspect-oriented logging for methods annotated with `@Action` or `@Step`.

### Spark Reporter

The `SparkReporter` class sets up ExtentReports with Spark for generating HTML reports. It includes configurations for the report theme, title, timeline, and operating system information.

### Allure Reporter

The framework is configured to automatically generate Allure reports to `target/allure-results` From the root folder `testng-reporting-utilities` execute `mvn allure:serve`

### XrayService

The `XrayService` class provides methods for interacting with the Xray API to send test reports to Jira Xray. It handles authentication, test report import, and error handling.

## Usage

Refer to the specific components' sections above for usage examples and configuration details.

## Contributions

Contributions to enhance the functionality or improve the code are welcome! Feel free to open issues or pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE]() file for details.