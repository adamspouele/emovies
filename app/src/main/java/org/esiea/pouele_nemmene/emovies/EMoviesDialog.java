package org.esiea.pouele_nemmene.emovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EMoviesDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emovies_dialog);

        String message = getIntent().getExtras().getString("message");
        Button ok = (Button)findViewById(R.id.buttonOk);

        this.setTitle(message);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
}
