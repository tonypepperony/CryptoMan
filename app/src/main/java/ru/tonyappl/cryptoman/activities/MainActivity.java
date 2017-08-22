package ru.tonyappl.cryptoman.activities;

import android.app.ProgressDialog;
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
import ru.tonyappl.cryptoman.models.USDCourse;

public class MainActivity extends AppCompatActivity {
    private CourseService courseService;
    private USDCourse usdCourse;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        downloadCourses();


    }

    //Создаем вьюхолдер
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewSymbol;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) findViewById(R.id.tvName);
            textViewSymbol = (TextView) findViewById(R.id.tvSymbol);
        }
    }

    //Создаем адаптер
    class Adapter extends RecyclerView.Adapter<MainActivity.ViewHolder>{
        private List<Result> results;

        public Adapter(List<Result> results) {
            this.results = results;
        }

        @Override
        public MainActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View rowView = layoutInflater.inflate(R.layout.main_rows, parent, false);
            return new MainActivity.ViewHolder(rowView);
        }

        @Override
        public void onBindViewHolder(MainActivity.ViewHolder holder, int position) {
            String name = results.get(position).getName();
            String symbol = results.get(position).getSymbol();
            holder.textViewName.setText(name);
            holder.textViewSymbol.setText(symbol);
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }

    public class Result{
        private String name;
        private String symbol;

        public Result(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private void downloadCourses() {
        // Создаем экземпляр запроса со всем необходимыми настройками
        Call<USDCourse> call = courseService.getUSDCourses();

        // Отображаем progress bar
        loadingDialog.show();

        // Выполняем запрос асинхронно
        call.enqueue(new Callback<USDCourse>() {

            // В случае если запрос выполнился успешно, то мы переходим в метод onResponse(...)
            @Override
            public void onResponse(Call<USDCourse> call, Response<USDCourse> response) {
                if (response.isSuccessful()) {
                    // Если в ответ нам пришел код 2xx, то отображаем содержимое запроса
                    usdCourse = response.body();
                    fillCourses();
                } else {
                    // Если пришел код ошибки, то обрабатываем её
                    Toast.makeText(MainActivity.this, "Ошибочка 1", Toast.LENGTH_SHORT).show();
                }

                // Скрываем progress bar
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<USDCourse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибочка2", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }

        });
    }

    private void fillCourses(){
        List<Result> resultList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            resultList.add(new Result(usdCourse.getValue().get(i).getName(), usdCourse.getValue().get(i).getSymbol()));
            final MainActivity.Adapter adapter = new MainActivity.Adapter(resultList);
            recyclerView.setAdapter(adapter);
        }
    }
}
