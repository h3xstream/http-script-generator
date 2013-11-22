require "net/http"
require "uri"

uri = URI.parse("${req.url}")
<#if req.method?lower_case == 'get'>
request = Net::HTTP::Post.new(uri.request_uri)
</#if>
<#if req.parametersPost??>
request.set_form_data(${util.rubyMap(req.parametersPost)})
</#if>