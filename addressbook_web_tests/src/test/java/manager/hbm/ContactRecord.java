package manager.hbm;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "addressbook")
public class ContactRecord {
    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "middlename")
    public String middlename;
    @Column(name = "lastname")
    public String lastname;
    @Column(name = "firstname")
    public String firstname;
    @Column(name = "nickname")
    public String nickname;
    @Column(name = "title")
    public String title;
    @Column(name = "company")
    public String company;
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
    public String phone2;
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
                         String middlename,
                         String lastname,
                         String firstname,
                         String nickname,
                         String title,
                         String company,
                         String address,
                         String mobile,
                         String email,
                         String photo) {
        this.id = id;
        this.middlename = middlename;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.photo = photo;
    }
}
