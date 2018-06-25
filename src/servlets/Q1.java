package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.StaticLists;

/**
 * Servlet implementation class Q1
 */
@WebServlet("/Q1")
public class Q1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Q1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("channelsList", StaticLists.STATICK_LISTS.channelsList);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/q1.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
