package manager.hbm;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "addressbook")
public class ContactRecord {
    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "firstname")
    public String firstname;
    @Column(name = "lastname")
    public String lastname;
    @Column(name = "address")
    public String address;
    @Column(name = "mobile")
    public String mobile;
    @Column(name = "email")
    public String email;
    @Column(name = "photo")
    public String photo;

    public String home = "";
    public String work = "";
    public String fax = "";
    public String email2 = "";
    public String email3 = "";
    public String homepage = "";

    @ManyToMany
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    public List<GroupRecord> groups;

    public ContactRecord() {
    }

    public ContactRecord(int id,
                         String lastname,
                         String firstname,
                         String address,
                         String mobile,
                         String email,
                         String photo) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.photo = photo;
    }
}
