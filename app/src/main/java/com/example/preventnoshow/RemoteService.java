package com.example.preventnoshow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RemoteService {
    public static final String BASE_URL2 = "http://192.168.25.23:8088/store/";
    public static final String BASE_URL3 = "http://192.168.25.23:8088/resvlist/";
    public static final String BASE_URL4 = "http://192.168.25.23:8088/board/";

    @GET("list.jsp")
    Call<List<StoreVO>> listStore();

    @GET("list.jsp")
    Call<List<ResvVO>> listResv();

    @GET("list.jsp")
    Call<List<Board>> listBoard();


    //@Multipart
    @POST("insert.jsp")
    Call<Void> insertStore(@Query("email") String email, @Query("sid") String sid,
                           @Query("sname") String sname, @Query("bname") String bname,
                           @Query("address") String address, @Query("tel") String tel,
                           @Query("intro") String intro, @Query("category") String category);


    @POST("insert.jsp")
    Call<Void> insertResv(@Query("email") String email, @Query("rname") String rname, @Query("rtel") String rtel,
                          @Query("rdate") String rdate, @Query("rtime") String rtime,
                          @Query("bname") String bname);

    @POST("insert.jsp")
    Call<Void> insertBoard(@Query("email") String email, @Query("title") String title, @Query("place") String place,
                          @Query("date") String date, @Query("time") String time,
                          @Query("diposit") String diposit, @Query("contents") String contents,
                           @Query("category") String category, @Query("createDate") String createDate);

    @GET("read.jsp")
    Call<StoreVO> readStore(@Query("bname") String bname);

    @POST("delete.jsp")
    Call<Void> deleteStore(@Query("sid") String sid);
}
