package com.example.projek8;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView ads = view.findViewById(R.id.ads);

        int[] imageIds = {R.id.image1sec1, R.id.image2sec1, R.id.image3sec1, R.id.image1sec2, R.id.image2sec2, R.id.image3sec2, R.id.image1sec3, R.id.image2sec3, R.id.image3sec3};

        Random random = new Random();
        String url;

//        for (int i = 0; i < imageIds.length; i++){
//            ImageView imageViews = view.findViewById(imageIds[i]);
//            int randomNumber = random.nextInt(1000);
//            url = "https://picsum.photos/300/300?random=" + randomNumber;
//            Glide.with(this)
//                    .load(url)
//                    .into(imageViews);
//        }

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
                        int[] imageIds = {R.id.image1sec1, R.id.image2sec1, R.id.image3sec1, R.id.image1sec2, R.id.image2sec2, R.id.image3sec2, R.id.image1sec3, R.id.image2sec3, R.id.image3sec3};
                        for (int i = 0; i < imageIds.length; i++) {
                            if (jsonArray.length() > 0) {
                                int customIndex = (i + random.nextInt(jsonArray.length())) % jsonArray.length();
                                JSONObject firstImage = jsonArray.getJSONObject(customIndex);
                                String imageUrl = firstImage.getString("preview_url");
                                if (!imageUrl.startsWith("http")) {
                                    imageUrl = "https:" + imageUrl;
                                }

                                ImageView imgID = view.findViewById(imageIds[i]);
                                // Jalankan Glide di main thread
                                String finalImageUrl = imageUrl;
                                imgID.post(() -> {
                                    Glide.with(imgID.getContext())
                                            .load(finalImageUrl)
                                            .into(imgID);
                                imgID.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                System.out.println(finalImageUrl);
                                });
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        int randomNumber = random.nextInt(1000);
        url = "https://picsum.photos/1000/400?random=" + randomNumber;
        Glide.with(this)
                .load(url)
                .into(ads);

        return view;
    }


}