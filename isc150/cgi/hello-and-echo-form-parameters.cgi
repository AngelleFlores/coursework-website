#!/usr/bin/perl

# file: hello-and-echo-form-parameters.cgi
# This uses a popular Perl module to get parameters passed in to the script
# This program demonstrates:
#     - printing the HTTP header BEFORE anything else
#     - the use of the "special" first line beginning with a #!
#     - sending "hard-coded" html to STDOUT.
#     - using "variable substitution".
#     - shows the use of the "here document" operator <<.
#     - using an external module

use 5.004;
use CGI qw(:standard);

   my $TITLE = "Hello World and here are the parameters that you sent.";
   my $BG = "#aaaaaa";
   my $TEXT = "#0000ff";
   my $MSG = "Hello World!";
   my $submessage = '&mdash; let me get your parameters &mdash;' . "\n";
   my $header = '<h2>Here are the parameters sent to me.</h2>';

my $query = new CGI();

   print <<"END_OF_STRING";
Content-type: text/html

<html><head><title>${TITLE}</title></head>
<body text="${TEXT}" bgcolor="${BG}">
   <p>
   <h1>${MSG}</h1>
   </p>
   <p>
   <h2>${submessage}</h2>
   </p>
   <p>
   ${header}
   </p>
   <p>
   <ol type="1">
END_OF_STRING

for $key ($query->param) {
   print "<li> ";
   print "<b>Key</b>: <u>" . $key . "</u>; <b>Value</b>: <u>" . $query->param($key) . "</u>\n";
   print "</li>\n";
}
print "</ol>\n";

   print <<"END_OF_STRING";
</body>
</html>

END_OF_STRING
   exit 0;

