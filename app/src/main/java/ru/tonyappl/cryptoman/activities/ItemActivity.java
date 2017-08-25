package ru.tonyappl.cryptoman.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ru.tonyappl.cryptoman.R;

public class ItemActivity extends AppCompatActivity {
    private ImageView itemImageView;
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

        textView.setText(getIntent().getStringExtra("name"));



        textView4.setText("1 " + getIntent().getStringExtra("symbol") + " = " + getIntent().getStringExtra("priceUsd") + " USD");
        textView2.setText("Rank #" + getIntent().getStringExtra("rank"));
        textView14.setText(checkSign("percentChange1h") + getIntent().getStringExtra("percentChange1h") + "%");
        setColorTv("percentChange1h", textView14);
        textView18.setText(checkSign("percentChange24h") + getIntent().getStringExtra("percentChange24h") + "%");
        setColorTv("percentChange24h", textView18);
        textView21.setText(checkSign("percentChange7d") + getIntent().getStringExtra("percentChange7d") + "%");
        setColorTv("percentChange7d", textView21);
        textView9.setText("Market Cap $" + getIntent().getStringExtra("marketCapUsd"));
        textView11.setText("Circulating Supply " + getIntent().getStringExtra("availableSupply") + " " + getIntent().getStringExtra("symbol"));
        textView23.setText(getIntent().getStringExtra("symbol") + "-BTC = " + getIntent().getStringExtra("priceBtc") + " BTC");
    }

    private String checkSign(String extra) {
        String sign = " ";
        if (Double.parseDouble(getIntent().getStringExtra(extra)) > 0.0){
            sign = "+";
            } else {
                sign = " ";
            }
        return sign;
    }

    private void setColorTv(String extra, TextView textView) {
        if (Double.parseDouble(getIntent().getStringExtra(extra)) >= 0.0){
            textView.setTextColor(getResources().getColor(R.color.colorGreen));
        } else {
            textView.setTextColor(getResources().getColor(R.color.colorRed));
        }
    }

    private void init() {
        itemImageView = (ImageView) findViewById(R.id.itemImageView);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textViewRank);
        textView4 = (TextView) findViewById(R.id.textViewCourse);
        textView9 = (TextView) findViewById(R.id.textViewMarketCap);
        textView11 = (TextView) findViewById(R.id.textViewCS);
        textView14 = (TextView) findViewById(R.id.textViewChange1h);
        textView18 = (TextView) findViewById(R.id.textViewChange24h);
        textView21 = (TextView) findViewById(R.id.textViewChange7d);
        textView23 = (TextView) findViewById(R.id.textViewOnBTC);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
