package com.example.basti.zmqtest;
import android.os.AsyncTask;
import org.zeromq.ZMQ;

/**
 * Created by basti on 17.01.2016.
 */
public class ZmqTask extends AsyncTask<String, Void, String> {
    private final MainActivity m_a;
    public ZmqTask(MainActivity a) {
        this.m_a = a;
    }

    @Override
    protected String doInBackground(String... params) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket worker = context.socket(ZMQ.REQ);
        System.out.println("connecting");
        //worker.connect("tcp://192.168.0.18:5555");
        worker.connect("tcp://192.168.0.1:5555");
        System.out.println("sending:");
        System.out.println(params[0]);
        worker.send(params[0].getBytes(), 0);

        String result = new String(worker.recv());
        worker.close();
        context.term();
        return result;
    }

    @Override
    protected void onPostExecute(String r) {
        if (m_a==null){
            return;
        }
        if (r.startsWith("listSoundsResult:")){
            m_a.listSoundsResult(r.split(":")[1]);
        }
        else if (r.startsWith("playSoundResult:")){
            m_a.playSoundResult(r);
        }
    }
}
