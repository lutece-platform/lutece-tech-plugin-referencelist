<jsp:useBean id="referencelistmanageMonolingValues" scope="session" class="fr.paris.lutece.plugins.referencelist.web.MonolingValueJspBean" />
<% String strContent = referencelistmanageMonolingValues.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
