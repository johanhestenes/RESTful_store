package edu.cs157b.restful;

public interface Hw2DAO 
{
	public String createCustomer(String name) throws Exception;
	
	public String getCustomers() throws Exception;
	
	public String createProduct(String name, String price) throws Exception;
	
	public String getProducts() throws Exception;
	
	public String createOrder(String total, String customer, String product) throws Exception;

	public String getOrders() throws Exception;
	
	public String getOrdersById(String id) throws Exception;
	
	public void updatePrice() throws Exception;
	
	public void deleteOrder() throws Exception;
}
