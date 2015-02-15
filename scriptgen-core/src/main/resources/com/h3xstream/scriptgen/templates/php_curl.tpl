<?php
<#if req.parametersPost??>
$paramsPost = "";
<#list req.parametersPost?keys as p>
$paramsPost .= ${util.phpUrlEncode(p)}.'='.${util.phpUrlEncode(req.parametersPost[p])}<#if p_has_next>.'&'</#if>;
</#list>

</#if>
<#if req.postData??>
$postData = "${util.phpStr(req.postData)}";

</#if>
$req = curl_init("${util.phpStr(req.url)}");
curl_setopt($req, CURLOPT_RETURNTRANSFER, true);
<#if req.parametersPost??>
curl_setopt($req, CURLOPT_POSTFIELDS, $paramsPost);
</#if>
<#if req.postData??>
curl_setopt($req, CURLOPT_POSTFIELDS, $postData);
</#if>
<#if req.headers??>
curl_setopt($req, CURLOPT_HTTPHEADER, ${util.phpHeadersList(req.headers)});
</#if>
<#if req.cookies??>
curl_setopt($req, CURLOPT_COOKIE,"${util.phpCookies(req.cookies)}");
</#if>
<#if req.basicAuth??>
curl_setopt($req, CURLOPT_USERPWD, '${util.phpStr(req.basicAuth.username)}:${util.phpStr(req.basicAuth.password)}');
curl_setopt($req, CURLOPT_HTTPAUTH, CURLAUTH_ANY);
</#if>
<#if req.ssl && settings.disableSsl>
curl_setopt($req, CURLOPT_SSL_VERIFYHOST, false);
curl_setopt($req, CURLOPT_SSL_VERIFYPEER, false);
</#if>
<#if settings.proxy>
curl_setopt($req, CURLOPT_PROXY, '127.0.0.1:8080');
</#if>
$result = curl_exec($req);

echo "Status code: ".curl_getinfo($req, CURLINFO_HTTP_CODE)."\n";
echo "Response body: ".$result."\n";

curl_close($req);
?>