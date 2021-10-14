

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Coffee;
import model.Supplier;

/**
 * Servlet implementation class SupplierCoffeeServlet
 */
@WebServlet("/SupplierCoffeeServlet")
public class SupplierCoffeeServlet extends HttpServlet {

	void add() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAOneMany");
		EntityManager em = emf.createEntityManager();		
		em.getTransaction().begin();
		
		Supplier sp = new Supplier();
		sp.setCity("Taipei");
		sp.setSupName("Gjun");
		sp.setState("TW");
		sp.setStreet("Kung Yuan Road");
		sp.setZip("101");
		sp.setSupId(2);
		
		Coffee c1=new Coffee();
		c1.setCofName("Flavor A");
		c1.setPrice(new BigDecimal(200.0)); //BigDecimal可以放很大數字的類別
		c1.setSales(10);
		c1.setTotal(5);
		c1.setSupplier(sp);
		
		Coffee c2=new Coffee();
		c2.setCofName("Flavor B");
		c2.setPrice(new BigDecimal(100.0));
		c2.setSales(11);
		c2.setTotal(6);
		c2.setSupplier(sp);
		
		//em.persist(c1);
		//em.persist(c2);  
		//沒有在Supplier寫cascade = CascadeType.PERSIST的話，要先新增咖啡資料才行，否則會出現錯誤。
		//javax.persistence.RollbackException: java.lang.IllegalStateException: During synchronization a new object was found through a relationship that was not marked cascade PERSIST: model.Coffee@576d0278.

		ArrayList<Coffee> list = new ArrayList<>();
		list.add(c1);
		list.add(c2);
		sp.setCoffees(list); //把咖啡資料放到經銷商物件中
		em.persist(sp);      //正式新增經銷商資料，因為有寫cascade = CascadeType.PERSIST，所以一併更新其咖啡資料。
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		}		
	
	void query() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAOneMany");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("Select c from Coffee c");
		
		List<Coffee> list =query.getResultList();
		System.out.println("Coffee:");
		for (Coffee s: list) {
			System.out.println(s);
			System.out.println("\t"+s.getSupplier());
		}
		em.clear();
		emf.close();
	}
	
	void query2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAOneMany");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("Select c from Coffee c");
		
		List<Coffee> list =query.getResultList();
		
//		System.out.println("Coffee:");
//		for (Coffee s: list) {
//			System.out.println(s);
//			System.out.println("\t"+s.getSupplier());
//		}
		em.clear();
		emf.close();
		
		request.setAttribute("coffee", list);
		request.getRequestDispatcher("viewCoffee.jsp").forward(request, response);

	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//add();
		//response.getWriter().append("1 Supplier 2 Coffees Added.");

		//query();
		//response.getWriter().append("Supplier Coffee Query");
		
		query2(request,response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
