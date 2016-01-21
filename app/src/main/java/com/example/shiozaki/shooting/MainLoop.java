package com.example.shiozaki.shooting;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.jar.Attributes;

/**
 * Created by shiozaki on 15/02/16.
 */
public class MainLoop extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private Thread thread;

    //どのActivityを利用しているかの変数
    private Shooting sa;
    private Mesod ms;
    private float disp_w,disp_h;
    private Drawable jikiimg,tamaimg;

    //STEP3追加
    //弾関係
    private Bitmap jikibit, tamabit;
    //STEP3追加
    //弾フラグ　連続で重ならないように変数
    private boolean tamaflg;
    private int tamatime;

    //STEP4
    //弾変化用ボタン
    private Rect tamabtn;

    //STEP5
    //音源用
    private MediaPlayer jitama1s, jitama2s, jitama3s;

    //STEP6追加
    //敵画像用
    private Drawable tekiimg;
    private Bitmap tekibit;

    //STEP7追加
    //爆発画像用


    //STEP3追加
    //弾をたくさん表示するためにArrayListを使う。
    //これはいくつ弾が飛ぶかわからないため、複数の配列を制御するためのもの
    private ArrayList<Object> object = new ArrayList();

    //Objectクラスを使用する準備
    private Object jiki;

    //自前でViewを実装するときに呼ばれるコンストラクタ
    public MainLoop(Context context){
        super(context);
        init(context);
    }

    //xml方式でViewを呼び出すときに呼ばれる
    public MainLoop(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        holder = getHolder();//お決まり
        holder.addCallback(this);//お決まり
        holder.setFixedSize(getWidth(), getHeight());//お決まり
        sa = (Shooting)context;
        ms = new Mesod();
        disp_w = sa.disp_w;
        disp_h = sa.disp_h;

        Resources resources = context.getResources();//画像登録準備
        //ビットマップ方式で画像取り込み
        Bitmap img = BitmapFactory.decodeResource(resources, R.drawable.jiki);
        //画像分割
        jikibit = Bitmap.createBitmap(img, 0, 0, img.getWidth() / 4, img.getHeight());
        tamabit = Bitmap.createBitmap(img, img.getWidth() / 4, 0, img.getWidth() / 4, img.getHeight());



        //Objectクラスではインスタンスできないので、ObjectクラスをExtendsさせたjikiクラスを実装
        //STEP3
        //ArrayListを使用しているため、addクラスでインスタンス化している。
        //ArrayListの０番目の要素に自機が入っている
        object.add(new Jiki(disp_w, disp_h));
        object.get(0).Oint(jikibit, 240, 425, 0, 0, jikibit.getWidth(), jikibit.getHeight(), 0);

        tamaflg = true;
        tamatime = 5;
        /*
        STEP3削除
        jiki = new Jiki(disp_w, disp_h);

        jiki.Oint(jikibit, 240, 800, 10, 10, jikibit.getWidth(), jikibit.getHeight());
        */
        //STEP4
        //弾ボタン用座標登録
        tamabtn = new Rect(50, 50, 100, 100);

        //STEP5音源追加
        jitama1s = MediaPlayer.create(context, R.raw.jitama1);
        jitama2s = MediaPlayer.create(context, R.raw.jitama2);
        jitama3s = MediaPlayer.create(context, R.raw.jitama3);
        try {
            jitama1s.prepare();
            jitama2s.prepare();
            jitama3s.prepare();
        }catch(Exception e){}

        //STEP6
        /*
        敵画像登録＆作成
         */
        tekibit = Bitmap.createBitmap(img, img.getWidth() / 4 * 2, 0, img.getWidth() / 4, img.getHeight());

        //STEP6　ランダムで敵機を１０機作成
        Random r = new Random(new Date().getTime());
        for(int i = 0; i < 10; i++){
            int x = r.nextInt((int)(disp_w - 50));
            int y = r.nextInt((int)(disp_h / 2));
            object.add(new Tekiki(disp_w, disp_h));
            object.get(object.size() - 1).
                    Oint(tekibit, x, y, 0, 0, tekibit.getWidth(), tekibit.getHeight(), 0);
        }


    }

    //implements Runnableを実装すると、このメソッドが自動追加

    public void run(){
        Canvas c;
        Paint p = new Paint();
        p.setAntiAlias(true);

        while(thread != null){
            c = holder.lockCanvas();//お決まり
            c.drawColor(Color.BLACK);

            //STEP4
            //弾変化ボタン
            p.setColor(Color.BLUE);
            c.drawRect(tamabtn, p);
            p.setTextSize(30);
            c.drawText("tama:" + object.get(0).tamajoutai, 50, 150, p);
            /*
            STEP3追加
            自機も弾も同じ Ocject を持っているので、インスタンス化時に作成したいクラスを指定すれば
            同じObjectクラスとして使用することができる。
             */
            for(int i = 0; i < object.size(); i++){
                object.get(i).ODraw(c);
                object.get(i).OMove();

                //STEP6 あたり判定
                Atarihantei(i);
                /*
                弾が画面外に出たらオブジェクト要素を消去する
                 */
                if(object.get(i).Ogetdead() == true) object.remove(i);
            }
            /*
                弾が１発撃ったら連続で打てないようになり、タイマーが０になったら
                打てるようにする。
            */
            if(tamaflg == false){
                --tamatime;
                if(tamatime < 0){
                    tamatime = 5;
                    tamaflg = true;
                }
            }

            /*
            STEP3削除
            jiki.ODraw(c);
            */
            holder.unlockCanvasAndPost(c);//お決まり
            try{
                thread.sleep(50);//お決まり
            }catch (Exception e){

            }
        }
    }

    //STEP6 当たり判定メソッド
    private void Atarihantei(int i) {
        for(int j = 0; j < object.size() - 1; j++){
            if(i != j && object.get(i).obsyurui != object.get(j).obsyurui){
                if(ms.RectRect(object.get(i).atarir, object.get(j).atarir) == true){
                    object.get(i).dead = true;
                    object.get(j).dead = true;
                }
            }
        }
    }


    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                /*
                STEP3追加
                弾を射出可能状態
                 */
                if(tamaflg == true && ms.RectTap(x, y, object.get(0).OgetTapRect()) == true){
                   // object.add(new JiTama(disp_w, disp_h));
                    //object.get(object.size() - 1).Oint(tamabit, object.get(0).cx, object.get(0).cy - jikibit.getHeight(),
                    //        10, 30, tamabit.getWidth(), tamabit.getHeight());
                    //tamaflg = false;
                    Tamajoutai();
                    tamaflg = false;
                    
                }

                /*
                弾状態を変化させるボタン
                青色短形をタップすると変化
                 */
                if(ms.RectTap(x, y, tamabtn) == true){
                    ++object.get(0).tamajoutai;
                    object.get(0).tamajoutai = (object.get(0).tamajoutai + 3) % 3;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                //タップ範囲にオブジェクトがあると自機の移動処理を行う
                //STEP3で移動しないようにして、タップすると弾を発射
                //STEP4で自機移動追加、ただし移動と発射は同時にできない
                //if(ms.RectTap(x, y, jiki.OgetTapRect()) == true) jiki.OMove(x, y);
                if(ms.RectTap(x, y, object.get(0).OgetTapRect()) == true)object.get(0).OMove(x, y);
                break;

        }
        return true;
    }

    /*
    STEP4追加
    Objectクラスに弾の状態を表す変数を用意している。
    それを外部から操作して変化させることで、ここのif文の分岐に割り当てられる
     */
    public void Tamajoutai(){
        //通常の１発だけ
        if(object.get(0).tamajoutai == 0){
            object.add(new JiTama(disp_w, disp_h));
            object.get(object.size() - 1).Oint(
                    tamabit, object.get(0).cx, object.get(0).cy - jikibit.getHeight(),
                    0, 30, tamabit.getWidth(), tamabit.getHeight(), 0
            );
            //STEP5追加
            //音源再生
            ms.playSound(jitama1s);
        }
        //２発並んで
        if(object.get(0).tamajoutai == 1){
            object.add(new JiTama(disp_w, disp_h));
            object.get(object.size() - 1).Oint(
                    tamabit, object.get(0).cx - 20, object.get(0).cy - jikibit.getHeight(),
                    0, 30, tamabit.getWidth(), tamabit.getHeight(), 0
            );
            object.add(new JiTama(disp_w, disp_h));
            object.get(object.size() - 1).Oint(
                    tamabit, object.get(0).cx + 20, object.get(0).cy - jikibit.getHeight(),
                    0, 30, tamabit.getWidth(), tamabit.getHeight(), 0
            );
            //STEP5追加
            //音源再生
            ms.playSound(jitama2s);

        }
        //36度ずつ自機の周りに１０発一気に出す
        if(object.get(0).tamajoutai == 2){
            for(int i = 0; i < 10; i++){
                object.add(new JiTama(disp_w, disp_h));
                object.get(object.size() - 1).Oint(
                        tamabit, object.get(0).cx, object.get(0).cy,
                        30, 30, tamabit.getWidth(), tamabit.getHeight(), i * (360 / 10)
                );
            }
            //STEP5追加
            //音源再生
            ms.playSound(jitama3s);

        }
    }


    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}//お決まり
    public void surfaceCreated(SurfaceHolder arg0){
        thread = new Thread(this);
        thread.start();//お決まり
    }
    public void surfaceDestroyed(SurfaceHolder arg0){thread = null;}//お決まり
}
