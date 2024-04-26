package com.example.pointofsale;

public class Receipt
{
    public String barcode;
    public String name;
    public String quantity;
    public String price;

    public Receipt(String barcode, String name, String quantity, String price) {
        this.barcode = barcode;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
