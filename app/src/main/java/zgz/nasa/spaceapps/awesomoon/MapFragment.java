package zgz.nasa.spaceapps.awesomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 * Fragmento para el contenido principal
 */
public class MapFragment extends Fragment {
    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_LAYOUT = "Layout";
    public static final String WEB = "https://www.google.com/moon/";

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);
        getActivity().setTitle(getString(R.string.moon_map));
        //Obtenemos el webview
        final WebView web = (WebView) view.findViewById(R.id.webview_map);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.getSettings().setAppCacheEnabled(false);
        web.clearCache(true); //vaciar caché
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setSupportZoom(true);
        web.loadUrl(WEB);
        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                web.loadUrl("javascript:(function() { " +
                        "document.getElementById('header').style.display=\"none\"; " +
                        "})()");
            }
        });
        return view;
    }

}
