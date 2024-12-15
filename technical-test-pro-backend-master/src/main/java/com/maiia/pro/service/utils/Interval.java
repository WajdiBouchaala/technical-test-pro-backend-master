package com.maiia.pro.service.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Interval {
    private LocalDateTime start;
    private LocalDateTime end;
}