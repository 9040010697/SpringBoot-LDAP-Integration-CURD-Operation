package com.ldap.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String uid;
    private String name;
    private String mobile;

}
