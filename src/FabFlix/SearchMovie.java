package FabFlix;
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SearchMovie extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
    	if(request.getParameter("title")== null&&request.getParameter("director")== null&&request.getParameter("first")== null&&request.getParameter("last")== null&&request.getParameter("year")== null&&request.getParameter("viewsize")== null){			
			response.sendRedirect("Search.jsp");	
			return;
		}
    	
        String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
      //  String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("menu.jsp").include(request, response);

        out.println("<HTML><HEAD><TITLE>Search Movie Results</TITLE></HEAD>");
        out.println("<BODY><H2>Search Movie Results</H2>");
        
        String sortType ="";
        sortType="asc";
        if(request.getParameter("sort")!=null)
        	sortType=request.getParameter("sort");
        if(request.getParameter("sortYear")!=null)
        	sortType=request.getParameter("sortYear");
        
        int limitSize = 10; //default limit search size = 10
        int offsetSize = 0; // offset starts at 0
        int offsetPrev=0;
        
        
        if(request.getParameter("viewsize")!=null)
        	limitSize=Integer.parseInt(request.getParameter("viewsize"));
        //out.println("OFF SIZE: "+offsetSize);
        
        int offsetNext = offsetSize + limitSize; // 0 + 10
        
        if(request.getParameter("offset")!=null)
        	offsetSize=Integer.parseInt(request.getParameter("offset"));
        if(request.getParameter("limit")!=null)
        	limitSize=Integer.parseInt(request.getParameter("limit"));
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              Statement statement = dbcon.createStatement();
              Statement statement2 = dbcon.createStatement();
              Statement statement3 = dbcon.createStatement();
	          String title = request.getParameter("title");
              String director = request.getParameter("director");
              String first = request.getParameter("first");
              String last = request.getParameter("last");
              String yearString = request.getParameter("year");        
              
              request.setAttribute("viewsize",title);
              
              
              int year=0;
              if(!yearString.isEmpty())
                year = Integer.parseInt(yearString);
              	
              //String query = "SELECT * from movies ";
             
              String query = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id left join genres_in_movies x on x.movie_id = s.m_id left join genres y on y.g_id = x.genre_id where ";
              String query2 = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id left join genres_in_movies x on x.movie_id = s.m_id left join genres y on y.g_id = x.genre_id where ";
            // String query2 = "select * from genres_in_movies m left join genres g on g.g_id = m.genre_id left join movies s on s.m_id = m.movie_id ";
              String query3 = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id where ";

             // out.println(query);

              // if someone entered title add it
              int countArgs =0;
              if(!title.isEmpty())
              {
                if(countArgs > 0)
                {
                  query+=" AND ";
              
                }
               // query+="title = '" + title + "'";
                query+=" title LIKE \"%"+title+"%\"";
               
                countArgs++;
              }
              if(!yearString.isEmpty())
              {
                if(countArgs > 0)
                {
                  query+=" AND ";
                 
                }

                query+=" year LIKE '%"+year+"%'";
               


                countArgs++;
              }
              if(!director.isEmpty())
              {
                if(countArgs > 0)
                {
                  query+=" AND ";
                  
                }

                 query+=" director LIKE '%"+director+"%'";
                 
                
                //query+="director = '" + director + "'";

                countArgs++;
              }
              if(!first.isEmpty())
              {
                if(countArgs > 0)
                {
                  query+=" AND ";
                 
                }

                query+=" first_name LIKE '%"+first+"%'";
                
                

                countArgs++;
              }
              if(!last.isEmpty())
              {
                if(countArgs > 0 )
                {
                  query+=" AND ";
                 
                }

                query+=" last_name LIKE '%"+last+"%'";
               
                

                countArgs++;
              }
              
             
             // out.println(query2);
             // out.println(query3);
       	
              query+="group by title";
              
              
              if(request.getParameter("sort")!=null&&!request.getParameter("sort").isEmpty())
            	  query+=" order by title "+request.getParameter("sort");
              if(request.getParameter("sortYear")!=null&&!request.getParameter("sortYear").isEmpty())
            	  query+=" order by year "+request.getParameter("sortYear");
              
              /*if(request.getParameter("offset")!=null&&!request.getParameter("offset").isEmpty())
            	  query+=" limit "+request.getParameter("next")+" offset "+request.getParameter("offset");
              if(request.getParameter("prev")!=null&&!request.getParameter("prev").isEmpty())
            	  query+=" limit "+request.getParameter("prev");*/
              
              // first query
              query+=" limit "+limitSize;
              query+=" offset "+offsetSize;
              //out.println(query);              
            
              ResultSet rs = statement.executeQuery(query);

              if(request.getParameter("sort")!=null&&request.getParameter("sort").equals("asc"))
            	  sortType="desc";
              if(request.getParameter("sortYear")!=null&&request.getParameter("sortYear").equals("asc"))
            	  sortType="desc";
              if(request.getParameter("sort")!=null&&request.getParameter("sort").equals("desc"))
            	  sortType="asc";
              if(request.getParameter("sortYear")!=null&&request.getParameter("sortYear").equals("desc"))
            	  sortType="asc";
              
              out.println("<TABLE class=\"searchresults\" >");

              // Iterate through each row of rs
              out.println("<tr class=\"results\">" +
	    		  "<th><h4>" + "Movie ID" + "</th></h4>" +
	    		  "<th ><h4><a   class=\"results\" name=\"viewsize\" href=\"SearchMovie?sort="+sortType+"&title="+title+"&director="+director+"&year="+yearString+"&first="+first+"&last="+last+"\"> Title </a></th></h4>" +
	    		  "<th  ><h4><a  class=\"results\" name=\"viewsize\" href=\"SearchMovie?sortYear="+sortType+"&title="+title+"&director="+director+"&year="+yearString+"&first="+first+"&last="+last+"\"> Year </a></th></h4>" +
	    		  //"<td>" + "Title" + "</td>" +
	    		 // "<td>" + "Year" + "</td>" +
	    		  "<th><h4>" + "Director" + "</th></h4>" +
	    		  "<th><h4>" + "Genres" + "</th></h4>" +
	    		  "<th><h4>" + "Stars" + "</th></h4>" +
	    		  "<th><h4>" + "Options" + "</th></h4>" +
	    		  "</tr>");
	      		String prev="";
	      		int countCol=0;
        
        		//print movie info
              while (rs.next())
              {
                  String m_ID = rs.getString("m_id");
                  String m_TITLE = rs.getString("title");
                  String m_YEAR = rs.getString("year");
                  String m_DIRECTOR = rs.getString("director");
                  out.println("<tr>" +
                              "<td>" + m_ID + "</td>" +
                              "<td><a href=\"SingleMovie?title="+m_TITLE+"\">" + m_TITLE + "</a></td>" +
                              "<td>" + m_YEAR  + "</td>" +
                              "<td>" + m_DIRECTOR + "</td>");
                  prev = m_TITLE;
                  out.println("<td>");
                 
                  
                 
                  //print each genre for that row
                  String savedQuery2=query2;
                  query2+="m.movie_id="+m_ID+" group by name";
                  //out.println("QUERY 2: "+query2);
                  ResultSet rs2 = statement2.executeQuery(query2);
                  
                  //Printing the list of genres
                  while(rs2.next())
                  {
                	  String m_GENRE = rs2.getString("name");
                      out.println("<li>" + m_GENRE + "</li>");           
                  }
                  query2=savedQuery2;
                  
                  countCol=0;
                  out.println("</td>");

                  out.println("<td>");
                  //print list of stars
                  
                  ////
                  String savedQuery3=query3;
                  if(!first.isEmpty()&&!last.isEmpty())
                	  query3+="m.movie_id="+m_ID+" group by first_name";
                  else if(!last.isEmpty())
                	  query3+="m.movie_id="+m_ID+" group by last_name";
                  else if(!first.isEmpty())
                	  query3+="m.movie_id="+m_ID+" group by first_name";      
                  else
                	  query3+="m.movie_id="+m_ID+" group by first_name";
                  
                
                  ResultSet rs3 = statement3.executeQuery(query3);
                  
                  //Printing the list of stars
                  while(rs3.next())
                  {                	                	  
                      String m_FIRST = rs3.getString("first_name");
                      String m_LAST = rs3.getString("last_name");
                      String s_ID = rs3.getString("s_id");
                      //out.println("ID HERE: "+s_ID);
                      // "<td><a href=\"SingleMovie?title="+m_TITLE+"\">" + m_TITLE + "</a></td>"
                      out.println("<li><a href=\"SingleStar?id="+s_ID+"\">" + m_FIRST +" "+ m_LAST + "</a></li>"); 
                  
                  }
                  query3=savedQuery3;
				  
				  out.println();
                  out.println("</td>");          
                  out.println("<td><a href=\"addToCart?m_id=" + m_ID +"\"> Add To Cart </a></td>");
                  out.println("</tr>");
              }


              out.println("</TABLE>");
              
              
              //setting values for limit and offset sizes for next/prev buttons
              offsetNext = offsetSize + limitSize; // 0 + 10        
              if(offsetSize==0)
            	  offsetPrev = 0; // 10 - 10
              else
            	  offsetPrev = offsetSize - limitSize;
              
              //out.println("<a href=\"SearchMovie?m_id=" + m_ID +"\"> Add To Cart </a>");
              out.println("<table>");

              out.println("<td><a  href=\"SearchMovie?limit="+limitSize+"&offset="+offsetPrev+"&title="+title+"&director="+director+"&year="+yearString+"&first="+first+"&last="+last+"\"> Prev </a></td>");
              out.println("<td>    </td>");
              out.println("<td><a id=\"link1\"  href=\"SearchMovie?limit="+limitSize+"&offset="+offsetNext+"&title="+title+"&director="+director+"&year="+yearString+"&first="+first+"&last="+last+"\"> Next </a></td>");    
              
              out.println("<form action=\"SearchMovie\">");
              
              out.println("<td><select id=\"mySelect\" name=\"viewsize\"><option value=\"10\">10</option><option value=\"25\">25</option><option value=\"50\">50</option><option value=\"100\">100</option></select></td>");
              out.println("<input type=\"hidden\" name=\"title\" value=\""+title+"\">");
              out.println("<input type=\"hidden\" name=\"director\" value=\""+director+"\">");
              out.println("<input type=\"hidden\" name=\"first\" value=\""+first+"\">");
              out.println("<input type=\"hidden\" name=\"last\" value=\""+last+"\">");
              out.println("<input type=\"hidden\" name=\"year\" value=\""+yearString+"\">");
              //out.println("<input type=\"hidden\" name=\"offset\" value=\""+offsetPrev+"\">");
             // out.println("<input type=\"hidden\" name=\"offset\" value=\""+offsetNext+"\">");
             // out.println("<input type=\"hidden\" name=\"limit\" value=\""+limitSize+"\">");
              out.println("<td><input type=\"submit\" value=\"Change\"></td>");
             // out.println("<input type=\"submit\" value=\"prev\">");
              out.println("</form>");
            
              out.println("</table>");
              
              
              rs.close();
              statement.close();
              dbcon.close();
            }
        
        
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                    response.sendRedirect("Search.jsp");
                    return;
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

