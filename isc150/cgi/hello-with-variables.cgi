#!/usr/bin/perl

# file: hello-with-variables.cgi
# This program demonstrates:
#     - printing the HTTP header BEFORE anything else
#     - the use of the "special" first line beginning with a #!
#     - sending "hard-coded" html to STDOUT.
#     - using "variable substitution".

use 5.004;

   my $TITLE = "Hello World with Variables";
   my $BG = "#aaaa00";
   my $TEXT = "#0000ff";
   my $MSG = "Hello World!";
   my $secondary_msg = '&mdash; I have variables!';

   print "Content-type: text/html\n";
   print "\n";

   print "<html><head><title>${TITLE}</title></head>\n";
   print "<body text=\"${TEXT}\" bgcolor=\"${BG}\">\n";

   print "   <p>\n";
   print "   <h1>${MSG}</h1>\n";
   print "   </p>\n";
   print "   <p>${secondary_msg}</p>\n";
   print "</body>\n";
   print "</html>\n";
   exit 0;

