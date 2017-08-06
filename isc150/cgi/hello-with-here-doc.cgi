#!/usr/bin/perl

# file: hello-with-here-doc.cgi
# This program demonstrates:
#     - printing the HTTP header BEFORE anything else
#     - the use of the "special" first line beginning with a #!
#     - sending "hard-coded" html to STDOUT.
#     - using "variable substitution".
#     - shows the use of the "here document" operator <<.


use 5.004;

   my $TITLE = "Hello World with a Here Document";
   my $BG = "#aaaaaa";
   my $TEXT = "#0000ff";
   my $MSG = "Hello World!";
   my $german_msg = '&mdash; Wie Geht\'s?';
   my $aussie_msg = '&mdash; G\'Day!';
   my $italian_msg = '&mdash; Ciao!';
   my $hawaiian_msg = '&mdash; Aloha!';

   print <<"END_OF_STRING";
Content-type: text/html

<html><head><title>${TITLE}</title></head>
<body text="${TEXT}" bgcolor="${BG}">
   <p>
   <h1>${MSG}</h1>
   </p>
   <p>
From a list of characters
<ol type="i">
<li> German $german_msg
<li> Australian $aussie_msg
<li> Italian $italian_msg
<li> Hawaiian $hawaiian_msg
</ol>
   </p>
</body>
</html>

END_OF_STRING
   exit 0;

