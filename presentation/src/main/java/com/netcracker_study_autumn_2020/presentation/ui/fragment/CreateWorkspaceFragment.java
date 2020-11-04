package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flask.colorpicker.ColorPickerPreference;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.view.CreateWorkspaceView;

public class CreateWorkspaceFragment extends BaseFragment implements CreateWorkspaceView {
    @Override
    void initializePresenter() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_workspace, container, false);
        initInteractions(root);
        return root;
    }

    private void initInteractions(View root) {
        ImageButton choseWorkspaceColor = root.findViewById(R.id.button_chose_workspace_color);
        ImageView marker = root.findViewById(R.id.create_workspace_color_marker);
        choseWorkspaceColor.setOnClickListener(l -> {
            ColorPickerDialogBuilder
                    .with(getActivity())
                    .setTitle("Выберете цвет карточки рабочего пространства:")
                    .initialColor(Color.argb(255,155,155,155))
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .lightnessSliderOnly()
                    .density(12)
                    .setOnColorSelectedListener(new OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(int selectedColor) { }
                    })
                    .setPositiveButton("Выбрать", new ColorPickerClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int lastSelectedColor, Integer[] allColors) {
                            marker.setColorFilter(lastSelectedColor); }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    })
                    .build()
                    .show();
        });
    }
}
