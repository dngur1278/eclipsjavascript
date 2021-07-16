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

@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModifyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력요청된 정보를 읽어옴
		String eid = request.getParameter("eid");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");
		String hire_date = request.getParameter("hire_date");
		
		// updateEmp()를 매개값으로
		EmpDAO dao = new EmpDAO();
		Employee emp = new Employee();
		emp.setEmployee_id(Integer.parseInt(eid));
		emp.setFirst_name(first_name);
		emp.setLast_name(last_name);
		emp.setEmail(email);
		emp.setHiredate(hire_date);
		
		dao.updateEmp(emp);
	      
	    PrintWriter out = response.getWriter();
	    Gson gson =new GsonBuilder().create();
	    out.println(gson.toJson(emp));

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
