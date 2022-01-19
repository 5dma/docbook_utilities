<?xml version="1.0"?>

<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               xmlns:e="http://ns.expertinfo.se/cms/xmlns/export/1.0"
               xmlns:docbook="http://docbook.org/ns/docbook"
               xmlns:xinfo="http://ns.expertinfo.se/cms/xmlns/1.0"
               xmlns:xlink="http://www.w3.org/1999/xlink" version="2.0">

    <xsl:param name="editorial_url" select="editorial_url"/>
    <xsl:param name="report_title" select="report_title"/>

    <xsl:template match="/">
        <html>
            <head>
                <title><xsl:value-of select="$report_title"/></title>
                <link rel="stylesheet" type="text/css" href="../stylesheet.css" />

            </head>
            <body>
                <h1><xsl:value-of select="$report_title"/></h1>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th><th>Title</th><th>External URL</th>
                        </tr>
                    </thead>
                    <tbody>
                    <xsl:apply-templates select="descendant::docbook:link[@xlink:href and not(starts-with(@xlink:href,'urn:resource'))]"/>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="docbook:link">
        <tr>
            <xsl:variable name="resourceid" select="ancestor::e:resource[1]/@id"/>
            <xsl:variable name="paranode" select="(//docbook:para[@xinfo:text=$resourceid])[1]"/>
            <xsl:variable name="sectionnode" select="$paranode/ancestor::docbook:section[1]"/>
            <td>
                <xsl:value-of select="$sectionnode/@xinfo:resource-id"/>
            </td>
            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="concat($editorial_url,$sectionnode/@xinfo:resource-id)"/></xsl:attribute>
                <xsl:value-of select="$sectionnode/@xinfo:resource-title"/>
                </xsl:element>
            </td>
            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="@xlink:href"/></xsl:attribute>
                    <xsl:attribute name="target">pu</xsl:attribute>
                    <xsl:attribute name="name">checkme</xsl:attribute>
                    <xsl:value-of select="@xlink:href"/>
                </xsl:element>

            </td>
        </tr>
    </xsl:template>

</xsl:transform>