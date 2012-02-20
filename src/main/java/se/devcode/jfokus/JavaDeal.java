package se.devcode.jfokus;

public class JavaDeal {

	final String title;
	final Integer discount;
	final String city;
	
	public JavaDeal(String title, Integer discount, String city) {
		super();
		this.title = title == null ? "Empty" : title;
		this.discount = discount == null ? 0 : discount;
		this.city = city == null ? "Stockholm" : city;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Integer getDiscount() {
		return discount;
	}
	
	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return "Deal [title=" + title + ", discount=" + discount + ", city="
				+ city + "]";
	}

	// TODO: create hashCode(), equals() ...
}


