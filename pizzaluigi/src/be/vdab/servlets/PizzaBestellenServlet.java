package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.dao.PizzaDAO;
import be.vdab.entities.Pizza;

@WebServlet("/pizzas/bestellen.htm")
public class PizzaBestellenServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/pizzabestellen.jsp";
	private final transient PizzaDAO pizzaDAO = new PizzaDAO(); 
 
  @Resource(name = PizzaDAO.JNDI_NAME) void setDataSource(DataSource dataSource) {
	  pizzaDAO.setDataSource(dataSource); 
  }
  
  @Override
  protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException { 
    request.setAttribute("allePizzas", pizzaDAO.findAll()); 
    HttpSession session = request.getSession(false); 
    if (session != null) {
      @SuppressWarnings("unchecked") 
      Set<Long> mandje = (Set<Long>) session.getAttribute("mandje"); 
      if (mandje != null) { 
        List<Pizza> pizzasInMandje = new ArrayList<>();
        for (long id : mandje) { 
          pizzasInMandje.add(pizzaDAO.read(id));
        }
        request.setAttribute("pizzasInMandje", pizzasInMandje); 
      }
    }
    request.getRequestDispatcher(VIEW).forward(request, response);
  }
  
  @Override 
  protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	  if (request.getCookies() == null) {   request.setAttribute("fout", "Dit werkt enkel als cookies aanstaan");
	  request.getRequestDispatcher(VIEW).forward(request, response);
	  return;
	  } 
	  if (request.getParameterValues("id") != null) {
      HttpSession session = request.getSession(); 
      @SuppressWarnings("unchecked")
      Set<Long> pizzaIdsInMandje=(Set<Long>)session.getAttribute("mandje");
      if (pizzaIdsInMandje == null) { 
        pizzaIdsInMandje = new LinkedHashSet<>(); 
      }
      for (String id : request.getParameterValues("id")) {
        pizzaIdsInMandje.add(Long.parseLong(id)); 
      }
      session.setAttribute("mandje", pizzaIdsInMandje);
    }
    response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
  }
} 
