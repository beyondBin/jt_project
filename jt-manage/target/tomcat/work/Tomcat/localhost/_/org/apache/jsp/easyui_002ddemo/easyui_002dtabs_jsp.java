/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-06-29 06:13:46 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.easyui_002ddemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class easyui_002dtabs_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/js/jquery-easyui-1.4.1/themes/default/easyui.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/js/jquery-easyui-1.4.1/themes/icon.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/jt.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"/js/jquery-easyui-1.4.1/jquery.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/js/jquery-easyui-1.4.1/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"easyui-layout\">\r\n");
      out.write("\r\n");
      out.write("  <div data-options=\"region:'west',title:'菜单',split:true\" style=\"width:10%;\">\r\n");
      out.write("   \t\t<ul class=\"easyui-tree\">\r\n");
      out.write("   \t\t\t<li>\r\n");
      out.write("   \t\t\t<span>商品管理</span>\r\n");
      out.write("   \t\t\t<ul>\r\n");
      out.write("   \t\t\t<li><a onclick=\"addTab('商品新增','/item-add')\">商品新增</a></li>\r\n");
      out.write("   \t\t\t<li><a onclick=\"addTab('商品查询','/item-list')\">商品查询</a></li>\r\n");
      out.write("   \t\t\t<li><a onclick=\"addTab('商品更新','/item-update')\">商品更新</a></li>\r\n");
      out.write("   \t\t\t</ul>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("   \t\t\t<span>网站内容管理</span>\r\n");
      out.write("   \t\t\t<ul>\r\n");
      out.write("   \t\t\t<li>内容新增</li>\r\n");
      out.write("   \t\t\t<li>内容修改</li>\r\n");
      out.write("   \t\t\t</ul>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("   \t\t\r\n");
      out.write("   \t\t</ul>\r\n");
      out.write("   </div>\r\n");
      out.write("   <div id=\"tt\" class=\"easyui-tabs\" data-options=\"region:'center',title:'首页'\"></div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("function addTab(title, url){  \r\n");
      out.write("    if ($('#tt').tabs('exists', title)){  \r\n");
      out.write("        $('#tt').tabs('select', title);  \r\n");
      out.write("    } else {  \r\n");
      out.write("        var content = '<iframe scrolling=\"auto\" frameborder=\"0\"  src=\"'+url+'\" style=\"width:100%;height:100%;\"></iframe>';  \r\n");
      out.write("        $('#tt').tabs('add',{  \r\n");
      out.write("            title:title,  \r\n");
      out.write("            content:content,  \r\n");
      out.write("            closable:true  \r\n");
      out.write("        });  \r\n");
      out.write("    }  \r\n");
      out.write("} \r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
