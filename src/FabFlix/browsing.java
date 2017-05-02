package FabFlix;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class browsing extends HttpServlet{
	
	public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{	
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("menu.jsp").include(request, response);
        out.println("<script type='text/javascript'>");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/func.js");
        out.println("</script>");
		out.println("<HTML><HEAD><TITLE>movielist</TITLE></HEAD>");
        out.println("<BODY><H1>Resulting Movies</H1>");
        
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
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false",
					"mytestuser","mypassword");			
			Statement select = connection.createStatement();
			Statement select2 = connection.createStatement();
			Statement select3 = connection.createStatement();
			
			String genre = request.getParameter("genre");
			String letter = request.getParameter("letter");
			//String query = "SELECT * from movies ";
			
			request.setAttribute("viewsize",genre);
			
			String query = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id left join genres_in_movies x on x.movie_id = s.m_id left join genres y on y.g_id = x.genre_id where ";
            String query2 = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id left join genres_in_movies x on x.movie_id = s.m_id left join genres y on y.g_id = x.genre_id where ";
          // String query2 = "select * from genres_in_movies m left join genres g on g.g_id = m.genre_id left join movies s on s.m_id = m.movie_id ";
            String query3 = "select * from stars_in_movies m left join stars g on g.s_id = m.star_id left join movies s on s.m_id = m.movie_id where ";
			
            if (genre!=null&&!genre.isEmpty()&&!genre.equals("null")){
				System.out.println(genre);
				query += " s.m_id = x.movie_id and y.g_id = x.genre_id and y.name = '" + genre + "'";
				//query2 += " s.m_id = x.movie_id and y.g_id = x.genre_id and y.name = '" + genre + "'";
				//query3 += " s.m_id = x.movie_id and y.g_id = x.genre_id and y.name = '" + genre + "'";
			}
			else{
				System.out.println(letter);
				query += "s.title like '" + letter + "%'";
				//query2 += "s.title like '" + letter + "%'";
				//query3 += "s.title like '" + letter + "%' and";
			}
			//ResultSet result = select.executeQuery(query);
			//response.setContentType("text/html");
            query+=" group by title";
            
	        if(request.getParameter("sort")!=null&&!request.getParameter("sort").isEmpty())
          	  query+=" order by title "+request.getParameter("sort");
            if(request.getParameter("sortYear")!=null&&!request.getParameter("sortYear").isEmpty())
          	  query+=" order by year "+request.getParameter("sortYear");
            
            query+=" limit "+limitSize;
            query+=" offset "+offsetSize;
           // out.println(query);
            ResultSet rs = select.executeQuery(query);
            
            if(request.getParameter("sort")!=null&&request.getParameter("sort").equals("asc"))
          	  sortType="desc";
            if(request.getParameter("sortYear")!=null&&request.getParameter("sortYear").equals("asc"))
          	  sortType="desc";
            if(request.getParameter("sort")!=null&&request.getParameter("sort").equals("desc"))
          	  sortType="asc";
            if(request.getParameter("sortYear")!=null&&request.getParameter("sortYear").equals("desc"))
          	  sortType="asc";
            
            out.println("<TABLE class=\"searchresults\">");

            // Iterate through each row of rs
            out.println("<tr>" +
	    		  "<th><h4>" + "Movie ID" + "</th></h4>" +
	    		  "<th><h4><a  class=\"results\" name=\"viewsize\" href=\"browsing?sort="+sortType+"&genre="+genre+"&letter="+letter+"\"> Title </a></th></h4>" +
	    		  "<th><h4><a  class=\"results\" name=\"viewsize\" href=\"browsing?sortYear="+sortType+"&genre="+genre+"&letter="+letter+"\"> Year </a></th></h4>" +
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
	                  ResultSet rs2 = select2.executeQuery(query2);
	                  
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
	                  
	                  
	                  String savedQuery3=query3;
//	                  if(!first.isEmpty()&&!last.isEmpty())
//	                	  query3+="and m.movie_id="+m_ID+" group by first_name";
//	                  else if(!last.isEmpty())
//	                	  query3+="and m.movie_id="+m_ID+" group by last_name";
//	                  else if(!first.isEmpty())
//	                	  query3+="and m.movie_id="+m_ID+" group by first_name";      
//	                  else
	                query3+=" m.movie_id="+m_ID+" group by first_name";
	                  
	                //  out.println("QUERY 3: "+query3);
	                  ResultSet rs3 = select3.executeQuery(query3);
	                  
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

	              out.println("<td><a  href=\"browsing?limit="+limitSize+"&offset="+offsetPrev+"&genre="+genre+"&letter="+letter+"\"> Prev </a></td>");
	              out.println("<td>    </td>");
	              out.println("<td><a id=\"link1\"  href=\"browsing?limit="+limitSize+"&offset="+offsetNext+"&genre="+genre+"&letter="+letter+"\"> Next </a></td>");    
	              
	              out.println("<form action=\"browsing\">");
	              
	              out.println("<td><select id=\"mySelect\" name=\"viewsize\"><option value=\"10\">10</option><option value=\"25\">25</option><option value=\"50\">50</option><option value=\"100\">100</option></select></td>");
	              out.println("<input type=\"hidden\" name=\"genre\" value=\""+genre+"\">");
	              out.println("<input type=\"hidden\" name=\"letter\" value=\""+letter+"\">");
//	              out.println("<input type=\"hidden\" name=\"first\" value=\""+first+"\">");
//	              out.println("<input type=\"hidden\" name=\"last\" value=\""+last+"\">");
//	              out.println("<input type=\"hidden\" name=\"year\" value=\""+yearString+"\">");
	              //out.println("<input type=\"hidden\" name=\"offset\" value=\""+offsetPrev+"\">");
	             // out.println("<input type=\"hidden\" name=\"offset\" value=\""+offsetNext+"\">");
	             // out.println("<input type=\"hidden\" name=\"limit\" value=\""+limitSize+"\">");
	              out.println("<td><input type=\"submit\" value=\"Change\"></td>");
	             // out.println("<input type=\"submit\" value=\"prev\">");
	              out.println("</form>");
	            
	              out.println("</table>");
	              
	              
	              rs.close();
	              select.close();
	              connection.close();
	            
            
//			while (result.next()){
//				out.println("m_id: " + result.getString(1));
//				out.println("title: " + result.getString(2));
//				out.println("year " + result.getString(3));
//				out.println("director: " + result.getString(4));
//				out.println("banner_url: " + result.getString(5));
//				out.println("trailer_url: " + result.getString(6));
//			}
//			
//			out.println("</BODY></HTML>");
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