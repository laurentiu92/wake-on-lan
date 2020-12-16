package com.wake.on.lan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WakeRequestBody {
    //This is a test comment to be pushed
    private String authentication;
}
