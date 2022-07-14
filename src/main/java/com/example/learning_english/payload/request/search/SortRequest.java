package com.example.learning_english.payload.request.search;

import com.example.learning_english.SearchCriteria.Operator;
import com.example.learning_english.SearchCriteria.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortRequest implements Serializable {
    private String key;
    private SortDirection direction;
}
