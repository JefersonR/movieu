package br.udacity.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jeferson on 04/03/2018.
 */
public class ReviewsResponse implements Parcelable {

    private int id;
    private int page;
    private List<Result> results = null;
    private int total_pages;
    private int total_results;
    public final static Parcelable.Creator<ReviewsResponse> CREATOR = new Creator<ReviewsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReviewsResponse createFromParcel(Parcel in) {
            return new ReviewsResponse(in);
        }

        public ReviewsResponse[] newArray(int size) {
            return (new ReviewsResponse[size]);
        }

    };

    protected ReviewsResponse(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.results, (Result.class.getClassLoader()));
        this.total_pages = ((int) in.readValue((int.class.getClassLoader())));
        this.total_results = ((int) in.readValue((int.class.getClassLoader())));
    }

    public ReviewsResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(page);
        dest.writeList(results);
        dest.writeValue(total_pages);
        dest.writeValue(total_results);
    }

    public int describeContents() {
        return 0;
    }


    public class Result implements Parcelable {

        private String id;
        private String author;
        private String content;
        private String url;
        public final Parcelable.Creator<Result> CREATOR = new Creator<Result>() {


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
            this.author = ((String) in.readValue((String.class.getClassLoader())));
            this.content = ((String) in.readValue((String.class.getClassLoader())));
            this.url = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Result() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(author);
            dest.writeValue(content);
            dest.writeValue(url);
        }

        public int describeContents() {
            return 0;
        }

    }


}
