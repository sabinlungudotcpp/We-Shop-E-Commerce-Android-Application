package com.example.weshopapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
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

// Author of Application: Sabin Constantin Lungu.
// Purpose of Application / Class: Contains the Java code for the DIY activity that corresponds to the DIY XML code.
// Date of Last Modification: 03/02/2020
// Any errors? None

public class DIYActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int current_product_id = 1;
    private TextView diyFirstProductTxt;

    private ImageView diyFirstProductImg;
    private TextView diyFirstProductCost;

    private TextView diyFirstProductColourLbl;
    private Spinner diyFirstProductColourMenu;

    private TextView diyFirstProductSizeLbl;
    private Spinner diyFirstProductSizeMenu;

    private TextView diyFirstProductQuantityLbl;
    private Spinner diyFirstProductQuantityMenu;
    private Button diyFirstProductToAddToBasketBtn;

    private TextView diySecondProductTxt;
    private ImageView diySecondProductImg;

    private TextView diySecondProductCost;

    private TextView diySecondProductColourLbl;
    private Spinner diySecondProductColourMenu;

    private TextView diySecondProductSizeLbl;
    private Spinner diySecondProductSizeMenu;

    private TextView diySecondProductQuantityLbl;
    private Spinner diySecondProductQuantityMenu;

    private Button diySecondProductAddToBasketBtn;

    private double[] diyFirstProductCosts = new double[]{0.00, 40.00, 80.00, 160.00, 320.00, 640.00}; // A double array of product costs for the first DIY product
    private double[] diySecondProductCosts = new double[]{0.00, 20.00, 40.00, 80.00, 160.00, 320.00};

    private boolean coloursAdded = false;
    private boolean sizesAdded = false;
    private boolean quantitiesAdded = false;

    private CustomArrayAdapter quantitiesAdapter;
    private SizeArrayAdapter sizeArrayAdapter;
    private ColourArrayAdapter coloursAdapter;

    private ArrayList<TechActivity.Colours> diyListOfColoursOne = null;
    private ArrayList<Size> diyListOfSizesOne = null;
    private ArrayList<TechActivity.Quantities> diyListOfQuantitiesOne = null; // An Array list of quantities for the first diy product

    // Creates the array lists for the second DIY product.
    private ArrayList<TechActivity.Colours> diyListOfColoursTwo = null;
    private ArrayList<Size> diyListOfSizesTwo = null;
    private ArrayList<TechActivity.Quantities> diyListOfQuantitiesTwo = null;

    private ImageView cartIcon;
    private HashMap<Integer, Products> listOfProductsToAddToBasket = new HashMap<Integer, Products>(); // Creates a new hash map of products with an associated ID
    private Button nextPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);

        // Initialise components

        this.diyFirstProductTxt = findViewById(R.id.diyFirstProductTxt);
        this.diyFirstProductImg = findViewById(R.id.diyFirstProductImg);

        this.diyFirstProductCost = findViewById(R.id.diyFirstProductCostTxt);
        this.diyFirstProductColourLbl = findViewById(R.id.diyFirstProductColourLbl);
        this.diyFirstProductColourMenu = findViewById(R.id.diyFirstProductColourMenu);

        this.diyFirstProductSizeLbl = findViewById(R.id.diyFirstProductSizeLbl);
        this.diyFirstProductSizeMenu = findViewById(R.id.diyFirstProductSizeMenu);

        this.diyFirstProductQuantityLbl = findViewById(R.id.diyFirstProductQuantityLbl);
        this.diyFirstProductQuantityMenu = findViewById(R.id.diyFirstProductQuantityMenu);
        this.diyFirstProductToAddToBasketBtn = findViewById(R.id.diyFirstProductAddToBasketBtn);

        this.diySecondProductTxt = findViewById(R.id.diySecondProductTxt);
        this.diySecondProductImg = findViewById(R.id.diySecondProductImg);

        this.diySecondProductCost = findViewById(R.id.diySecondProductCostLbl);
        this.diySecondProductColourLbl = findViewById(R.id.diySecondProductColourLbl);
        this.diySecondProductColourMenu = findViewById(R.id.diySecondProductColourMenu);
        this.diySecondProductQuantityMenu = findViewById(R.id.diySecondProductQuantityMenu);

        this.diySecondProductSizeLbl = findViewById(R.id.diySecondProductSizeLbl);
        this.diySecondProductSizeMenu = findViewById(R.id.diySecondProductSizeMenu);

        this.diySecondProductAddToBasketBtn = findViewById(R.id.diySecondProductAddToBasketBtn);

        this.diyListOfColoursOne = new ArrayList<>();
        this.diyListOfColoursTwo = new ArrayList<>();

        this.diyListOfSizesOne = new ArrayList<>();
        this.diyListOfSizesTwo = new ArrayList<>();

        this.diyListOfQuantitiesOne = new ArrayList<>();
        this.diyListOfQuantitiesTwo = new ArrayList<>();

        addToDIYColourList();
        addToDIYSizesList();

        addToDIYQuantitiesListOne();
        addToDIYQuantitiesListTwo();

        // Set-up adapters for the firsts Array List

        this.coloursAdapter = new ColourArrayAdapter(DIYActivity.this, diyListOfColoursOne);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diyFirstProductColourMenu.setAdapter(coloursAdapter);
        diyFirstProductColourMenu.setOnItemSelectedListener(DIYActivity.this);

        this.quantitiesAdapter = new CustomArrayAdapter(DIYActivity.this, diyListOfQuantitiesOne); // Creates the quantities adapter for the first list of quantities.
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diyFirstProductQuantityMenu.setAdapter(quantitiesAdapter);
        diyFirstProductQuantityMenu.setOnItemSelectedListener(DIYActivity.this);

        this.sizeArrayAdapter = new SizeArrayAdapter(DIYActivity.this, diyListOfSizesOne);
        diyFirstProductSizeMenu.setAdapter(sizeArrayAdapter);
        diyFirstProductSizeMenu.setOnItemSelectedListener(DIYActivity.this);

        // Set-up adapters for the second ArrayList

        this.coloursAdapter = new ColourArrayAdapter(DIYActivity.this, diyListOfColoursTwo);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diySecondProductColourMenu.setAdapter(coloursAdapter);
        diySecondProductColourMenu.setOnItemSelectedListener(this);

        this.quantitiesAdapter = new CustomArrayAdapter(DIYActivity.this, diyListOfQuantitiesTwo);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diySecondProductQuantityMenu.setAdapter(quantitiesAdapter);
        diySecondProductQuantityMenu.setOnItemSelectedListener(this);

        this.sizeArrayAdapter = new SizeArrayAdapter(DIYActivity.this, diyListOfSizesTwo);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diySecondProductSizeMenu.setAdapter(sizeArrayAdapter);
        diySecondProductSizeMenu.setOnItemSelectedListener(this);

        this.diyFirstProductToAddToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diyFirstProductToAddToBasketBtn.getId() == R.id.clothingSecondProductAddToBasketBtn) {

                    if (diyFirstProductColourMenu.getSelectedItemPosition() == 0 || diyFirstProductSizeMenu.getSelectedItemPosition() == 0 || diyFirstProductQuantityMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(DIYActivity.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.errorMsg)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }
                                });

                        error.show();
                        error.setCancelable(true);
                    } else {
                        diyAddToBasketOne();
                    }
                }
            }
        });

        this.diySecondProductAddToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View secondButton) {
                if (secondButton.getId() == R.id.diySecondProductAddToBasketBtn) {
                    if (diySecondProductColourMenu.getSelectedItemPosition() == 0 || diySecondProductSizeMenu.getSelectedItemPosition() == 0 || diySecondProductQuantityMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(DIYActivity.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.errorMsg)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (dialog != null) {

                                            dialog.dismiss();
                                        }
                                    }
                                });

                        error.show();
                        error.setCancelable(true);
                    } else {
                        diyAddToBasketTwo();
                    }
                }
            }
        });

        this.nextPageBtn = findViewById(R.id.diyNextPageBtn);

        this.nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (v.getId() == R.id.diyNextPageBtn) {
                        Intent nextDiyIntent = new Intent(DIYActivity.this, DIYActivityTwo.class);
                        startActivity(nextDiyIntent);
                    }
                } catch (ActivityNotFoundException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }
            }
        });
    }

    private boolean addToDIYColourList() {
        Context context = getApplicationContext();
        String[] diyColoursResources = new String[]{context.getString(R.string.colourPrompt), context.getString(R.string.gallantGray), context.getString(R.string.darkBlack), context.getString(R.string.strawberryRed), context.getString(R.string.gardenGreen)};

        TechActivity.Colours[] colours = new TechActivity.Colours[]{new TechActivity.Colours(0, diyColoursResources[0]), new TechActivity.Colours(1, diyColoursResources[1]), new TechActivity.Colours(2, diyColoursResources[2]), new TechActivity.Colours(3, diyColoursResources[3]),
                new TechActivity.Colours(4, diyColoursResources[4])};

        for (TechActivity.Colours theColours : colours) {
            diyListOfColoursOne.add(theColours);
            diyListOfColoursTwo.add(theColours);
            coloursAdded = true;
        }

        return true;
    }

    private boolean addToDIYSizesList() {
        Context context = getApplicationContext();
        String[] diySizesResources = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.smallSize), context.getString(R.string.mediumSize), context.getString(R.string.largeSize), context.getString(R.string.extraLargeSize)};

        Size[] sizes = new Size[]{new Size(0, diySizesResources[0]), new Size(1, diySizesResources[1]), new Size(2, diySizesResources[2]), new Size(3, diySizesResources[3]), new Size(4, diySizesResources[4])};

        for (Size theSizes : sizes) {
            diyListOfSizesTwo.add(theSizes);
            diyListOfSizesTwo.add(theSizes);

            sizesAdded = true;
        }

        return true;
    }

    private boolean addToDIYQuantitiesListOne() {
        return true;
    }

    private boolean addToDIYQuantitiesListTwo() {
        return true;
    }


    private boolean diyAddToBasketOne() { // Routine that adds the first DIY product to the basket list view.

        return true;
    }

    private boolean diyAddToBasketTwo() {
        return true;
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

                Intent basketIntent = new Intent(DIYActivity.this, BasketActivity.class); // Create a basket intent
                basketIntent.putExtra("map", listOfProductsToAddToBasket); // Transit over the hash map data to the basket
                startActivity(basketIntent); // Start the intent
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Routine that determines which item has been selected.

        try {
            switch (item.getItemId()) {
                case R.id.sportsAndOutdoorsCategory:
                    Intent sportsCategory = new Intent(DIYActivity.this, SportsAndOutdoorsActivity.class);
                    startActivity(sportsCategory);

                    break;

                case R.id.techCategory:
                    Intent techActivity = new Intent(DIYActivity.this, TechActivity.class);
                    startActivity(techActivity);
                    break;

                case R.id.clothingCategory:
                    Intent clothingActivity = new Intent(DIYActivity.this, ClothingCategory.class);
                    startActivity(clothingActivity);
                    break;

                case R.id.diyCategory:
                    Intent diyActivity = new Intent(DIYActivity.this, DIYActivity.class);
                    startActivity(diyActivity);
                    break;

                default:
                    super.onOptionsItemSelected(item);

            }

        } catch (ActivityNotFoundException exc) {
            Log.d(String.valueOf(R.string.error), exc.toString());
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
