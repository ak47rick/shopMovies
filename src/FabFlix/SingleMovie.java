package FabFlix;
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleMovie extends HttpServlet
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
		String title = (String) request.getParameter("title");
    	
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
              String query = "select * from genres_in_movies m left join genres g on g.g_id = m.genre_id left join movies s on s.m_id = m.movie_id where title=\""+ title +"\"";
              String query2 = query;
              String query3 = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id where title=\""+ title +"\"";           
              query+=" group by title";
              ResultSet rs = statement.executeQuery(query);
              //ResultSet rs2 = statement2.executeQuery(query2);
              ResultSet rs3 = statement3.executeQuery(query3);


              out.println("<TABLE border>");

              // Iterate through each row of rs
	          out.println("<tr>" +
	    		  "<td>" + "Movie ID" + "</td>" +
	    		  "<td>" + "Title" + "</td>" +
	    		  "<td>" + "Year" + "</td>" +
	    		  "<td>" + "Director" + "</td>" +
	    		  "<td>" + "Genres" + "</td>" +
	    		  "<td>" + "Stars" + "</td>" +
	    		  "<td>" + "Trailer" + "</td>" +
	    		  "<td>" + "Options" + "</td>" +
	    		  "</tr>");
	          String prev="";
	          int countCol=0;
              while (rs.next())
              {
                  String m_ID = rs.getString("m_id");
                  String m_TITLE = rs.getString("title");
                  String m_YEAR = rs.getString("year");
                  String m_DIRECTOR = rs.getString("director");
                  String m_POSTER = rs.getString("banner_url");
                  String m_TRAILER = rs.getString("trailer_url");
                  
                  out.println("<img src=\""+m_POSTER+"\" width=200px; height=450px;"); //hard coding post size since its really big
                  out.println("<tr>" +
                              "<td>" + m_ID + "</td>" +
                              "<td><a href=\"SingleMovie?title="+m_TITLE+"\">" + m_TITLE + "</a></td>" +
                              "<td>" + m_YEAR  + "</td>" +
                              "<td>" + m_DIRECTOR + "</td>");// +
                              //"</tr>");
                  prev = m_TITLE;
                  out.println("<td>");

                  //print each genre for that row
                  String savedQuery2=query2;
                  query2+=" and m.movie_id="+m_ID+" group by name";
                  ResultSet rs2 = statement2.executeQuery(query2);
                  
                  while(rs2.next())
                  {
                    String m_GENRE = rs2.getString("name");
                    out.println("<li>" + m_GENRE + "</li>"); 
                  }
                  query2=savedQuery2;

                  countCol=0;
                 
                  out.println("</td>");
                  out.println("<td>");
                  
                  //Print stars
                  while(rs3.next())
                  {
                    String m_FIRST = rs3.getString("first_name");
                    String m_LAST = rs3.getString("last_name");
                    String s_ID = rs3.getString("s_id");
                    out.println("<li><a href=\"SingleStar?id="+s_ID+"\">" + m_FIRST +" "+ m_LAST + "</a></li>");                 
                  }
                  out.println("</td>");
                  out.println("<td>");                 
                  out.println("<a href=\""+m_TRAILER+"\">" + m_TRAILER + "</a>");
                  out.println("</td>");
                  out.println("<td><a href=\"addToCart?m_id=" + m_ID +"\"> Add To Cart </a>");
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

