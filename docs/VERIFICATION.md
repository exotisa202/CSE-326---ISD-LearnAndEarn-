# Verification log — November 2026

## Hosted validation: passed on 17 November 2026

[GitHub Actions run 43999731842](https://github.com/exotisa202/CSE-326---ISD-LearnAndEarn-/actions/runs/43999731842) tested commit `34e5b0d12012b3afa25aa33c4dd1951f54c6a6c9` on an Ubuntu runner with Temurin JDK 17.

- `sh ./mvnw -B test package`: **BUILD SUCCESS**.
- **3 tests passed; 0 failures, 0 errors, 0 skipped.**
- Started the real Spring application context with an in-memory H2 database and synthetic student/teacher accounts.
- Verified successful login, course-page rendering, HTML logout redirection, session invalidation, unauthenticated access redirection, and invalid-login rejection.
- Produced the executable Spring Boot JAR `target/spring-boot-project-0.0.1-SNAPSHOT.jar`.
- The prior failure was a test request missing the browser HTML Accept header: logout legitimately returned HTTP 204 for that non-HTML request. The corrected test checks browser semantics and session invalidation rather than dropping the check.

This validates the local demo startup and tested workflows, not every classroom feature, a production deployment, MySQL integration, or Docker execution.

## Earlier local preparation attempts


Environment: isolated cloud workspace with JDK 21 and a temporary Maven dependency cache. No live MySQL server, production credentials or external CSV datasets are used by the demo tests.

- Static configuration review: completed. Runtime database credentials are environment-driven; synthetic H2 profile is isolated from the legacy network seeders.
- `git diff --check`: passed.
- Maven `test`: **not completed in the preparation environment**. Application dependencies downloaded, but slow build-plugin dependency resolution prevented reaching compilation/test execution before handoff. No test result or Surefire report was produced; this is neither a passing-build claim nor a code-test failure.
- Added tests: synthetic accounts and roles, login page, student login, course browsing, logout, and invalid-login rejection. Execution remains unverified.
- Original MySQL workflows: **not tested**.
- Docker/Podman: not installed in the preparation workspace. Dockerfile supplied but not executed.
- Browser-based walkthrough: **not performed**.

The available Java process could not resolve Maven Central directly. Validation uses a temporary loopback mirror that fetches unmodified artifacts from official Maven Central through the workspace's supported HTTPS transport. No transport workaround is part of the repository's runtime requirements.
