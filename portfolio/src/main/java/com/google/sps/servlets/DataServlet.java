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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import usercomments.Comments;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

    UserService userService = UserServiceFactory.getUserService();

    //Get input from form.
    String text = request.getParameter("comments");
    String email = userService.getCurrentUser().getEmail();

    //Create new entity.
    Entity taskEntity = new Entity("Comments");
    taskEntity.setProperty("message", text);
    taskEntity.setProperty("email", email);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);

    // Respond with the result.
    response.sendRedirect("/status");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String comment_null = request.getParameter("limit");
    
    Query query = new Query("Comments");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Comments> messages = new ArrayList<Comments>();

    for (Entity entity : results.asIterable()) {
      String message = (String) entity.getProperty("message");
      String email = (String) entity.getProperty("email");
      Comments userInfo =  new Comments(message, email);

        if(comment_null != null){
         int comment_num = (Integer.valueOf(request.getParameter("limit")));
          if(messages.size() == comment_num){
            break;
          }
        }
      messages.add(userInfo);
    }

    //Convert arraylist data into json
    String json = convertToJsonUsingGson(messages);
   
    //Send JSON as response to the client
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJsonUsingGson(List<Comments> messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
}

