import java.util.List;

public interface HousingManager {
    public boolean addHousing(Housing h);
    public boolean editHousing(Housing h);
    public boolean delHousing(Housing h);

    public List<Housing> searchHousing(String name);
    public List<Housing> searchHousingByPrice(double price);
    public List<Housing> searchHousingByArea(double area);

    public List<Housing> sortedHousing(double price);
    public List<Housing> sortedHousingByArea();

    // --- CÁC HÀM THÊM MỚI PHỤC VỤ TX2 ---
    public List<Housing> getAllHousing(); // Lấy toàn bộ danh sách để đưa lên GUI
    public void generateLargeData(); // Sinh dữ liệu số lượng lớn
    public List<Housing> sortedHousing(boolean isAscending); // Sắp xếp theo giá tăng/giảm
}