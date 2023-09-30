package com.example.openweatherstartassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
TextView textView;
Button button;
EditText editText;
TextView longi;
TextView lat;
private static final String API_KEY = "4a0200f7df8d65297ec8215555dfc68c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextTextPersonName);
        longi = findViewById(R.id.textView3);
        lat = findViewById(R.id.textView2);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t1 = new Thread("Thread") {
                    @Override
                    public void run() {
                        String zipcode = editText.getText().toString();
                        if (zipcode == null || zipcode.isEmpty()) {
                            return;
                        }
                        try {
                            URL geoApiUrl = new URL("http://api.openweathermap.org/geo/1.0/zip?zip=" + zipcode + ",US&appid=" + API_KEY);
                            URLConnection urlCon = geoApiUrl.openConnection();
                            InputStream is = urlCon.getInputStream();
                            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                            String content = readAllLines(bf);
                            System.out.println("Read:" + content);
                            JSONObject jsonObject = new JSONObject(content);
                            lat.setText(jsonObject.getString("lat"));
                            longi.setText(jsonObject.getString("lon"));


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                };
                t1.start();

            }
        });
    }
    public String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        return content.toString();
    }
}
