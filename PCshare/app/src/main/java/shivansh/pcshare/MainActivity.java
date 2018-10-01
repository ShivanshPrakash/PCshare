package shivansh.pcshare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String IPadd;
    EditText ip;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send_recieve = findViewById(R.id.sere);
        ImageButton about = findViewById(R.id.aboutButton);
        ip = (EditText)findViewById(R.id.ipText);
        ip.setText("192.168.43.141");
        send_recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IPadd = ip.getText().toString();
                Intent Intent = new Intent(MainActivity.this, SendRecieve.class);
                Context context = MainActivity.this;
                toast = Toast.makeText(context,"Chatting with "+IPadd,Toast.LENGTH_SHORT);
                toast.show();
                Intent.putExtra("IPaddress",IPadd);
                view.getContext().startActivity(Intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(MainActivity.this, AboutActivity.class);
                Context context = MainActivity.this;
                view.getContext().startActivity(Intent);
            }
        });
    }
}
