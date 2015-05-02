package edu.cs157b.restful;


import java.sql.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import edu.cs157b.util.DatabaseConnection;

@Path("/")
public class Restful implements Hw2DAO
{
	private String updaterId;
	private String updaterPrice;
	private String deleterId;
	
	public void createTables() throws Exception
	{
		String customers = "create table if not exists customers (id int not null auto_increment, name varchar(45) default null, primary key (id))";
		
		String products = "create table if not exists products (id int not null auto_increment, name varchar(45) default null, price double default null, primary key (id))";
		
		String orders = "create table if not exists orders (id int not null auto_increment, total double default null, customer int default null, products int default null, primary key (id), key customer_idx (customer), key products_idx (products), constraint customer foreign key (customer) references customers (id) on delete set null on update no action, constraint products foreign key (products) references products (id) on delete set null on update cascade)";
		
		Statement s = null;
		
		Connection conn = null;
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			s = conn.createStatement();
			s.execute(customers);
			s.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			s = conn.createStatement();
			s.execute(products);
			s.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			s = conn.createStatement();
			s.execute(orders);
			s.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
	}
	
	@Path("/customers")
    @POST
	public String createCustomer(@FormParam("name") String name) throws Exception
	{
		createTables();
		
		PreparedStatement ps = null;
		
		Connection conn = null;
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "insert into customers (name) values (?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ps.executeUpdate();
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		return "Successfully added " + name + "<br><br> <a href=\"..\\index.html\">Home</a>";
	}
	
	@Path("/customers")
    @GET
    @Produces(MediaType.TEXT_HTML)
	public String getCustomers() throws Exception
	{
		createTables();
		
		PreparedStatement ps = null;
		Connection conn = null;
		String returnString = " ";
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "select * from customers";
			ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
			 
				returnString += id + " " + name + "<br>"; 
			}
			
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		return "<p> Customers </p> " + returnString + "<br><br> <a href=\"..\\index.html\">Home</a>";
	}
	
	@Path("/products")
    @POST
	public String createProduct(@FormParam("name") String name, @FormParam("price") String price) throws Exception
	{
		createTables();
		
		PreparedStatement ps = null;
		
		Connection conn = null;
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "insert into products (name, price) values (?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, price);
			ps.executeUpdate();
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		return "Successfully added " + name + " " + price + "<br><br> <a href=\"..\\index.html\">Home</a>";
	}
	
	@Path("/products")
    @GET
    @Produces(MediaType.TEXT_HTML)
	public String getProducts() throws Exception
	{
		createTables();
		
		PreparedStatement ps = null;
		Connection conn = null;
		String returnString = " ";
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "select * from products";
			ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
			 
				returnString += id + " " + name + " " + price + "<br>"; 
			}
			
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		return "<p> Products </p> " + returnString + "<br><br> <a href=\"..\\index.html\">Home</a>";
	}
	
	@Path("/orders")
    @POST
	public String createOrder(@FormParam("total") String total, @FormParam("customer") String customer, @FormParam("product") String product) throws Exception
	{
		createTables();
		
		PreparedStatement ps = null;
		
		Connection conn = null;
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "insert into orders (total, customer, products) values (?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, total);
			ps.setString(2, customer);
			ps.setString(3,  product);
			ps.executeUpdate();
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		return "Successfully added " + total + " " + customer + " " + product + "<br><br> <a href=\"..\\index.html\">Home</a>";
	}
	
	@Path("/orders")
    @GET
    @Produces(MediaType.TEXT_HTML)
	public String getOrders() throws Exception
	{
		createTables();
		
		PreparedStatement ps = null;
		Connection conn = null;
		String returnString = " ";
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "select * from orders";
			ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				int id = rs.getInt("id");
				double total = rs.getDouble("total");
				int customer = rs.getInt("customer");
				int products = rs.getInt("products");
			 
				returnString += id + " " + total + " " + customer + " " + products + "<br>"; 
			}
			
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		return "<p> Orders </p> " + returnString + "<br><br> <a href=\"..\\index.html\">Home</a>";
	}
	
	@Path("/orders/id")
    @GET
    @Produces(MediaType.TEXT_HTML)
	public String getOrdersById(@QueryParam("id") String id) throws Exception
	{
		createTables();
		
		PreparedStatement ps = null;
		Connection conn = null;
		String returnString = " ";
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "select * from orders where id = " + id;
			ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				int id1 = rs.getInt("id");
				double total = rs.getDouble("total");
				int customer = rs.getInt("customer");
				int products = rs.getInt("products");
			 
				returnString += id1 + " " + total + " " + customer + " " + products + "<br>"; 
			}
			
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
		return "<p> Orders </p> " + returnString + "<br><br> <a href=\"..\\..\\index.html\">Home</a>";
	}
	
    @PUT
	public void updatePrice() throws Exception
	{
    	createTables();
    	
		PreparedStatement ps = null;
		
		Connection conn = null;
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "update products set price = " + updaterPrice + " where id = " + updaterId;
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
	}
	
    @DELETE
	public void deleteOrder() throws Exception
	{
    	createTables();
    	
		PreparedStatement ps = null;
		Connection conn = null;
		
		try
		{
			conn = DatabaseConnection.getDataSource().getConnection();
			String query = "delete from orders where id = " + deleterId;
			ps = conn.prepareStatement(query);
			
			ps.executeUpdate();
			
			
			
			ps.close();
		}
		finally 
		{
			if (conn!=null) conn.close();
		}
	}
	
	@Path("/products/id/newprice")
    @POST
	public String updateGetter(@FormParam("id") String id, @FormParam("newprice") String newprice) throws Exception
	{
		createTables();
		
		updaterId = id;
		updaterPrice = newprice;
		updatePrice();
		
		return "Successfully updated" + "<br><br> <a href=\"..\\..\\..\\index.html\">Home</a>";
	}
	
	@Path("/orders/id")
    @POST
    @Produces(MediaType.TEXT_HTML)
	public String deleteGetter(@FormParam("id") String id) throws Exception
	{
		createTables();
		
		deleterId = id;
		deleteOrder();
		
		return "Successfully deleted " + deleterId + "<br><br> <a href=\"..\\..\\index.html\">Home</a>";
	}
}
