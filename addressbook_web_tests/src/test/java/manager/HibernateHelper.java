package manager;

import manager.hbm.ContactRecord;
import manager.hbm.GroupInContact;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateHelper extends HelperBase  {
    private final SessionFactory sessionFactory;
    private static String jdbcMysqlUrl;
    private static String jdbcMysqlUsername;
    private static String jdbcMysqlPassword;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        jdbcMysqlUrl = manager.properties().getProperty("db.baseUrl");
        jdbcMysqlUsername = manager.properties().getProperty("db.username");
        jdbcMysqlPassword = manager.properties().getProperty("db.password");
        sessionFactory = new Configuration()
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .addAnnotatedClass(GroupInContact.class)
                .setProperty(AvailableSettings.URL, jdbcMysqlUrl)
                .setProperty(AvailableSettings.USER, jdbcMysqlUsername)
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, jdbcMysqlPassword)
                .buildSessionFactory();
    }

    public void createContact(ContactData contactData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            try {
                session.persist(convert(contactData));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                session.getTransaction().commit();
            } catch (UnsupportedOperationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }
    
    public void addGroupToContact(ContactData contactdata, GroupData groupdata) {
        sessionFactory.inSession(session -> {
            var newGroupInContact = new GroupInContact(Integer.parseInt(contactdata.id()),
                    Integer.parseInt(groupdata.id()));
            session.getTransaction().begin();
            session.persist(newGroupInContact);
            session.getTransaction().commit();
        });
    }

    public long getContactsCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    public void removeGroupFromContact(ContactData contactdata, GroupData groupdata) {
        sessionFactory.inSession(session -> {
            var oldGroupInContact = new GroupInContact(Integer.parseInt(contactdata.id()),
                    Integer.parseInt(groupdata.id()));
            session.getTransaction().begin();
            session.remove(oldGroupInContact);
            session.getTransaction().commit();
        });
    }

    public long getGroupsCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    public List<ContactData> getContactList() {
        return sessionFactory.fromSession(session -> {
            return convertContactList(session.createQuery("from ContactRecord", ContactRecord.class).list());
        });
    }

    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            var result = session.get(GroupRecord.class, group.id()).contacts;
            var unique_result = result.stream().distinct();
            return convertContactList(unique_result.collect(Collectors.toList()));
        });
    }

    public List<GroupData> getGroupList() {
        return sessionFactory.fromSession(session -> {
            return convertGroupList(session.createQuery("from GroupRecord", GroupRecord.class).list());
        });
    }

    public long getGroupsInContactCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (distinct(group_id)) from GroupInContact", Long.class).getSingleResult();
        });
    }

    public List<GroupData> getGroupsInContact() {
        return sessionFactory.fromSession(session -> {
            var contacts = getContactList();
            List<GroupRecord> result = new ArrayList<>();
            for (var contact : contacts) {
                result.addAll(session.get(ContactRecord.class, contact.id()).groups);
            }
            var unique_result = result.stream().distinct();
            return convertGroupList(unique_result.collect(Collectors.toList()));
        });
    }

    static List<ContactData> convertContactList(List<ContactRecord> records) {
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    static List<GroupData> convertGroupList(List<GroupRecord> records) {
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    private static ContactData convert(ContactRecord record) {
        return new ContactData().withId("" + record.id)
                .withFirstName(record.firstname)
                .withLastName(record.lastname)
                .withNickname(record.nickname)
                .withTitle(record.title)
                .withCompany(record.company)
                .withAddress(record.address)
                .withMobile(record.mobile)
                .withEmail(record.email)
                .withPhoto(record.photo)
                .withWorkphone(record.work)
                .withHomephone(record.home)
                .withEmail2(record.email2)
                .withEmail3(record.email3)
                .withPhone2(record.phone2);

    }

    private static ContactRecord convert(ContactData data) throws IOException, SQLException {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        
        var photo = "";
        if (data.photo().startsWith("PHOTO;ENCODING=BASE64;TYPE=")) {
            photo = data.photo();
        } else {
            File photoFile = new File(data.photo());
            FileInputStream photoStream = new FileInputStream(photoFile);
            byte[] photoBytes = new byte[(int) photoFile.length()];
            photoStream.read(photoBytes);
            photoStream.close();

            var myPhotoType = "";
            var photoFileType = Files.probeContentType(photoFile.toPath());
            if (photoFileType.equals("image/apng")) {
                myPhotoType = "APNG";
            } else if (photoFileType.equals("image/avif")) {
                myPhotoType = "AVIF";
            } else if (photoFileType.equals("image/png")) {
                myPhotoType = "PNG";
            } else if (photoFileType.equals("image/gif")) {
                myPhotoType = "GIF";
            } else if (photoFileType.equals("image/jpeg")) {
                myPhotoType = "JPEG";
            } else if (photoFileType.equals("image/svg+xml")) {
                myPhotoType = "SVG";
            } else if (photoFileType.equals("image/webp")) {
                myPhotoType = "WebP";
            } else {
                myPhotoType = "UNKNOWN";
            }
            byte[] base64photoBytes = Base64.getEncoder().encode(photoBytes);
            photo = "PHOTO;ENCODING=BASE64;TYPE=" + myPhotoType + ":" + new String(base64photoBytes);
        }
        
        var result = new ContactRecord(Integer.parseInt(id),
                data.middlename(),
                data.lastName(),
                data.firstName(),
                data.nickname(),
                data.title(),
                data.company(),
                data.address(),
                data.mobile(),
                data.email(),
                photo);
        return result;
    }

    private static GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id,
                record.name,
                record.header,
                record.footer);
    }

    private static GroupRecord convert(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id),
                data.name(),
                data.header(),
                data.footer());
    }
}
