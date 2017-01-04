package tw.com.opticalflow.luokangyu.base64;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import junit.framework.Assert;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private static String file;
    private ImageView imageView;
    private Button btn_img_from_assets;
    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 載入圖片
        imageView = (ImageView) findViewById(R.id.showImage);
        btn_img_from_assets = (Button) findViewById(R.id.btn_image_from_assets);

        btn_img_from_assets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //帶入Assets資料夾內的圖片路徑(Assets資料夾: android資料夾/black圖片)
                imageView.setImageBitmap(getBitmapFromAsset(MainActivity.this,"dogg.jpg"));


            }
        });


    }

    public Bitmap getBitmapFromAsset(Context context, String file){

        Bitmap bitmap = null;
        InputStream istr;
        AssetManager assetManager = context.getAssets();

        try{
            istr = assetManager.open(file);
            bitmap = BitmapFactory.decodeStream(istr);

         String base64Simage = Base64EnCode(bitmap);

            Base64DeCode(base64Simage);

            istr.close();
            return  bitmap;


        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }


    private String Base64EnCode(Bitmap bitmap){ // 傳入 bitmap 做 base64 編碼

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();

        String encodeImage = Base64.encodeToString(b,Base64.DEFAULT);

        Log.d(TAG,"!! Base64EnCode !! \n "+encodeImage);

        return encodeImage;

    }

    private void Base64DeCode(String base64dStr){ // 傳入 base64 編碼的字串，做解碼

//        Drawable drawable = MainActivity.this.getDrawable(R.drawable.bird);
//        imageView.setBackground(drawable);

        Toast.makeText(this,"do Base64DeCode ",Toast.LENGTH_SHORT).show();

        byte[] decodedBytes = Base64.decode(base64dStr,Base64.DEFAULT);

        Log.d(TAG,"!! Base64DnCode !!  "+decodedBytes.length);

        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length);

        imageView.setImageBitmap(bitmap);

        Toast.makeText(this," Base64DeCode DONE",Toast.LENGTH_SHORT).show();

    }
}
