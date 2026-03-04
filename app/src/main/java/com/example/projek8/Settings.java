package com.example.projek8;

import static com.example.projek8.R.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.Random;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_settings, container, false);
        ImageView imageBackground = view.findViewById(id.background);
        ImageView imageProfile = view.findViewById(id.circleIMG);

        Random random = new Random();
//        final int[] randomNumber = {random.nextInt(1000)};
//        String url = "https://picsum.photos/400/400?random=" + randomNumber[0];
//        Glide.with(this)
//                .load(url)
//                .transform(
//                        new CenterCrop(),
//                        new BlurTransformation(20, 3),
//                        new BrightnessFilterTransformation(-0.15f)
//                )
//                .into(imageBackground);

//        Glide.with(this)
//                .load(url)
//                .into(imageProfile);

        // Inflate the layout for this fragment

        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://safebooru.org/index.php?page=dapi&s=post&q=index&tags=jujutsu_kaisen&json=1";

        Request request = new Request.Builder().url(apiUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        if (jsonArray.length() > 0) {
                            int randomIndex = random.nextInt(jsonArray.length());
                            JSONObject firstImage = jsonArray.getJSONObject(randomIndex);
                            String imageUrl = firstImage.getString("file_url");
                            if (!imageUrl.startsWith("http")) {
                                imageUrl = "https:" + imageUrl;
                            }

                            // Jalankan Glide di main thread
                            String finalImageUrl = imageUrl;
                            imageProfile.post(() -> {
                                Glide.with(imageProfile.getContext())
                                        .load(finalImageUrl)
                                        .into(imageProfile);
                            });
                            imageBackground.post(()->{
                                Glide.with(imageBackground.getContext())
                                        .load(finalImageUrl)
                                        .transform(
                                                new CenterCrop(),
                                                new BlurTransformation(20, 3),
                                                new BrightnessFilterTransformation(-0.15f)
                                        )
                                        .into(imageBackground);
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }
}