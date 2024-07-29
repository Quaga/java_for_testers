package model;

import common.CommonFunctions;

public record ContactData(String id, String middlename, String lastName, String firstName, String nickname, String title, String company,
                          String address, String mobile, String email, String photo, String workphone, String homephone,
                          String phone2, String email2, String email3) {
    public ContactData(){
        this("", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "");
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this.id, this.middlename, lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, this.middlename, this.lastName, firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withMobile(String mobile) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withPhoto(String photo) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withNickname(String nickname) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withTitle(String title) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withCompany(String company) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withMiddlename(String middlename) {
        return new ContactData(this.id, middlename, this.lastName, this.firstName, this.nickname, this.title, company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withWorkphone(String workphone) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, workphone, this.homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withHomephone(String homephone) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, homephone, this.phone2, this.email2, this.email3);
    }

    public ContactData withPhone2(String phone2) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, phone2, this.email2, this.email3);
    }

    public ContactData withEmail2(String email2) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, email2, this.email3);
    }

    public ContactData withEmail3(String email3) {
        return new ContactData(this.id, this.middlename, this.lastName, this.firstName, this.nickname, this.title, this.company, this.address,
                this.mobile, this.email, this.photo, this.workphone, this.homephone, this.phone2, this.email2, email3);
    }

    public ContactData withRandomData(int salt, String photo_dir) {
        return new ContactData(this.id,
                CommonFunctions.randomString(salt * 2),
                CommonFunctions.randomString(salt * 2),
                CommonFunctions.randomString(salt * 3),
                CommonFunctions.randomString(salt * 3),
                CommonFunctions.randomString(salt * 3),
                CommonFunctions.randomString(salt * 3),
                CommonFunctions.randomString(salt * 5),
                CommonFunctions.randomString(11),
                String.format("%s@%s.ru",
                        CommonFunctions.randomString(salt * 2),
                        CommonFunctions.randomString(salt * 2)),
                CommonFunctions.randomFile(photo_dir),
                "", "", "", "", "");
    }
}