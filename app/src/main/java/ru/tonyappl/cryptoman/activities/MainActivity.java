package ru.tonyappl.cryptoman.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tonyappl.cryptoman.R;
import ru.tonyappl.cryptoman.api.ApiFactory;
import ru.tonyappl.cryptoman.api.CourseService;
import ru.tonyappl.cryptoman.models.Image;
import ru.tonyappl.cryptoman.models.Value;

public class MainActivity extends AppCompatActivity {
    private CourseService courseService;
    private List<Value> values;
    private RecyclerView recyclerView;
    private ProgressDialog loadingDialog;
    private List<Image> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаем сервис через который будет выполняться запрос
        courseService = ApiFactory.getRetrofitInstance().create(CourseService.class);
        loadingDialog = ProgressDialog.show(this, "", getString(R.string.loading), true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        downloadCourses();
        initImages();

    }

    //Создаем вьюхолдер
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewSymbol;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.tvName);
            textViewSymbol = (TextView) itemView.findViewById(R.id.tvSymbol);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    //Создаем адаптер
    private class Adapter extends RecyclerView.Adapter<MainActivity.ViewHolder>{
        private List<Value> values;
        //Создаем обработчик нажатий на список
        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildLayoutPosition(view);
                String item = values.get(position).getName();
                    Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                intent.putExtra("id", values.get(position).getId());
                intent.putExtra("name", values.get(position).getName());
                intent.putExtra("rank", values.get(position).getRank());
                intent.putExtra("priceUsd", values.get(position).getPriceUsd());
                intent.putExtra("priceBtc", values.get(position).getPriceBtc());
                intent.putExtra("marketCapUsd", values.get(position).getMarketCapUsd());
                intent.putExtra("availableSupply", values.get(position).getAvailableSupply());
                intent.putExtra("percentChange1h", values.get(position).getPercentChange1h());
                intent.putExtra("percentChange24h", values.get(position).getPercentChange24h());
                intent.putExtra("percentChange7d", values.get(position).getPercentChange7d());
                startActivity(intent);
            }
        };

        Adapter(List<Value> values) {
            this.values = values;
        }

        @Override
        public MainActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View rowView = layoutInflater.inflate(R.layout.main_rows, parent, false);
            rowView.setOnClickListener(onClickListener);
            return new MainActivity.ViewHolder(rowView);
        }

        @Override
        public void onBindViewHolder(MainActivity.ViewHolder holder, int position) {
            String name = values.get(position).getName();
            TextView textViewName = holder.textViewName;
            textViewName.setText(name);

            String symbol = values.get(position).getSymbol();
            TextView textViewSymbol = holder.textViewSymbol;
            textViewSymbol.setText(symbol);

            String url = values.get(position).getImageUrl();
            ImageView image = holder.imageView;
            Picasso.with(MainActivity.this).load(url).into(image);
        }

        @Override
        public int getItemCount() {
            return values.size();
        }
    }

    private void downloadCourses() {
        // Создаем экземпляр запроса со всем необходимыми настройками
        Call<List<Value>> call = courseService.getUSDCourses();

        // Отображаем progress bar
        loadingDialog.show();

        // Выполняем запрос асинхронно
        call.enqueue(new Callback<List<Value>>() {

            // В случае если запрос выполнился успешно, то мы переходим в метод onResponse(...)
            @Override
            public void onResponse(@NonNull Call<List<Value>> call, @NonNull Response<List<Value>> response) {
                if (response.isSuccessful()) {
                    // Если в ответ нам пришел код 2xx, то отображаем содержимое запроса
                    values = response.body();
                    fillCourses();
                } else {
                    // Если пришел код ошибки, то обрабатываем её
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                // Скрываем progress bar
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<Value>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "invalid downloading", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }

        });
    }

    private void fillCourses(){
        List<Value> resultValues = new ArrayList<>();

        //Выгрузка всех
//        for (Value vl : values){
//            resultValues.add(new Value(vl.getId(), vl.getName(), vl.getSymbol(), vl.getRank(), vl.getPriceUsd(), vl.getPriceBtc(), vl.getMarketCapUsd(), vl.getAvailableSupply(), vl.getPercentChange1h(), vl.getPercentChange24h(), vl.getPercentChange7d(), getImgURL(vl.getSymbol())));
//            final MainActivity.Adapter adapter = new MainActivity.Adapter(resultValues);
//            recyclerView.setAdapter(adapter);
//        }

        //Выгрузка топ 50
        for (int i = 0; i < 50; i++) {
            resultValues.add(new Value(values.get(i).getId(), values.get(i).getName(), values.get(i).getSymbol(), values.get(i).getRank(), values.get(i).getPriceUsd(), values.get(i).getPriceBtc(), values.get(i).getMarketCapUsd(), values.get(i).getAvailableSupply(), values.get(i).getPercentChange1h(), values.get(i).getPercentChange24h(), values.get(i).getPercentChange7d(), getImgURL(values.get(i).getSymbol())));
            final MainActivity.Adapter adapter = new MainActivity.Adapter(resultValues);
            recyclerView.setAdapter(adapter);
        }

    }

    private void initImages() {
        images = new ArrayList<>();
        images.add(new Image("BTC", "/media/19633/btc.png"));
        images.add(new Image("ETH", "/media/20646/eth.png"));
        images.add(new Image("BCH", "/media/1383919/bch.jpg"));
        images.add(new Image("XRP", "/media/19972/ripple.png"));
        images.add(new Image("LTC", "/media/19782/litecoin-logo.png"));
        images.add(new Image("XEM", "/media/20490/xem.png"));
        images.add(new Image("DASH", "/media/20626/dash.png"));
        images.add(new Image("NEO", "/media/1383858/neo.jpg"));
        images.add(new Image("ETC", "/media/20275/etc2.png"));
        images.add(new Image("XMR", "/media/19969/xmr.png"));
        images.add(new Image("OMG", "/media/1383814/omg.png"));
        images.add(new Image("BCC", "/media/9350709/bccoin1.png"));
        images.add(new Image("QTUM", "/media/1383382/qtum.png"));
        images.add(new Image("STRAT", "/media/351303/strat.png"));
        images.add(new Image("WAVES", "/media/350884/waves_1.png"));
        images.add(new Image("ZEC", "/media/351360/zec.png"));
        images.add(new Image("LSK", "/media/352246/lsk.png"));
        images.add(new Image("EOS", "/media/1383652/eos-logo.png"));
        images.add(new Image("PAY", "/media/1383687/pay.png"));
        images.add(new Image("BTS", "/media/20705/bts.png"));
        images.add(new Image("USDT", "/media/1383672/usdt.png"));
        images.add(new Image("STEEM", "/media/350907/steem.png"));
        images.add(new Image("GNT", "/media/351995/golem-logo.png"));
        images.add(new Image("BCN", "/media/20197/bytecoin-logo.png"));
        images.add(new Image("VERI", "/media/1383562/veri.png"));
        images.add(new Image("REP", "/media/350815/augur-logo.png"));
        images.add(new Image("ICN", "/media/351400/icn.png"));
        images.add(new Image("BNB", "/media/1383947/bnb.png"));
        images.add(new Image("FCT", "/media/1382863/fct1.png"));
        images.add(new Image("MAID", "/media/352247/maid.png"));
        images.add(new Image("XLM", "/media/20696/str.png"));
        images.add(new Image("SC", "/media/20726/siacon-logo.png"));
        images.add(new Image("BAT", "/media/1383370/bat.png"));
        images.add(new Image("GAS", "/media/1383858/neo.jpg"));
        images.add(new Image("DOGE", "/media/19684/doge.png"));
        images.add(new Image("ZRX", "/media/1383799/zrx.png"));
        images.add(new Image("GBYTE", "/media/351835/bytes.png"));
        images.add(new Image("CVC", "/media/1383611/cvc.png"));
        images.add(new Image("SNT", "/media/1383568/snt.png"));
        images.add(new Image("PPT", "/media/1383747/ppt.png"));
        images.add(new Image("DGD", "/media/350851/dgd.png"));
        images.add(new Image("MTL", "/media/1383743/mtl.png"));
        images.add(new Image("DCR", "/media/1382607/decred.png"));
        images.add(new Image("GNO", "/media/1383083/gnosis-logo.png"));
        images.add(new Image("ARK", "/media/351931/ark.png"));
        images.add(new Image("GAME", "/media/350887/game.png"));
        images.add(new Image("MCAP", "/media/1383559/mcap.png"));
        images.add(new Image("KMD", "/media/351408/kmd.png"));
        images.add(new Image("ARDR", "/media/351736/ardr.png"));
    }

    private String getImgURL(String name){
        String baseUrl = "https://www.cryptocompare.com";
        String url = "http://images.cdn1.stockunlimited.net/preview1300/pixel-art-coin_1959575.jpg";
        for (int i = 0; i < images.size(); i++) {
            if (name.equals(images.get(i).getName())){
                url = baseUrl + images.get(i).getUrl();
            } else if (name.equals("MIOTA")){
                url = "https://satoshiwatch.com/wp-content/uploads/2016/10/2.jpg";
            }
        }
        return url;
    }
}
