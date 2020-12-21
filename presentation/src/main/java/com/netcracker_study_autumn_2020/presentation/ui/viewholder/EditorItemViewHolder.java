package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.library.network.UnsafeOkHttpClient;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.EditorItemModel;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.PhotoEditorFragment;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class EditorItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView editorItemPreview;
    private TextView editorItemTitle;
    private ImageButton deleteItemFrame;

    public EditorItemViewHolder(@NonNull View itemView) {
        super(itemView);
        editorItemPreview = itemView.findViewById(R.id.editor_item_preview);
        editorItemTitle = itemView.findViewById(R.id.editor_item_title);
        deleteItemFrame = itemView.findViewById(R.id.button_delete_frame);
        deleteItemFrame.setVisibility(View.GONE);
    }

    public void onBind(EditorItemModel editorItemModel, Fragment fragment) {
        switch (editorItemModel.getItemType()) {
            case FRAME:
                deleteItemFrame.setVisibility(View.VISIBLE);
                deleteItemFrame.setOnClickListener(l -> {
                    ((PhotoEditorFragment) fragment).deleteFrame(editorItemModel.getId());
                });
                editorItemTitle.setText(editorItemModel.getTitle());
                //Set preview using Picasso
                final long frameId = editorItemModel.getId();
                OkHttpClient client = UnsafeOkHttpClient.getPicassoUnsafeOkHttpClient(
                        SessionManager.getSessionToken());
                Picasso picasso = new Picasso.Builder(fragment.requireContext())
                        .downloader(new OkHttp3Downloader(client))
                        .build();

                String url = NetworkUtils.API_ADDRESS + NetworkUtils.GET_FRAME_PREVIEW_BY_ID +
                        "?id=" + frameId;
                Log.d("ONBIND", url);
                picasso.load(url)
                        .error(R.drawable.loading_error)
                        .placeholder(R.drawable.loading_animation)
                        .into(editorItemPreview);
                break;
            case FILTER:
                editorItemTitle.setText(editorItemModel.getTitle());
                editorItemPreview.setImageResource(editorItemModel.getImageResourceId());
                break;
        }
    }
}
