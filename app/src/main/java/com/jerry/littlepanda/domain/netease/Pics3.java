
package com.jerry.littlepanda.domain.netease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pics3 {

    @SerializedName("pic")
    @Expose
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


}
