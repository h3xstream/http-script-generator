//JQuery preload (optional)
(function(){
  var s = document.createElement('script');s.type = 'text/javascript';s.async = true;s.src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js';
  (document.getElementsByTagName('head')[0]||document.getElementsByTagName('body')[0]).appendChild(s);
})();

$.ajax({
    url: "${util.jsStr(req.queryString)}<#if req.parametersGet?? && req.parametersPost??>?${util.jsUrlParam(req.parametersGet)}</#if>",
    type: "${req.method?lower_case}",
    data:
        <#if req.parametersPost??>
        ${util.jsMap(req.parametersPost)}
        <#elseif req.parametersGet?? && !req.parametersPost??>
        ${util.jsMap(req.parametersGet)}
        <#else>
        {}
        </#if>
    ,
    <#if req.headers??>headers: {
    <#list req.headers?keys as h>
        "${util.jsStr(h)}":"${util.jsStr(req.headers[h])}"<#if h_has_next>,</#if>
    </#list>
    },
</#if>    success: function (data) {
        console.info(data);
    }
});