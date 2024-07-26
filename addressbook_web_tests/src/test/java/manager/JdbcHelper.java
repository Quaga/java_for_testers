package manager;

import model.ContactData;
import model.GroupData;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper extends HelperBase {
    private static String jdbcMysqlUrl;
    private static String jdbcMysqlUsername;
    private static String jdbcMysqlPassword;

    public JdbcHelper(ApplicationManager manager) {
        super(manager);
        jdbcMysqlUrl = manager.properties().getProperty("db.baseUrl");
        jdbcMysqlUsername = manager.properties().getProperty("db.username");
        jdbcMysqlPassword = manager.properties().getProperty("db.password");
    }

    public List<ContactData> getContactList() {
        var contacts = new ArrayList<ContactData>();
        try (var conn = DriverManager.getConnection(jdbcMysqlUrl, jdbcMysqlUsername, jdbcMysqlPassword);
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT id, firstname, middlename, lastname, nickname, title, company, address, mobile, email, photo FROM addressbook;")) {
            while (result.next()) {
                contacts.add(new ContactData()
                        .withId(result.getString("id"))
                        .withFirstName(result.getString("firstname"))
                        .withLastName(result.getString("lastname"))
                        .withAddress(result.getString("address"))
                        .withMobile(result.getString("mobile"))
                        .withEmail(result.getString("email"))
                        .withPhoto(result.getString("photo")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    public List<GroupData> getGroupList() {
        var groups = new ArrayList<GroupData>();
        try (var conn = DriverManager.getConnection(jdbcMysqlUrl, jdbcMysqlUsername, jdbcMysqlPassword);
             var statement = conn.createStatement();
             var result = statement.executeQuery("SELECT group_id, group_name, group_header, group_footer FROM group_list;")) {
            while (result.next()) {
                groups.add(new GroupData()
                        .withId(result.getString("group_id"))
                        .withName(result.getString("group_name"))
                        .withHeader(result.getString("group_header"))
                        .withFooter(result.getString("group_footer")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }
}