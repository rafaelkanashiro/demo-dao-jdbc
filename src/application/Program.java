package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== TEST 1: seller findById ===");
		Seller seller = sellerDao.findById(1);
		
		System.out.println(seller);
		
		System.out.println("=== TEST 2: seller findByDepartment ===");
		List<Seller> list = new ArrayList<>();
		list = sellerDao.findByDepartment(new Department(2, null));
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("=== TEST 3: seller findAll ===");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("=== TEST 4: seller insert ===");
		Seller newSeller = new Seller(null, "Alex", "alex@gmail.com", new Date(), 4500.0, new Department(2, null));
		sellerDao.insert(newSeller);
		System.out.println("New seller added, id: " + newSeller.getId());
		
		
		System.out.println("=== TEST 4: seller update ===");
		Seller sellerUpdate = sellerDao.findById(10);
		sellerUpdate.setName("Marcus");
		sellerUpdate.setEmail("marcus@gmail.com");
		sellerDao.update(sellerUpdate);
	}

}
