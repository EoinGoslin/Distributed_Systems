/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.82
 * Generated at: 2018-10-12 09:02:05 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");

    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().equalsIgnoreCase("locale")) {
                if (cookies[i].getValue() != null) {
                    session.setAttribute("locale", cookies[i].getValue());
                    response.sendRedirect("home.jsp");
                    return;
                }
            }
        }
    }
    if (request.getMethod().equalsIgnoreCase("post")) {
        String lang = request.getParameter("language");
        String checked=request.getParameter("setcookie");
        if (lang != null) {
            session.setAttribute("locale", lang);
            if (checked != null && checked.equalsIgnoreCase("on")) {
                Cookie cookie = new Cookie("locale", lang);
                cookie.setMaxAge(Integer.MAX_VALUE);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            response.sendRedirect("home.jsp");
        }
    }

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("        <title>Welcome to Apache jUDDI</title>\r\n");
      out.write("        <meta charset=\"utf-8\">\r\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("        <meta name=\"description\" content=\"Apache jUDDI\">\r\n");
      out.write("        <meta name=\"author\" content=\"Apache Software Foundation\">\r\n");
      out.write("\r\n");
      out.write("        <!-- Le styles -->\r\n");
      out.write("        <link href=\"css/bootstrap.css\" rel=\"stylesheet\">\r\n");
      out.write("        <link rel=\"shortcut icon\" href=\"favicon.ico\" />\r\n");
      out.write("        <link href=\"css/bootstrap-responsive.css\" rel=\"stylesheet\">\r\n");
      out.write("\r\n");
      out.write("        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->\r\n");
      out.write("        <!--[if lt IE 9]>\r\n");
      out.write("          <script src=\"js/html5shiv.js\"></script>\r\n");
      out.write("        <![endif]-->\r\n");
      out.write("\r\n");
      out.write("        <!-- Fav and touch icons -->\r\n");
      out.write("\r\n");
      out.write("        <link rel=\"shortcut icon\" href=\"ico/favicon.png\">\r\n");
      out.write("        <style type=\"text/css\">\r\n");
      out.write("            body {\r\n");
      out.write("                padding: 0px 0px 0px 0px;\r\n");
      out.write("                margin: 0px 0px 0px 0px;\r\n");
      out.write("            }\r\n");
      out.write("        </style>\r\n");
      out.write("\r\n");
      out.write("    </head>\r\n");
      out.write("    <body>\r\n");
      out.write("        <div style=\"width:100%; height: 100%; position:absolute; text-align: center; vertical-align: middle; padding: 0px; margin: 0px; \r\n");
      out.write("             background-image: url('img/bluemarble2.jpg'); background-repeat: no-repeat; background-position-x: center;\r\n");
      out.write("             background-position-y: center; background-size: cover\">\r\n");
      out.write("            <div style=\"color: black; background-color: whitesmoke; \r\n");
      out.write("                 background: rgb(235, 235, 235); /* Fall-back for browsers that don't\r\n");
      out.write("                                    support rgba */\r\n");
      out.write("                 background: rgba(235, 235, 235, .7);width:60%; position: relative; left:20%; top:25%; height:50%; vertical-align: middle\">\r\n");
      out.write("                <br><br>\r\n");
      out.write("                <h1>Welcome to jUDDI</h1>\r\n");
      out.write("                <form method=\"POST\">\r\n");
      out.write("\r\n");
      out.write("                    <select id=\"language\" name=\"language\" >\r\n");
      out.write("                        <option value=\"en\" selected>English</option>\r\n");
      out.write("                        <option value=\"es\" >Español</option>\r\n");
      out.write("                    </select>\r\n");
      out.write("                    <br>\r\n");
      out.write("                    <input type=\"checkbox\" name=\"setcookie\" checked> Remember my decision<br>\r\n");
      out.write("                    <button type=\"submit\" value=\"Go\" class=\"btn btn-primary\">Go</button>\r\n");
      out.write("                </form>\r\n");
      out.write("                <b>We welcome help internationalizing jUDDI!</b>\r\n");
      out.write("                <noscript>Your browser does not support JavaScript! Functionality will be so severely reduced, that you might as well give up, sorry!</noscript>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
