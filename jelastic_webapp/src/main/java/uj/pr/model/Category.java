package uj.pr.model;

public class Category {
	private int id;
	private String name;
	private String description;
	private String discountStart;
	private String discountEnd;
	private float percentDiscount;

	public Category() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscountStart() {
		return discountStart;
	}

	public void setDiscountStart(String discountStart) {
		this.discountStart = discountStart;
	}

	public String getDiscountEnd() {
		return discountEnd;
	}

	public void setDiscountEnd(String discountEnd) {
		this.discountEnd = discountEnd;
	}

	public float getPercentDiscount() {
		return percentDiscount;
	}

	public void setPercentDiscount(float percentDiscount) {
		this.percentDiscount = percentDiscount;
	}
}
