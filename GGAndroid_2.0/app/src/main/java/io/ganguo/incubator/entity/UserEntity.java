package io.ganguo.incubator.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tony on 10/6/15.
 */
public class UserEntity implements Parcelable {
    private int id;
    private String name;
    @SerializedName("api_token")
    private String apiToken;

    public UserEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "apiToken='" + apiToken + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.apiToken);
    }

    protected UserEntity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.apiToken = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
