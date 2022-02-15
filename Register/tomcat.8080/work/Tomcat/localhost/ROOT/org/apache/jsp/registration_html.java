/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.23
 * Generated at: 2022-02-14 09:34:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class registration_html extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
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

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("    <title> Register yourself</title>\r\n");
      out.write("\r\n");
      out.write("    <link rel=\"icon\" href=\"data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>â½</text></svg>\">\r\n");
      out.write("\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"style.css\">\r\n");
      out.write("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n");
      out.write("    <script src=\"main.js\"></script>\r\n");
      out.write("    <script src=\"formvalidation.js\"></script>\r\n");
      out.write("    <script src=\"formVaidationResponse.js\"></script>\r\n");
      out.write("    <script src=\"validationsmessages.js\"></script>\r\n");
      out.write("    <script src=\"country.js\"></script>\r\n");
      out.write("    <script src=\"resources.js\"></script>\r\n");
      out.write("    <script src=\"resourceResponse.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("    <div class=\"container\" style=\"background: aliceblue;\">\r\n");
      out.write("\r\n");
      out.write("        <div class=\"row py-2 my-2\">\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"row align-items-center justify-content-center\">\r\n");
      out.write("            <div class=\"col-md\"></div>\r\n");
      out.write("            <div class=\"col-md\"><h4><b>Football Registration Form</b></h4></div>\r\n");
      out.write("            <div class=\"col-md\"></div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"row\">            \r\n");
      out.write("            <div class=\"container\">\r\n");
      out.write("                <form class=\"needs-validation\" onsubmit=\"validateForm(event)\" id=\"registration-form\" style=\"background: white;\" novalidate>\r\n");
      out.write("\r\n");
      out.write("                    <div class=\"row border border-bottom-0 mx-0\">\r\n");
      out.write("                        <div class=\"col-md border-end py-2\">\r\n");
      out.write("                            <label for=\"userName\" class=\"form-label\">USER NAME<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                            <div class=\"input-group\">\r\n");
      out.write("                                <input type=\"text\"  inputmode=\"text\" autofocus class=\"form-feild form-control\" aria-describedby=\"retriveUserDataBtn\" placeholder=\"Please choose a username\" id=\"userName\" name=\"username\" required>\r\n");
      out.write("                                <button type=\"button\" id=\"retriveUserDataBtn\" class=\"btn btn-primary\" title=\"Click to fetch user data if username exists.\" name=\"retriveuserdata\" disabled onclick=\"fetchUserDataByUserName()\">Fetch</button>                            \r\n");
      out.write("                                \r\n");
      out.write("                                <div class=\"feedback valid-feedback\">                                \r\n");
      out.write("                                </div>\r\n");
      out.write("                                <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                    Please provide a valid username.\r\n");
      out.write("                                </div>\r\n");
      out.write("                            </div>                           \r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md border-end py-2\">\r\n");
      out.write("                            <label for=\"firstName\" class=\"form-label\">FIRST NAME<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                            <input type=\"text\"  inputmode=\"text\" class=\"form-feild form-control\" id=\"firstName\" placeholder=\"What is your first name?\" name=\"firstname\" required>\r\n");
      out.write("                            <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                Please provide a first name.\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md py-2\">\r\n");
      out.write("                            <label for=\"lastName\" class=\"form-label\">LAST NAME</label>\r\n");
      out.write("                            <input type=\"text\"  inputmode=\"text\" class=\"form-feild form-control\" id=\"lastName\" placeholder=\"What is your last name?\" name=\"lastname\">\r\n");
      out.write("                            <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                Please provide a last name.\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("\r\n");
      out.write("                    <div class=\"row border border-bottom-0 mx-0\">                        \r\n");
      out.write("                        <div class=\"col-md py-2 border-end\">                         \r\n");
      out.write("                            \r\n");
      out.write("                            <label for=\"phoneNumer\" class=\"form-label\">PHONE NUMBER<sup style=\"color: red;\">*</sup></label>                                                           \r\n");
      out.write("                            <div class=\"input-group\">                                                        \r\n");
      out.write("                                <select class=\"form-feild form-select w-25\" name=\"countrydailcodeselect\" id=\"countryDailCodeSelect\" required aria-describedby=\"countrydailcodeselect\">\r\n");
      out.write("                                    <option selected disabled value=\"0\">Choose...</option>\r\n");
      out.write("                                </select>                                                      \r\n");
      out.write("                                <input type=\"text\" inputmode=\"numeric\" class=\"form-feild form-control w-75\" id=\"phoneNumer\" placeholder=\"What is best # to reach you?\" name=\"phonenumber\" aria-describedby=\"phonenumber\" required>\r\n");
      out.write("                                <div id=\"phonenumber\" class=\"feedback invalid-feedback\">\r\n");
      out.write("                                    Please provide a valid phone number.\r\n");
      out.write("                            </div>      \r\n");
      out.write("                        </div>                    \r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md border-end py-2\">\r\n");
      out.write("                            <div class=\"row justify-content-between\">\r\n");
      out.write("                                <div class=\"col\">\r\n");
      out.write("                                    <label for=\"email\" class=\"form-label\">EMAIL<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                                </div>\r\n");
      out.write("                                <div class=\"col\">\r\n");
      out.write("                                    <div class=\"form-check form-switch\">\r\n");
      out.write("                                        <input class=\"form-check-input float-end primaryBackgroundColor\" name=\"flexSwitchforemail\" onclick=\"toggleEmailControl(event);\" type=\"checkbox\" role=\"switch\" id=\"flexSwitchForEmail\">\r\n");
      out.write("                                    </div>\r\n");
      out.write("                                </div>    \r\n");
      out.write("                            </div>                                                \r\n");
      out.write("                            \r\n");
      out.write("                            <input type=\"email\" class=\"form-feild form-control\" id=\"email\" placeholder=\"What is your e-mail address?\" name=\"email\" required disabled>\r\n");
      out.write("                            <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                Please provide a valid email.\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md py-2\">\r\n");
      out.write("                            <label for=\"ageGroup\" class=\"form-label\">Age group<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                            <select class=\"form-feild form-select\" name=\"agegroup\" id=\"ageGroup\" required>\r\n");
      out.write("                                <option selected disabled value=\"0\">Select age group</option>\r\n");
      out.write("                                <option value=\"1\">16-19 years</option>\r\n");
      out.write("                                <option value=\"2\">20-23 years</option>\r\n");
      out.write("                                <option value=\"3\">24-27 years</option> \r\n");
      out.write("                                <option value=\"3\">28-32 years</option> \r\n");
      out.write("                              </select>\r\n");
      out.write("                            <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                Please select a valid age group.\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("\r\n");
      out.write("                    <div class=\"row border mx-0\">\r\n");
      out.write("                        <div class=\"col-md border-end col-sm-4 py-2\">\r\n");
      out.write("                            <label for=\"desiredTeam\" class=\"form-label\">DESIRED TEAM<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                            <div class=\"form-check\" id=\"desiredTeam\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"radio\" name=\"desiredteamradios\" id=\"chelseaRadio\" value=\"1\" aria-describedby=\"desiredteamradios\" >\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"chelseaRadio\">\r\n");
      out.write("                                  CHELSEA\r\n");
      out.write("                                </label>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"form-check\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"radio\" name=\"desiredteamradios\" id=\"manchasterUnitedRadio\" value=\"2\" aria-describedby=\"desiredteamradios\" >\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"manchasterUnitedRadio\">\r\n");
      out.write("                                  MANCHESTER UNITED\r\n");
      out.write("                                </label>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"form-check\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"radio\" name=\"desiredteamradios\" id=\"liverpoolRadio\" value=\"3\" aria-describedby=\"desiredteamradios\" >\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"liverpoolRadio\">\r\n");
      out.write("                                  LIVERPOOL\r\n");
      out.write("                                </label>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"form-check\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"radio\" name=\"desiredteamradios\" id=\"barcelonaRadio\" value=\"4\" aria-describedby=\"desiredteamradios\" >\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"barcelonaRadio\">\r\n");
      out.write("                                   BARCELONA\r\n");
      out.write("                                </label>\r\n");
      out.write("                                <div id=\"desiredteamradios\" class=\"feedback invalid-feedback\"></div>\r\n");
      out.write("                            </div>                            \r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md border-end col-sm-4 py-2\">\r\n");
      out.write("                            <label for=\"firstName\" class=\"form-label\">DESIRED POSITION<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                              <div class=\"form-check\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"checkbox\" name=\"desiredpositionchecks\" value=\"1\" id=\"goalKeeperCheck\" aria-describedby=\"desiredpositionchecks\">\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"goalKeeperCheck\">\r\n");
      out.write("                                  GOAL KEEPER\r\n");
      out.write("                                </label>                                \r\n");
      out.write("                              </div>\r\n");
      out.write("                              <div class=\"form-check\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"checkbox\" name=\"desiredpositionchecks\" value=\"2\" id=\"offensiveCheck\" aria-describedby=\"desiredpositionchecks\">\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"offensiveCheck\">\r\n");
      out.write("                                  OFFENSIVE\r\n");
      out.write("                                </label>\r\n");
      out.write("                              </div>\r\n");
      out.write("                              <div class=\"form-check\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"checkbox\" name=\"desiredpositionchecks\" value=\"3\" id=\"defenseCheck\" aria-describedby=\"desiredpositionchecks\">\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"defenseCheck\">\r\n");
      out.write("                                  DEFENSE\r\n");
      out.write("                                </label>\r\n");
      out.write("                              </div>\r\n");
      out.write("                              <div class=\"form-check\">\r\n");
      out.write("                                <input class=\"form-feild form-check-input\" type=\"checkbox\" name=\"desiredpositionchecks\" value=\"4\" id=\"receiverCheck\" aria-describedby=\"desiredpositionchecks\">\r\n");
      out.write("                                <label class=\"form-check-label\" for=\"receiverCheck\">\r\n");
      out.write("                                  RECEIVER\r\n");
      out.write("                                </label>\r\n");
      out.write("                                <div id=\"desiredpositionchecks\" class=\"feedback invalid-feedback\"></div>\r\n");
      out.write("                              </div>            \r\n");
      out.write("                            \r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md-5 col-md border-end py-2\">\r\n");
      out.write("                            <label for=\"address\" class=\"form-label\">ADDRESS</label>\r\n");
      out.write("                            <textarea class=\"form-feild form-control\" name=\"address\" id=\"address\" rows=\"3\"></textarea>                            \r\n");
      out.write("                            <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                Please provide a valid address.\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md-3 col-md py-2\">\r\n");
      out.write("                            <label for=\"pincode\" class=\"form-label\">PIN CODE</label>\r\n");
      out.write("                            <input type=\"text\" inputmode=\"numeric\" autofocus class=\"form-feild form-control\" id=\"pinCode\" placeholder=\"Enter your PIN code?\" name=\"pincode\">                    \r\n");
      out.write("                            <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                Please provide a valid pincode.\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("\r\n");
      out.write("                    <div class=\"row mx-0\">\r\n");
      out.write("                        <div class=\"col\"></div>\r\n");
      out.write("                        <div class=\"col-md-5 p-1 m-1\">\r\n");
      out.write("                            <div class=\"col-md col-xl p-1 m-1\">\r\n");
      out.write("                                <label for=\"countrySelect\" class=\"form-label\">COUNTRY<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                                <select class=\"form-feild form-select\" name=\"countryselect\" id=\"countrySelect\" required>\r\n");
      out.write("                                    <option selected disabled value=\"0\">Select the country</option>                                    \r\n");
      out.write("                                  </select>\r\n");
      out.write("                                <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                    Please select a valid country. \r\n");
      out.write("                                </div>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"col-md col-xl p-1 m-1\">\r\n");
      out.write("                                <label for=\"stateSelect\" class=\"form-label\">STATE<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                                <select class=\"form-feild form-select\" name=\"stateselect\" id=\"stateSelect\" required>\r\n");
      out.write("                                    <option selected disabled value=\"0\">Select the state</option>                                    \r\n");
      out.write("                                </select>\r\n");
      out.write("                                <div class=\"feedback valid-feedback\">                                    \r\n");
      out.write("                                </div>\r\n");
      out.write("                                <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                    Please select a valid state. \r\n");
      out.write("                                </div>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"col-md col-xl p-1 m-1\">\r\n");
      out.write("                                <label for=\"citySelect\" class=\"form-label\">CITY<sup style=\"color: red;\">*</sup></label>\r\n");
      out.write("                                <select class=\"form-feild form-select\" name=\"cityselect\" id=\"citySelect\" required>\r\n");
      out.write("                                    <option selected disabled value=\"0\">Select the city</option>                                    \r\n");
      out.write("                                </select>\r\n");
      out.write("                                <div class=\"feedback valid-feedback\">                                    \r\n");
      out.write("                                </div>\r\n");
      out.write("                                <div class=\"feedback invalid-feedback\">\r\n");
      out.write("                                    Please select a valid city. \r\n");
      out.write("                                </div>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col\"></div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    \r\n");
      out.write("                    <div class=\"row p-2 m-2\">\r\n");
      out.write("                        <div class=\"col\"></div>\r\n");
      out.write("                        <div class=\"col-md-4 col-md text-center\">\r\n");
      out.write("                            <button type=\"submit\" id=\"form-submit\" name=\"form-submit\" class=\"btn btn-primary\" disabled >Phewww! Let's submit it</button>\r\n");
      out.write("\r\n");
      out.write("                            <div class=\"feedback valid-feedback\">                                    \r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"feedback invalid-feedback\">                                \r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col\"></div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </form>                \r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js\" integrity=\"sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13\" crossorigin=\"anonymous\"></script>    \r\n");
      out.write("    \r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
