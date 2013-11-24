$url = "${req.url}"

$req = curl_init($url);
<#if req.parametersPost??>
curl_setopt($req, CURLOPT_POSTFIELDS, ${util.phpMap(req.parametersPost)});
</#if>
<#if req.headers??>
curl_setopt($req, CURLOPT_HTTPHEADER, ${util.phpHeadersList(req.headers)});
</#if>

