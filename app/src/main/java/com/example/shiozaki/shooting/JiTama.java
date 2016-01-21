package com.example.shiozaki.shooting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by shiozaki on 15/02/17.
 */
//Step3追加
public class JiTama extends Object {
    //弾の画像の角度
    public int tamakakudo;

    public JiTama(){}
    public JiTama(float dw, float dh){
        super(dw, dh);
    }

    @Override
    public void ODraw(Canvas c) {
        //STEP4追加
        /*
        画像回転にあたり、rotateのみでやろうとすると、
        別画像も一緒に回転してしまう。そこで、save()で
        一旦画像を保存し、表示し終わったらrestore()で解放するようにする。

        画像を表示させていいかどうかdead状態を調べて表示
        cx,cyは中心座標のため、画像の中心にしっかりとcx,cyが来るように
        調整する
         */
        if(dead == false){
            c.save();
            img.setBounds((int)(cx - imgw / 2), (int)(cy - imgh / 2),(int)(cx + imgw / 2), (int)(cy + imgw / 2));
            c.rotate(tamar, cx, cy);
            img.draw(c);
            OdrawRect(c);
            c.restore();
        }
    }

    @Override
    public void Oint(Bitmap imgb, float x, float y, float sx, float sy, int w, int h) {
    }

    @Override
    public void Oint(Bitmap imgb, float x, float y, float sx, float sy, int w, int h, int tj) {
        //STEP4追加 初期設定
        img = new BitmapDrawable(imgb);
        cx = ms.setSizeX(disp_w, x);
        cy = ms.setSizeY(disp_h, y);
        spx = sx;
        spy = sy;
        imgw = w;
        imgh = h;
        //step3追記
        dead = false;
        tamar = tj;
        //当たり判定の初期化
        atarir = new Rect((int)cx - 30, (int)cy - 30, (int)cx + 30, (int)cy + 30);
        obsyurui = 0;

    }

    /*
    まっすぐ飛ぶだけの弾
     */
    //STEP4追加
    /*
    角度で弾を飛ばすように設定
    画像角度と移動角度は-90度ほどの誤差があるので調整
    例えば角度０度でここもそのままの角度０にすると
    画像表示は正常だが飛ぶ方向が右方向になる
     */

    public void OMove(){
        cx += (float)Math.cos(ms.toRadian(tamar - 90)) * spx;
        cy += (float)Math.sin(ms.toRadian(tamar - 90)) * spy;

        //STEP6
        //当たり判定更新
        atarir = new Rect((int)cx - 10, (int)cy - 10, (int)cx + 10, (int)cy + 10);

        /*
        範囲外に出たら弾を消す
         */
        if(OsotoX( -imgw / 2) == true) dead = true;
        //STEP4追加
        //範囲外に出たら消す
        if(OsotoY( -imgh / 2) == true) dead = true;
    }

    public void OMove(int x, int y){}
    public Rect OgetTapRect(){return null;}
}
