package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupModifyTests extends TestBase{

    @Test
    public void canModifyGroup() {
        if (!app.groups().isGroupPresent())
        {
            app.groups().createGroup(new GroupData("gr2", "gr_header2", "gr_footer2"));
        }
        app.groups().modifyGroup(new GroupData().withName("modified name"));
    }

}
