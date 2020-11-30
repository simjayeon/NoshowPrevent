package com.example.preventnoshow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RemoteService {
    public static final String BASE_URL = "http://192.168.25.51:8088/check/";
    public static final String BASE_URL2 = "http://192.168.25.51:8088/boss/";
    public static final String BASE_URL3 = "http://192.168.25.51:8088/resvlist/";
    public static final String BASE_URL4 = "http://192.168.25.51:8088/board/";

    @GET("list.jsp")
    Call<List<StoreVO>> listStore();

    @GET("resvlist.jsp")
    Call<List<ResvVO>> listResv();

    @GET("list.jsp")
    Call<List<Board>> listBoard();


    @Multipart
    @POST("insert.jsp")
    Call<Void> insertStore(@Query("category") String category, @Query("bid") String bid, @Query("bname") String bname,
                           @Query("name") String name, @Query("address") String address,
                           @Query("tel") String tel, @Query("intro") String intro,
                           @Query("logo") String logo);


    @POST("resvinsert.jsp")
    Call<Void> insertResv(@Query("rname") String rname, @Query("rtel") String rtel,
                          @Query("rdate") String rdate, @Query("rtime") String rtime,
                          @Query("bname") String bname);

    @POST("insert.jsp")
    Call<Void> insertBoard(@Query("title") String title, @Query("place") String place,
                          @Query("date") String date, @Query("time") String time,
                          @Query("diposit") String diposit, @Query("contents") String contents,
                           @Query("category") String category, @Query("createDate") String createDate);

    @GET("read.jsp")
    Call<StoreVO> readStore(@Query("bname") String bname);
}
