/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    //boolean whippedCream = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int calculatePrice(boolean whip, boolean choc){
        int price = quantity * 5;
        if(whip)
            price += quantity * 1;
        if(choc)
            price += quantity * 2;
        return price;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox)findViewById(R.id.checkWhippedCream);
        CheckBox chocolate = (CheckBox)findViewById(R.id.checkChocolate);
        EditText name = (EditText)findViewById(R.id.name);

        String priceMessage = getString(R.string.order_summary_name, name.getText()) + "\n" +
                "Add whipped cream? " + whippedCream.isChecked() + "\n" +
                "Add chocolate? " + chocolate.isChecked() + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: $ " + calculatePrice(whippedCream.isChecked(), chocolate.isChecked()) + "\n" +
                getString(R.string.thank_you);
        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name.getText() );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //Intent with google maps
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if(intent.resolveActivity(getPackageManager())!= null){
//            startActivity(intent);
//        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view){
        if(quantity < 100)
            display(++quantity);
        else{
            Toast.makeText(this, "You cannot have more than 100 coffees!", Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view){
        if(quantity > 1)
            display(--quantity);
        else{
            Toast.makeText(this, "You need to have a least 1 coffee!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}