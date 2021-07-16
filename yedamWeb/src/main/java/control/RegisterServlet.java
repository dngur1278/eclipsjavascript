package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common.EmpDAO;
import common.Employee;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String eid = request.getParameter("eid");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");
		String hire_date = request.getParameter("hire_date");
		
		EmpDAO dao = new EmpDAO();
		Employee emp = new Employee();
		emp.setEmployee_id(Integer.parseInt(eid));
		emp.setFirst_name(first_name);
		emp.setLast_name(last_name);
		emp.setEmail(email);
		emp.setHiredate(hire_date);
		
		dao.insertEmp(emp);
		
		// {"id":?, "first_name":?, "last_name":?, "email":? "hire_date":?}
		PrintWriter out = response.getWriter();
//		out.println("{\"id\":\""+emp.getEmployee_id()
//				+"\",\"first_name\":\""+emp.getFirst_name()
//				+"\",\"last_name\":\""+emp.getLast_name()
//				+"\",\"email\":\""+emp.getEmail()
//				+"\",\"hire_date\":\""+emp.getHiredate()+"\"}");
		
		Gson gson = new GsonBuilder().create();
		out.println(gson.toJson(emp));
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
