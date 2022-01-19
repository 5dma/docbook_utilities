<?xml version="1.0"?>
<!-- This transform lists all the roles for the emphasis tag. 
 It's useful for identifying special inline text formatting that may be necessary.

Usage: saxon -xsl:find_text_usage.xsl -s:resource-9821.xml target="target text"

-->
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               xmlns:e="http://ns.expertinfo.se/cms/xmlns/export/1.0"
               xmlns:docbook="http://docbook.org/ns/docbook"
               xmlns:xinfo="http://ns.expertinfo.se/cms/xmlns/1.0" version="2.0">

    <xsl:output method="html" indent="yes"/>
    <xsl:param name="editorial_url" select="editorial_url"/>
    <xsl:param name="report_title" select="report_title"/>

    <xsl:template match="/">
        <html>
            <head>
                <title><xsl:value-of select="$report_title"/></title>
                <link rel="stylesheet" type="text/css" href="../stylesheet.css"/>
            </head>
            <body>
                <h1><xsl:value-of select="$report_title"/></h1>
                <p>The following glossary entries have an empty acronym tag.</p>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Entry</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:variable name="targetnodeset" select="descendant::docbook:acronym[not(@xinfo:text)]"/>
                        <xsl:choose>
                            <xsl:when test="$targetnodeset">
                                <xsl:apply-templates select="$targetnodeset"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <tr>
                                    <td colspan="3">No elements found.</td>
                                </tr>
                            </xsl:otherwise>
                        </xsl:choose>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="docbook:acronym">

        <xsl:variable name="term_id" select="preceding-sibling::docbook:glossterm/@xinfo:text"/>
        <xsl:variable name="term" select="//e:resource[@id=$term_id]/e:content"/>
        <xsl:variable name="section_title" select="ancestor::docbook:section/@xinfo:resource-title"/>
        <xsl:variable name="ancestor_id" select="ancestor::docbook:section/@xinfo:resource-id||ancestor::e:resource/@id"/>
        <xsl:variable name="final_section_title">
            <xsl:choose>
                <xsl:when test="not($section_title)">
                    N/A
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$section_title"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <tr>
            <td>
                <xsl:value-of select="$ancestor_id"/>
            </td>
            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="concat($editorial_url,$ancestor_id)"/></xsl:attribute>
                    <xsl:attribute name="target">pu</xsl:attribute>
                    <xsl:value-of select="$final_section_title"/>
                </xsl:element>


               </td>
            <td><xsl:value-of select="$term"/></td>
        </tr>



    </xsl:template>


</xsl:transform>