public class Housing extends Product {

    private double area;
    private String address;

    public Housing() {
        super();
    }

    public Housing(String product_id, String product_name, double product_price, int product_total, double area, String address) {
        super(product_id, product_name, product_price, product_total);
        this.area = area;
        this.address = address;
    }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Housing [" + super.toString() +
                ", Area: " + area +
                ", Address: " + address + "]";
    }
}