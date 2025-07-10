# Personal Finance Manager 💰

A comprehensive Java-based desktop application for managing personal finances with an intuitive GUI built using Java Swing.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

## 🌟 Features

### 📊 Dashboard
- **Financial Summary Cards**: Six key metrics displayed prominently
  - Total Income
  - Total Expenses  
  - Current Balance
  - Transaction Count
  - Budget Status
  - This Month's Balance
- **Quick Actions**: One-click buttons for adding income, expenses, and viewing reports
- **Visual Design**: Color-coded cards with Indian Rupee (₹) currency formatting

### 💳 Transaction Management
- **Add Transactions**: Support for both income and expense entries
- **Transaction History**: Tabular view with date, type, category, description, and amount
- **Edit/Delete**: Modify or remove existing transactions
- **Categories**: Flexible categorization (Food, Transportation, Entertainment, etc.)

### 📈 Budget Management
- **Budget Creation**: Set spending limits for different categories
- **Budget Tracking**: Monitor actual spending against budgeted amounts
- **Status Indicators**: Visual feedback ("On Track" vs "Over Budget")
- **Budget Analysis**: Compare allocated vs spent amounts with percentage calculations

### 📋 Reporting System
- **Monthly Reports**: Complete income vs expense breakdown
- **Category Reports**: Spending analysis by category with percentages
- **Budget Reports**: Detailed budget performance analysis
- **Export-Ready**: Text-based reports suitable for printing or sharing

## 🏗️ Technical Architecture

### Core Classes
- **PersonalFinanceManager**: Main application class extending JFrame
- **Transaction**: Data model for individual transactions
- **TransactionManager**: Business logic for transaction operations
- **Budget**: Data model for budget entries
- **BudgetManager**: Business logic for budget operations

### Technology Stack
- **GUI Framework**: Java Swing
- **Layout Managers**: BorderLayout, GridLayout, FlowLayout, GridBagLayout
- **Data Storage**: In-memory ArrayList collections
- **Date Handling**: Java Date and Calendar classes
- **Formatting**: DecimalFormat for currency, SimpleDateFormat for dates

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans) or text editor
- Git (optional, for cloning)

### Installation & Setup

#### Option 1: Clone Repository
```bash
# Clone the repository
git clone https://github.com/yourusername/personal-finance-manager.git

# Navigate to project directory
cd personal-finance-manager
```

#### Option 2: Download ZIP
1. Click on the green "Code" button above
2. Select "Download ZIP"
3. Extract the downloaded file to your preferred location

### 🏃‍♂️ Running the Application

#### Method 1: Using IDE
1. Open your preferred Java IDE
2. Import the project as a Java project
3. Navigate to the main class `PersonalFinanceManager.java`
4. Right-click and select "Run as Java Application"

#### Method 2: Using Command Line
```bash
# Navigate to the src directory
cd src

# Compile the Java files
javac *.java

# Run the main class
java PersonalFinanceManager
```

#### Method 3: Using JAR File (if available)
```bash
# If JAR file is provided
java -jar PersonalFinanceManager.jar
```



## 🎯 Sample Data

The application comes with pre-populated sample data to help you get started:

**Sample Transactions:**
- Salary: ₹50,000 (Income)
- Groceries: ₹3,500 (Expense)
- Gas: ₹2,000 (Expense)
- Movie Tickets: ₹800 (Expense)
- Freelance Income: ₹15,000 (Income)

**Sample Budgets:**
- Food: ₹5,000
- Transportation: ₹2,000
- Entertainment: ₹1,000

## 📸 Screenshots

### Dashboard
![Dashboard](screenshots/dashboard.png)

### Transaction Management
![Transactions](screenshots/transactions.png)

### Budget Tracking
![Budget](screenshots/budget.png)

### Reports
![Reports](screenshots/reports.png)

## 🔧 Key Functionalities

### Financial Calculations
- **Income Totals**: Sum of all income transactions
- **Expense Totals**: Sum of all expense transactions
- **Balance Calculation**: Income minus expenses
- **Monthly Tracking**: Current month financial performance
- **Category Analysis**: Spending breakdown by category
- **Budget Variance**: Comparison of budgeted vs actual spending

### User Experience Features
- **One-Click Actions**: Quick access to common operations
- **Clear Navigation**: Intuitive tab-based structure
- **Visual Feedback**: Color-coded status indicators
- **Error Handling**: User-friendly error messages and validation
- **Keyboard Navigation**: Support for keyboard shortcuts

## 🚀 Future Enhancements

### Technical Improvements
- [ ] Database integration for persistent storage
- [ ] CSV/PDF export functionality
- [ ] Chart integration for data visualization
- [ ] Multi-user support with authentication

### Feature Additions
- [ ] Recurring transactions
- [ ] Financial goal setting and tracking
- [ ] Budget limit alerts and notifications
- [ ] Mobile companion app

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [Hari06om](https://github.com/Hari06om)
- LinkedIn: [hari-om-verma20](https://linkedin.com/in/hari-om-verma20)

## 🙏 Acknowledgments

- Java Swing documentation and community
- Personal finance management best practices
- Open source community for inspiration

---

⭐ **If you found this project helpful, please give it a star!** ⭐

---

**Note**: This application is designed for educational and personal use. For production use, consider implementing additional security measures and data validation.
