package com.abigail.androidclickergame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView clicksTextField;
    private double totalClicks = 0;
    private double clicksAtATime = 1;
    private double autoclicks = 1;
    private Map<String, Monster> monsters = new HashMap<>();
    private List<ImageView> eggs = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding monster objects to hashmap
        monsters.put("monster1", new Monster(R.id.egg1,
                false,
                50,
                R.drawable.egg1,
                R.drawable.flower_monster,
                "multiplier"));
        monsters.put("monster2", new Monster(R.id.egg2,
                false,
                200,
                R.drawable.egg2,
                R.drawable.monster2,
                "autoclick"));
        monsters.put("monster3", new Monster(R.id.egg3,
                 false,
                  750,
                  R.drawable.egg3,
                  R.drawable.monster3,
                        "boost"));

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clicksTextField = findViewById(R.id.clicks);
        clicksTextField.setText("$"+totalClicks);

        for (Monster m: monsters.values()) {
            ImageView egg = findViewById(m.getViewId()); // setting egg id
            egg.setImageResource(m.getEggImgId()); // setting egg img
            egg.setOnClickListener(this); // adding click listener
            eggs.add(egg); // adding ImageView egg to list
        }
    }
    Runnable autoclicker = new Runnable() {
        @Override
        public void run() {
            totalClicks+=autoclicks;
            handler.postDelayed(this, 1000);
            clicksTextField.setText("$"+totalClicks);
        }
    };
    public void treeClicks(View view){
        totalClicks+= clicksAtATime;
        clicksTextField.setText("$"+totalClicks);
    }

    @Override
    public void onClick(View v) {
        ImageView egg = (ImageView)v; // assigns Imageview based on which egg you clicked
        Monster m = monsters.get(egg.getTag()); // gets monster object based on which egg you clicked
        if(!m.isHatched()) {
            if (totalClicks >= m.getCost()) {
                totalClicks -= m.getCost();
                egg.setImageResource(m.getMonsterImgId());
                clicksTextField.setText("$"+totalClicks);
                m.setHatched(true);
                if (m.getEffectId().equals("multiplier")) {
                    clicksAtATime = 2;
                } else if (m.getEffectId().equals("autoclick")) {
                    handler.post(autoclicker);
                } else if (m.getEffectId().equals("boost")){
                    clicksAtATime += clicksAtATime*0.25;
                    autoclicks += autoclicks*0.25;
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Not enough clicks!")
                        .setTitle((String) egg.getTag());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
}