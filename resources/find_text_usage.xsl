<?xml version="1.0"?>
<!-- This transform lists all the roles for the emphasis tag. 
 It's useful for identifying special inline text formatting that may be necessary.

Usage: saxon -xsl:find_text_usage.xsl -s:resource-9821.xml target="target text"

-->
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:e="http://ns.expertinfo.se/cms/xmlns/export/1.0"
  xmlns:docbook="http://docbook.org/ns/docbook"
  xmlns:xinfo="http://ns.expertinfo.se/cms/xmlns/1.0" version="2.0">

  <xsl:output method="html" indent="yes" />

  <xsl:param name="target" select="target"/>
  <xsl:param name="editorial_url" select="editorial_url"/>
  <xsl:param name="report_title" select="report_title"/>
  <xsl:variable name="include_code_snippets">true</xsl:variable>

  <xsl:template match="/">
    <html>
    <head>
      <title><xsl:value-of select="$report_title"/></title>
      <link rel="stylesheet" type="text/css" href="../stylesheet.css" />
    </head>
      <body>
      <h1><xsl:value-of select="$report_title"/></h1>
        <p>The following topics contain the string <span class="target"><xsl:value-of select="$target"/></span>.</p>
      <table>
      <thead>
        <tr>
          <th>ID</th><th>Title</th>
        </tr>
      </thead>
      <tbody>
        <xsl:variable name="targetnodeset" select="descendant::e:content[contains(.,$target)]"/>
        <xsl:choose>
          <xsl:when test="$targetnodeset"> <xsl:apply-templates select="$targetnodeset" /></xsl:when>
          <xsl:otherwise><tr><td colspan="2">No topics found.</td></tr></xsl:otherwise>
        </xsl:choose>
      </tbody>
      </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="e:content">

    <xsl:variable name="resourceid" select="parent::e:resource/@id"/>

  <!-- Do not process program listing elements -->
    <xsl:if test="not(//descendant::docbook:programlisting/@xinfo:text=$resourceid)">
      <!-- <xsl:message><xsl:value-of select="concat($resourceid,' ')" /></xsl:message> -->
      <!-- <xsl:value-of select="concat($resourceid,' ')" /> -->
      <xsl:variable name="paranode" select="(//docbook:para[@xinfo:text=$resourceid])[1]"/>
      <xsl:variable name="titlenode" select="(//docbook:title[@xinfo:text=$resourceid])[1]"/>
      <!-- <xsl:message>Count of paranode <xsl:value-of select="count($paranode)" /></xsl:message> -->
      <!-- <xsl:message> <xsl:value-of select="generate-id($paranode)" /></xsl:message> -->
      <!-- <xsl:value-of select="generate-id($paranode[1])" /> -->
      <!-- <xsl:value-of select="concat($paranode/@xinfo:text,' ')" /> -->

      <xsl:variable name="sectionnode" select="//$paranode/ancestor::docbook:section | //$titlenode/ancestor::docbook:section"/>
      <tr>
        <td><xsl:value-of select="$sectionnode/@xinfo:resource-id"/></td>
        <td>
          <xsl:element name="a">
            <xsl:attribute name="href"><xsl:value-of select="concat($editorial_url,$sectionnode/@xinfo:resource-id)"/></xsl:attribute>
            <xsl:attribute name="target">pu</xsl:attribute>
            <xsl:value-of select="$sectionnode/@xinfo:resource-title"/>
          </xsl:element>

        </td>
      </tr>
    </xsl:if>
  </xsl:template>

</xsl:transform>