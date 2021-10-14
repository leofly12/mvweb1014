

import java.io.IOException;
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

import model.Student;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//運用JPA語法執行新增、修改、查詢、刪除
		
		//insertQuery(response);  //新增資料 //實測不用request也可以
		//updateQuery(response);  //修改資料：方式一
		//update2Query(response); //修改資料：方式二(運用merge方法)
		//deleteQuery(response);  //刪除資料：方式一
		delete2Query(response);   //刪除資料：方式二(運用find&remove方法)
		selectQuery(response);      //查詢資料
	}
	
	public void selectQuery(HttpServletResponse response) throws ServletException,IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQLAllDemo");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("Select s from Student s ");
		@SuppressWarnings("unchecked") //註解掉會出現unchecked警告，這句話是要系統忽略警告
		List<Student> list = (List<Student>) query.getResultList();
		
	/*	List<Student> list= null;  //一般要做轉型要先判斷轉得對不對(兩者是否有關係)
		if(query.getResultList() instanceof List) {
			list = (List<Student>) query.getResultList();
		} 
	*/	
		System.out.print("sid");
		System.out.print("\t sname");
		System.out.println("\t age");
		
		for (Student s : list) {
			System.out.print(s.getSid());
			System.out.print("\t" + s.getSname());
			System.out.print("\t" + s.getAge());
	
			System.out.println();
		}
		
		em.getTransaction().commit(); //執行
		em.close(); //關閉連線
		emf.close();
		response.getWriter().append("See Tomcat Console Student Data");		
	}
	
	public void insertQuery(HttpServletResponse response)throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQLAllDemo");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Student s1 = new Student();
		s1.setSid(1001);
		s1.setAge(27);
		s1.setSname("Max");

		
		Student s2 = new Student(1102, 21, "Yen");
//		s2.setSid(1102);
//		s2.setAge(21);
//		s2.setSname("Yen");

		em.persist(s1); //把物件存起來到動作庫
		em.persist(s2);
		em.getTransaction().commit();
		em.close();
		emf.close();
		response.getWriter().append("See Tomcat Console Student Data");
	}
	
	public void updateQuery(HttpServletResponse response) throws ServletException,IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQLAllDemo");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
	
		Query query = em.createQuery("update Student SET age=20 where sid=103");
		int r=query.executeUpdate(); //有執行修正程序會回傳1
		if(r>0)
			System.out.println("update successfully");
		else
			System.out.println("update failed");
		em.getTransaction().commit();
		em.close(); //沒關閉連線的話，有些資料庫不會自動結束連線，就會占用到系統端記憶體資源
		emf.close();
		response.getWriter().append("See Tomcat Console Student Data");
	}
	
	public void update2Query(HttpServletResponse response)throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQLAllDemo");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Student sx=new Student(101,18,"Mary");
		em.merge(sx);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void deleteQuery(HttpServletResponse response)throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQLAllDemo");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createQuery("delete from Student where sid=1102");
		int r=query.executeUpdate();
		
		if(r>0)
			System.out.println("delete successfully");
		else
			System.out.println("delete failed");
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		response.getWriter().append("See Tomcat Console Student Data");
	}
	
	public void delete2Query(HttpServletResponse response)throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQLAllDemo");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Student sx=em.find(Student.class, 1001); //找不到的話會跳出錯誤
		em.remove(sx); //Entity must be managed to call remove: model.Student@1a239bc0, try merging the detached and try the remove again.
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
