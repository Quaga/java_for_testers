package model;

public record ContactData(String id, String lastName, String firstName, String address, String email, String mobile, String photo) {
    public ContactData(){
        this("", "", "", "", "", "", "");
    }

    public ContactData withLastName(String name) {
        return new ContactData(this.id, name, this.firstName, this.address, this.email, this.mobile, this.photo);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, this.lastName, firstName, this.address, this.email, this.mobile, this.photo);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.lastName, this.firstName, address, this.email, this.mobile, this.photo);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.lastName, this.firstName, this.address, email, this.mobile, this.photo);
    }

    public ContactData withMobile(String mobile) {
        return new ContactData(this.id, this.lastName, this.firstName, this.address, this.email, mobile, this.photo);
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.lastName, this.firstName, this.address, this.email, mobile, this.photo);
    }

    public ContactData withPhoto(String photo) {
        return new ContactData(this.id, this.lastName, this.firstName, this.address, this.email, mobile, photo);
    }
}