# Founder Command Center

Native Android executive operating system for startup founders. This repository intentionally uses **Java + Android Views + Gradle**—there is no Expo, React Native, or EAS dependency.

## What works in this build

- Founder command center with north-star confidence, runway, revenue, burn, and team energy signals.
- Critical-path priorities with tap-to-complete and local persistence across app restarts.
- Strategy plan with initiatives, progress, health status, and decision queue.
- Team health and leadership cadence.
- Finance operating model and runway guardrail.
- Founder Copilot with executive prompts and local context.
- Native Android debug APK built by GitHub Actions.

## Build locally

```bash
./gradlew test
./gradlew assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions

Every push to `main`, and every manual workflow dispatch, runs unit tests, builds the debug APK, checks that the file exists, and uploads it as the `founder-command-center-debug-apk` artifact. Download it from the completed **Android APK** workflow run in the repository's Actions tab.

The first release is deliberately offline-first and dependency-light. The next production phase should add authenticated cloud sync, a real multi-founder workspace, audit logs, integrations, and a signed release build with Play App Signing.
