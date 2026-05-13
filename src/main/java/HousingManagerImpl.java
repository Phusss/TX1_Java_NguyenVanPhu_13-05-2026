import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HousingManagerImpl implements HousingManager {
    private List<Housing> housingList = new ArrayList<>();

    @Override
    public boolean addHousing(Housing h) {
        // Kiểm tra trùng lặp ID trước khi thêm
        for (Housing item : housingList) {
            if (item.getProduct_id().equals(h.getProduct_id())) {
                System.out.println("Lỗi: ID đã tồn tại!");
                return false;
            }
        }
        return housingList.add(h);
    }

    @Override
    public boolean editHousing(Housing h) {
        for (int i = 0; i < housingList.size(); i++) {
            if (housingList.get(i).getProduct_id().equals(h.getProduct_id())) {
                housingList.set(i, h);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delHousing(Housing h) {
        return housingList.removeIf(item -> item.getProduct_id().equals(h.getProduct_id()));
    }

    @Override
    public List<Housing> searchHousing(String name) {
        return housingList.stream()
                .filter(h -> h.getProduct_name().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Housing> searchHousingByPrice(double price) {
        return housingList.stream()
                .filter(h -> h.getProduct_price() == price)
                .collect(Collectors.toList());
    }

    @Override
    public List<Housing> searchHousingByArea(double area) {
        return housingList.stream()
                .filter(h -> h.getArea() == area)
                .collect(Collectors.toList());
    }

    @Override
    public List<Housing> sortedHousing(double price) {
        List<Housing> filteredList = housingList.stream()
                .filter(h -> h.getProduct_price() >= price)
                .collect(Collectors.toList());

        Collections.sort(filteredList, new Comparator<Housing>() {
            @Override
            public int compare(Housing h1, Housing h2) {
                return Double.compare(h1.getProduct_price(), h2.getProduct_price());
            }
        });
        return filteredList;
    }

    @Override
    public List<Housing> sortedHousingByArea() {
        List<Housing> sortedList = new ArrayList<>(housingList);
        Collections.sort(sortedList, new Comparator<Housing>() {
            @Override
            public int compare(Housing h1, Housing h2) {
                return Double.compare(h1.getArea(), h2.getArea());
            }
        });
        return sortedList;
    }

    public static void main(String[] args) {
        HousingManagerImpl manager = new HousingManagerImpl();

        // 1. Thêm mới
        System.out.println("--- Thêm mới BĐS ---");
        Housing h1 = new Housing("H01", "Chung cu Vin", 1500.0, 10, 75.5, "Ha Noi");
        Housing h2 = new Housing("H02", "Biet thu sala", 8500.0, 2, 250.0, "TP HCM");
        Housing h3 = new Housing("H03", "Nha cap 4", 500.0, 5, 60.0, "Da Nang");

        manager.addHousing(h1);
        manager.addHousing(h2);
        manager.addHousing(h3);
        manager.housingList.forEach(System.out::println);

        // 2. Sửa thông tin
        System.out.println("\n--- Sửa thông tin H03 ---");
        Housing h3_updated = new Housing("H03", "Nha pho", 1200.0, 4, 80.0, "Da Nang");
        manager.editHousing(h3_updated);
        manager.housingList.forEach(System.out::println);

        // 3. Tìm kiếm
        System.out.println("\n--- Tìm kiếm theo tên 'Vin' ---");
        List<Housing> searchResult = manager.searchHousing("Vin");
        searchResult.forEach(System.out::println);

        System.out.println("\n--- Tìm kiếm theo giá (8500.0) ---");
        List<Housing> searchByPriceResult = manager.searchHousingByPrice(8500.0);
        searchByPriceResult.forEach(System.out::println);

        System.out.println("\n--- Tìm kiếm theo diện tích (80.0) ---");
        List<Housing> searchByAreaResult = manager.searchHousingByArea(80.0);
        searchByAreaResult.forEach(System.out::println);

        // 4. Sắp xếp
        System.out.println("\n--- Sắp xếp theo giá tăng dần (Lọc giá >= 1000) ---");
        List<Housing> sortedResult = manager.sortedHousing(1000.0);
        sortedResult.forEach(System.out::println);

        System.out.println("\n--- Sắp xếp theo diện tích tăng dần ---");
        List<Housing> sortedByAreaResult = manager.sortedHousingByArea();
        sortedByAreaResult.forEach(System.out::println);

        // 5. Xóa thông tin
        System.out.println("\n--- Xóa thông tin H01 ---");
        manager.delHousing(h1);
        manager.housingList.forEach(System.out::println);
    }
}