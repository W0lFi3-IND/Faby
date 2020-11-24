package com.findingbetteryou.faby.caloriecal;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.findingbetteryou.faby.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseCustomLocalModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetectCalorie extends AppCompatActivity {
    CameraView mCameraView;
    FirebaseModelInterpreter interpreter;
    FirebaseModelInterpreterOptions options;
    FirebaseModelInputOutputOptions inputOutputOptions;
    FirebaseCustomLocalModel localModel;
    String ans = "";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.stop();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_calorie);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Calorie_Recorder").child("Breakfast").child(firebaseAuth.getUid());


        mCameraView = findViewById(R.id.cameraview);
        localModel = new FirebaseCustomLocalModel.Builder()
                .setAssetFilePath("model.tflite")
                .build();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraView.start();
                mCameraView.captureImage();
            }
        });

        mCameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                mCameraView.stop();
                try {
                    classifier(bitmap);
                } catch (FirebaseMLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }
    void classifier(Bitmap bitmap) throws FirebaseMLException {

        try {
            options =
                    new FirebaseModelInterpreterOptions.Builder(localModel).build();
            interpreter = FirebaseModelInterpreter.getInstance(options);
        } catch (FirebaseMLException e) {
            // ...
        }
        inputOutputOptions =
                new FirebaseModelInputOutputOptions.Builder()
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 224, 224, 3})
                        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 21})
                        .build();
        int batchNum = 0;
        float[][][][] input = new float[1][224][224][3];
        for (int x = 0; x < 224; x++) {
            for (int y = 0; y < 224; y++) {
                int pixel = bitmap.getPixel(x, y);
                // Normalize channel values to [-1.0, 1.0]. This requirement varies by
                // model. For example, some models might require values to be normalized
                // to the range [0.0, 1.0] instead.
                input[batchNum][x][y][0] = (Color.red(pixel) - 127) / 128.0f;
                input[batchNum][x][y][1] = (Color.green(pixel) - 127) / 128.0f;
                input[batchNum][x][y][2] = (Color.blue(pixel) - 127) / 128.0f;
            }
        }
        FirebaseModelInputs inputs = new FirebaseModelInputs.Builder()
                .add(input)  // add() as many input arrays as your model requires
                .build();
        interpreter.run(inputs, inputOutputOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseModelOutputs>() {
                            @Override
                            public void onSuccess(FirebaseModelOutputs result) {
                                HashMap<Float, String> hashMap = new HashMap<>();
                                float[][] output = result.getOutput(0);
                                float[] probabilities = output[0];
                                BufferedReader reader = null;
                                try {
                                    reader = new BufferedReader(
                                            new InputStreamReader(getAssets().open("dict.txt")));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                for (int i = 0; i < probabilities.length; i++) {
                                    String label = null;
                                    try {
                                        label = reader.readLine();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i("MLKit", String.format("%s: %1.4f", label, probabilities[i]));
                                    hashMap.put(probabilities[i],label);
                                }
                                Map<Float,String> reverseSortedMap = new TreeMap<Float,String>(Collections.reverseOrder());
                                reverseSortedMap.putAll(hashMap);
                                Map.Entry<Float, String> entry = reverseSortedMap.entrySet().iterator().next();
                                //Toast.makeText(getApplicationContext(),"Value: "+entry.getValue(),Toast.LENGTH_LONG).show();
                                ans = "";

                                //hashmap to store values
                                 final String item = entry.getValue();
                                 final HashMap<String,Integer> calorie = new HashMap<String, Integer>();
                                 calorie.put("pizza",266);
                                calorie.put("butter_chicken",437);
                                calorie.put("butter_naan",110);
                                calorie.put("chicken_fried_rice",163);
                                calorie.put("chole_bhature",427);
                                calorie.put("dahi_bhalla",394);
                                calorie.put("dal_makhani",278);
                                calorie.put("gajar_halwa",386);
                                calorie.put("hilsa_fish_curry",217);
                                calorie.put("idli",39);
                                calorie.put("jalebi",150);
                                calorie.put("kachori",190);
                                calorie.put("kadai_paneer",248);
                                calorie.put("kulfi",161);
                                calorie.put("masala_dosa",540);
                                calorie.put("nalli_nihari",560);
                                calorie.put("pasta",131);
                                calorie.put("peda",98);
                                calorie.put("rasgulla",106);
                                calorie.put("samosa",90);
                                calorie.put("vada_pav",197);

                                //alert dialog to display item and enter quantity
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetectCalorie.this);
                                builder.setTitle(item.toUpperCase());
                                builder.setMessage("Enter quantity wrt servings");
                                final View alertdialog = getLayoutInflater().inflate(R.layout.alertdialog,null);
                                builder.setView(alertdialog);
                                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String id = databaseReference.push().getKey();

                                        SharedPreferences sharedPreferences = getSharedPreferences("PA",0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("id",id);
                                        editor.commit();

                                        EditText editText = alertdialog.findViewById(R.id.editText);
                                        String txt = editText.getText().toString();
                                        Integer txt1 = Integer.parseInt(txt);
                                        Integer x = calorie.get(item);
                                        Integer quantity = x*txt1;
                                        String totalcal = quantity.toString();
                                        //database storing
                                        CalDetails calDetails = new CalDetails(item,totalcal);
                                        databaseReference.child(id).setValue(calDetails);
                                       //databaseReference.child(firebaseAuth.getUid()).child(id).child("TotalCal").setValue(totalcal);
                                        Toast.makeText(getApplicationContext(),"Calories: "+totalcal,Toast.LENGTH_SHORT).show();
                                    }
                                });

                                AlertDialog ad = builder.create();
                                ad.show();
                            }
                        })

                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("MLKit", e.getMessage());
                            }
                        });
    }
}