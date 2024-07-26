package manager.hbm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

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
