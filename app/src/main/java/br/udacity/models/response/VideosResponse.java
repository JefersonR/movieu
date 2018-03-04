package br.udacity.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jeferson on 04/03/2018.
 */
public class VideosResponse implements Parcelable {

    private int id;
    private List<Result> results = null;
    public final static Parcelable.Creator<VideosResponse> CREATOR = new Creator<VideosResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VideosResponse createFromParcel(Parcel in) {
            return new VideosResponse(in);
        }

        public VideosResponse[] newArray(int size) {
            return (new VideosResponse[size]);
        }

    };

    protected VideosResponse(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.results, (Result.class.getClassLoader()));
    }

    public VideosResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

    public class Result implements Parcelable {

        private String id;
        private String iso_639_1;
        private String iso_3166_1;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;
        public final  Parcelable.Creator<Result> CREATOR = new Creator<Result>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            public Result[] newArray(int size) {
                return (new Result[size]);
            }

        };

        protected Result(Parcel in) {
            this.id = ((String) in.readValue((String.class.getClassLoader())));
            this.iso_639_1 = ((String) in.readValue((String.class.getClassLoader())));
            this.iso_3166_1 = ((String) in.readValue((String.class.getClassLoader())));
            this.key = ((String) in.readValue((String.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.site = ((String) in.readValue((String.class.getClassLoader())));
            this.size = ((int) in.readValue((int.class.getClassLoader())));
            this.type = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Result() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public void setIso_639_1(String iso_639_1) {
            this.iso_639_1 = iso_639_1;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public void setIso_3166_1(String iso_3166_1) {
            this.iso_3166_1 = iso_3166_1;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(iso_639_1);
            dest.writeValue(iso_3166_1);
            dest.writeValue(key);
            dest.writeValue(name);
            dest.writeValue(site);
            dest.writeValue(size);
            dest.writeValue(type);
        }

        public int describeContents() {
            return 0;
        }

    }


}
