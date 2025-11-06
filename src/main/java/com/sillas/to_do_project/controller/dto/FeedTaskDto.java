package com.sillas.to_do_project.controller.dto;

import java.util.List;

public record FeedTaskDto(List<FeedItemTaskDto> feedItens,
                          int page,
                          int pageSize,
                          int totalPages,
                          long totalElements) {
}
