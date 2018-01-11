package com.jerry.littlepanda.ireader.model.local;

import com.jerry.littlepanda.ireader.model.bean.AuthorBean;
import com.jerry.littlepanda.ireader.model.bean.BookCommentBean;
import com.jerry.littlepanda.ireader.model.bean.BookHelpfulBean;
import com.jerry.littlepanda.ireader.model.bean.BookHelpsBean;
import com.jerry.littlepanda.ireader.model.bean.BookReviewBean;
import com.jerry.littlepanda.ireader.model.bean.ReviewBookBean;

import java.util.List;

/**
 * Created by newbiechen on 17-4-28.
 */

public interface DeleteDbHelper {
    void deleteBookComments(List<BookCommentBean> beans);
    void deleteBookReviews(List<BookReviewBean> beans);
    void deleteBookHelps(List<BookHelpsBean> beans);
    void deleteAuthors(List<AuthorBean> beans);
    void deleteBooks(List<ReviewBookBean> beans);
    void deleteBookHelpful(List<BookHelpfulBean> beans);
    void deleteAll();
}
