package mca.project.agrozen;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class YoutubeViewHolder extends RecyclerView.ViewHolder {
WebView webView;
Button btn;


    @SuppressLint("SetJavaScriptEnabled")
    public YoutubeViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        webView = itemView.findViewById(R.id.video_view);
        btn = itemView.findViewById(R.id.fullscreen);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);

    }
}
