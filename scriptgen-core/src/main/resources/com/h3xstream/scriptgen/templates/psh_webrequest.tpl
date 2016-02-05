<#if util.atLeastOneSsl(requests) && settings.disableSsl>
add-type @"
    using System.Net;
    using System.Security.Cryptography.X509Certificates;
    public class TrustAllCertsPolicy : ICertificatePolicy {
        public bool CheckValidationResult(
            ServicePoint srvPoint, X509Certificate certificate,
            WebRequest request, int certificateProblem) {
            return true;
        }
    }
"@
[System.Net.ServicePointManager]::CertificatePolicy = New-Object TrustAllCertsPolicy

</#if>
<#list requests as req>
<#if req.parametersGet??>
$paramsGet = ${util.powershellDict(req.parametersGet)}
</#if>
<#if req.parametersPost??>
$paramsPost = ${util.powershellDict(req.parametersPost)}
</#if>
<#if req.headers??>
$headers = ${util.powershellDict(req.headers)}
</#if>

<#if req.cookies??>
$cc = New-Object System.Net.CookieContainer
<#list req.cookies?keys as c>
$cc.Add( $(New-Object Uri( $uri )), $(New-Object System.Net.Cookie("${util.powershellStr(c)}", "${util.powershellStr(req.cookies[c])}")) )
</#list>
$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession 
$session.Cookies = $cc

</#if>
$response = Invoke-WebRequest -Method "${req.method}" -Uri "${req.url}"<#if req.headers??> -Headers $headers</#if><#if req.cookies??> -WebSession $session</#if><#if req.parametersPost??> -Body $paramsPost</#if><#if req.parametersGet??> -Body $paramsGet</#if><#if settings.proxy> -Proxy 'http://127.0.0.1:8080'</#if>

Write-Host "Status code: $($response.StatusCode)" 
Write-host "Response body: $($response.Content)"

</#list>