#! /usr/bin/perl

# file: helloworld.cgi
# This program demonstrates:
#     - printing the HTTP header BEFORE anything else
#     - sending "hard-coded" html to STDOUT.
#     - the use of the "special" first line beginning with a #!

use 5.004;

   print "Content-type: text/html\n";
   print "\n";

   print "<html><head><title>Hello World</title></head>\n";
   print "<body text=\"#0000ff\" bgcolor=\"#aaaaaa\">\n";

   print "   <p>\n";
   print "   <h1>Hello World!</h1>\n";
   print "   </p>\n";
   print "   <p>And how are you today?</p>\n";
   print "</body>\n";
   print "</html>\n";
   exit 0;

