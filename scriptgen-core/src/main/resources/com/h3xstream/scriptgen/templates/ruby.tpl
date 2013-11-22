require "net/http"
require "uri"

uri = URI.parse("${req.url}")

request = Net::HTTP::Post.new(uri.request_uri)
<#if req.parametersPost??>
request.set_form_data(${util.rubyMap(req.parametersPost)})
</#if>