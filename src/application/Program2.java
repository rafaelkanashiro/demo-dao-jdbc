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
		
		System.out.println("=== TEST 2: department findById ===");
		Department dp2 = departmentDao.findById(2);
		System.out.println(dp2);
		
		System.out.println("=== TEST 3: department delete ===");
		departmentDao.deleteById(6);
		System.out.println("Delete completed!");
	}
}
