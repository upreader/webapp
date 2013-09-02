package com.upreader;

import com.upreader.context.Context;
import org.apache.log4j.Logger;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Hides JSP file extension
 * 
 * @author Flavius
 *
 */
@WebServlet(name = "ujsp", urlPatterns = "/p/*")
public class JspServlet extends HttpServlet {
    private Logger log = Logger.getLogger(UpreaderServlet.class);

    @Inject
    @Named("jdbc/mysql")
    private DataSource dataSource;

    @PersistenceUnit(unitName="default")
    private EntityManagerFactory entityManagerFactory;

    private final UpreaderApplication application = getApplication();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
            if (this.application.isRunning()) {
                UpreaderRequest httpRequest = new UpreaderRequest(request, response, request.getServletContext(), this.application);
                Context context = this.application.getContext(httpRequest, entityManagerFactory, dataSource);
                request.getServletContext().setAttribute("context", context);
                request.getRequestDispatcher("/WEB-INF/jsp" + request.getPathInfo() + ".jsp").forward(request, response);
            }
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

    public UpreaderApplication getApplication() {
        return UpreaderApplication.getInstance();
    }
}
