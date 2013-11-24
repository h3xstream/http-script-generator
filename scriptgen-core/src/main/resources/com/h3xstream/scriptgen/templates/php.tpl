<?php

$url = "${req.url}";

$paramsPost="";
<#list req.parametersPost?keys as p>
$paramsPost .= ${util.phpUrlEncode(p)}.'='.${util.phpUrlEncode(req.parametersPost[p])}<#if p_has_next>.'&'</#if>;
</#list>

$req = curl_init($url);
curl_setopt($req, CURLOPT_POSTFIELDS, $paramsPost);
<#if req.headers??>
curl_setopt($req, CURLOPT_HTTPHEADER, ${util.phpHeadersList(req.headers)});
</#if>

<#if req.cookies??>
curl_setopt($req, CURLOPT_COOKIE,"${util.phpCookies(req.cookies)}");
</#if>
curl_exec($req);
curl_close($req);

?>