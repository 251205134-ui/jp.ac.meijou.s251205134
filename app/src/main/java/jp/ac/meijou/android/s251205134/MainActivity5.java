package jp.ac.meijou.android.s251205134;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.TextLanguage;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import jp.ac.meijou.android.s251205134.databinding.ActivityMain5Binding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity5 extends AppCompatActivity {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final Moshi moshi = new Moshi.Builder().build();

    private final JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class);

    private ActivityMain5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getImage("https://placehold.jp/350x350.png");
        binding.buttonGet.setOnClickListener(view ->{
            var text = binding.editText.getText().toString();

            //textパラメータをつけたURLの作成
            var url = Uri.parse("https://placehold.jp/500x500.png")
                    .buildUpon()
                    .appendQueryParameter("text",text)
                    .build()
                    .toString();
            Log.d("test",url);
            getImage(url);
        });

    }

    private void getImage(String url){
        var request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 通信に失敗した時に呼ばれる
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // InputStreamをBitmapに変換
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                // UIスレッド以外で更新するとクラッシュするので、UIスレッド上で実行させる
                runOnUiThread(() -> binding.imageView.setImageBitmap(bitmap));
            }
        });
    }
}