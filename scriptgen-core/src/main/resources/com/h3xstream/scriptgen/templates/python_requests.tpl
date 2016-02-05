import requests
<#if util.atLeastOneBasicAuth(requests)>
from requests.auth import HTTPBasicAuth
</#if>
<#if settings.proxy>

proxies = {
  "http":  "http://127.0.0.1:8080",
  "https": "http://127.0.0.1:8080",
}
</#if>

session = requests.Session()

<#list requests as req>
<#if req.parametersGet??>
paramsGet = ${util.pythonDict(req.parametersGet)}
</#if>
<#if req.parametersPost??>
paramsPost = ${util.pythonDict(req.parametersPost)}
</#if>
<#if req.parametersMultipart??>
paramsMultipart = ${util.pythonDictMultipart(req.parametersMultipart)}
</#if>
<#if req.postData??>
rawBody = "${util.pythonStr(req.postData)}"
</#if>
<#if req.headers??>
headers = ${util.pythonDict(req.headers)}
</#if>
<#if req.cookies??>
cookies = ${util.pythonDict(req.cookies)}
</#if>
response = session.${req.method?lower_case}("${util.pythonStr(req.url)}"<#if req.parametersPost??>, data=paramsPost</#if><#if req.parametersMultipart??>, files=paramsMultipart</#if><#if req.postData??>, data=rawBody</#if><#if req.parametersGet??>, params=paramsGet</#if><#if req.headers??>, headers=headers</#if><#if req.cookies??>, cookies=cookies</#if><#if req.basicAuth??>, auth=HTTPBasicAuth("${util.pythonStr(req.basicAuth.username)}","${util.pythonStr(req.basicAuth.password)}")</#if><#if settings.proxy>, proxies=proxies</#if><#if req.ssl && settings.disableSsl>, verify=False</#if>)

print("Status code:   %i" % response.status_code)
print("Response body: %s" % response.content)

</#list>