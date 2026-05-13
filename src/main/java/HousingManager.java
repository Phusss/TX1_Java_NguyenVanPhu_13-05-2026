import java.util.List;

public interface HousingManager {
    public boolean addHousing(Housing h);

    public boolean editHousing(Housing h);

    public boolean delHousing(Housing h);

    // Chữ ký hàm theo đúng đề bài
    public List<Housing> searchHousing(String name);

    // Đề yêu cầu tìm theo cả giá và thuộc tính riêng (diện tích), nên nạp chồng thêm:
    public List<Housing> searchHousingByPrice(double price);
    public List<Housing> searchHousingByArea(double area);

    // Chữ ký hàm sắp xếp theo đúng đề bài (sắp xếp theo giá)
    public List<Housing> sortedHousing(double price);

    // sắp xếp theo diện tích:
    public List<Housing> sortedHousingByArea();
}