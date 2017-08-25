package ru.tonyappl.cryptoman.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.tonyappl.cryptoman.R;

public class ItemActivity extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewRank;
    private TextView textViewCourse;
    private TextView textViewMarketCap;
    private TextView textViewCS;
    private TextView textViewChange1h;
    private TextView textViewChange24h;
    private TextView textViewChange7d;
    private TextView textViewOnBTC;
    private ImageView imageViewItem;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        init();

        textViewName.setText(getIntent().getStringExtra("name"));



        textViewCourse.setText("1 " + getIntent().getStringExtra("symbol") + " = " + getIntent().getStringExtra("priceUsd") + " USD");
        textViewRank.setText("Rank #" + getIntent().getStringExtra("rank"));
        textViewChange1h.setText(checkSign("percentChange1h") + getIntent().getStringExtra("percentChange1h") + "%");
        setColorTv("percentChange1h", textViewChange1h);
        textViewChange24h.setText(checkSign("percentChange24h") + getIntent().getStringExtra("percentChange24h") + "%");
        setColorTv("percentChange24h", textViewChange24h);
        textViewChange7d.setText(checkSign("percentChange7d") + getIntent().getStringExtra("percentChange7d") + "%");
        setColorTv("percentChange7d", textViewChange7d);
        textViewMarketCap.setText("Market Cap $" + getIntent().getStringExtra("marketCapUsd"));
        textViewCS.setText("Circulating Supply " + getIntent().getStringExtra("availableSupply") + " " + getIntent().getStringExtra("symbol"));
        textViewOnBTC.setText(getIntent().getStringExtra("symbol") + "-BTC = " + getIntent().getStringExtra("priceBtc") + " BTC");
        Picasso.with(ItemActivity.this).load(getIntent().getStringExtra("image")).into(imageViewItem);
    }

    private String checkSign(String extra) {
        String sign;
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
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewRank = (TextView) findViewById(R.id.textViewRank);
        textViewCourse = (TextView) findViewById(R.id.textViewCourse);
        textViewMarketCap = (TextView) findViewById(R.id.textViewMarketCap);
        textViewCS = (TextView) findViewById(R.id.textViewCS);
        textViewChange1h = (TextView) findViewById(R.id.textViewChange1h);
        textViewChange24h = (TextView) findViewById(R.id.textViewChange24h);
        textViewChange7d = (TextView) findViewById(R.id.textViewChange7d);
        textViewOnBTC = (TextView) findViewById(R.id.textViewOnBTC);
        imageViewItem = (ImageView) findViewById(R.id.imageViewItem);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
