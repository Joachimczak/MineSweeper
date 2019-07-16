package com.example.michal.fullsaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;

    RunnableDemo( String name) {
        threadName = name;
    }

    public void run() {
        try {
                MainActivity.funkcja();
                Thread.sleep(750);

        } catch (InterruptedException e) {

        }
    }

    public void start () {
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}

public class MainActivity extends AppCompatActivity {
//Integer i = 3;
final static ImageButton[][] Tab_b = new ImageButton[5][5];
final static int pl_[][]=new int[5][5];

    Random rand_=new Random();
Integer liczbaBomb = rand_.nextInt(5)+1;
   static ImageButton[][] przyciski2;
   static TextView tekst2;
   RunnableDemo watek = new RunnableDemo("watek");
   static public void funkcja() {
        for (; ; ) {
           int i = 0;
        //}
            for(int x=0; x<5;x++) {
                for (int z = 0; z < 5; z++) {
                    if (pl_[x][z] != -1) {
                        if (Integer.parseInt(Tab_b[x][z].getTag().toString()) == 2)
                            i += 1;
                    }
                    else i+=1;
                }
            }
            if(i==25) {
                for (int x = 0; x < 5; x++)
                    for (int z = 0; z < 5; z++)
                        Tab_b[x][z].setTag(3);
                tekst2.setText("Wygrałeś!");
            }
        }
    }
   public void odsloniecie(int x, int y, int[][] pl ) {
       for (int k = x - 1; k < x + 2; k++)
           for (int l = y - 1; l < y + 2; l++) {
               if(k>=0 && k<5 && l>=0 && l<5 && Integer.parseInt(Tab_b[k][l].getTag().toString()) == 0) {
                   if (k != x || l != y) {
                       Tab_b[k][l].setTag(2);
                       switch (pl[k][l]) {
                           case 0:
                               Tab_b[k][l].setImageDrawable(getResources().getDrawable(R.drawable.button0));
                               odsloniecie(k, l, pl);
                               break;
                           case 1:
                               Tab_b[k][l].setImageDrawable(getResources().getDrawable(R.drawable.button1));
                               break;
                           case 2:
                               Tab_b[k][l].setImageDrawable(getResources().getDrawable(R.drawable.button2));
                               break;
                           case 3:
                               Tab_b[k][l].setImageDrawable(getResources().getDrawable(R.drawable.button3));
                               break;
                           case 4:
                               Tab_b[k][l].setImageDrawable(getResources().getDrawable(R.drawable.button4));
                               break;
                           case 5:
                               Tab_b[k][l].setImageDrawable(getResources().getDrawable(R.drawable.button5));
                               break;
                       }
                   }
               }
           }
   }
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout ta1=findViewById(R.id.tableLayout1);
        final TextView liczba;
        final TextView tekst = (TextView)findViewById(R.id.textView2);
        tekst2 = tekst;
        liczba = (TextView)findViewById(R.id.textView);
        liczba.setText(""+liczbaBomb);
        TableRow Tab_tr[]=new TableRow[5];

        for(int z=0;z<5;z++){
            Tab_tr[z]=new TableRow(ta1.getContext());
            for(int x=0;x<5;x++){
                Tab_b[z][x]=new ImageButton(Tab_tr[z].getContext());
               // Tab_b[z][x].setTag(""+z+" "+x);
                Tab_tr[z].addView(Tab_b[z][x]);
            }

            ta1.addView(Tab_tr[z]);
        }
        for(int x=0; x<5;x++)
            for(int z=0; z<5; z++){
                Tab_b[x][z].setTag(0);
                Tab_b[x][z].setImageDrawable(getResources().getDrawable(R.drawable.button));}

przyciski2 = Tab_b;

        for(int x=0; x<5;x++) {
            for (int z = 0; z < 5; z++) {
                final int c = x;
                final int b = z;
                Tab_b[x][z].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(liczbaBomb>=0){
                        if(Integer.parseInt(Tab_b[c][b].getTag().toString()) == 0 && liczbaBomb>0){
                            Tab_b[c][b].setImageDrawable(getResources().getDrawable(R.drawable.x));
                            Tab_b[c][b].setTag(1);
                            liczbaBomb--;}
                        else if(Integer.parseInt(Tab_b[c][b].getTag().toString()) == 1){
                            Tab_b[c][b].setImageDrawable(getResources().getDrawable(R.drawable.button));
                            Tab_b[c][b].setTag(0);
                            liczbaBomb++;}
                        liczba.setText(liczbaBomb.toString());}
                    return true;
                }
            });
            }
        }

        int bomba[][]=new int[liczbaBomb][2];

        //Random rand_=new Random();

        for(int x=0;x<liczbaBomb;x++){
            bomba[x][0]=rand_.nextInt(5);
            bomba[x][1]=rand_.nextInt(5);
            //Tab_b[bomba[i][0]][bomba[i][1]].setText("b");
            for(int k=0;k<x;k++)
                if(bomba[k][0]==bomba[x][0] && bomba[k][1]==bomba[x][1]){
                    x--;
                    break;
                }
        }


        for(int x=0;x<5;x++) for(int z=0;z<5;z++) pl_[x][z]=0;
        for(int x=0;x<liczbaBomb;x++) {
            pl_[bomba[x][0]][bomba[x][1]]=-1;
            for(int k=bomba[x][0]-1;k<bomba[x][0]+2;k++)
                for(int l=bomba[x][1]-1;l<bomba[x][1]+2;l++)
                    if(k>=0 && k<5 && l>=0 && l<5 && pl_[k][l]>=0) pl_[k][l]+=1;
        }

        for(int x=0;x<5;x++)
            for(int z=0;z<5;z++){
           // if(pl_[x][z]==0){
                final int x1 = x, z1 = z;
                Tab_b[x][z].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Integer.parseInt(Tab_b[x1][z1].getTag().toString()) == 0){
                            if(pl_[x1][z1]==0){
                            Tab_b[x1][z1].setImageDrawable(getResources().getDrawable(R.drawable.button0));
                            Tab_b[x1][z1].setTag(2);
                            odsloniecie(x1,z1,pl_);
                                }
                            if(pl_[x1][z1]>0){
                                switch (pl_[x1][z1]) {
                                    case 1:  Tab_b[x1][z1].setImageDrawable(getResources().getDrawable(R.drawable.button1));
                                        break;
                                    case 2:  Tab_b[x1][z1].setImageDrawable(getResources().getDrawable(R.drawable.button2));
                                        break;
                                    case 3:  Tab_b[x1][z1].setImageDrawable(getResources().getDrawable(R.drawable.button3));
                                        break;
                                    case 4:  Tab_b[x1][z1].setImageDrawable(getResources().getDrawable(R.drawable.button4));
                                        break;
                                    case 5:  Tab_b[x1][z1].setImageDrawable(getResources().getDrawable(R.drawable.button5));
                                        break;
                                }
                                Tab_b[x1][z1].setTag(2);
                            }
                            if(pl_[x1][z1]==-1){
                                for(int x=0;x<5;x++)
                                    for(int z=0;z<5;z++){
                                    if(pl_[x][z]==-1) {
                                        Tab_b[x][z].setImageDrawable(getResources().getDrawable(R.drawable.x));
                                        Tab_b[x][z].setTag(3);}
                                        else {
                                        Tab_b[x][z].setImageDrawable(getResources().getDrawable(R.drawable.fail));
                                        Tab_b[x][z].setTag(3);}
                                    }
                                    tekst.setText("Przegrałeś");
                            }
                            }

                        }
                });
            //}
            }


watek.start();
    }
}
