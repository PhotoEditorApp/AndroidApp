package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.ImageItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ImagesGridRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isChoosingImageForCollage = false;

    private List<ImageModel> imageList;
    private Context context;

    private int rowIndex = -1;

    private ImagesView imagesView;

    private RecyclerView.ViewHolder touchedItem;

    public ImagesGridRecyclerAdapter(ImagesView imagesView) {
        imageList = new ArrayList<>();
        this.imagesView = imagesView;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ImageItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false), imagesView);
    }

    //TODO rewrite or delete this feature
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageItemViewHolder imageItemViewHolder = (ImageItemViewHolder) holder;

        imageItemViewHolder.getPreviewContainer().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("ACTION_UP", "test");
                if (!imagesView.isChoosingImagesForCollage()) {
                    int eventX = (int) event.getRawX();
                    int eventY = (int) event.getRawY();
                    if (!isViewInBounds(touchedItem.itemView, eventX, eventY)) {
                        touchedItem.itemView.setBackgroundColor(Color.rgb(255, 255, 255));
                        touchedItem = imageItemViewHolder;
                    }
                }
                imageItemViewHolder.itemView.setBackgroundColor(Color.rgb(255, 255, 255));
                return false;
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!imagesView.isChoosingImagesForCollage()) {
                    Log.d("ACTION_DOWN", "test");
                    touchedItem = imageItemViewHolder;
                    imageItemViewHolder.itemView.setBackgroundColor(Color.rgb(177, 247, 246));
                }
                return false;

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                Log.d("ACTION_MOVE", "test");
                if (!imagesView.isChoosingImagesForCollage()) {
                    int eventX = (int) event.getRawX();
                    int eventY = (int) event.getRawY();
                    if (!isViewInBounds(touchedItem.itemView, eventX, eventY)) {
                        touchedItem.itemView.setBackgroundColor(Color.rgb(255, 255, 255));
                        touchedItem = imageItemViewHolder;
                    }
                }
                return false;
            }
            return false;
        });
        if (rowIndex == position) {

        } else {

        }
        ((ImageItemViewHolder) holder).onBind(imageList.get(position), (Fragment) imagesView);
    }

    Rect outRect = new Rect();
    int[] location = new int[2];

    private boolean isViewInBounds(View view, int x, int y) {
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public void setImageList(List<ImageModel> imageList) {
        this.imageList.clear();
        this.imageList.addAll(imageList);
        notifyDataSetChanged();
    }
}
