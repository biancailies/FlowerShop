$ErrorActionPreference = "Stop"

$services = @(
    "FlowerCatalogService",
    "InventoryService",
    "NotificationService",
    "StatisticsService",
    "UserManagementService"
)

foreach ($service in $services) {
    Write-Host "Running Maven tests for $service..."
    Push-Location $service
    mvn test
    Pop-Location
}

Write-Host "Building FlowerShopClient..."
Push-Location "FlowerShopClient"
npm run build
Pop-Location

Write-Host "All checks passed."
