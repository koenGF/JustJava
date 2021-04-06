package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.order_name);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String orderSummary = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        composeEmail(orderSummary);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean chocolate, boolean cream) {
        int pricePerCup = 5;
        if (chocolate) {
            pricePerCup += 2;
        }
        if (cream) {
            pricePerCup += 1;
        }
        return quantity * pricePerCup;
    }

    /**
     * creates an order summary
     *
     * @param price
     * @param hasCream
     * @param hasChoco
     * @return order summary
     */
    private String createOrderSummary(String name, int price, boolean hasCream, boolean hasChoco) {
        return "name: " + name + "\n" +
                "Quantity: " + quantity + "\n" +
                "cream: " + hasCream + "\n" +
                "chocolate: " + hasChoco + "\n" +
                "Total: $" + price + "\n" +
                "Thank you!";
    }

    /**
     * compose an email containing order information.
     *
     *
     */
    private void composeEmail(String summary) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "example@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "new order");
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;
        }
        displayQuantity(quantity);
    }
}