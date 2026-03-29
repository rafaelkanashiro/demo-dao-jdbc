package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== TEST 1: department insert ===");
		Department dp = new Department(null, "Games");
		departmentDao.insert(dp);
		System.out.println("Department added, department id: " + dp.getId());
	}
}
