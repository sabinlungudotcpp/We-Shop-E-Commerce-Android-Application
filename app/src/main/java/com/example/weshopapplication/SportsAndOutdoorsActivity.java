package com.example.weshopapplication;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;


// Author of Application & Class: Sabin Constantin Lungu
// Purpose of Class: Stores the code needed to implement the Sports and Outdoors Activity 1.
// Date of Last Modification: 13/02/2020
// Any Errors? Currently None.


public class SportsAndOutdoorsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int current_product_id = 1;
    private ImageView cartIcon;
    private TextView firstSportsOutdoorTxt;
    private ImageView firstSportsOutdoorImg;
    private TextView firstSportsOutdoorCostTxt;
    private TextView firstSportsOutdoorColourLbl;
    private Spinner firstSportsOutdoorsColourMenu;


    private TextView firstSportsOutdoorQuantityLbl;
    private Spinner firstSportsOutdoorQuantityMenu;

    private TextView firstSportsOutdoorsSizeLbl;
    private Spinner firstSportsOutdoorsSizeMenu;
    private Button firstSportsAddToBasketBtn;

    private TextView secondSportsOutdoorTxt;
    private ImageView secondSportsOutdoorImg;
    private TextView secondSportsOutdoorCostLbl;
    private TextView secondSportsOutdoorColourLbl;
    private Spinner secondSportsOutdoorsColourMenu;

    private TextView secondSportsOutdoorQuantityLbl;
    private Spinner secondSportsOutdoorQuantityMenu;

    private TextView secondSportsOutdoorSizeLbl;
    private Spinner secondSportsOutdoorSizeMenu;
    private Button secondSportsAddToBasketBtn;
    private Button nextPageBtn;

    // The costs of the products
    private double[] productOneCosts = {0.00, 90.00, 180.00, 360.00, 720.00, 1440.00};
    private double[] productTwoCosts = {0.00, 50.00, 100.00, 150.00, 200.00, 250.00};

    private ArrayList<TechActivity.Colours> listOfColoursOne; // An array list of colours
    private ArrayList<TechActivity.Quantities> listOfQuantitiesOne; // An array list of quantities
    private ArrayList<Size> listOfSizesOne;

    private ArrayList<TechActivity.Colours> listOfColoursTwo;
    private ArrayList<TechActivity.Quantities> listOfQuantitiesTwo;
    private ArrayList<Size> listOfSizesTwo;

    private ColourArrayAdapter coloursAdapter; // A colours adapter is needed to store objects in a drop-down menu (spinner)
    private CustomArrayAdapter quantitiesAdapter;
    private SizeArrayAdapter sizeArrayAdapter;

    private boolean coloursAdded; // Flag to determine if the colours have been added to the drop-down list
    private boolean quantitiesAdded;

    private boolean sizesAdded;
    private boolean addedToBasket; // True or false to determine if the products have been added to the basket after adding to the hash map

    private HashMap<Integer, Products> listOfProductsToAddToBasket = new HashMap<>(); // A HashMap of products to add to the basket with a corresponding ID that each product will have.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_and_outdoors);

        // INITIALISE COMPONENTS
        this.firstSportsOutdoorTxt = findViewById(R.id.firstSportsOutdoorTxt);
        this.firstSportsOutdoorImg = findViewById(R.id.firstSportsOutdoorImg);
        this.firstSportsOutdoorCostTxt = findViewById(R.id.firstSportsOutdoorCostLbl);
        this.firstSportsOutdoorColourLbl = findViewById(R.id.firstSportsColourLbl);

        this.firstSportsOutdoorsColourMenu = findViewById(R.id.firstSportsOutdoorColourMenu);
        this.firstSportsOutdoorQuantityLbl = findViewById(R.id.firstSportsOutdoorQuantityLbl);
        this.firstSportsOutdoorQuantityMenu = findViewById(R.id.firstSportsOutdoorQuantityMenu);

        this.firstSportsOutdoorsSizeLbl = findViewById(R.id.firstSportsOutdoorSizeLbl);
        this.firstSportsOutdoorsSizeMenu = findViewById(R.id.firstSportsOutdoorSizeMenu);
        this.firstSportsAddToBasketBtn = findViewById(R.id.firstAddToBasketBtn); // Button for the first product to add to the basket.

        this.secondSportsOutdoorTxt = findViewById(R.id.secondSportsOutdoorsProductTxt);
        this.secondSportsOutdoorImg = findViewById(R.id.secondSportsOutdoorsImg);
        this.secondSportsOutdoorCostLbl = findViewById(R.id.secondSportsOutdoorProductCostLbl);
        this.secondSportsOutdoorColourLbl = findViewById(R.id.secondSportsOutdoorsColourLbl);
        this.secondSportsOutdoorsColourMenu = findViewById(R.id.secondSportsOutdoorsColourMenu);

        this.secondSportsOutdoorQuantityLbl = findViewById(R.id.secondsSportsOutdoorQuantityLbl);
        this.secondSportsOutdoorQuantityMenu = findViewById(R.id.secondSportsOutdoorsQuantityMenu);
        this.secondSportsOutdoorSizeLbl = findViewById(R.id.secondSportsOutdoorsSizeLbl);
        this.secondSportsOutdoorSizeMenu = findViewById(R.id.secondSportsOutdoorsSizeMenu);

        this.secondSportsAddToBasketBtn = findViewById(R.id.secondSportsOutdoorsAddToBasketBtn);
        this.nextPageBtn = findViewById(R.id.nextPageTech); // Button for taking the user to the next page.

        // Create the array lists
        this.listOfColoursOne = new ArrayList<>();
        this.listOfQuantitiesOne = new ArrayList<>();
        this.listOfSizesOne = new ArrayList<>();

        this.listOfColoursTwo = new ArrayList<>();
        this.listOfQuantitiesTwo = new ArrayList<>();
        this.listOfSizesTwo = new ArrayList<>();

        // Method calls to add to the specific array lists
        addToColoursList();
        addToQuantitiesList();
        addToSizesList();

        // Set up the colours adapter
        this.coloursAdapter = new ColourArrayAdapter(SportsAndOutdoorsActivity.this, listOfColoursOne);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstSportsOutdoorsColourMenu.setAdapter(coloursAdapter);
        firstSportsOutdoorsColourMenu.setOnItemSelectedListener(this);

        // Create array adapter for the quantities for product 1
        this.quantitiesAdapter = new CustomArrayAdapter(SportsAndOutdoorsActivity.this, listOfQuantitiesOne);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstSportsOutdoorQuantityMenu.setAdapter(quantitiesAdapter);
        firstSportsOutdoorQuantityMenu.setOnItemSelectedListener(this);

        // Create array adapter for the sizes
        this.sizeArrayAdapter = new SizeArrayAdapter(SportsAndOutdoorsActivity.this, listOfSizesOne);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstSportsOutdoorsSizeMenu.setAdapter(sizeArrayAdapter);
        firstSportsOutdoorsSizeMenu.setOnItemSelectedListener(this);

        this.coloursAdapter = new ColourArrayAdapter(SportsAndOutdoorsActivity.this, listOfColoursTwo);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondSportsOutdoorsColourMenu.setAdapter(coloursAdapter);
        secondSportsOutdoorsColourMenu.setOnItemSelectedListener(this);

        // Create the Array Adapter for the quantities for the second product
        this.quantitiesAdapter = new CustomArrayAdapter(SportsAndOutdoorsActivity.this, listOfQuantitiesTwo);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondSportsOutdoorQuantityMenu.setAdapter(quantitiesAdapter);
        secondSportsOutdoorQuantityMenu.setOnItemSelectedListener(this);

        // Create the sizes array adapter for the second product
        this.sizeArrayAdapter = new SizeArrayAdapter(SportsAndOutdoorsActivity.this, listOfSizesTwo);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondSportsOutdoorSizeMenu.setAdapter(sizeArrayAdapter);
        secondSportsOutdoorSizeMenu.setOnItemSelectedListener(this);

        this.nextPageBtn = findViewById(R.id.nextPageTech); // The next page button

        this.nextPageBtn.setOnClickListener(new View.OnClickListener() { // Add an action listener to the next page button to take the user to the next page
            @Override
            public void onClick(View v) {
                try {

                    Intent nextSportsActivity = new Intent(SportsAndOutdoorsActivity.this, SportsAndOutdoorsActivityTwo.class);
                    startActivity(nextSportsActivity);

                } catch (ActivityNotFoundException exc) { // Catch the error if the activity has not been found

                    Log.d("Error : ", exc.toString()); // Log the error to the console
                }
            }
        });

        this.firstSportsAddToBasketBtn.setOnClickListener(new View.OnClickListener() { // Add action listener to the first button
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.firstAddToBasketBtn) {

                    if (firstSportsOutdoorsColourMenu.getSelectedItemPosition() == 0 || firstSportsOutdoorsSizeMenu.getSelectedItemPosition() == 0 || firstSportsOutdoorsSizeMenu.getSelectedItemId() == 0) {

                        AlertDialog.Builder error = new AlertDialog.Builder(SportsAndOutdoorsActivity.this) // Create an alert dialogue for the user to see
                                .setTitle("Error")
                                .setMessage("You must select a valid option before adding the product to the basket")

                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) { // If there is a dialog
                                            dialog.dismiss(); // Dismiss it
                                        }
                                    }
                                });

                        error.show();
                        error.setCancelable(true);

                    } else {

                        addToBasketOne(); // Otherwise add the product to the basket one
                    }
                }

            }
        });

        this.secondSportsAddToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.secondAddToBasketBtn) { // If the second add to basket button is clicked

                    if (secondSportsOutdoorsColourMenu.getSelectedItemPosition() == 0) { // If index 0 is chosen for the colour menu

                        AlertDialog.Builder colourMenu = new AlertDialog.Builder(SportsAndOutdoorsActivity.this)
                                .setTitle("Colour Error")

                                .setMessage("You must choose a colour before adding to the basket")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }
                                });

                        colourMenu.show();
                        colourMenu.setCancelable(true);
                    }
                }
            }
        });
    }

    private boolean addToColoursList() { // Routine returns true or false if the colours have been added to the array list or not.

        TechActivity.Colours[] colours = {new TechActivity.Colours(0, "Choose colour please"), new TechActivity.Colours(1, "Honey Yellow"),
                new TechActivity.Colours(2, "Graphite Gray"), new TechActivity.Colours(3, "Midnight Black"),
                new TechActivity.Colours(4, "Orange")};

        for (TechActivity.Colours productColours : colours) { // For each colours in the array
            listOfColoursOne.add(productColours); // Add it to the array list

            listOfColoursTwo.add(productColours);
            coloursAdded = true; // Colours have been added
        }

        return true;
    }

    private boolean addToQuantitiesList() {
        TechActivity.Quantities[] quantities = {new TechActivity.Quantities(0), new TechActivity.Quantities(1), new TechActivity.Quantities(2)
                , new TechActivity.Quantities(3), new TechActivity.Quantities(4), new TechActivity.Quantities(5)};

        for (TechActivity.Quantities quantitiesArray : quantities) {
            // Add the quantities to the list
            listOfQuantitiesOne.add(quantitiesArray);
            listOfQuantitiesTwo.add(quantitiesArray);

            quantitiesAdded = true;
        }

        return true;
    }

    private boolean addToSizesList() {
        Size[] sizes = {new Size(0, "Please choose Size"), new Size(1, "S"), new Size(2, "M"),
                new Size(3, "L"), new Size(4, "XL")};

        for (Size theSizes : sizes) { // For each of the sizes in the array
            listOfSizesOne.add(theSizes);
            listOfSizesTwo.add(theSizes);

            sizesAdded = true;
        }

        return true;
    }

    private boolean addToBasketOne() { // Adds the first product to the basket

        return true;
    }

    private boolean addToBasketTwo() { // Adds the second product to the basket

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // Method that determines which item has been selected and at which index
        boolean valueAppended = false;
        int[] indexes = {0, 1, 2, 3, 4, 5};
        String appendText = "Product Cost £: ";

        if (parent.getItemAtPosition(position).equals(listOfQuantitiesOne.get(indexes[1]))) {
            firstSportsOutdoorCostTxt.setText(null);
            firstSportsOutdoorCostTxt.append(appendText + productOneCosts[1]);
            valueAppended = true; // Value is appended
        } else if (parent.getItemAtPosition(position).equals(listOfQuantitiesOne.get(indexes[2]))) {
            firstSportsOutdoorCostTxt.setText(null);
            firstSportsOutdoorCostTxt.append(appendText + productOneCosts[2]);
            valueAppended = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { // Nothing selected

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Add the toolbar menu
        // Inflate the activities menu
        MenuInflater activityInflater = getMenuInflater(); // Get the activity inflater
        activityInflater.inflate(R.menu.homepagemenu, menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basket_action_button, menu);

        View view = menu.findItem(R.id.cart_menu).getActionView();

        cartIcon = view.findViewById(R.id.cart_icon);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent basketIntent = new Intent(SportsAndOutdoorsActivity.this, BasketActivity.class); // Create a basket intent
                basketIntent.putExtra("map", listOfProductsToAddToBasket); // Transit over the hash map data to the basket
                startActivity(basketIntent); // Start the intent
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        try {

            switch (item.getItemId()) {
                case R.id.sportsAndOutdoorsCategory:
                    Intent sportsCategory = new Intent(SportsAndOutdoorsActivity.this, SportsAndOutdoorsActivity.class);
                    startActivity(sportsCategory);

                    break;

                case R.id.techCategory:
                    Intent techActivity = new Intent(SportsAndOutdoorsActivity.this, TechActivity.class);
                    startActivity(techActivity);
                    break;

                case R.id.clothingCategory:
                    Intent clothingActivity = new Intent(SportsAndOutdoorsActivity.this, ClothingCategory.class);
                    startActivity(clothingActivity);
                    break;

                case R.id.diyCategory:
                    Intent diyActivity = new Intent(SportsAndOutdoorsActivity.this, DIYActivity.class);
                    startActivity(diyActivity);
                    break;

                default:
                    super.onOptionsItemSelected(item);

            }

        } catch (ActivityNotFoundException exc) {
            Log.d("Error ", exc.toString());
        }

        return true;
    }
}