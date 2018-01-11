package com.jerry.littlepanda.ireader.model.local;

import com.jerry.littlepanda.ireader.model.bean.AuthorBean;
import com.jerry.littlepanda.ireader.model.bean.BookCommentBean;
import com.jerry.littlepanda.ireader.model.bean.BookHelpfulBean;
import com.jerry.littlepanda.ireader.model.bean.BookHelpsBean;
import com.jerry.littlepanda.ireader.model.bean.BookReviewBean;
import com.jerry.littlepanda.ireader.model.bean.DownloadTaskBean;
import com.jerry.littlepanda.ireader.model.bean.ReviewBookBean;
import com.jerry.littlepanda.ireader.model.bean.packages.BillboardPackage;
import com.jerry.littlepanda.ireader.model.bean.packages.BookSortPackage;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by newbiechen on 17-4-28.
 */

public interface GetDbHelper {
    Single<List<BookCommentBean>> getBookComments(String block, String sort, int start, int limited, String distillate);
    Single<List<BookHelpsBean>> getBookHelps(String sort, int start, int limited, String distillate);
    Single<List<BookReviewBean>> getBookReviews(String sort, String bookType, int start, int limited, String distillate);
    BookSortPackage getBookSortPackage();
    BillboardPackage getBillboardPackage();

    AuthorBean getAuthor(String id);
    ReviewBookBean getReviewBook(String id);
    BookHelpfulBean getBookHelpful(String id);

    /******************************/
    List<DownloadTaskBean> getDownloadTaskList();
}