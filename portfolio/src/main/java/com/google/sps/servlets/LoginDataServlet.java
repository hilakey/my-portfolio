// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//This servlet returns the login status of the user.
@WebServlet("/status")
public class LoginDataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    UserService userService = UserServiceFactory.getUserService();
    response.getWriter().println("<h2 style=\"color: darkslategrey;font-size: 50px;text-align: center;height: 50px;background-color: mediumturquoise;margin-bottom: 15px; \">Comment</h2>");
    String returnHomeURL = "index.html";
    if (userService.isUserLoggedIn()) {
      String urlToRedirectToAfterUserLogsOut = "/status";
      String userEmail = userService.getCurrentUser().getEmail();
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
      
      response.getWriter().println("<div style=\"text-align:center;display:block; font-size:30px;\">");
      response.getWriter().println("<p>Hello " + userEmail + "!</p>");
      response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
      response.getWriter().println("<p><a id=\"hi\"href=\"" + returnHomeURL + "\">Home</a></p>");
      response.getWriter().println("</div>");

      //If user is logged in display the comments form
      response.getWriter().println("<div class=\"user_data\" style=\"display: block;text-align: center;font-size: 30px;\">");
      response.getWriter().println("<form action=\"/data\" method=\"POST\">");
      response.getWriter().println("Enter <em>anything</em>  you want, seriously.<br>");
      response.getWriter().println("<textarea name=\"comments\" size=\"width: 150px; height:100px;\"> Write anything here!</textarea><br>");
      response.getWriter().println("<input type=\"submit\" value=\"Submit\"  style=\"height:35px; width:70px\" onchange=\"getDataResponseUsingArrowFunctions();return false;(this.value)\">");
      response.getWriter().println("</form>");
      response.getWriter().println("</div>");
            
    } else {
      String urlToRedirectToAfterUserLogsIn = "/status";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

      response.getWriter().println("<div style=\"text-align:center;display:block; font-size:30px;\">");
      response.getWriter().println("<p>Hello stranger.</p>");
      response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
      response.getWriter().println("<p><a href=\"" + returnHomeURL + "\">Home</a></p>");
      response.getWriter().println("</div>");
    }
  }
}