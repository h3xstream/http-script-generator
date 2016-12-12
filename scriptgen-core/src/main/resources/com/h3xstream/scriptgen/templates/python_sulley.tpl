from sulley import *
from urllib import quote

<#list requests as req>
s_initialize("HTTP request to ${util.pythonStr(req.url)}")

#Query string
s_static("${util.pythonStr(req.method?upper_case)} ")
s_string("${util.pythonStr(req.queryString)}")
<#if req.parametersGet??>
s_delim("?")
<#list (req.parametersGet)?keys as param_name>
if s_block_start("get_param_${param_name_index + 1}",encoder=quote):
    s_static(quote("${util.pythonStr(param_name)}"))
    s_delim("=")
    s_string("${util.pythonStr(req.parametersGet[param_name])}")
s_block_end()
<#if param_name_has_next>
s_delim("&")
</#if>
</#list>
</#if>
s_static(" HTTP/1.1\r\n")

#Headers
<#if req.headers??>
<#list (req.headers)?keys as header_name>
s_static("${header_name}: ")
s_string("${util.pythonStr(req.headers[header_name])}")
s_static("\r\n")
</#list>
</#if>
<#if req.cookies??>
s_static("Cookie: ")
<#list (req.cookies)?keys as cookie_name>
s_static("${util.pythonStr(cookie_name)}")
s_delim("=")
s_string("${util.pythonStr(req.cookies[cookie_name])}")
<#if cookie_name_has_next>
s_delim("; ")
</#if>
</#list>
</#if>
<#if req.postData?? || req.parametersPost??>
s_static("Content-Length: ")
s_size("post_data", format="ascii", signed=True, fuzzable=True)
s_static("\r\n")
<#else>
s_static("Content-Length: 0\r\n")
</#if>

s_static("\r\n")
<#if req.postData?? || req.parametersPost??>

#Post data
if s_block_start("post_data"):
<#if req.postData??>
    s_string("${util.pythonStr(req.postData)}")
</#if>
<#if req.parametersPost??>
    <#list (req.parametersPost)?keys as param_name>
    if s_block_start("post_param_${param_name_index + 1}",encoder=quote):
        s_static(quote("${util.pythonStr(param_name)}"))
        s_delim("=")
        s_string("${util.pythonStr(req.parametersPost[param_name])}")
    s_block_end()
    <#if param_name_has_next>
    s_delim("&")
    </#if>
    </#list>
</#if>
s_block_end()
</#if>

</#list>