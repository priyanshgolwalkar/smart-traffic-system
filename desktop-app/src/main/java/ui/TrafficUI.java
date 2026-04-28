package ui;

import dao.ViolationDAO;
import model.ViolationRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TrafficUI extends JFrame {

    JTable table;
    DefaultTableModel model;
    JTextField searchField;
    JComboBox<String> zoneFilter;

    ViolationDAO dao = new ViolationDAO();

    public TrafficUI(List<ViolationRecord> violations) {

        setTitle("Smart Traffic Dashboard");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔍 Top Panel (Search + Filter + Refresh)
        JPanel topPanel = new JPanel();

        searchField = new JTextField(10);
        JButton searchBtn = new JButton("Search");

        zoneFilter = new JComboBox<>(new String[]{
                "All", "Pune-West", "Pune-East", "Mumbai", "UNKNOWN_ZONE"
        });
        JButton filterBtn = new JButton("Filter");

        JButton refreshBtn = new JButton("Refresh");

        topPanel.add(new JLabel("Vehicle ID:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        topPanel.add(new JLabel("Zone:"));
        topPanel.add(zoneFilter);
        topPanel.add(filterBtn);

        topPanel.add(refreshBtn);

        add(topPanel, BorderLayout.NORTH);

        // 📊 Table
        String[] columns = {"Vehicle ID", "Speed", "Zone", "Fine"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load initial data
        loadTableData(violations);

        // 🔍 Search Action
        searchBtn.addActionListener(e -> {
            String id = searchField.getText().trim();
            List<ViolationRecord> all = dao.getAllViolations();

            model.setRowCount(0);
            for (ViolationRecord v : all) {
                if (v.vehicleId.equalsIgnoreCase(id)) {
                    addRow(v);
                }
            }
        });

        // 🌍 Filter Action
        filterBtn.addActionListener(e -> {
            String selectedZone = zoneFilter.getSelectedItem().toString();
            List<ViolationRecord> all = dao.getAllViolations();

            model.setRowCount(0);
            for (ViolationRecord v : all) {
                if (selectedZone.equals("All") || v.zone.equalsIgnoreCase(selectedZone)) {
                    addRow(v);
                }
            }
        });

        // 🔄 Refresh Action
        refreshBtn.addActionListener(e -> {
            List<ViolationRecord> all = dao.getAllViolations();
            model.setRowCount(0);
            loadTableData(all);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadTableData(List<ViolationRecord> data) {
        model.setRowCount(0);
        for (ViolationRecord v : data) {
            addRow(v);
        }
    }

    private void addRow(ViolationRecord v) {
        model.addRow(new Object[]{
                v.vehicleId,
                v.speed,
                v.zone,
                v.fine
        });
    }
}