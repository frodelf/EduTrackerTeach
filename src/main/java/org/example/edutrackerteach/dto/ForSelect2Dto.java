package org.example.edutrackerteach.dto;

import lombok.Data;

@Data
public class ForSelect2Dto {
    private String query;
    private int page;
    private int size;
}