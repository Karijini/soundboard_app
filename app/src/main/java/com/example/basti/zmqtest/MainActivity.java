package com.example.basti.zmqtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.view.LayoutInflater;
import android.os.Handler;
import java.util.HashMap;
import android.animation.ObjectAnimator;
import android.animation.Animator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {
    SoundButton[] m_soundButtons;
    HashMap<String,SoundButton> m_progressBars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_progressBars = new HashMap<String,SoundButton>();

    }

    public void listSoundsOnClick(View v)
    {
        Button b = (Button)v;
        b.setText("clicked");
        System.out.println("test");
        new ZmqTask(this).execute("listSounds");
    }

    public void listSoundsResult(String r){
        System.out.println(r);
        String[] sounds = r.split("\\,");
        System.out.println(sounds.length);
        GridLayout gl = (GridLayout)this.findViewById(R.id.sound_list_layout);
        //gl.setLayoutParams(new LayoutParams
        //        (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //.setOrientation(GridLayout.HORIZONTAL);
        gl.removeAllViews();
        //gl.setColumnCount(3);
        //gl.setRowCount(3);
        m_soundButtons = new SoundButton[sounds.length];

        for(int i=0;i<sounds.length;i++)
        {
            m_soundButtons[i] = new SoundButton(this);
            m_soundButtons[i].setSound(sounds[i]);
            m_progressBars.put(sounds[i], m_soundButtons[i]);
            m_soundButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundButton b = (SoundButton)v;
                    new ZmqTask(MainActivity.this).execute("playSound:" + b.getSound());
                }
            });
            gl.addView(m_soundButtons[i]);

//            View child = getLayoutInflater().inflate(R.layout.sound_item, null);
//            m_soundButtons[i] = (Button)child.findViewById(R.id.play_button);
//            m_soundButtons[i].setText(sounds[i]);
//            m_soundButtons[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Button a = (Button) v;
//                    System.out.println(a.getText());
//                    new ZmqTask(MainActivity.this).execute("playSound:" + a.getText());
//                }
//            });
//            ProgressBar progress_bar = (ProgressBar)child.findViewById(R.id.progress_bar);
//            m_progressBars.put(sounds[i],progress_bar);
//            gl.addView(child);
        }
    }
    public void playSoundResult(String r){
        GridLayout gl = (GridLayout)this.findViewById(R.id.sound_list_layout);

        //ProgressBar progress = new ProgressBar(MainActivity.this,
        //        null,
        //        android.R.attr.progressBarStyleHorizontal);
        //progress.setProgress(50);
        String[] result = r.split("\\:");
        System.out.println(result[1]);
        //m_progressBars.get(result[1]).setProgress(50);
        final SoundButton pb = m_progressBars.get(result[1]);
        //gl.addView(progress);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "percent", 0, 360);
        System.out.println(result[2]);
        Float duration = Float.parseFloat(result[2]);
        animation.setDuration((int)(duration*1000));
        animation.setInterpolator(new LinearInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
                pb.setPercent(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();
    }
    public void result(String r)
    {
        System.out.println(r);
        //((TextView)this.findViewById(R.id.textView)).append(r);
    }
}
