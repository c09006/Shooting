package com.example.shiozaki.shooting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by shiozaki on 15/02/17.
 */

//STEP6追加
//敵機用
public class Tekiki extends Object {
    public Tekiki(){}
    public Tekiki(float dw, float dh){
        super(dw, dh);
    }

    @Override
    public void ODraw(Canvas c) {
        //画像を表示させていいかどうかの、dead状態を調べる
        //cx, cyは中心座標のため、画像の中心に来るように調整
        if(dead == false){
            img.setBounds((int)(cx - imgw / 2), (int)(cy - imgh / 2),(int)(cx + imgw / 2), (int)(cy + imgw / 2));
            img.draw(c);
            OdrawRect(c);
        }
    }

    @Override
    public void Oint(Bitmap imgb, float x, float y, float sx, float sy, int w, int h) {

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
        dead = false;
        tamar = tj;
        //当たり判定の初期化
        atarir = new Rect((int)cx - 30, (int)cy - 30, (int)cx + 30, (int)cy + 30);
        obsyurui = 1;

    }

    @Override
    public void OMove() {

    }

    @Override
    public void OMove(int x, int y) {

    }

    @Override
    public Rect OgetTapRect() {
        return null;
    }
}
