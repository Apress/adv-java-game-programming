<?xml version="1.0" encoding="UTF-8"?>

<!--
wyrm.xsl
2002-11-04
David Wallace Croft
http://www.croftsoft.com/
-->

<stylesheet
  xmlns="http://www.w3.org/1999/XSL/Transform"
  xmlns:account="http://croftsoft.com/wyrm/xsl/account"
  xmlns:createuser="http://croftsoft.com/wyrm/xsl/createuser"
  xmlns:login="http://croftsoft.com/wyrm/xsl/login"
  xmlns:response="http://croftsoft.com/wyrm/xsl/response"
  xmlns:template="http://croftsoft.com/wyrm/xsl/template"
  xmlns:wyrm="http://croftsoft.com/apps/wyrm/xjc"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  version="1.0">

<output
  method="html"
  indent="no"
  doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" />

<variable name="content" select="/"/>

<template match="/wyrm:response">
  <choose>
    <when test="type/node()='createUser'">
      <apply-templates select="document('createuser.xml')/node()"/>
    </when>
    <when test="type/node()='destroyUser'">
      <choose>
        <when test="granted/node()='true'">
          <apply-templates select="document('createuser.xml')/node()"/>
        </when>
        <otherwise>
          <apply-templates select="document('response.xml')/node()"/>
        </otherwise>
      </choose>
    </when>
    <when test="type/node()='login'">
      <apply-templates select="document('login.xml')/node()"/>
    </when>
    <when test="type/node()='logout'">
      <choose>
        <when test="granted/node()='true'">
          <apply-templates select="document('login.xml')/node()"/>
        </when>
        <otherwise>
          <apply-templates select="document('response.xml')/node()"/>
        </otherwise>
      </choose>
    </when>
    <otherwise>
      <apply-templates select="@*|node()" />
    </otherwise>
  </choose>
</template>

<template match="/wyrm:responseAccount">
  <apply-templates select="document('account.xml')/node()"/>
</template>

<template match="/wyrm:responseState">
  <apply-templates select="document('template.xml')/node()"/>
</template>

<template match="account:*">
  <variable name="initial" select="current()"/>
  <for-each select="$content/wyrm:responseAccount/node()">
    <if test="local-name($initial)=local-name(current())">
      <choose>
        <when test="node()">
          <apply-templates select="node()" />
        </when>
        <otherwise>
          <apply-templates select="$initial/node()" />
        </otherwise>
      </choose>
    </if>
  </for-each>
</template>

<template match="response:*">
  <variable name="initial" select="current()"/>
  <for-each select="$content/wyrm:response/node()">
    <if test="local-name($initial)=local-name(current())">
      <choose>
        <when test="node()">
          <apply-templates select="node()" />
        </when>
        <otherwise>
          <apply-templates select="$initial/node()" />
        </otherwise>
      </choose>
    </if>
  </for-each>
</template>

<template match="template:content">
  <variable name="state"
    select="document(concat($content/wyrm:responseState/state/node(),'.xml'))"/>
  <choose>
    <when test="$state/xhtml:html/xhtml:body/node()">
      <apply-templates select="$state/xhtml:html/xhtml:body/node()"/>
    </when>
    <otherwise>
      <apply-templates select="@*|node()" />
    </otherwise>
  </choose>
</template>

<template match="template:title">
  <!-- Does this reload the document over the Internet? -->
  <variable name="state"
    select="document(concat($content/wyrm:responseState/state/node(),'.xml'))"/>
  <choose>
    <when test="$state/xhtml:html/xhtml:head/xhtml:title/node()">
      <apply-templates select="$state/xhtml:html/xhtml:head/xhtml:title/node()"/>
    </when>
    <otherwise>
      <apply-templates select="@*|node()" />
    </otherwise>
  </choose>
</template>

<template match="template:*">
  <variable name="initial" select="current()"/>
  <for-each select="$content/wyrm:responseState/node()">
    <if test="local-name($initial)=local-name(current())">
      <choose>
        <when test="node()">
          <apply-templates select="node()" />
        </when>
        <otherwise>
          <apply-templates select="$initial/node()" />
        </otherwise>
      </choose>
    </if>
  </for-each>
</template>

<template match="login:username">
  <choose>
    <when test="$content/wyrm:response/username/node()">
      <apply-templates select="$content/wyrm:response/username/node()" />
    </when>
    <otherwise>
      <apply-templates select="@*|node()" />
    </otherwise>
  </choose>
</template>

<template match="login:message">
  <choose>
    <when test="$content/wyrm:response/message/node()">
      <apply-templates select="$content/wyrm:response/message/node()" />
    </when>
    <otherwise>
      <apply-templates select="@*|node()" />
    </otherwise>
  </choose>
</template>

<template match="xhtml:input/@login:value">
  <choose>
    <when test="$content/wyrm:response/username/node()">
      <attribute name="xhtml:value">
        <value-of select="$content/wyrm:response/username/node()" />
      </attribute>
    </when>
  </choose>
</template>

<template match="xhtml:input/@createuser:value">
  <choose>
    <when test="$content/wyrm:response/username/node()">
      <attribute name="xhtml:value">
        <value-of select="$content/wyrm:response/username/node()" />
      </attribute>
    </when>
  </choose>
</template>

<template match="createuser:message">
  <choose>
    <when test="$content/wyrm:response/message/node()">
      <apply-templates select="$content/wyrm:response/message/node()" />
    </when>
    <otherwise>
      <apply-templates select="@*|node()" />
    </otherwise>
  </choose>
</template>

<template match="@*|*">
  <copy>
    <apply-templates select="@*|node()" />
  </copy>
</template>

</stylesheet>