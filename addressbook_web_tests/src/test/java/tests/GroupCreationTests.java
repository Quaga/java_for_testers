package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GroupCreationTests extends TestBase{

    public static List<String> groupNameProvider() {
        var result = new ArrayList<String>(List.of("group name", "group name'"));
        for (int i = 0; i < 5; i++) {
            result.add(randomString(i * 10));
        }
        return result;
    }

    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>();
        for (var name : List.of("", "group name")){
            for (var header : List.of("", "group header")){
                for (var footer : List.of("", "group footer")){
                    result.add(new GroupData(name, header, footer));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new GroupData(randomString(i * 10), randomString(i * 10), randomString(i * 10)));
        }
        return result;
    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
//                new GroupData(),
//                new GroupData().withName("some name"),
//                new GroupData("group name", "", ""),
                new GroupData("group name'", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("groupNameProvider")
    public void canCreateMultipleGroups(String name) {
        var groupCount = app.groups().getCount();
        app.groups().createGroup(new GroupData(name, "gr_header2", "gr_footer2"));
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroups2(GroupData group) {
        var groupCount = app.groups().getCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroups(GroupData group) {
        var groupCount = app.groups().getCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount, newGroupCount);
    }

}
