<?xml version="1.0" encoding="UTF-8"?>

<!--
browser.xsl
2002-10-29
David Wallace Croft
http://www.croftsoft.com/
-->

<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="1.0">

<xsl:output method="xml" indent="yes" />

<xsl:template match="/">
  <xsl:processing-instruction name="xml-stylesheet">type="text/xsl" href="xsl/wyrm.xsl"</xsl:processing-instruction>
  <xsl:apply-templates select="@*|node()" />
</xsl:template>

<xsl:template match="@*|node()">
  <xsl:copy>
    <xsl:apply-templates select="@*|node()" />
  </xsl:copy>
</xsl:template>

</xsl:stylesheet>