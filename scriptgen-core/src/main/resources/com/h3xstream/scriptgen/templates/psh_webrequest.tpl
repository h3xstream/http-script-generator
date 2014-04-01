$uri = "${req.url}"
$method = "${req.method}"

<#if req.headers??>
$headers = ${util.powershellDict(req.headers)}
$headers.Remove("Proxy-Connection")
</#if>

<#if req.cookies??>
$cc = New-Object System.Net.CookieContainer
<#list req.cookies?keys as c>
$cc.Add( $(New-Object Uri( $uri )), $(New-Object System.Net.Cookie("${util.powershellStr(c)}", "${util.powershellStr(req.cookies[c])}")) )
</#list>
$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession 
$session.Cookies = $cc  
</#if>

<#if req.parametersGet??>
$paramsGet = ${util.powershellDict(req.parametersGet)}
</#if>

<#if req.parametersPost??>
$paramsPost = ${util.powershellDict(req.parametersPost)}
</#if>

$response = Invoke-WebRequest -Method $method -Uri $uri<#if req.headers??> -Headers $headers</#if><#if req.cookies??> -WebSession $session</#if><#if req.parametersPost??> -Body $paramsPost</#if><#if req.parametersGet??> -Body $paramsGet</#if>

Write-Host "Status code: $($response.StatusCode)" 
Write-host "Response body: $($response.Content)"
