<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version = "1.0"
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" />

<xsl:template match="/">
<html>
<head>
	<title>Jokes</title>
	<link rel="stylesheet" type="text/css" href="jokes_css.css"/>
</head>

<body>
	<h3>Jokes</h3>
	<p>Read the following jokes and give an overall rating on a scale of 1 to 10 with 1 meaning "bad jokes" and 10 meaning "very funny jokes."</p>
	<form action="paramseries.cgi" method="post">
		<xsl:for-each select="jokes/joke">
			<p><xsl:value-of select="question"/></p>
			<i><xsl:value-of select="answer"/></i>
		</xsl:for-each>
		<p><input type="range" name="rating" min="1" max="10" /></p>
		<input type="submit" value="Submit"/>
	</form>
</body>
</html>
</xsl:template>

</xsl:stylesheet>
