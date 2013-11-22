use LWP::UserAgent;

my $url = URI->new("${req.url}");
<#if req.parametersGet??>
$url->query_form(${util.perlMap(req.parametersGet)});
</#if>

my $ua = LWP::UserAgent->new();
<#if req.parametersPost??>
my $resp = $ua->post( $url, ${util.perlMap(req.parametersPost)});
<#elseif req.method?lower_case == 'get'>
my $resp = $ua->get( $url);
<#else>
my $req = HTTP::Request->new("${req.method?upper_case}", $url);
my $res = $ua->request($req);
</#if>
