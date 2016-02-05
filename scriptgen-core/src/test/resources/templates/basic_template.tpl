<#list requests as req>

url = "${req.url}" //Unescape
url = "${util.pythonStr(req.url)}" //Escape

</#list>