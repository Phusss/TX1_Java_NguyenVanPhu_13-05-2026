import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HousingGUI extends JFrame {
    private HousingManagerImpl manager;
    private DefaultTableModel tableModel;
    private JTable table;

    // Các ô nhập liệu
    private JTextField txtId, txtName, txtPrice, txtTotal, txtArea, txtAddress, txtSearch;
    private JComboBox<String> cbSearchCriteria;

    public HousingGUI() {
        manager = new HousingManagerImpl();
        // manager.generateLargeData(); 

        setTitle("Quản Lý Bất Động Sản (Housing) - Bài Kiểm Tra 2");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245)); // Màu nền tổng thể sáng sủa

        initComponents();
        loadDataToTable(manager.getAllHousing());
    }

    private void initComponents() {
        // --- 1. BANNER HEADER ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185)); // Màu xanh biển đậm
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setLayout(new GridBagLayout()); // Căn giữa nội dung
        JLabel lblHeader = new JLabel("🏢 HỆ THỐNG QUẢN LÝ BẤT ĐỘNG SẢN");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        // --- PANEL TRUNG TÂM (Chứa Form và Bảng) ---
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        centerPanel.setOpaque(false);

        // --- 2. FORM NHẬP LIỆU ---
        JPanel panelInput = new JPanel(new GridLayout(3, 4, 15, 15));
        panelInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)), "Thông tin chi tiết"));
        panelInput.setBackground(Color.WHITE);

        panelInput.add(new JLabel("Mã BĐS (ID):"));
        txtId = new JTextField("Tự động sinh");
        txtId.setEditable(false); // Khóa không cho người dùng tự nhập ID
        txtId.setBackground(new Color(236, 240, 241));
        panelInput.add(txtId);

        panelInput.add(new JLabel("Tên BĐS:"));
        txtName = new JTextField();
        panelInput.add(txtName);

        panelInput.add(new JLabel("Giá (Price):"));
        txtPrice = new JTextField();
        panelInput.add(txtPrice);

        panelInput.add(new JLabel("Số lượng:"));
        txtTotal = new JTextField();
        panelInput.add(txtTotal);

        panelInput.add(new JLabel("Diện tích:"));
        txtArea = new JTextField();
        panelInput.add(txtArea);

        panelInput.add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        panelInput.add(txtAddress);

        centerPanel.add(panelInput, BorderLayout.NORTH);

        // --- 3. BẢNG DỮ LIỆU ---
        String[] columns = {"ID", "Tên", "Giá", "Số lượng", "Diện tích", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25); // Hàng bảng to rõ hơn
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtPrice.setText(tableModel.getValueAt(row, 2).toString());
                txtTotal.setText(tableModel.getValueAt(row, 3).toString());
                txtArea.setText(tableModel.getValueAt(row, 4).toString());
                txtAddress.setText(tableModel.getValueAt(row, 5).toString());
            }
        });
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // --- 4. PANEL CHỨC NĂNG (NÚT BẤM) ---
        JPanel panelActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelActions.setBackground(Color.WHITE);
        panelActions.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(223, 228, 234)));

        // Sử dụng nút bấm bo góc tự custom
        RoundedButton btnAdd = new RoundedButton("➕ Thêm", new Color(39, 174, 96));
        RoundedButton btnEdit = new RoundedButton("✏️ Sửa", new Color(243, 156, 18));
        RoundedButton btnDelete = new RoundedButton("🗑️ Xóa", new Color(231, 76, 60));

        String[] searchOptions = {"Tìm theo Tên", "Tìm theo Giá", "Tìm theo Diện tích"};
        cbSearchCriteria = new JComboBox<>(searchOptions);
        txtSearch = new JTextField(12);
        txtSearch.setPreferredSize(new Dimension(150, 35));

        RoundedButton btnSearch = new RoundedButton("🔍 Tìm kiếm", new Color(52, 152, 219));
        RoundedButton btnSortAsc = new RoundedButton("📈 Giá Tăng", new Color(142, 68, 173));
        RoundedButton btnSortDesc = new RoundedButton("📉 Giá Giảm", new Color(142, 68, 173));
        RoundedButton btnRefresh = new RoundedButton("🔄 Làm mới", new Color(127, 140, 141));

        panelActions.add(btnAdd);
        panelActions.add(btnEdit);
        panelActions.add(btnDelete);
        panelActions.add(new JLabel(" | "));
        panelActions.add(cbSearchCriteria);
        panelActions.add(txtSearch);
        panelActions.add(btnSearch);
        panelActions.add(btnSortAsc);
        panelActions.add(btnSortDesc);
        panelActions.add(btnRefresh);

        add(panelActions, BorderLayout.SOUTH);

        // --- Bắt sự kiện cho các nút ---
        btnAdd.addActionListener(e -> addAction());
        btnEdit.addActionListener(e -> editAction());
        btnDelete.addActionListener(e -> deleteAction());
        btnSearch.addActionListener(e -> searchAction());
        btnSortAsc.addActionListener(e -> loadDataToTable(manager.sortedHousing(true)));
        btnSortDesc.addActionListener(e -> loadDataToTable(manager.sortedHousing(false)));
        btnRefresh.addActionListener(e -> {
            loadDataToTable(manager.getAllHousing());
            clearForm();
        });
    }

    private void loadDataToTable(List<Housing> list) {
        tableModel.setRowCount(0);
        for (Housing h : list) {
            tableModel.addRow(new Object[]{
                    h.getProduct_id(), h.getProduct_name(), h.getProduct_price(),
                    h.getProduct_total(), h.getArea(), h.getAddress()
            });
        }
    }

    // Hàm tự động sinh ID dựa trên số thứ tự lớn nhất
    private String generateAutoId() {
        int maxId = 0;
        for (Housing h : manager.getAllHousing()) {
            try {
                // Giả sử format ID là H001, H002. Cắt chữ H để lấy số
                int currentNum = Integer.parseInt(h.getProduct_id().replace("H", ""));
                if (currentNum > maxId) {
                    maxId = currentNum;
                }
            } catch (Exception ignored) {} // Bỏ qua nếu có ID không đúng format
        }
        return "H" + String.format("%03d", maxId + 1);
    }

    private void clearForm() {
        txtId.setText("Tự động sinh");
        txtName.setText(""); txtPrice.setText("");
        txtTotal.setText(""); txtArea.setText(""); txtAddress.setText("");
        table.clearSelection();
    }

    private void addAction() {
        try {
            String name = txtName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int total = Integer.parseInt(txtTotal.getText().trim());
            double area = Double.parseDouble(txtArea.getText().trim());
            String address = txtAddress.getText().trim();

            // Tự động sinh ID tại đây
            String autoId = generateAutoId();
            Housing h = new Housing(autoId, name, price, total, area, address);

            if (manager.addHousing(h)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công! Mã tự động sinh: " + autoId);
                loadDataToTable(manager.getAllHousing());
                clearForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Giá, Số lượng và Diện tích!");
        }
    }

    private void editAction() {
        String id = txtId.getText().trim();
        if (id.equals("Tự động sinh") || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một BĐS trong bảng để sửa!");
            return;
        }
        try {
            String name = txtName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int total = Integer.parseInt(txtTotal.getText().trim());
            double area = Double.parseDouble(txtArea.getText().trim());
            String address = txtAddress.getText().trim();

            Housing h = new Housing(id, name, price, total, area, address);
            if (manager.editHousing(h)) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                loadDataToTable(manager.getAllHousing());
                clearForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!");
        }
    }

    private void deleteAction() {
        String id = txtId.getText().trim();
        if (id.equals("Tự động sinh") || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một BĐS trong bảng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa BĐS mã " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Housing h = new Housing();
            h.setProduct_id(id);
            if (manager.delHousing(h)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadDataToTable(manager.getAllHousing());
                clearForm();
            }
        }
    }

    private void searchAction() {
        String keyword = txtSearch.getText().trim();
        int criteria = cbSearchCriteria.getSelectedIndex();
        try {
            if (criteria == 0) loadDataToTable(manager.searchHousing(keyword));
            else if (criteria == 1) loadDataToTable(manager.searchHousingByPrice(Double.parseDouble(keyword)));
            else if (criteria == 2) loadDataToTable(manager.searchHousingByArea(Double.parseDouble(keyword)));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ khi tìm theo Giá/Diện tích!");
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new HousingGUI().setVisible(true));
    }

    // --- CLASS NÚT BẤM BO GÓC (TÙY CHỈNH) ---
    class RoundedButton extends JButton {
        private Color bgColor;

        public RoundedButton(String text, Color bgColor) {
            super(text);
            this.bgColor = bgColor;
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(8, 15, 8, 15)); // Căn lề cho nút to ra
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Bo góc 15px
            super.paintComponent(g);
            g2.dispose();
        }
    }
}