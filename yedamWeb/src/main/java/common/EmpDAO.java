package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	
	public Employee selectEmp(String employeeId) {
		conn = DBCon.getConnect();
		String sql = "select * from empl_demo where employee_id = ?";
		Employee emp = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, employeeId);
			rs = psmt.executeQuery();
			if (rs.next()) {
				emp = new Employee();
				emp.setEmail(rs.getString("email"));
				emp.setEmployee_id(rs.getInt("employee_id"));
				emp.setFirst_name(rs.getString("first_name"));
				emp.setHiredate(rs.getString("hire_date").substring(0, 10));
				emp.setLast_name(rs.getString("last_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	
	
	public List<Employee> getEmpList()	{
		List<Employee> empList = new ArrayList();
		conn = DBCon.getConnect();
		try {
			psmt = conn.prepareStatement("select * from empl_demo");
			rs = psmt.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getInt("employee_id"));
				emp.setFirst_name(rs.getString("first_name"));
				emp.setLast_name(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setHiredate(rs.getString("hire_date").substring(0, 10));
				empList.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empList;
	}

	public void insertEmp(Employee emp) {
		conn = DBCon.getConnect();
		String sql = "insert into empl_demo(employee_id, first_name, last_name, email, hire_date, job_id) values(?,?,?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, emp.getEmployee_id());
			psmt.setString(2, emp.getFirst_name());
			psmt.setString(3, emp.getLast_name());
			psmt.setString(4, emp.getEmail());
			psmt.setString(5, emp.getHiredate());
			psmt.setString(6, "IT_PROG");
			psmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateEmp(Employee emp) {
		conn = DBCon.getConnect();
		 String sql = "update empl_demo "
		            + "set first_name = ?, last_name = ?, email = ?, hire_date = ? "
		            + "where employee_id= ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, emp.getFirst_name());
			psmt.setString(2, emp.getLast_name());
			psmt.setString(3, emp.getEmail());
			psmt.setString(4, emp.getHiredate());
			psmt.setInt(5, emp.getEmployee_id());
			
			psmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEmp(String employee_id) {
		conn = DBCon.getConnect();
		String sql = "delete from empl_demo where employee_id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, employee_id);
			
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
