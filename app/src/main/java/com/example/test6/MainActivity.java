package com.example.test6;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;
import static com.example.test6.R.array.gubun_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.chromium.base.task.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;


    private ArrayList<Covid> arrayCovid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //retrofit
        ListView listView = findViewById(R.id.listview);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Covid>> call =  api.get_Covid();

        call.enqueue(new Callback<List<Covid>>() {
            @Override
            public void onResponse(@NonNull Call<List<Covid>> call, @NonNull Response<List<Covid>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Covid> covids = response.body();
                    String[] strArray_covid_info = new String[covids.size()];
                    int idx = 0;
                    Log.d("covid_log", Integer.toString(covids.size())); // 데이터를 출력하기 위해서 현재 데이터의 개수 로그로 확인.
                    for(Covid c : covids){
                        arrayCovid.add(c); // 받아온 데이터 저장해 둠.
                        //strArray_covid_info[idx] = "지역 : "+c.getGubun()+",  날짜 :  "+c.getStdDay()+", 총 확진자수 :  "+c.getDefCnt() + ", 일일확진자수 :  "+c.getLocalOccCnt();
                         //Log.d("covid_log", strArray_covid_info[idx]);
                        //idx++; // 위의 로그를 찍기 위해 idx 는 여기서 증가
                    }
                  /*  listView.setAdapter(
                            new ArrayAdapter<String>(
                                    getApplicationContext(),
                                    android.R.layout.simple_list_item_1,
                                    strArray_covid_info
                            )
                    );*/

                }else{
                    Toast.makeText(getApplicationContext(),"Failed to get data from the server.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Covid>> call, Throwable t) {
                Log.d("covid-err", t.getMessage().toString());
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                gubun_list, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                ArrayList<String> selectedArr = new ArrayList<String>();

                for(Covid c: arrayCovid){
                    if(arrayCovid.get(position).getGubun() == c.getGubun()){ //선택한 지역 == array 지역 비교
                        Log.e(">>>>> logggggg", c.getGubun());
                        selectedArr.add("지역 : "+c.getGubun()+"\n  날짜 :  "+c.getStdDay()+"\n 총 확진자수 :  "+c.getDefCnt() + "\n 일일확진자수 :  "+c.getLocalOccCnt()); //두지역이 같으면 배열에 데이터 추가
                    }
                }
                
                listView.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                selectedArr //Strein 타입, 지역 비교해서 동일한데이터 불러오기
                        )
                );

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner.setSelection(0);
            }


        });
    }
}