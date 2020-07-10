package com.ldap.repository;

import com.ldap.config.PersonContextMapper;
import com.ldap.model.Person;
import com.ldap.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapName;
import java.util.List;
import java.util.UUID;

@Service
public class PersonRepository implements BaseLdapNameAware {

    @Autowired
    private LdapTemplate ldapTemplate;
    private LdapName baseLdapPath;

    @Override
    public void setBaseLdapPath(LdapName baseLdapPath) {
        this.baseLdapPath = baseLdapPath;
    }

    public void create(Person person) {
        person.setUid(UUID.randomUUID().toString());
        ldapTemplate.bind(buildDn(person.getUid()), null, CommonUtils.buildAttributes(person));
    }

    public List<Person> findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass", "person");
        return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), new PersonContextMapper());
    }

    public void updateMobileNumber(String uId, String mobile) {
        updateRecord(uId, "mobile", mobile);
    }

    public Person findOne(String uid) {
        return ldapTemplate.lookup(buildDn(uid), new PersonContextMapper());
    }

    private Name buildDn(String uId) {
        return LdapNameBuilder.newInstance(baseLdapPath)
                .add("ou", "person")
                .add("uid", uId)
                .build();
    }

    private void updateRecord(String uId, String attrName, String atrrValue) {
        Attribute attr = new BasicAttribute(attrName, atrrValue);
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(buildDn(uId), new ModificationItem[]{item});
    }
}
