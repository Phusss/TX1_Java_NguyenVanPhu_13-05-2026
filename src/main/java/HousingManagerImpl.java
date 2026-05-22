import java.io.*; // Thư viện đọc/ghi file
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HousingManagerImpl implements HousingManager {
    private List<Housing> housingList = new ArrayList<>();
    private static final String FILE_NAME = "Housing.bin"; // Tên file theo đề bài

    // Constructor: Tự động đọc dữ liệu từ file khi khởi tạo
    public HousingManagerImpl() {
        loadFromFile();
    }

    // --- LOGIC ĐỌC / GHI FILE ---
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(housingList);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                housingList = (List<Housing>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Lỗi đọc file: " + e.getMessage());
            }
        }
    }

    // --- CÁC HÀM IMPLEMENT MỚI CHO TX2 ---
    @Override
    public List<Housing> getAllHousing() {
        return housingList;
    }

    @Override
    public void generateLargeData() {
        if (housingList.isEmpty()) {
            for (int i = 1; i <= 1000; i++) {
                // Tạo 1000 dữ liệu ngẫu nhiên
                housingList.add(new Housing("H" + i, "BDS " + i, 1000 + (Math.random() * 9000), 1, 50 + (Math.random() * 150), "Dia chi " + i));
            }
            saveToFile(); // Lưu xuống file ngay sau khi tạo
        }
    }

    @Override
    public List<Housing> sortedHousing(boolean isAscending) {
        List<Housing> sortedList = new ArrayList<>(housingList);
        if (isAscending) {
            sortedList.sort(Comparator.comparingDouble(Housing::getProduct_price)); // Tăng dần
        } else {
            sortedList.sort((h1, h2) -> Double.compare(h2.getProduct_price(), h1.getProduct_price())); // Giảm dần
        }
        return sortedList;
    }

    // --- CÁC HÀM CŨ TỪ TX1 (ĐÃ THÊM LỆNH saveToFile) ---
    @Override
    public boolean addHousing(Housing h) {
        for (Housing item : housingList) {
            if (item.getProduct_id().equals(h.getProduct_id())) {
                System.out.println("Lỗi: ID đã tồn tại!");
                return false;
            }
        }
        housingList.add(h);
        saveToFile(); // Lưu file khi thêm thành công
        return true;
    }

    @Override
    public boolean editHousing(Housing h) {
        for (int i = 0; i < housingList.size(); i++) {
            if (housingList.get(i).getProduct_id().equals(h.getProduct_id())) {
                housingList.set(i, h);
                saveToFile(); // Lưu file khi sửa thành công
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delHousing(Housing h) {
        boolean isRemoved = housingList.removeIf(item -> item.getProduct_id().equals(h.getProduct_id()));
        if (isRemoved) {
            saveToFile(); // Lưu file khi xóa thành công
        }
        return isRemoved;
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
}