<?php
$url = "${req.url}";

<#if req.parametersPost??>
$paramsPost="";
<#list req.parametersPost?keys as p>
$paramsPost .= ${util.phpUrlEncode(p)}.'='.${util.phpUrlEncode(req.parametersPost[p])}<#if p_has_next>.'&'</#if>;
</#list>

</#if>
$req = curl_init($url);
curl_setopt($req, CURLOPT_RETURNTRANSFER, true);
<#if req.parametersPost??>
curl_setopt($req, CURLOPT_POSTFIELDS, $paramsPost);
</#if>
<#if req.headers??>
curl_setopt($req, CURLOPT_HTTPHEADER, ${util.phpHeadersList(req.headers)});
</#if>
<#if req.cookies??>
curl_setopt($req, CURLOPT_COOKIE,"${util.phpCookies(req.cookies)}");
</#if>
$result = curl_exec($req);

echo "Status code: ".curl_getinfo($req, CURLINFO_HTTP_CODE)."\n";
echo "Response body: ".$result."\n";

curl_close($req);
?>