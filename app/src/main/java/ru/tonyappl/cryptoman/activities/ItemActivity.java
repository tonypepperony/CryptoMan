package ru.tonyappl.cryptoman.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.tonyappl.cryptoman.R;
import ru.tonyappl.cryptoman.models.Value;

public class ItemActivity extends AppCompatActivity {
    private ImageView imageView2;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;
    private TextView textView14;
    private TextView textView15;
    private TextView textView16;
    private TextView textView17;
    private TextView textView18;
    private TextView textView19;
    private TextView textView20;
    private TextView textView21;
    private TextView textView22;
    private TextView textView23;
    private TextView textView24;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        init();
        //setId(getIntent().getExtras().getInt("itemPosition"));
        textView.setText(getIntent().getStringExtra("name"));
        textView3.setText(getIntent().getStringExtra("rank"));
        textView5.setText(getIntent().getStringExtra("symbol"));
        textView7.setText(getIntent().getStringExtra("priceUsd"));
        textView23.setText(getIntent().getStringExtra("priceBtc"));
        textView10.setText(getIntent().getStringExtra("marketCapUsd"));
        textView12.setText(getIntent().getStringExtra("availableSupply"));
        textView14.setText(getIntent().getStringExtra("percentChange1h"));
        textView18.setText(getIntent().getStringExtra("percentChange24h"));
        textView21.setText(getIntent().getStringExtra("percentChange7d"));

    }

    private void init() {
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
        textView9 = (TextView) findViewById(R.id.textView9);
        textView10 = (TextView) findViewById(R.id.textView10);
        textView11 = (TextView) findViewById(R.id.textView11);
        textView12 = (TextView) findViewById(R.id.textView12);
        textView13 = (TextView) findViewById(R.id.textView13);
        textView14 = (TextView) findViewById(R.id.textView14);
        textView15 = (TextView) findViewById(R.id.textView15);
        textView16 = (TextView) findViewById(R.id.textView16);
        textView17 = (TextView) findViewById(R.id.textView17);
        textView18 = (TextView) findViewById(R.id.textView18);
        textView19 = (TextView) findViewById(R.id.textView19);
        textView20 = (TextView) findViewById(R.id.textView20);
        textView21 = (TextView) findViewById(R.id.textView21);
        textView22 = (TextView) findViewById(R.id.textView22);
        textView23 = (TextView) findViewById(R.id.textView23);
        textView24 = (TextView) findViewById(R.id.textView24);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
