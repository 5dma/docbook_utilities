<?xml version="1.0"?>
<!-- This transform lists all the roles for the emphasis tag. 
 It's useful for identifying special inline text formatting that may be necessary.

Usage: saxon -xsl:find_text_usage.xsl -s:resource-9821.xml element="element text"

-->
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               xmlns:e="http://ns.expertinfo.se/cms/xmlns/export/1.0"
               xmlns:docbook="http://docbook.org/ns/docbook"
               xmlns:xinfo="http://ns.expertinfo.se/cms/xmlns/1.0" version="2.0">

    <xsl:output method="html" indent="yes"/>
    <xsl:param name="element" select="element"/>
    <xsl:param name="attribute" select="attribute"/>
    <xsl:param name="editorial_url" select="editorial_url"/>
    <xsl:param name="report_title" select="report_title"/>
    <xsl:param name="variable_url" select="variable_url"/>
    <xsl:template match="/">
        <html>
            <head>
                <title><xsl:value-of select="$report_title"/></title>
                <link rel="stylesheet" type="text/css" href="../stylesheet.css"/>
            </head>
            <body>
                <h1><xsl:value-of select="$report_title"/></h1>
                <p>The following topics contain the element <span class="target"><xsl:value-of select="$element"/></span> and the attribute <span class="target"><xsl:value-of select="$attribute"/></span>.</p>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Context</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:variable name="elementnodeset" select="descendant::*[name() = $element and @*/name()=$attribute ]"/>
                        <xsl:choose>
                            <xsl:when test="$elementnodeset">
                                <xsl:apply-templates select="$elementnodeset"/>
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

    <xsl:template match="*[name()=$element and parent::docbook:var]">
        <xsl:variable name="variablename" select="parent::docbook:var/@label-name"/>
        <xsl:variable name="variablesetid" select="ancestor::docbook:varset[1]/@id"/>
        <xsl:variable name="variablesetname" select="//e:varset[@id=$variablesetid]/@title"/>
        <tr>
            <td>
                <xsl:value-of select="$variablesetid" />
            </td>

            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="concat($variable_url,$variablesetid)"/></xsl:attribute>
                    <xsl:attribute name="target">pu</xsl:attribute>
                    <xsl:value-of select="concat($variablename,' (variable)')"/>
                </xsl:element>
               </td>
            <td><xsl:value-of select="."/></td>
        </tr>
    </xsl:template>
    <xsl:template match="*[name()=$element and not(parent::docbook:var)]">
        <xsl:variable name="resource" select="ancestor::e:resource[1]"/>
        <xsl:variable name="component" select="//e:component[e:text/@id=$resource/@id]"/>
        <tr>
            <td>
                  <xsl:value-of select="$resource/@id" />
            </td>
            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="$editorial_url"/></xsl:attribute>
                    <xsl:attribute name="target">pu</xsl:attribute>
                    <xsl:value-of select="$component/@title"/>
                </xsl:element>


            </td>
            <td><xsl:value-of select="."/></td>
        </tr>



    </xsl:template>
</xsl:transform>