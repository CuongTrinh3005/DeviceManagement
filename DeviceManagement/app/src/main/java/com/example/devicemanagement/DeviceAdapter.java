package com.example.devicemanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Entities.Device;

import java.util.Arrays;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class DeviceAdapter extends ArrayAdapter {
    Context context;
    List<Device> devicesList;
    int layoutId;
    Button btnDelete;

    public DeviceAdapter(Context context, List devicesList, int layoutId) {
        super(context, layoutId);
        this.context = context;
        this.devicesList = devicesList;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return devicesList.size();
    }

    @SuppressLint("LongLogTag")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.device_list, null);

            ImageView icon = convertView.findViewById(R.id.icon);
            TextView deviceId = convertView.findViewById(R.id.tvId);
            TextView deviceName = convertView.findViewById(R.id.tvName);

            String id = devicesList.get(position).getId();
            String name = devicesList.get(position).getName();
            String type = devicesList.get(position).getTypeId();
            String origin = devicesList.get(position).getOrigin();
            int quantity = devicesList.get(position).getQuantity();
            String state = devicesList.get(position).getState();

            deviceId.setText(id);
            deviceName.setText(name);

            byte[] imageBytes = new byte[0];
            imageBytes = devicesList.get(position).getImage();

            if(imageBytes!=null && imageBytes.length > 0){
                // Recover to image from bytes array
                Bitmap decodedImage = ((DevicesManageActivity)context).restoreImage(imageBytes);
                if(decodedImage != null)
                    icon.setImageBitmap(decodedImage);
                else
                    icon.setImageResource(R.drawable.maychieu);
            }
            else { // set default image for icon
                icon.setImageResource(R.drawable.maychieu);
            }
            btnDelete = convertView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Device device = new Device(id, name, type, origin, null, Integer.valueOf(quantity), state);
                    ((DevicesManageActivity)context).deleteDV(device);
                }
            });
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Icon's clicked!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), showImageInFullScreen.class);
                    Bundle b = new Bundle();
                    b.putString("deviceId", id.trim());

                    intent.putExtras(b);
                    getContext().startActivity(intent);
                }
            });
        }
        catch (Exception ex){
            Log.d("Exception in custom list view: ", ex.toString());
            ex.printStackTrace();
        }

        return convertView;
    }
}