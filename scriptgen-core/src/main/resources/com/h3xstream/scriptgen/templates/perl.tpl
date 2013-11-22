use LWP::UserAgent;

my $url = "${req.url}";

my $ua = LWP::UserAgent->new();
my $response = $ua->post( $url, ${util.perlMap(req.parametersPost)} );
