package ru.tonyappl.cryptoman.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tonyappl.cryptoman.R;
import ru.tonyappl.cryptoman.api.ApiFactory;
import ru.tonyappl.cryptoman.api.CourseService;
import ru.tonyappl.cryptoman.models.Value;

public class MainActivity extends AppCompatActivity {
    private CourseService courseService;
    private List<Value> values;
    private RecyclerView recyclerView;
    private ProgressDialog loadingDialog;

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



        downloadCourses();


    }

    //Создаем вьюхолдер
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewSymbol;

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.tvName);
            textViewSymbol = (TextView) itemView.findViewById(R.id.tvSymbol);
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
                    Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                intent.putExtra("id", values.get(position).getId());
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
                    Toast.makeText(MainActivity.this, "Ошибочка 1", Toast.LENGTH_SHORT).show();
                }

                // Скрываем progress bar
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<Value>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибочка2", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }

        });
    }

    private void fillCourses(){
        List<Value> resultValues = new ArrayList<>();
        for (Value vl : values){
            resultValues.add(new Value(vl.getId(), vl.getName(), vl.getSymbol(), vl.getPriceUsd()));
            final MainActivity.Adapter adapter = new MainActivity.Adapter(resultValues);
            recyclerView.setAdapter(adapter);
        }

    }
}
