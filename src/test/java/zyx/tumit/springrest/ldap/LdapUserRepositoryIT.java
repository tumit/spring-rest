package zyx.tumit.springrest.ldap;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LdapUserRepositoryIT {

    @Autowired
    private LdapUserRepository ldapRepository;

    @Test
    public void shouldAuthenSuccessful() {
        String username = "ostumit";
        String password = "Jan#2019";

        boolean authed = ldapRepository.authenticate(username, password);

        assertThat(authed).isTrue();
    }

    @Test
    public void shouldAuthenFail() {
        String username = "tumittest";
        String password = "Jan#2019";

        boolean authed = ldapRepository.authenticate(username, password);

        assertThat(authed).isFalse();
    }

    @Test
    public void shouldNotLocked() {
        String username = "ostumit";
        List<LdapUser> ldapUsers = ldapRepository.locked(username);
        log.debug("ldapUsers={}", ldapUsers);
    }

    @Test
    public void shouldLocked() throws NamingException {

        String username = "tumittest";
        LdapContext ldapctx;
        DirContext ldapDirContext;
        String searchBase;
        String adminName = "CN=bind u. user,CN=Users,DC=domain,DC=com";

        String adminPassword = "Password1";

        //create an initial directory context

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.REFERRAL, "ignore");
        env.put("com.sun.jndi.ldap.connect.timeout", "300000000");
        env.put(Context.PROVIDER_URL, "ldap://adserverIP:389");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put("java.naming.ldap.attributes.binary", "tokenGroups objectSid objectGUID");
        ldapctx = new InitialLdapContext(env, null);
        ldapDirContext = new InitialDirContext(env);

        SearchControls searchCtls = new SearchControls();

        String returnedAtts[] = {"sn", "givenName", "samAccountName", "accountExpires", "badPwdCount", "userAccountControl", "objectGUID", "lockoutThreshold", "lockoutDuration", "AccountExpirationDate"};
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String searchFilter = "cn=ravi*";
        searchBase = "DC=myccm,DC=com";

        int totalResults = 0;

        NamingEnumeration<SearchResult> answer = ldapDirContext.search(searchBase, searchFilter, searchCtls);
        while (answer.hasMoreElements()) {
            SearchResult sr = (SearchResult) answer.next();
            totalResults++;
            System.out.println(">>>" + sr.getName());
            Attributes attrs = sr.getAttributes();
            System.out.println("User is   :" + attrs.get("samAccountName"));
            System.out.println("Bad Password Count is     :" + attrs.get("badPwdCount"));
            System.out.println("Account Expires on    :" + attrs.get("accountExpires"));
            System.out.println("Password Never Expires    :" + attrs.get("userAccountControl"));
        }
    }
}
