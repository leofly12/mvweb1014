package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the suppliers database table.
 * 
 */
@Entity
@Table(name="suppliers")
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s") //name是變數名稱，以此指稱query並執行其內容
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUP_ID")
	private int supId;

	private String city;

	private String state;

	private String street;

	@Column(name="SUP_NAME")
	private String supName;

	private String zip;

	//bi-directional many-to-one association to Coffee
	@OneToMany(mappedBy="supplier", cascade = CascadeType.PERSIST) 
	//先找到supplier再建立Coffee物件陣列，系統會自動建立好，不會自己寫方法抓資料
	//cascade = CascadeType.PERSIST 表示連動建立經銷商物件的咖啡相關欄位資料，要自己加入這個語法
		
	private List<Coffee> coffees; //一種咖啡可以由數個承銷商供應

	public Supplier() {
	}

	public int getSupId() {
		return this.supId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSupName() {
		return this.supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<Coffee> getCoffees() {
		return this.coffees;
	}

	public void setCoffees(List<Coffee> coffees) {
		this.coffees = coffees;
	}

	public Coffee addCoffee(Coffee coffee) {
		getCoffees().add(coffee);
		coffee.setSupplier(this);

		return coffee;
	}

	public Coffee removeCoffee(Coffee coffee) {
		getCoffees().remove(coffee);
		coffee.setSupplier(null);

		return coffee;
	}

	@Override
	public String toString() {
		return this.supId+":"+this.supName+":"+this.getCity();
	}
	
	

}