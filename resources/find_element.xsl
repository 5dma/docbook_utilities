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
    <xsl:param name="target" select="target"/>
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
                <p>The following topics contain the element<span class="target"><xsl:value-of select="$target"/></span>.</p>
                <table class="listelement">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Context</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:variable name="targetnodeset" select="descendant::*[name() = $target]"/>
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

    <xsl:template match="*[name()=$target and not(. ='')] ">
        <xsl:variable name="sectionnode" select="ancestor::docbook:section[1]"/>

        <tr>
            <xsl:choose>
                <xsl:when test="$sectionnode">
                    <xsl:message>has sectionnode</xsl:message>
            <td>
                <xsl:value-of select="$sectionnode/@xinfo:resource-id"/>
            </td>
            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="concat($editorial_url,$sectionnode/@xinfo:resource-id)"/></xsl:attribute>
                    <xsl:attribute name="target">pu</xsl:attribute>
                    <xsl:value-of select="$sectionnode/@xinfo:resource-title"/>
                </xsl:element>
               </td>
                </xsl:when>
                <xsl:otherwise>

                    <xsl:variable name="resourcenode" select="ancestor::e:resource[1]"/>

                    <xsl:variable name="paranode" select="//docbook:para[@xinfo:text = $resourcenode/@id]"/>

                    <xsl:variable name="sectionnode" select="$paranode[1]/ancestor::docbook:section[1]"/>

                    <td>
                        <xsl:value-of select="$sectionnode/@xinfo:resource-id"/>
                    </td>
                    <td>
                        <xsl:element name="a">
                            <xsl:attribute name="href"><xsl:value-of select="concat($editorial_url,$sectionnode/@xinfo:resource-id)"/></xsl:attribute>
                            <xsl:attribute name="target">pu</xsl:attribute>
                            <xsl:value-of select="$sectionnode/@xinfo:resource-title"/>
                        </xsl:element>
                    </td>
                </xsl:otherwise>
            </xsl:choose>
            <td><xsl:value-of select="."/></td>
        </tr>
    </xsl:template>
    <xsl:template match="*[name()=$target and (. = '')]">
        <xsl:message>Got here</xsl:message>
        <xsl:variable name="textid" select="@xinfo:text"/>
        <xsl:variable name="content" select="//e:resource[@id=$textid]/e:content"/>
        <xsl:variable name="sectionnode" select="ancestor::docbook:section[1]"/>
        <tr>
            <td>
                <xsl:value-of select="$sectionnode/@xinfo:resource-id"/>
            </td>
            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="concat($editorial_url,$sectionnode/@xinfo:resource-id)"/></xsl:attribute>
                    <xsl:attribute name="target">pu</xsl:attribute>
                    <xsl:value-of select="$sectionnode/@xinfo:resource-title"/>
                </xsl:element>
            </td>
            <td><xsl:value-of select="$content"/></td>
        </tr>
    </xsl:template>

</xsl:transform>