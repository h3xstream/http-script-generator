require "net/http"
require "uri"
<#if req.ssl>
require "openssl"
</#if>
<#if req.parametersMultipart??>
require "net/http/post/multipart"
# Psst! It require an additional gem : gem install multipart-post
</#if>

uri = URI.parse("${req.url}")
<#if req.parametersGet??>
uri.query = URI.encode_www_form(${util.rubyMap(req.parametersGet)})
</#if>
http = Net::HTTP.new(uri.host, uri.port)
<#if req.ssl && !settings.proxy>
http.use_ssl = true
<#if settings.disableSsl>
http.verify_mode = OpenSSL::SSL::VERIFY_NONE
</#if>
</#if>

<#if req.parametersMultipart??>
multipartParams = {${util.rubyDictMultipart(req.parametersMultipart)}}
<#if req.parametersPost??>
multipartParams = multipartParams.merge(${util.rubyMap(req.parametersPost)})
</#if>
</#if>
request = Net::HTTP::${req.method?capitalize}<#if req.parametersMultipart??>::Multipart</#if>.new(uri.request_uri<#if req.parametersMultipart??>, multipartParams</#if>)
<#if req.headers??>
<#list req.headers?keys as h>
request["${util.rubyStr(h)}"] = "${util.rubyStr(req.headers[h])}"
</#list>
</#if>
<#if req.parametersPost?? && !req.parametersMultipart??>
request.set_form_data(${util.rubyMap(req.parametersPost)})
</#if>
<#if req.postData??>
request.body = "${util.rubyStr(req.postData)}"
</#if>
<#if req.basicAuth??>
request.basic_auth("${util.rubyStr(req.basicAuth.username)}","${util.rubyStr(req.basicAuth.password)}")
</#if>
<#if settings.proxy>

Net::HTTP::Proxy('127.0.0.1', 8080).start("${req.hostname}"<#if req.ssl>, :use_ssl => true<#if settings.disableSsl>, :verify_mode => OpenSSL::SSL::VERIFY_NONE</#if></#if>) {|http|
</#if>
response = http.request(request)

puts "Status code: "+response.code
puts "Response body: "+response.body
<#if settings.proxy>
}
</#if>