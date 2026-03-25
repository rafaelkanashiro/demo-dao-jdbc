package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, Departmentid) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
			}else {
				throw new DbException("Unexpected Error. No rows affected.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET NAME = ?, email = ?, birthdate = ?, basesalary = ?, departmentid = ? "
					+ "WHERE Id = ?");

			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dp = instantiateDepartment(rs);

				Seller obj = instantiateSeller(rs, dp);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		return dp;
	}

	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBirthDate(rs.getDate("birthDate"));
		obj.setBaseSalary(rs.getDouble("baseSalary"));
		obj.setDepartment(dp);
		return obj;
	}

	@Override
	public List<Seller> findAll() {
		List<Seller> lista = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		Map<Integer, Department> map = new HashMap<>();

		try {
			st = conn.prepareStatement(
					"SELECT seller.* , department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
	        while (rs.next()) {
	        	Department dp = map.get(rs.getInt("DepartmentId"));
	        	
	        	if(dp == null) {
	        		dp = instantiateDepartment(rs);
	        		map.put(rs.getInt("DepartmentId"), dp);
	        	}
	            Seller obj = instantiateSeller(rs, dp);
	            lista.add(obj);
	        }
	        return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		List<Seller> lista = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		Map<Integer, Department> map = new HashMap<>();

		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			st.setInt(1, department.getId());
			rs = st.executeQuery();

			
			
	        while (rs.next()) {
	        	Department dp = map.get(rs.getInt("DepartmentId"));
	        	
	        	if(dp == null) {
	        		dp = instantiateDepartment(rs);
	        		map.put(rs.getInt("DepartmentId"), dp);
	        	}
	            Seller obj = instantiateSeller(rs, dp);
	            lista.add(obj);
	        }
	        return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
