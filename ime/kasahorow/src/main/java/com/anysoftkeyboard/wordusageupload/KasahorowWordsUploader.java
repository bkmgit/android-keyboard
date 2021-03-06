package com.anysoftkeyboard.wordusageupload;

import android.util.Log;
import com.anysoftkeyboard.nextword.BuildConfig;
import com.anysoftkeyboard.nextword.NextWordsContainer;
import com.anysoftkeyboard.nextword.NextWordsStorage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KasahorowWordsUploader {

    private static String PATH = ".kasahorow.org/metrics/android";
    private static String TAG = "KasaWordsUploader";
    private Request.Builder mRequestBuilder;
    private NextWordsStorage mNextWordsStorage;

    public KasahorowWordsUploader(NextWordsStorage nextWordsStorage) {
        this.mNextWordsStorage = nextWordsStorage;
        this.mRequestBuilder = new Request.Builder();
    }

    public int upload(String locale) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean areStoredNextWordsEmpty = true;
        for (NextWordsContainer container : mNextWordsStorage.loadStoredNextWords()) {
            areStoredNextWordsEmpty = false;
            if (BuildConfig.DEBUG) Log.d(TAG, "Loaded for " + locale + " " + container);
            KasahorowData kasahorowData = new KasahorowData(container.word);
            stringBuilder.append(kasahorowData).append("\n");
        }
        int code = 0;
        if (areStoredNextWordsEmpty) return code;
        String url = "https://" + locale + PATH;
        if (BuildConfig.DEBUG)
            Log.d(TAG, "upload url " + url + "data: " + stringBuilder.toString());
        RequestBody body = new FormBody.Builder().add(locale, stringBuilder.toString()).build();
        Request request = mRequestBuilder.url(url).post(body).build();
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (BuildConfig.DEBUG) Log.d(TAG, "response code for " + response.code());
            code = response.code();
        } catch (IOException e) {
            Log.e(TAG, "HTTP upload request failed", e);
        }
        return code;
    }

    @SuppressWarnings("JdkObsolete")
    static class KasahorowData {
        private String mWord;
        private String mDate;

        KasahorowData(String word, Date date) {
            this.mWord = word;
            this.mDate = parseDate(date);
        }

        KasahorowData(String word) {
            this(word, new Date());
        }

        @Override
        public String toString() {
            return mWord + "\t" + mDate;
        }

        private String parseDate(Date date) {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date);
        }
    }
}
