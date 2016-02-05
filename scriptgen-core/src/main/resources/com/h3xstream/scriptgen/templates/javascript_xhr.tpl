<#list requests as req>
var http = new XMLHttpRequest();
<#if req.parametersPost??>
var params = "${util.jsUrlParam(req.parametersPost)}";
</#if>
<#if req.postData??>
var params = "${util.jsStr(req.postData)}";
</#if>
http.open("${req.method?upper_case}", "${util.jsStr(req.queryString)}<#if req.parametersGet??>?${util.jsUrlParam(req.parametersGet)}</#if>", true);

<#if req.postData??>
http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
</#if>
<#if req.headers??>
<#list req.headers?keys as h>
http.setRequestHeader("${util.jsStr(h)}","${util.jsStr(req.headers[h])}");
</#list>
</#if>

http.onreadystatechange = function() {
    if(http.readyState == 4 && http.status == 200) {
        console.info(http.status);
        console.info(http.responseText);
    }
}
http.send(params);

</#list>