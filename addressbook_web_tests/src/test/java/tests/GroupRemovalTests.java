package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase{

    @Test
    public void canRemoveGroup() {
//        if (!app.groups().isGroupPresent())
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("gr2", "gr_header2", "gr_footer2"));
        }
        var groupCount = app.groups().getCount();
        app.groups().removeGroup();
        var newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount - 1, newGroupCount);
    }

    @Test
    public void canRemoveAllGroupsAtOnce() {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("gr2", "gr_header2", "gr_footer2"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.groups().getCount());
    }

}
