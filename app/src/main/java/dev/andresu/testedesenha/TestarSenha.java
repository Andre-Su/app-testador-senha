package dev.andresu.testedesenha;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.andresu.testedesenha.databinding.ActivityTestarSenhaBinding;

public class TestarSenha extends AppCompatActivity {
    private ActivityTestarSenhaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestarSenhaBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initObjects();
    }

    private void initObjects() {
        binding.txtVoltar.setOnClickListener(v -> finish());

        binding.textEditSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Este método é chamado para notificar que caracteres estão prestes a ser alterados
                // no intervalo [start, start+count) com novos caracteres com o comprimento de 'after'.
                // Você pode usar este método para fazer qualquer preparação antes do texto mudar.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int pontos = 0;
                Pattern patternSizeMin = Pattern.compile("^.{6,}$");
                Pattern patternSizeGood = Pattern.compile("^.{8,}$");
                Pattern patternSizeMax = Pattern.compile("^.{6,30}$");
                Pattern patternabz = Pattern.compile("^(?=.*[a-z])$");
                Pattern patternABZ = Pattern.compile("^(?=.*[A-Z])$");
                Pattern pattern123 = Pattern.compile("^(?=.*\\d)$");
                Pattern pttrnPassW = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,30}$");

                String test = s.toString();
                if(testString(test, patternSizeMin)){
                    pontos=pontos+10;
                } else pontos=pontos-10;

                progress(pontos);
                binding.txtSaida.setText(test);
                // Este método é chamado para notificar que caracteres dentro do intervalo [start, start+before)
                // foram substituídos por novos caracteres com comprimento de 'count'.
                // Este é o método principal usado para monitorar mudanças de texto em tempo real.
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Este método é chamado para notificar que o texto foi alterado.
                // Você pode usar este método para realizar ações após o texto ter sido alterado.
            }
        });
    }

    public void progress(int size){
        binding.progressBar.setProgress(size, true);
    }

    public static boolean testString(String test, @NonNull Pattern pattern){
        Matcher matcher = pattern.matcher(test);
        return matcher.matches();
    }
}