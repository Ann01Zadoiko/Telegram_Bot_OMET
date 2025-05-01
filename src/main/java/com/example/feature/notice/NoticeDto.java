package com.example.feature.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NoticeDto {
    private String text;

    public NoticeDto(String text) {
        this.text = text;
    }

}
