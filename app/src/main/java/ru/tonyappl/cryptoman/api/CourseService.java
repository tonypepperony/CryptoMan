package ru.tonyappl.cryptoman.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.tonyappl.cryptoman.models.Value;

public interface CourseService {
    @GET("v1/ticker/")
    Call<List<Value>> getUSDCourses();
}
