package com.example.demo.Utilities;

import com.example.demo.Model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResponse<P extends BaseModel> {
    private List<P> elements;
    private long totalElements;
    private int totalPages;
}

