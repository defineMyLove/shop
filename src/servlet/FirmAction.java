package servlet;

import bean.Firm;
import dao.FirmDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class FirmAction {
    FirmDAO dao= new FirmDAO();
	public void action(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		Firm bean = new Firm();
        bean.setName(name);
		String target = null;
		try {
            request.setAttribute("domainList",dao.find(bean));
			target = "/firm/firmMain.jsp";
		} catch (Exception e) {
			target = "/error.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}
    public void addAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Firm bean = new Firm();
        bean.setName(name);
        if(id==null||id.equals("")){
            bean.setCreateTime(new Date());
            dao.add(bean);
        }else{
            dao.update(bean);
        }
        String target = null;
        try {

            ServletContext context = request.getSession().getServletContext();
            response.sendRedirect(context.getContextPath() + "/display?method=firmMain");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", true);
            target = "/firm/firmAdd.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(target);
            rd.forward(request, response);
        }
    }

    public void delAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("id");
        String target = null;
        try {
            dao.del(username);
            ServletContext context = request.getSession().getServletContext();
            response.sendRedirect(context.getContextPath()+"/display?method=firmMain");
        } catch (Exception e) {
            target = "/error.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(target);
            rd.forward(request, response);
        }
    }
}
