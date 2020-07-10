package com.ldap.config;

import com.ldap.model.Person;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

public class PersonContextMapper extends AbstractContextMapper<Person> {
    public Person doMapFromContext(DirContextOperations context) {
        Person person = new Person();
        person.setUid(context.getStringAttribute("uid"));
        person.setName(context.getStringAttribute("name"));
        person.setMobile(context.getStringAttribute("mobile"));
        return person;
    }
}
