package com.example.learning_english.payload.request.search;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchRequest implements Serializable {
    private List<FilterRequest> filters;

    private List<SortRequest> sorts;

    private Integer page;

    private Integer limit;
}
