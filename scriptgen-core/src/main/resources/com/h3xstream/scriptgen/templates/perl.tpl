use LWP::UserAgent;
<#if req.cookies??>
use HTTP::Cookies;
</#if>
<#if req.parametersPost??>
use URI::Escape;
</#if>

my $url = URI->new("${req.url}");
<#if req.parametersGet??>
$url->query_form(${util.perlMap(req.parametersGet)});
</#if>

<#if req.cookies??>
my $cookies = HTTP::Cookies->new();
<#list req.cookies?keys as c>
$cookies->set_cookie(0,"${util.perlStr(c)}", "${util.perlStr(req.cookies[c])}","/","${util.perlStr(req.hostname)}");
</#list>
</#if>

my $req = HTTP::Request->new("${req.method?upper_case}", $url);
<#if req.parametersPost??>
$paramsPost='';
<#list req.parametersPost?keys as p>
$paramsPost.=uri_escape("${util.perlStr(p)}")."=".uri_escape("${util.perlStr(req.parametersPost[p])}")<#if p_has_next>.'&'</#if>;
</#list>
$req->content($paramsPost);
$req->content_type('application/x-www-form-urlencoded');
</#if>
<#if req.headers??>
<#list req.headers?keys as h>
$req->header("${util.perlStr(h)}" => "${util.perlStr(req.headers[h])}");
</#list>
</#if>
my $ua = LWP::UserAgent->new();
<#if req.cookies??>
$ua->cookie_jar($cookies);
</#if>
my $resp = $ua->request($req);

print "Status code : ".$resp->code."\n";
print "Response body : ".$resp->content."\n";
