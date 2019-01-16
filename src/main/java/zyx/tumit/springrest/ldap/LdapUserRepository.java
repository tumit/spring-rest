package zyx.tumit.springrest.ldap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Repository;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.util.List;

@Slf4j
@Repository
public class LdapUserRepository {

    private LdapTemplate ldapTemplate;

    @Autowired
    public LdapUserRepository(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public boolean authenticate(String username, String password) {
        AndFilter filter = getAndFilter(username);
        return ldapTemplate.authenticate("", filter.toString(), password);
    }

    public List<LdapUser> locked(String username) {
        SearchControls sc = new SearchControls();
        String returnedAtts[] = {"cn", "sn", "givenName", "samAccountName", "accountExpires", "badPwdCount", "userAccountControl", "objectGUID", "lockoutThreshold", "lockoutDuration", "AccountExpirationDate"};
        sc.setReturningAttributes(returnedAtts);
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        AndFilter filter = getAndFilter(username);
        return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), sc, new LdapUserAttributesMapper());
    }

    private AndFilter getAndFilter(String username) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person"))
              .and(new EqualsFilter("uid", username));
        return filter;
    }

    private class LdapUserAttributesMapper implements AttributesMapper<LdapUser> {
        @Override
        public LdapUser mapFromAttributes(Attributes attributes) throws NamingException {

            log.debug("attributes={}", attributes);

            LdapUser ldapUser = new LdapUser();
            ldapUser.setFullName((String)attributes.get("cn").get());

            Attribute sn = attributes.get("sn");
            if (sn != null){
                ldapUser.setLastName((String)sn.get());
            }

            Attribute lockoutTime = attributes.get("lockoutTime");
            if (lockoutTime != null){
                ldapUser.setLockedTime((String)lockoutTime.get());
            }

            return ldapUser;
        }
    }
}


