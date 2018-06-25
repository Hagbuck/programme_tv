package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.StaticLists;

/**
 * Servlet implementation class Init
 */
@WebServlet(name="/Init", urlPatterns = {"/Init"}, loadOnStartup=1)
public class Init extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Init() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	System.out.println("[INFO] INIT STATIC LISTS");
    	
    	ServletContext context = getServletContext();
    	String fullPath = context.getRealPath("/WEB-INF/files/ptv.xml");
    	
        StaticLists.initStaticLists(fullPath);
        System.out.println("[INFO] LISTS Loaded");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
