package com.example.shiozaki.shooting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by shiozaki on 15/02/16.
 */
public class Jiki extends Object{
    public Jiki(){}
    public Jiki(float dw, float dh) {
        super(dw, dh);
    }

    //STEP4追加
    public void Oint(Bitmap imgb, float x, float y, float sx, float sy, int w, int h){

    }

    @Override
    public void Oint(Bitmap imgb, float x, float y, float sx, float sy, int w, int h, int tj) {
        img = new BitmapDrawable(imgb);
        cx = ms.setSizeX(disp_w, x);
        cy = ms.setSizeY(disp_h, y);
        spx = sx;
        spy = sy;
        imgw = w;
        imgh = h;
        //step3追記
        dead = false;
        //弾の初期状態を受け取る
        tamajoutai = tj;

        //STEP6
        //当たり判定の初期化
        atarir = new Rect((int)cx - 30, (int)cy - 30, (int)cx + 30, (int)cy + 30);
        obsyurui = 0;
    }

    public void ODraw(Canvas c){
        if(dead == false){
            img.setBounds((int)(cx - imgw / 2), (int)(cy - imgh / 2), (int)(cx + imgw / 2), (int)(cy + imgh / 2));
            img.draw(c);
            OdrawRect(c);
        }
    }

    public void OMove(int x, int y) {
        float cxx = cx;
        float cyy = cy;
        cx = x;
        cy = y;
        if(OsotoX(imgw / 2) == true)cx = cxx;
        if(OsotoY(imgh / 2) == true)cy = cyy;
       // if(cx - imgw / 2 < 0 || cx + imgw / 2 > disp_w) cx = cxx;
       //if(cy - imgh / 2 < 0 || cy + imgh / 2 > disp_h) cy = cyy;
    }
    public void OMove(){}
    /*
    タップ範囲にオブジェクトがあると移動できるようにする。

     */

    public Rect OgetTapRect() {
        Rect taprect = new Rect(img.getBounds().left - 50, img.getBounds().top - 50,
                img.getBounds().right + 50, img.getBounds().bottom + 50);
        return taprect;

    }

}
