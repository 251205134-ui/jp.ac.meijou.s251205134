package jp.ac.meijou.android.s251205134;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s251205134.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prefDataStore = PrefDataStore.getInstance(this);
        prefDataStore.getString("text")
                        .ifPresent(text -> {
                            if("あ".equals(text)){
                                binding.textView.setText("Aの画像");
                                binding.imageView.setImageResource(R.drawable.wifi);
                            } else if ("い".equals(text)) {
                                binding.textView.setText("Bの画像");
                                binding.imageView.setImageResource(R.drawable.no_wifi);
                            } else {
                                binding.textView.setText("知らない画像");
                            }

                        });

        binding.changebutton.setOnClickListener(view -> {
            String text = binding.editTextText.getText().toString();
            binding.textView.setText(text);
            if("あ".equals(text)){
                binding.textView.setText("Aの画像");
                binding.imageView.setImageResource(R.drawable.wifi);
            } else if ("い".equals(text)) {
                binding.textView.setText("Bの画像");
                binding.imageView.setImageResource(R.drawable.no_wifi);
            } else {
                binding.textView.setText("知らない画像");
            }
        });

        binding.savebutton.setOnClickListener(view -> {
            String text = binding.editTextText.getText().toString();
            prefDataStore.setString("text",text);
        });
    }
}