package shivansh.pcshare;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import static android.content.ContentValues.TAG;

public class Client extends AsyncTask<Void,Void,String> {
    private String ipAddress,message,response=null;
    private int portNumber;
    Toast toast;
    Client (String addr, int port, String message, Toast toast)
    {
        ipAddress=addr;
        portNumber=port;
        this.message=message;
        this.toast=toast;
    }
    @Override
    protected String doInBackground(Void... voids) {
        Socket socket = null;
        try {
            socket = new Socket();
            SocketAddress address = new InetSocketAddress(ipAddress,portNumber);
            socket.connect(address,50);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int charsRead = 0;
            char[] buffer = new char[400];
            charsRead = in.read(buffer);
            response = new String(buffer).substring(0, charsRead);
            socket.close();
        }catch (SocketTimeoutException e){
            Log.d(TAG, "doInBackground: Timeout ho raha hai");
            toast.setText("Timeout Ho raha hai");
            toast.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"Exception ho raha hai");
            toast.setText("Unable to connect to server");
            toast.show();
        }
        return response;
    }
}
