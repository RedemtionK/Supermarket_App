package sample;

public class Product {
    private String name;
    private int quantity = 0;



    private int price;

    public Product(String name, int price, int qunatity) {
        this.name = name;
        this.price = price;
        this.quantity = qunatity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}




