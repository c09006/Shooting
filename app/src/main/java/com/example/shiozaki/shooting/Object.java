/*
画面に表示させるオブジェクトを作成するためのクラス
このクラス単体ではインスタンス化できない。
このクラスをextendsして利用するようにする。
 */

package com.example.shiozaki.shooting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by shiozaki on 15/02/16.
 */
public abstract class Object {
    public Mesod ms = new Mesod();
    public float disp_w, disp_h;
    //オブジェクトの画像
    public Drawable img;
    //オブジェクトの中心座標
    public float cx, cy;
    //向かおうとしている座標
    public float vx, vy;
    //オブジェクトの移動スピード
    public  float spx, spy;
    //オブジェクトの大きさ
    public int imgw, imgh;

    //step3追記
    //オブジェクトを消去させるための変数
    public boolean dead;

    //STEP4追加
    //弾の状態
    public int tamajoutai;
    //弾画像の角度
    public int tamar;

    //STEP6
    //オブジェクトの当たり範囲
    public Rect atarir;
    //オブジェクトの種類
    public int obsyurui;

    public Object(){}
    public Object(float dw, float dh){
        disp_w = dw;
        disp_h = dh;
    }

    //STEP4追加
    //abstractを追加
    public abstract void ODraw(Canvas c);
    public abstract void Oint(Bitmap imgb, float x, float y, float sx, float sy, int w, int h);
    public abstract void Oint(Bitmap imgb, float x, float y, float sx, float sy, int w, int h, int tj);



    public abstract void OMove();
    public abstract void OMove(int x, int y);
    public abstract Rect OgetTapRect();

    public boolean OsotoX(int ww){return (cx - ww < 0 || cx + ww > disp_w);}
    public boolean OsotoY(int hh){return (cy - hh < 0 || cy + hh > disp_h);}
    public boolean Ogetdead(){return dead;}//表示していいかどうかを返す。

    //STEP6追加
    /*
    オブジェクト全てに、オブジェクト毎の当たり判定を作成した。
    ゲーム中見えないものなので、デバッグ用に可視化
     */
    public void OdrawRect(Canvas c){
        Paint p = new Paint();
        p.setColor(Color.RED);
        //c.drawRect(atarir, p);
    }

}
