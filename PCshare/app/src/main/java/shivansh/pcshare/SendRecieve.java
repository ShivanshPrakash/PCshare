package shivansh.pcshare;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class SendRecieve extends AppCompatActivity {
    String status = "Connected to server";
    public Toast toast;
    Button sendButt;
    EditText enterMess;
    TextView chat;
    String ipAddress;
    String chatting = "Chat Window\n-----------",message=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_recieve);
        Context mContext = SendRecieve.this;
        sendButt = (Button)findViewById(R.id.sendButton);
        enterMess = (EditText) findViewById(R.id.enterMessage);
        chat =(TextView) findViewById(R.id.chatWindow);
        toast = Toast.makeText(mContext,status,Toast.LENGTH_SHORT);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ipAddress= null;
            } else {
                ipAddress= extras.getString("IPaddress");
            }
        } else {
            ipAddress= (String) savedInstanceState.getSerializable("IPaddress");
        }
    }
    public void send(View view) {
        String response=null;
        message = (enterMess.getText()).toString();
        chatting=chatting+"\nMessage Sent : "+message;
        chat.setText(chatting);
        startThread(message);
        enterMess.getText().clear();
    }
    public void lock(View view){
        String lockMessage="LockTheFuckingScreen";
        String response=null;
        if(lockMessage!=null) {
            try {
                response = new Client(ipAddress, 8080, lockMessage, toast).execute().get();
            } catch (InterruptedException e) {
                toast.setText("Some Error Occured");
                toast.show();
            } catch (ExecutionException e) {
                e.printStackTrace();
                toast.setText("Some Error Occured");
                toast.show();
            }
        }
        chatting=chatting+"\nReply Recieved : "+response;
        chat.setText(chatting);
    }
    private void startThread(final String message) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                String response = null;
                if(message!=null) {
                    try {
                        response = new Client(ipAddress, 8080, message, toast).execute().get();
                    } catch (InterruptedException e) {
                        toast.setText("Some Error Occured");
                        toast.show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        toast.setText("Some Error Occured");
                        toast.show();
                    }
                }
                else
                {
                    toast.setText("Enter the message first");
                    toast.show();
                    return;
                }
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String finalResponse = response;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        chatting=chatting+"\nReply Recieved : "+ finalResponse;
                        chat.setText(chatting);
                    }
                });
            }
        };
        new Thread(runnable).start();
    }
}
