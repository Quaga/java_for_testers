package model;

public record ContactData(String id, String lastName, String firstName, String address, String email, String mobile) {
    public ContactData(){
        this("", "", "", "", "", "");
    }

    public ContactData withLastName(String name) {
        return new ContactData(this.id, name, this.firstName, this.address, this.email, this.mobile);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, this.lastName, firstName, this.address, this.email, this.mobile);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.lastName, this.firstName, address, this.email, this.mobile);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.lastName, this.firstName, this.address, email, this.mobile);
    }

    public ContactData withMobile(String mobile) {
        return new ContactData(this.id, this.lastName, this.firstName, this.address, this.email, mobile);
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.lastName, this.firstName, this.address, this.email, mobile);
    }
}