import requests
<#if req.basicAuth??>
from requests.auth import HTTPBasicAuth
</#if>

session = requests.Session()

<#if req.parametersGet??>
paramsGet = ${util.pythonDict(req.parametersGet)}
</#if>
<#if req.parametersPost??>
paramsPost = ${util.pythonDict(req.parametersPost)}
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
response = session.${req.method?lower_case}("${util.pythonStr(req.url)}"<#if req.parametersPost??>, data=paramsPost</#if><#if req.postData??>, data=rawBody</#if><#if req.parametersGet??>, params=paramsGet</#if><#if req.headers??>, headers=headers</#if><#if req.cookies??>, cookies=cookies</#if><#if req.basicAuth??>, auth=HTTPBasicAuth("${util.pythonStr(req.basicAuth.username)}","${util.pythonStr(req.basicAuth.password)}")</#if>)

print "Status code:", response.status_code
print "Response body:", response.text
