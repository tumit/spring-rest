package zyx.tumit.springrest.ldap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LdapUser {
    private String fullName;
    private String lastName;
    private String lockedTime;
}
