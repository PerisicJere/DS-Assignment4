class SaleRecord {
    String date;
    String salesperson;
    String customerName;
    String carMake;
    String carModel;
    int carYear;
    double salePrice;
    double commissionRate;
    double commissionEarned;




    public SaleRecord(String date, String salesperson, String customerName, String carMake, String carModel, int carYear, double salePrice, double commissionRate, double commissionEarned) {
        this.date = date;
        this.salesperson = salesperson;
        this.customerName = customerName;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.salePrice = salePrice;
        this.commissionRate = commissionRate;
        this.commissionEarned = commissionEarned;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public double getCommissionEarned() {
        return commissionEarned;
    }

    public void setCommissionEarned(double commissionEarned) {
        this.commissionEarned = commissionEarned;
    }
}