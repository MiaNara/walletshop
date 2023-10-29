package com.example.project.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Wallet implements Parcelable {
    private String id;
    private String name;
    private String price;
    private String image;
    private String description;

    private String material;
    private String size;



    public Wallet(String name, String price, String image, String description, String material, String size) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.material = material;
        this.size = size;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // Parcelable implementation
    protected Wallet(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readString();
        image = in.readString();
        description = in.readString();
        material = in.readString();
        size = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(material);
        dest.writeString(size);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
}
