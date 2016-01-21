package com.example.shiozaki.shooting;

import android.graphics.Rect;
import android.media.MediaPlayer;

/**
 * Created by shiozaki on 15/02/16.
 */

/*
何度も使う汎用メソッド群
無理やりクラス
 */
public class Mesod {
    //各アプリの画面幅に合うように調整させるための変数
    static public final float NEXUS_W = 1200f;
    static public final float NEXUS_H = 1920f;

    //0も固定値に
    static public final float ZERO = 0f;

    //STEP4
    //πの値も固定値に
    static public final double PIE = 3.1415926;

    /*
    sin cosなどを使用するときに入れる値は、
    3.14を半周とした数値を１８０で割ったラジアン値
    角度設定などでは度数で出した方が簡単なので、設定は度数で行う。
    使用する時はこのメソッドでラジアン値に変換している。
     */
    //STEP4で追加
    public double toRadian(double deg){return (deg * PIE / 180);}

    //STEP5追加
    //サウンド再生用
    public void playSound(MediaPlayer mp){
        mp.seekTo(0);
        mp.start();
    }

    //受け取ったxy座標と調べたい短形範囲が重なっているかいないか
    public boolean RectTap(int x, int y, Rect gazou){
        return gazou.left < x && gazou.top < y && gazou.right > x && gazou.bottom > y;
    }

    //ここで各座標を実装機種の画面比に合わせる
    public int setSizeX(float disp_w, float zahyou){
        return (int)(zahyou * (disp_w / NEXUS_W));
    }
    public int setSizeY(float disp_h, float zahyou){
        return (int)(zahyou * (disp_h / NEXUS_W));
    }

    //STEP6
    //渡された短形と短形の当たり判定、重なっていればtrue
    public boolean RectRect(Rect oa, Rect ob){
        return oa.left < ob.right && ob.left < oa.right && oa.top < ob.bottom && ob.top < oa.bottom;
    }

}
