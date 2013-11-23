require "net/http"
require "uri"

uri = URI.parse("${req.url}")
<#if req.parametersGet??>
paramsGet = ${util.rubyMap(req.parametersGet)}
uri.query = URI.encode_www_form(paramsGet)
</#if>
http = Net::HTTP.new(uri.host, uri.port)

request = Net::HTTP::${req.method?capitalize}.new(uri.request_uri)
<#if req.headers??>
<#list req.headers?keys as h>
request["${util.rubyStr(h)}"] =  "${util.rubyStr(req.headers[h])}"
</#list>
</#if>
<#if req.parametersPost??>
request.set_form_data(${util.rubyMap(req.parametersPost)})
</#if>
response = http.request(request)

puts "Status code: "+response.code
puts "Response body: "+response.body
