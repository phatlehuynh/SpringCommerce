package com.example.demo.Service.Implement;

import com.example.demo.Model.Category;
import com.example.demo.Repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService extends BaseService<Category, CategoryRepository>{
    public Page<Category> getByParentId(UUID parentId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.findByParentId(parentId, pageable);
    }
}
