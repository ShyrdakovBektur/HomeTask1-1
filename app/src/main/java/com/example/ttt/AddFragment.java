package com.example.ttt;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AddFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment, container, false);
        imageView = view.findViewById(R.id.image_view);
        imageView.setOnClickListener(view1 -> pickImageFromGallery());
        Bundle bundle = getArguments();
        if (bundle != null) {
            String searchText = bundle.getString("searchText");
            EditText editText = view.findViewById(R.id.edit_text);
            editText.setText(searchText);
        }

        Button button = view.findViewById(R.id.button);
        button.setText("Edit");

        return view;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public static AddFragment newInstance(String searchText) {
        AddFragment addFragment = new AddFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchText", searchText);
        addFragment.setArguments(bundle);
        return addFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(),uri);
                ImageView imageView1 = requireView().findViewById(R.id.image_view);
                imageView1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
