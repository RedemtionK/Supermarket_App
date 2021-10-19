package sample;

import java.util.ArrayList;

public class Suppliers {
    private String name;
    private ArrayList<String> products = new ArrayList <>();

    public Suppliers(String name, ArrayList <String > products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList <String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList <String > products) {
        this.products = products;
    }
}
