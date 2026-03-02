# ============================================================
# Run Tests & Open Report in Browser (No Docker Required)
# ============================================================
#
# PREREQUISITES (local setup):
#   1. Appium server running:    appium
#   2. Android emulator running: emulator -avd <your_avd_name>
#   3. Allure CLI installed:     npm install -g allure-commandline
#      OR via Scoop:             scoop install allure
#
# USAGE:
#   .\run-tests-report.ps1              # Run tests + open Allure report
#   .\run-tests-report.ps1 -SkipTests   # Open last report without re-running
#   .\run-tests-report.ps1 -ExtentOnly  # Open ExtentReport instead of Allure
# ============================================================

param(
    [switch]$SkipTests,
    [switch]$ExtentOnly
)

$ErrorActionPreference = "Continue"

Write-Host "============================================" -ForegroundColor Cyan
Write-Host " Mobile Test Automation - Report Viewer"      -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# --- Step 1: Run tests (unless skipped) ---
if (-not $SkipTests) {
    Write-Host "`n[1/3] Running tests with Maven..." -ForegroundColor Yellow
    mvn clean test -B
    $testExitCode = $LASTEXITCODE
    
    if ($testExitCode -ne 0) {
        Write-Host "`nTests finished with failures (exit code $testExitCode)." -ForegroundColor Red
        Write-Host "Reports are still generated - opening them now." -ForegroundColor Yellow
    } else {
        Write-Host "`nAll tests passed!" -ForegroundColor Green
    }
} else {
    Write-Host "`n[1/3] Skipping test execution (using previous results)." -ForegroundColor DarkGray
}

# --- Step 2: Open the report ---
if ($ExtentOnly) {
    # Option A: ExtentReports (static HTML file)
    Write-Host "`n[2/3] Opening ExtentReport..." -ForegroundColor Yellow
    $extentReport = Join-Path $PSScriptRoot "test-output\ExtentReport.html"
    
    if (Test-Path $extentReport) {
        Start-Process $extentReport
        Write-Host "ExtentReport opened in browser." -ForegroundColor Green
    } else {
        Write-Host "ExtentReport not found at: $extentReport" -ForegroundColor Red
        Write-Host "Run tests first without -SkipTests flag." -ForegroundColor Yellow
    }
} else {
    # Option B: Allure Report (interactive web server)
    Write-Host "`n[2/3] Generating Allure Report..." -ForegroundColor Yellow
    $allureResults = Join-Path $PSScriptRoot "target\allure-results"
    
    # Check if allure CLI is available
    $allureCmd = Get-Command allure -ErrorAction SilentlyContinue
    if (-not $allureCmd) {
        Write-Host "Allure CLI not found. Installing via npm..." -ForegroundColor Yellow
        npm install -g allure-commandline
    }
    
    if (Test-Path $allureResults) {
        Write-Host "`n[3/3] Opening Allure Report in browser..." -ForegroundColor Yellow
        Write-Host "       (Press Ctrl+C to stop the report server)" -ForegroundColor DarkGray
        allure serve $allureResults
    } else {
        Write-Host "Allure results not found at: $allureResults" -ForegroundColor Red
        Write-Host "Run tests first without -SkipTests flag." -ForegroundColor Yellow
        
        # Fallback to ExtentReport
        $extentReport = Join-Path $PSScriptRoot "test-output\ExtentReport.html"
        if (Test-Path $extentReport) {
            Write-Host "`nFalling back to ExtentReport..." -ForegroundColor Yellow
            Start-Process $extentReport
        }
    }
}

Write-Host "`nDone." -ForegroundColor Green
