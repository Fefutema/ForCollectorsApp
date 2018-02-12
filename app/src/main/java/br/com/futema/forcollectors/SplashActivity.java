package br.com.futema.forcollectors;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {


    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivLogo = findViewById(R.id.ivLogo);
        carregar();

    }

    private void carregar(){
        Animation animLogo = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        animLogo.reset();

        if(ivLogo != null){
            ivLogo.clearAnimation();
            ivLogo.startAnimation(animLogo);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent proximaTela = new Intent(SplashActivity.this, LoginActivity.class);

                startActivity(proximaTela);
                SplashActivity.this.finish();
            }
        }, 3500);

    }

}
