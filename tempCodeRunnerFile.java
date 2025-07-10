

    // Label for the card title (e.g., "Total Income")
    JLabel titleLabel = new JLabel(title, SwingConstants.CENTER); // Centered text
    titleLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Set font style
    titleLabel.setForeground(color); // Set text color to match border

    // Label for the main value (e.g., ₹5,000.00)
    JLabel valueLabel = new JLabel(value, SwingConstants.CENTER); // Centered text
    valueLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Slightly larger font
    valueLabel.setForeground(color); // Same color for consistency

    // Add both labels to card
    card.add(titleLabel, BorderLayout.NORTH); // Title at top
    card.add(valueLabel, BorderLayout.CENTER); // Value in center

    return card; // Return the final panel
}
private JPanel createTransactionsPanel() {
    JPanel panel = new JPanel(new BorderLayout()); // Main panel layout

    // Define column names for the table
    String[] columnNames = {"Date", "Type", "Category", "Description", "Amount"};
    
    // Create table model with columns, no initial rows
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make all cells read-only
        }
    };

    // Create JTable to display transaction data
    JTable transactionTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(transactionTable); // Add scroll bar support

    // Panel for transaction buttons
    JPanel buttonPanel = new JPanel(new FlowLayout()); // Horizontal layout for buttons

    // Buttons for transaction actions
    JButton addBtn = new JButton("Add Transaction");
    JButton editBtn = new JButton("Edit");
    JButton deleteBtn = new JButton("Delete");
    JButton refreshBtn = new JButton("Refresh");

    // Action listeners for button functionality
    addBtn.addActionListener(e -> showAddTransactionDialog(null)); // Open add dialog
    editBtn.addActionListener(e -> editSelectedTransaction(transactionTable)); // Edit selected
    deleteBtn.addActionListener(e -> deleteSelectedTransaction(transactionTable, tableModel)); // Delete selected
    refreshBtn.addActionListener(e -> refreshTransactionTable(tableModel)); // Refresh list

    // Add buttons to panel
    buttonPanel.add(addBtn);
    buttonPanel.add(editBtn);
    buttonPanel.add(deleteBtn);
    buttonPanel.add(refreshBtn);

    // Add table and buttons to the main panel
    panel.add(scrollPane, BorderLayout.CENTER); // Table in center
    panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at bottom

    // Load initial transaction data
    refreshTransactionTable(tableModel);

    return panel; // Return the final transactions panel
}
private JPanel createBudgetPanel() {
    JPanel panel = new JPanel(new BorderLayout()); // Main layout

    // Define column names for budget table
    String[] columnNames = {"Category", "Budget", "Spent", "Remaining", "Status"};

    // Create table model with columns and 0 rows
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make cells read-only
        }
    };

    // Create JTable with the model
    JTable budgetTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(budgetTable); // Add scroll support

    // Panel for budget-related buttons
    JPanel buttonPanel = new JPanel(new FlowLayout());

    // Buttons to manage budget
    JButton addBudgetBtn = new JButton("Add Budget");