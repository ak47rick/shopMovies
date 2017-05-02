package FabFlix;
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleStar extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
		/*if(request.getParameter("title")!= null){			
			//HttpSession s = request.getSession(false);
			//s.invalidate();
			response.sendRedirect("Index.jsp");	
			return;
		}*/
		String starID = (String) request.getParameter("id");
    	
        String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
      //  String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("menu.jsp").include(request, response);
        out.println("<HTML><HEAD><TITLE>Search Movie</TITLE></HEAD>");
        out.println("<BODY><H1>Single Movie</H1>");


        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              Statement statement = dbcon.createStatement();
              Statement statement2 = dbcon.createStatement();
              Statement statement3 = dbcon.createStatement();
              //String query = "SELECT * from movies where title='"+ title +"'";
              //String query = "select * from genres_in_movies m left join genres g on g.g_id = m.genre_id left join movies s on s.m_id = m.movie_id where s_id='"+ starID +"'";             
              String query = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id where s_id='"+ starID +"'";           
              String query2 = query;
              query+=" group by first_name";
              ResultSet rs = statement.executeQuery(query);
              //ResultSet rs2 = statement2.executeQuery(query2);
             // ResultSet rs3 = statement3.executeQuery(query3);


              out.println("<TABLE border>");

              // Iterate through each row of rs
              out.println("<tr>" +
	    		  		"<td>" + "Star ID" + "</td>" +
	    		  		"<td>" + "First Name" + "</td>" +
	    		  		"<td>" + "Last Name" + "</td>" +
	    		  		"<td>" + "DOB" + "</td>" +
	    		  		"<td>" + "Movies" + "</td>" +
            		  	"</tr>");
	      	  String prev="";
              int countCol=0;
              while (rs.next())
              {
                  String s_ID = rs.getString("s_id");
                  String s_FIRST = rs.getString("first_name");
                  String s_LAST = rs.getString("last_name");
                  String s_DOB = rs.getString("DOB");
                  String s_PHOTO = rs.getString("photo_url");
                  String m_ID = rs.getString("m_id");
                  
                  out.println("<img src=\""+s_PHOTO+"\" width=200px; height=450px;"); //hard coding post size since its really big
                  out.println("<tr>" +
                              "<td>" + s_ID + "</td>" +
                              "<td>" + s_FIRST  + "</td>" +
                              "<td>" + s_LAST + "</td>" +
                              "<td>" + s_DOB + "</td>");// +
                              //"</tr>");
                  prev = s_ID;
                  out.println("<td>");
                 
                  //out.println(query2);
                  String savedQuery2=query2;
                  ResultSet rs2 = statement2.executeQuery(query2);
                  //print each genre for that row
                  while(rs2.next())
                  {
                    String testTitle = rs2.getString("s_id");
                    String m_MOVIE = rs2.getString("title");
                   // "<td><a href=\"SingleMovie?title="+m_TITLE+"\">" + m_TITLE + "</a></td>" +
                    out.println("<li><a href=\"SingleMovie?title="+m_MOVIE+"\">" + m_MOVIE + "</a></li> ");      
                  }
                  query2=savedQuery2;
                  countCol=0;
                  out.println("</td></tr>");
                  
              }
              out.println("</TABLE>");

              rs.close();
              statement.close();
              dbcon.close();
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }
    
    /* public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
	} */
}

