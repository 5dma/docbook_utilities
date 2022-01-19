<?xml version="1.0"?>

<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               xmlns:e="http://ns.expertinfo.se/cms/xmlns/export/1.0"
               xmlns:docbook="http://docbook.org/ns/docbook"
               xmlns:xinfo="http://ns.expertinfo.se/cms/xmlns/1.0"
               xmlns:xlink="http://www.w3.org/1999/xlink" version="2.0">
    <xsl:param name="editorial_url" select="editorial_url"/>
    <xsl:template match="/">
<!--                    <xsl:apply-templates select="descendant::docbook:link[@xlink:href and not(starts-with(@xlink:href,'urn:resource'))]"/>-->
<!--        <xsl:apply-templates select="descendant::docbook:link[@xlink:href and contains(@xlink:href,'://')]"/>-->
        <xsl:apply-templates select="descendant::docbook:link[@xlink:href and not(starts-with(@xlink:href,'urn:resource')) and not(starts-with(@xlink:href,'#'))]"/>
    </xsl:template>

    <xsl:template match="docbook:link">

            <xsl:variable name="resourceid" select="ancestor::e:resource[1]/@id"/>
            <xsl:variable name="paranode" select="(//docbook:para[@xinfo:text=$resourceid])[1]"/>
            <xsl:variable name="sectionnode" select="$paranode/ancestor::docbook:section[1]"/>
        <xsl:value-of select="concat($sectionnode/@xinfo:resource-id,'&#x9;',concat($editorial_url,$sectionnode/@xinfo:resource-id),'&#x9;',$sectionnode/@xinfo:resource-title,'&#x9;',@xlink:href,'&#xa;')"/>


    </xsl:template>

</xsl:transform>