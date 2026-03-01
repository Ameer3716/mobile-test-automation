git checkout main && git pull origin main
git checkout -b feature/issue-2-appium-config
git add config/config.properties
git commit -m "feat: configure Appium server and device properties - closes #2"
git push origin feature/issue-2-appium-config
# → Create PR → Merge# ============================================================
# Mobile Test Automation - Push All Issues Script
# ============================================================
# This script creates feature branches, commits files, and
# pushes for each GitHub issue. Run this from the project root.
#
# BEFORE RUNNING:
# 1. Create all 10 GitHub Issues on your repo first
# 2. Either disable branch protection OR merge PRs manually
#    after each push (partner review not needed if protection is off)
# 3. Make sure you're on 'main' and it's up to date
#
# USAGE: 
#   cd F:\Semester8\Devops_Assign_1\mobile-test-automation
#   .\push-all-issues.ps1
#
# If branch protection is ON, after each push:
#   - Go to GitHub → Create PR → Merge (or ask partner to approve)
#   - Then uncomment and run the remaining sections one at a time
# ============================================================

$ErrorActionPreference = "Stop"

Write-Host "============================================" -ForegroundColor Cyan
Write-Host " Mobile Test Automation - Issue Push Script" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# ============================================================
# ISSUE #1: ALREADY PUSHED (feature/issue-1-maven-setup)
# ============================================================
Write-Host "`n>>> Issue #1: ALREADY PUSHED - Skipping" -ForegroundColor DarkGray
Write-Host ">>> If not yet merged: Go to GitHub -> Create PR -> Merge it" -ForegroundColor DarkGray

Read-Host "`nPress ENTER after you have merged PR #1 on GitHub (skip if already done)"
git checkout main
git pull origin main

# ============================================================
# ISSUE #2: Configure Appium and dependencies
# ============================================================
Write-Host "`n>>> Issue #2: Configure Appium and dependencies" -ForegroundColor Yellow
git checkout -b feature/issue-2-appium-config
git add config/config.properties
git commit -m "feat: configure Appium server and device properties - closes #2"
git push origin feature/issue-2-appium-config
Write-Host ">>> Issue #2 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #2 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #3: Implement BaseTest class
# ============================================================
Write-Host "`n>>> Issue #3: Implement BaseTest class" -ForegroundColor Yellow
git checkout -b feature/issue-3-base-test
git add src/main/java/com/automation/base/BaseTest.java src/main/java/com/automation/utils/AppiumUtils.java
git commit -m "feat: implement BaseTest with driver setup and AppiumUtils - closes #3"
git push origin feature/issue-3-base-test
Write-Host ">>> Issue #3 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #3 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #4: Implement LoginPage POM
# ============================================================
Write-Host "`n>>> Issue #4: Implement LoginPage POM" -ForegroundColor Yellow
git checkout -b feature/issue-4-login-page-pom
git add src/main/java/com/automation/pages/LoginPage.java
git commit -m "feat: implement LoginPage POM class - closes #4"
git push origin feature/issue-4-login-page-pom
Write-Host ">>> Issue #4 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #4 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #5: Implement HomePage POM (Partner's task - you push on their behalf)
# ============================================================
Write-Host "`n>>> Issue #5: Implement HomePage POM" -ForegroundColor Yellow
git checkout -b feature/issue-5-home-page-pom
git add src/main/java/com/automation/pages/HomePage.java
git commit -m "feat: implement HomePage POM class - closes #5"
git push origin feature/issue-5-home-page-pom
Write-Host ">>> Issue #5 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #5 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #6: Write login test cases (TC01-TC04)
# ============================================================
Write-Host "`n>>> Issue #6: Write login test cases (TC01-TC04)" -ForegroundColor Yellow
git checkout -b feature/issue-6-login-tests
git add src/test/java/com/automation/tests/LoginTests.java
git commit -m "test: add login test cases TC01-TC04 - closes #6"
git push origin feature/issue-6-login-tests
Write-Host ">>> Issue #6 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #6 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #7: Write navigation test cases (TC05-TC07) (Partner's task)
# ============================================================
Write-Host "`n>>> Issue #7: Write navigation test cases (TC05-TC07)" -ForegroundColor Yellow
git checkout -b feature/issue-7-navigation-tests
git add src/test/java/com/automation/tests/NavigationTests.java src/main/java/com/automation/pages/SearchPage.java
git commit -m "test: add navigation test cases TC05-TC07 and SearchPage POM - closes #7"
git push origin feature/issue-7-navigation-tests
Write-Host ">>> Issue #7 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #7 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #8: Write feature test cases (TC08-TC10)
# ============================================================
Write-Host "`n>>> Issue #8: Write feature test cases (TC08-TC10)" -ForegroundColor Yellow
git checkout -b feature/issue-8-feature-tests
git add src/test/java/com/automation/tests/FeatureTests.java src/main/java/com/automation/pages/ProductPage.java
git commit -m "test: add feature test cases TC08-TC10 and ProductPage POM - closes #8"
git push origin feature/issue-8-feature-tests
Write-Host ">>> Issue #8 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #8 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #9: Setup GitHub Actions CI pipeline
# ============================================================
Write-Host "`n>>> Issue #9: Setup GitHub Actions CI pipeline" -ForegroundColor Yellow
git checkout -b feature/issue-9-ci-pipeline
git add .github/workflows/ci.yml
git commit -m "ci: configure GitHub Actions CI pipeline - closes #9"
git push origin feature/issue-9-ci-pipeline
Write-Host ">>> Issue #9 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #9 on GitHub"
git checkout main
git pull origin main

# ============================================================
# ISSUE #10: Write README documentation (Partner's task)
# ============================================================
Write-Host "`n>>> Issue #10: Write README documentation" -ForegroundColor Yellow
git checkout -b feature/issue-10-readme
git add README.md
git commit -m "docs: add comprehensive README documentation - closes #10"
git push origin feature/issue-10-readme
Write-Host ">>> Issue #10 pushed. Create PR on GitHub and merge it." -ForegroundColor Green

Read-Host "`nPress ENTER after you have merged PR #10 on GitHub"
git checkout main
git pull origin main

# ============================================================
# DONE
# ============================================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host " ALL 10 ISSUES PUSHED AND MERGED!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host ""
Write-Host "Checklist:" -ForegroundColor Cyan
Write-Host "  [x] 10 feature branches created"
Write-Host "  [x] 10 PRs merged to main"
Write-Host "  [x] 10 issues auto-closed via commit messages"
Write-Host "  [x] CI pipeline should be green on Actions tab"
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "  1. Go to GitHub Actions tab - verify CI is green"
Write-Host "  2. Enable branch protection on main (if not already)"
Write-Host "  3. Verify all 10 issues are closed"
Write-Host ""
