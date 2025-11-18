package com.abigail.androidclickergame;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView clicksTextField;
    private double totalClicks = 0;
    private double clicksAtATime = 1;
    private double autoclicks = 1;
    private boolean monster1Maxed = false;
    private boolean monster2Maxed = false;
    private boolean monster3Maxed = false;
    private Map<String, Monster> monsters = new HashMap<>();
    private Map<String, Food> foods = new HashMap<>();
    private List<TextView> foodAmounts = new ArrayList<>();
    private List<ImageView> eggs = new ArrayList<>();
    private List<ImageView> foodImages = new ArrayList<>();
    private List<String> foodNames = Arrays.asList("Orange & Lemon Tea", "Clovers & Petals Salad", "Strawberries & Corn Icecream");
    private Handler handler = new Handler(Looper.getMainLooper());
    private int amtMonstersHatched = 1;
    private ImageView willowTree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding monster objects to hashmap
        monsters.put("monster1", new Monster(R.id.egg1,
                false,
                50,
                R.drawable.egg1,
                R.drawable.flower_monster_resized,
                "multiplier",
                "The Flower Monster will increase your clicks by 2x",
                1,
                50,
                0));
        monsters.put("monster2", new Monster(R.id.egg2,
                false,
                200,
                R.drawable.egg2,
                R.drawable.crystal_dog_2,
                "autoclick",
                "The Jewel Pup will do 1 click per second.",
                1,
                75,
                0));
        monsters.put("monster3", new Monster(R.id.egg3,
                 false,
                  750,
                  R.drawable.egg3,
                  R.drawable.wisp_monster_2,
                "boost",
                "The Wisp will passively increase the other monster's boost by +25%",
                1,
                100,
                0));

        foods.put("food1", new Food(10,0,25, R.id.food1Amt, R.id.food1, 0));
        foods.put("food2", new Food(25,0,50, R.id.food2Amt, R.id.food2, 2));
        foods.put("food3", new Food(50,0,100, R.id.food3Amt, R.id.food3, 1));

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clicksTextField = findViewById(R.id.clicks);
        clicksTextField.setText("$"+totalClicks);

        for (Food f: foods.values()){
            TextView foodAmtTextfield = findViewById(f.getTextViewId());
            foodAmtTextfield.setText(String.valueOf(f.getAmountOfFood()));
            foodAmounts.add(foodAmtTextfield);
            ImageView food = findViewById(f.getViewId());
            food.setOnClickListener(this);
            foodImages.add(food);
        }

        for (Monster m: monsters.values()) {
            ImageView egg = findViewById(m.getViewId()); // setting egg id
            egg.setImageResource(m.getEggImgId()); // setting egg img
            egg.setOnClickListener(this); // adding click listener
            eggs.add(egg); // adding ImageView egg to list
        }
        willowTree = findViewById(R.id.willowTree);
        willowTree.setImageResource(R.drawable.willow_tree_1_resized);
        willowTree.setTranslationY(20f);
    }
    private boolean autoclickerRunning = false;
    Runnable autoclicker = new Runnable() {
        @Override
        public void run() {
            if (autoclickerRunning) {
                totalClicks+=autoclicks;
                String formatedClicks = String.format("%.2f", totalClicks);
                if (monster1Maxed && monster2Maxed && monster3Maxed){
                    clicksTextField.setText("$"+formatedClicks+" / $ 500,000");
                } else {
                    clicksTextField.setText("$"+formatedClicks);
                }
                handler.postDelayed(this, 1000);
            }
        }
    };
    public void treeClicks(View view){
        totalClicks+= clicksAtATime;
        String formatedClicks = String.format("%.2f", totalClicks);
        if (monster1Maxed && monster2Maxed && monster3Maxed){
            clicksTextField.setText("$"+formatedClicks+" / $500000");
        } else {
            clicksTextField.setText("$"+formatedClicks);
        }
        if (totalClicks >= 100000){
            Intent gameDone = new Intent(this, endingPage.class);
            startActivity(gameDone);
            finish(); // Close landing page to prevent multiple instances
        }
    }
    public void treeGrowth(){
        if (amtMonstersHatched == 2){
            willowTree.setImageResource(R.drawable.willow_tree_2_resized);
            willowTree.setTranslationY(-20f);
        }
        else if (amtMonstersHatched == 3){
            willowTree.setScaleX(2f);
            willowTree.setScaleY(2f);
            willowTree.setTranslationY(-150f);
            willowTree.setImageResource(R.drawable.willow_tree_3_resized);
        }
        else if (amtMonstersHatched == 4){
            willowTree.setScaleX(2.25f);
            willowTree.setScaleY(2.25f);
            willowTree.setTranslationY(-150f);
            willowTree.setImageResource(R.drawable.willow_tree_4_resized);
        }
    }
    private void updateMonsterEffects(Monster m) { // fix this later
        if (m.getEffectId().equals("multiplier")) {
            if (m.getLevel() == 1) {
                clicksAtATime = 2;
                m.setLevelCap(50);
            } else if (m.getLevel() == 2) {
                clicksAtATime = 3;
                m.setLevelCap(100);
            } else if (m.getLevel() == 3) {
                clicksAtATime = 5;
                m.setLevelCap(250);
            } else if (m.getLevel() == 4) {
                clicksAtATime = 10;
                m.setLevelCap(500);
            } else if (m.getLevel() == 5) {
                clicksAtATime = 25;
                monster1Maxed = true;
            }
            m.setMonsterDesc("The Flower Monster will increase your clicks by "+clicksAtATime+"x");
        } else if (m.getEffectId().equals("autoclick")) {
            if (m.getLevel() == 1) {
                autoclicks = 1;
                m.setLevelCap(75);
            } else if (m.getLevel() == 2) {
                autoclicks = 3;
                m.setLevelCap(200);
            } else if (m.getLevel() == 3) {
                autoclicks = 5;
                m.setLevelCap(400);
            } else if (m.getLevel() == 4) {
                autoclicks = 10;
                m.setLevelCap(750);
            } else if (m.getLevel() == 5) {
                autoclicks = 25;
                monster2Maxed = true;
            }
            m.setMonsterDesc("The Jewel Pup will do "+autoclicks+" click per second.");
        } else if (m.getEffectId().equals("boost")) {
            double multiplier = 0;
            if (m.getLevel() == 1) {
                multiplier = 0.25;
                m.setLevelCap(100);
            } else if (m.getLevel() == 2) {
                multiplier = 0.5;
                m.setLevelCap(300);
            } else if (m.getLevel() == 3) {
                multiplier = 0.75;
                m.setLevelCap(550);
            } else if (m.getLevel() == 4) {
                multiplier = 1;
                m.setLevelCap(1000);
            } else if (m.getLevel() == 5) {
                multiplier = 1.5;
                monster3Maxed = true;
            }
            clicksAtATime += clicksAtATime * multiplier;
            autoclicks += autoclicks * multiplier;
            m.setMonsterDesc("The Wisp will passively increase the other monster's boost by +"+multiplier*100+"%");
        }
    }
    private void showMonsterPopup(Monster m) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.monster_pop_up);

        // sets up pop up window
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(900, 800);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        // setting images and textviews of pop up according to each monster
        ImageView monsterImage = dialog.findViewById(R.id.monsterImage);
        if (monsterImage != null) {
            monsterImage.setImageResource(m.getMonsterImgId());
        }
        TextView monsterDesc = dialog.findViewById(R.id.monsterDesc);
        if (monsterDesc != null) {
            monsterDesc.setText(m.getMonsterDesc());
        }
        TextView progress = dialog.findViewById(R.id.progress);
        if (progress != null){
            progress.setText(m.getCurrentLevelPts()+"/"+m.getLevelCap());
        }
        TextView level = dialog.findViewById(R.id.level);
        if (level != null){
            if (m.getLevel() == 5){
                level.setText("MAX Level");
            } else{
                level.setText("Level "+m.getLevel());
            }
        }
        // set up drop down menu
        Spinner dropDownMenu = dialog.findViewById(R.id.dropDownMenu);
        if (dropDownMenu != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropDownMenu.setAdapter(adapter);

            // Listen for when user selects an item
            dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // selects food object based on user input on drop down menu
                    String selectedFood = foodNames.get(position);
                    Food selectedFoodObj;
                    if (position == 0) {
                        selectedFoodObj = foods.get("food1");
                    } else if (position == 1) {
                        selectedFoodObj = foods.get("food2");
                    } else if (position == 2) {
                        selectedFoodObj = foods.get("food3");
                    } else {
                        selectedFoodObj = null;
                    }
                    // sets amount of food left
                    TextView foodAmount = dialog.findViewById(R.id.foodAmount);
                    if (foodAmount != null && selectedFoodObj != null) {
                        foodAmount.setText(String.valueOf(selectedFoodObj.getAmountOfFood()));
                    }
                    //
                    Button feedButton = dialog.findViewById(R.id.feedButton);
                    if (feedButton != null && selectedFoodObj != null){
                        feedButton.setOnClickListener(v -> {
                            if (selectedFoodObj.getAmountOfFood() > 0 && m.getLevel()!=5) {
                                selectedFoodObj.decreaseFoodAmount();
                                foodAmount.setText(String.valueOf(selectedFoodObj.getAmountOfFood()));

                                // Update the food amount on the main screen
                                foodAmounts.get(selectedFoodObj.getTextFieldIndex()).setText(String.valueOf(selectedFoodObj.getAmountOfFood()));

                                m.increaseLevelPts(selectedFoodObj.foodPoints);
                                progress.setText(m.getCurrentLevelPts()+"/"+m.getLevelCap());
                                if (m.getCurrentLevelPts() >= m.getLevelCap()){
                                    m.levelUp();
                                    updateMonsterEffects(m); // Update autoclicks/clicksAtATime
                                    monsterDesc.setText(m.getMonsterDesc());
                                    if (m.getLevel()!=5){
                                        level.setText("Level " + m.getLevel());
                                        m.setCurrentLevelPts(0);
                                        progress.setText(m.getCurrentLevelPts() + "/" + m.getLevelCap());
                                    } else {
                                        level.setText("MAX Level");
                                    }
                                }
                            }
                        });
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Called when selection is cleared (rarely happens)
                }
            });
        }
        dialog.show();
    }
    public void adjustImages(Monster m, ImageView monsterImage){
        if (m.getMonsterImgId() == R.drawable.flower_monster_resized) {
            monsterImage.setScaleX(1.5f);
            monsterImage.setScaleY(1.5f);
            monsterImage.setTranslationX(40f);
        }
        else if (m.getMonsterImgId() == R.drawable.crystal_dog_2){
            monsterImage.setScaleX(1.25f);
            monsterImage.setScaleY(1.25f);
            monsterImage.setTranslationY(5f);

        }
    }
    @Override
    public void onClick(View v) {
        ImageView image = (ImageView)v; // assigns Imageview based on which egg you clicked
        Monster m = monsters.get(image.getTag()); // gets monster object based on which egg you clicked
        Food f = foods.get(image.getTag());
        if (m != null) {
            if (!m.isHatched()) {
                if (totalClicks >= m.getCost()) {
                    totalClicks -= m.getCost();
                    image.setImageResource(m.getMonsterImgId());
                    String formatedClicks = String.format("%.2f", totalClicks);
                    clicksTextField.setText("$"+formatedClicks);
                    m.setHatched(true);
                    amtMonstersHatched++;
                    updateMonsterEffects(m); // Use the helper method
                    adjustImages(m,image);
                    treeGrowth();
                    if (m.getEffectId().equals("autoclick")) {
                        if (!autoclickerRunning) {
                            autoclickerRunning = true;
                            handler.post(autoclicker);
                        }
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Not enough clicks!")
                            .setTitle((String) image.getTag());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }else {
                showMonsterPopup(m);
            }
        } else {
            if (f != null) {
                if (totalClicks >= f.getCost()) {
                    f.increaseFoodAmount();
                    foodAmounts.get(f.getTextFieldIndex()).setText(String.valueOf(f.getAmountOfFood()));
                    totalClicks -= f.getCost();
                    String formatedClicks = String.format("%.2f", totalClicks);
                    if (monster1Maxed && monster2Maxed && monster3Maxed){
                        clicksTextField.setText("$"+formatedClicks+" / $500000");
                    } else {
                        clicksTextField.setText("$"+formatedClicks);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Not enough clicks!")
                            .setTitle((String) image.getTag());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Stop autoclicker when app goes to background to prevent crashes
        autoclickerRunning = false;
        handler.removeCallbacks(autoclicker);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Restart autoclicker when returning to app if monster2 was hatched
        Monster monster2 = monsters.get("monster2");
        if (monster2 != null && monster2.isHatched() && !autoclickerRunning) {
            autoclickerRunning = true;
            handler.post(autoclicker);
        }
    }
}