package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the coffees database table.
 * 
 */
@Entity
@Table(name="coffees")
@NamedQuery(name="Coffee.findAll", query="SELECT c FROM Coffee c")
public class Coffee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COF_NAME")
	private String cofName;

	private BigDecimal price;

	private int sales;

	private int total;

	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name="SUP_ID" /*,referencedColumnName="SUP_ID"*/) 
							   //找的時候是用物件裡的關聯欄位去找
	private Supplier supplier; //建立整個供應商物件出來
							   //一個經銷商會供應很多種咖啡	

	public Coffee() {
	}

	public String getCofName() {
		return this.cofName;
	}

	public void setCofName(String cofName) {
		this.cofName = cofName;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSales() {
		return this.sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@Override
	public String toString() {
		return this.cofName+":"+this.price;
	}

}