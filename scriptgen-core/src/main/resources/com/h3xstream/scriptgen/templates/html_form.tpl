<form name="csrf_poc" action="${util.jsStr(req.url)}<#if req.parametersGet?? && req.parametersPost??>?${util.jsUrlParam(req.parametersGet)}</#if>" method="${req.method?upper_case}">
<#if req.parametersPost??>
<#list req.parametersPost?keys as p>
<input type="hidden" name="${util.jsStr(p)}" value="${util.jsStr(req.parametersPost[p])}">
</#list>
<#elseif req.parametersGet??>
<#list req.parametersGet?keys as p>
<input type="hidden" name="${util.jsStr(p)}" value="${util.jsStr(req.parametersGet[p])}">
</#list>
</#if>
<#if req.parametersMultipart??>
<#list req.parametersMultipart as mp>
<input type="file" name="${util.jsStr(mp.name)}">
</#list>
</#if>

<input type="submit" value="Replay!">
</form>
<!-- Auto-submit script:
<script type="text/javascript">document.forms.csrf_poc.submit();</script>
-->