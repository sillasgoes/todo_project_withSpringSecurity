package com.sillas.to_do_project.controller.dto;

import java.util.UUID;

public record TaskNewDto(String content, UUID user_id)  {
}
