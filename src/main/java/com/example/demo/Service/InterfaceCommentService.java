package com.example.demo.Service;

import com.example.demo.Model.Comment;

import org.springframework.data.domain.Page;



import java.util.NoSuchElementException;
import java.util.UUID;

public interface InterfaceCommentService {
    public Comment insert(Comment newComment);
    public Page<Comment> getByProductId(UUID productId, int pageIndex, int pageSize);
    public Comment updateInfo(UUID commentId, String newStringComment);

    }
