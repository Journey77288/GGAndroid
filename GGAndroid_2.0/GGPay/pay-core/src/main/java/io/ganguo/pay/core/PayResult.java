package io.ganguo.pay.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roger on 05/07/2017.
 */

public class PayResult implements Parcelable {
    public String type;
    public String message;
    public String code;
    public String result;

    @Override
    public String toString() {
        return "PayResult{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", result='" + result + '\'' +
                '}';
    }


    public PayResult() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.message);
        dest.writeString(this.code);
        dest.writeString(this.result);
    }

    protected PayResult(Parcel in) {
        this.type = in.readString();
        this.message = in.readString();
        this.code = in.readString();
        this.result = in.readString();
    }

    public static final Creator<PayResult> CREATOR = new Creator<PayResult>() {
        @Override
        public PayResult createFromParcel(Parcel source) {
            return new PayResult(source);
        }

        @Override
        public PayResult[] newArray(int size) {
            return new PayResult[size];
        }
    };
}
