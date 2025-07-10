// Import necessary Java Swing and utility libraries
import javax.swing.*; // For GUI components like JFrame, JPanel, JButton etc.
import javax.swing.table.DefaultTableModel; // For creating tables
import java.awt.*; // For layouts and colors
import java.text.DecimalFormat; // To format numbers (like currency)
import java.text.SimpleDateFormat; // To format dates
import java.util.*; // For general utilities like ArrayList, Date
import java.util.List; // For using List specifically

// This is the main class of the application and extends JFrame to create a GUI window
public class PersonalFinanceManager extends JFrame {
    
    // Declare UI components and helpers
    private JTabbedPane tabbedPane; // A tabbed pane to hold different sections like Dashboard, Transactions etc.
    private TransactionManager transactionManager; // Handles transaction-related logic
    private BudgetManager budgetManager; // Handles budget-related logic
    private DecimalFormat currencyFormat; // Used to format currency values (e.g., ₹1,000.00)

    // Constructor - runs when the object of this class is created
    public PersonalFinanceManager() {
        // Initialize the manager objects
        transactionManager = new TransactionManager();
        budgetManager = new BudgetManager();

        // Set currency format with Indian Rupee symbol
        currencyFormat = new DecimalFormat("₹#,##0.00");
        
        // Set application icon (you must have "image.png" in your project directory)
        ImageIcon icon = new ImageIcon("image.png");
        setIconImage(icon.getImage());

        // Set main window properties
        setLayout(new BorderLayout()); // Use BorderLayout for main layout
        setTitle("Personal Finance Management System"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when window is closed
        setSize(800, 600); // Set window size
        setLocationRelativeTo(null); // Center the window on screen
        
        // Call function to initialize GUI components
        initializeComponents();

        // Make the window visible
        setVisible(true);
    }

    // This function sets up the tabbed interface
    private void initializeComponents() {
        tabbedPane = new JTabbedPane(); // Create tab container
        
        // Add each main tab to the tabbed pane
        tabbedPane.addTab("Dashboard", createDashboardPanel()); // Summary and quick actions
        tabbedPane.addTab("Transactions", createTransactionsPanel()); // Shows all transactions
        tabbedPane.addTab("Budget", createBudgetPanel()); // Set/view monthly budget
        tabbedPane.addTab("Reports", createReportsPanel()); // Show charts or reports

        // Add the tabbed pane to the main frame
        add(tabbedPane);
    }

    // Create the Dashboard panel (home page of app)
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout()); // Main panel using BorderLayout
        panel.setBorder(BorderFactory.createTitledBorder("Financial Dashboard")); // Add title border

        // Create a panel to show 6 financial summary cards
        JPanel summaryPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 rows x 3 columns
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding inside the panel

        // Create individual summary cards with labels and data
        JPanel totalIncomeCard = createSummaryCard(
            "Total Income",
            currencyFormat.format(transactionManager.getTotalIncome()), // Format income
            Color.GREEN
        );
        JPanel totalExpensesCard = createSummaryCard(
            "Total Expenses",
            currencyFormat.format(transactionManager.getTotalExpenses()),
            Color.RED
        );
        JPanel balanceCard = createSummaryCard(
            "Balance",
            currencyFormat.format(transactionManager.getBalance()),
            Color.BLUE
        );
        JPanel transactionsCard = createSummaryCard(
            "Transactions",
            String.valueOf(transactionManager.getTransactionCount()), // Just convert to string
            Color.ORANGE
        );
        JPanel budgetCard = createSummaryCard(
            "Budget Status",
            budgetManager.getBudgetStatus(), // Maybe like "On Track" or "Over Budget"
            Color.MAGENTA
        );
        JPanel savingsCard = createSummaryCard(
            "This Month",
            currencyFormat.format(transactionManager.getThisMonthBalance()), // Current month net balance
            Color.CYAN
        );

        // Add all cards to the summary panel
        summaryPanel.add(totalIncomeCard);
        summaryPanel.add(totalExpensesCard);
        summaryPanel.add(balanceCard);
        summaryPanel.add(transactionsCard);
        summaryPanel.add(budgetCard);
        summaryPanel.add(savingsCard);

        // Add summary panel to center of the dashboard
        panel.add(summaryPanel, BorderLayout.CENTER);

        // Create panel for quick action buttons
        JPanel actionsPanel = new JPanel(new FlowLayout()); // Horizontal button layout
        JButton addIncomeBtn = new JButton("Add Income"); // Button to add income
        JButton addExpenseBtn = new JButton("Add Expense"); // Button to add expense
        JButton viewReportBtn = new JButton("View Report"); // Button to go to Reports tab

        // Set actions for the buttons when clicked
        addIncomeBtn.addActionListener(e -> showAddTransactionDialog("INCOME"));
        addExpenseBtn.addActionListener(e -> showAddTransactionDialog("EXPENSE"));
        viewReportBtn.addActionListener(e -> tabbedPane.setSelectedIndex(3)); // Go to "Reports" tab

        // Add buttons to the panel
        actionsPanel.add(addIncomeBtn);
        actionsPanel.add(addExpenseBtn);
        actionsPanel.add(viewReportBtn);

        // Add actions panel to bottom of the dashboard
        panel.add(actionsPanel, BorderLayout.SOUTH);

        return panel; // Return the dashboard panel
    }
// This method creates a single summary card (like Total Income, Expenses etc.)
private JPanel createSummaryCard(String title, String value, Color color) {
    JPanel card = new JPanel(new BorderLayout()); // Use border layout to place items vertically
    card.setBorder(BorderFactory.createLineBorder(color, 2)); // Add colored border
    card.setBackground(Color.WHITE); // Set background color

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
    JButton editBudgetBtn = new JButton("Edit Budget");
    JButton deleteBudgetBtn = new JButton("Delete Budget");
    JButton refreshBudgetBtn = new JButton("Refresh");

    // Action listeners for each button
    addBudgetBtn.addActionListener(e -> showAddBudgetDialog()); // Open add budget dialog
    editBudgetBtn.addActionListener(e -> editSelectedBudget(budgetTable)); // Edit selected row
    deleteBudgetBtn.addActionListener(e -> deleteSelectedBudget(budgetTable, tableModel)); // Delete selected row
    refreshBudgetBtn.addActionListener(e -> refreshBudgetTable(tableModel)); // Reload budget data

    // Add all buttons to the panel
    buttonPanel.add(addBudgetBtn);
    buttonPanel.add(editBudgetBtn);
    buttonPanel.add(deleteBudgetBtn);
    buttonPanel.add(refreshBudgetBtn);

    // Add table and button panel to the main budget panel
    panel.add(scrollPane, BorderLayout.CENTER); // Budget table in center
    panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at bottom

    // Load initial budget data
    refreshBudgetTable(tableModel);

    return panel; // Return the budget panel
}
  
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Report text area
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton monthlyReportBtn = new JButton("Monthly Report");
        JButton categoryReportBtn = new JButton("Category Report");
        JButton budgetReportBtn = new JButton("Budget Report");
        
        monthlyReportBtn.addActionListener(e -> generateMonthlyReport(reportArea));
        categoryReportBtn.addActionListener(e -> generateCategoryReport(reportArea));
        budgetReportBtn.addActionListener(e -> generateBudgetReport(reportArea));
        
        buttonPanel.add(monthlyReportBtn);
        buttonPanel.add(categoryReportBtn);
        buttonPanel.add(budgetReportBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void showAddTransactionDialog(String type) {
        JDialog dialog = new JDialog(this, "Add Transaction", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Type
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"INCOME", "EXPENSE"});
        if (type != null) {
            typeCombo.setSelectedItem(type);
        }
        panel.add(typeCombo, gbc);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JTextField categoryField = new JTextField(20);
        panel.add(categoryField, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextField descField = new JTextField(20);
        panel.add(descField, gbc);
        
        // Amount
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        JTextField amountField = new JTextField(20);
        panel.add(amountField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            try {
                String transactionType = (String) typeCombo.getSelectedItem();
                String category = categoryField.getText().trim();
                String description = descField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                
                if (category.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
                    return;
                }
                
                Transaction transaction = new Transaction(transactionType, category, description, amount);
                transactionManager.addTransaction(transaction);
                
                dialog.dispose();
                refreshDashboard();
                JOptionPane.showMessageDialog(this, "Transaction added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid amount.");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showAddBudgetDialog() {
        JDialog dialog = new JDialog(this, "Add Budget", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JTextField categoryField = new JTextField(15);
        panel.add(categoryField, gbc);
        
        // Budget Amount
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Budget:"), gbc);
        gbc.gridx = 1;
        JTextField budgetField = new JTextField(15);
        panel.add(budgetField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            try {
                String category = categoryField.getText().trim();
                double budget = Double.parseDouble(budgetField.getText().trim());
                
                if (category.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a category.");
                    return;
                }
                
                budgetManager.addBudget(category, budget);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Budget added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid budget amount.");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void refreshTransactionTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Transaction t : transactionManager.getTransactions()) {
            Object[] row = {
                dateFormat.format(t.getDate()),
                t.getType(),
                t.getCategory(),
                t.getDescription(),
                currencyFormat.format(t.getAmount())
            };
            tableModel.addRow(row);
        }
    }
    
    private void refreshBudgetTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        
        for (Budget b : budgetManager.getBudgets()) {
            double spent = transactionManager.getSpentByCategory(b.getCategory());
            double remaining = b.getAmount() - spent;
            String status = remaining >= 0 ? "On Track" : "Over Budget";
            
            Object[] row = {
                b.getCategory(),
                currencyFormat.format(b.getAmount()),
                currencyFormat.format(spent),
                currencyFormat.format(remaining),
                status
            };
            tableModel.addRow(row);
        }
    }
    
    private void editSelectedTransaction(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to edit.");
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Edit functionality would be implemented here.");
    }
    
    private void deleteSelectedTransaction(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to delete.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this transaction?");
        if (confirm == JOptionPane.YES_OPTION) {
            transactionManager.deleteTransaction(selectedRow);
            refreshTransactionTable(model);
            refreshDashboard();
        }
    }
    
    private void editSelectedBudget(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a budget to edit.");
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Edit budget functionality would be implemented here.");
    }
    
    private void deleteSelectedBudget(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a budget to delete.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this budget?");
        if (confirm == JOptionPane.YES_OPTION) {
            budgetManager.deleteBudget(selectedRow);
            refreshBudgetTable(model);
        }
    }
    
    private void generateMonthlyReport(JTextArea reportArea) {
        StringBuilder report = new StringBuilder();
        report.append("=== MONTHLY FINANCIAL REPORT ===\n\n");
        
        report.append("Income: ").append(currencyFormat.format(transactionManager.getTotalIncome())).append("\n");
        report.append("Expenses: ").append(currencyFormat.format(transactionManager.getTotalExpenses())).append("\n");
        report.append("Net Balance: ").append(currencyFormat.format(transactionManager.getBalance())).append("\n\n");
        
        report.append("=== EXPENSE BREAKDOWN ===\n");
        Map<String, Double> categoryTotals = transactionManager.getCategoryTotals();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            report.append(entry.getKey()).append(": ").append(currencyFormat.format(entry.getValue())).append("\n");
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateCategoryReport(JTextArea reportArea) {
        StringBuilder report = new StringBuilder();
        report.append("=== CATEGORY SPENDING REPORT ===\n\n");
        
        Map<String, Double> categoryTotals = transactionManager.getCategoryTotals();
        double totalExpenses = transactionManager.getTotalExpenses();
        
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            double percentage = (entry.getValue() / totalExpenses) * 100;
            report.append(String.format("%-15s: %s (%.1f%%)\n", 
                entry.getKey(), currencyFormat.format(entry.getValue()), percentage));
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateBudgetReport(JTextArea reportArea) {
        StringBuilder report = new StringBuilder();
        report.append("=== BUDGET ANALYSIS REPORT ===\n\n");
        
        for (Budget budget : budgetManager.getBudgets()) {
            double spent = transactionManager.getSpentByCategory(budget.getCategory());
            double remaining = budget.getAmount() - spent;
            double percentage = (spent / budget.getAmount()) * 100;
            
            report.append(String.format("Category: %s\n", budget.getCategory()));
            report.append(String.format("Budget: %s\n", currencyFormat.format(budget.getAmount())));
            report.append(String.format("Spent: %s (%.1f%%)\n", currencyFormat.format(spent), percentage));
            report.append(String.format("Remaining: %s\n", currencyFormat.format(remaining)));
            report.append(String.format("Status: %s\n\n", remaining >= 0 ? "On Track" : "Over Budget"));
        }
        
        reportArea.setText(report.toString());
    }
    
    private void refreshDashboard() {
        // Refresh dashboard by recreating the tab
        Component dashboard = createDashboardPanel();
        tabbedPane.setComponentAt(0, dashboard);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PersonalFinanceManager();
        });
    }
}

// Transaction class
class Transaction {
    private Date date;
    private String type;
    private String category;
    private String description;
    private double amount;
    
    public Transaction(String type, String category, String description, double amount) {
        this.date = new Date();
        this.type = type;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }
    
    // Getters
    public Date getDate() { return date; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    
    // Setters
    public void setType(String type) { this.type = type; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
}

// Transaction Manager class
class TransactionManager {
    private List<Transaction> transactions;
    
    public TransactionManager() {
        transactions = new ArrayList<>();
        // Add some sample data
        addSampleData();
    }
    
    private void addSampleData() {
        transactions.add(new Transaction("INCOME", "Salary", "Monthly salary", 5000.00));
        transactions.add(new Transaction("EXPENSE", "Food", "Groceries", 150.00));
        transactions.add(new Transaction("EXPENSE", "Transportation", "Gas", 60.00));
        transactions.add(new Transaction("EXPENSE", "Entertainment", "Movie tickets", 25.00));
        transactions.add(new Transaction("INCOME", "Freelance", "Web development", 800.00));
    }
    
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    public void deleteTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
        }
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType().equals("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public double getTotalExpenses() {
        return transactions.stream()
                .filter(t -> t.getType().equals("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }
    
    public int getTransactionCount() {
        return transactions.size();
    }
    
    public double getThisMonthBalance() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        
        return transactions.stream()
                .filter(t -> {
                    Calendar tCal = Calendar.getInstance();
                    tCal.setTime(t.getDate());
                    return tCal.get(Calendar.MONTH) == currentMonth && 
                           tCal.get(Calendar.YEAR) == currentYear;
                })
                .mapToDouble(t -> t.getType().equals("INCOME") ? t.getAmount() : -t.getAmount())
                .sum();
    }
    
    public double getSpentByCategory(String category) {
        return transactions.stream()
                .filter(t -> t.getType().equals("EXPENSE") && t.getCategory().equals(category))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public Map<String, Double> getCategoryTotals() {
        Map<String, Double> categoryTotals = new HashMap<>();
        
        transactions.stream()
                .filter(t -> t.getType().equals("EXPENSE"))
                .forEach(t -> {
                    categoryTotals.merge(t.getCategory(), t.getAmount(), Double::sum);
                });
        
        return categoryTotals;
    }
}

// Budget class
class Budget {
    private String category;
    private double amount;
    
    public Budget(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
    
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    
    public void setCategory(String category) { this.category = category; }
    public void setAmount(double amount) { this.amount = amount; }
}

// Budget Manager class
class BudgetManager {
    private List<Budget> budgets;
    
    public BudgetManager() {
        budgets = new ArrayList<>();
        // Add sample budgets
        budgets.add(new Budget("Food", 500.00));
        budgets.add(new Budget("Transportation", 200.00));
        budgets.add(new Budget("Entertainment", 100.00));
    }
    
    public void addBudget(String category, double amount) {
        budgets.add(new Budget(category, amount));
    }
    
    public void deleteBudget(int index) {
        if (index >= 0 && index < budgets.size()) {
            budgets.remove(index);
        }
    }
    
    public List<Budget> getBudgets() {
        return budgets;
    }
    
    public String getBudgetStatus() {
        if (budgets.isEmpty()) return "No budgets";
        
        long onTrack = budgets.stream()
                .filter(b -> b.getAmount() > 0) // Simplified check
                .count();
        
        return onTrack + "/" + budgets.size() + " on track";
    }
}