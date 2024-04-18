package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import common.CommonFunctions;
import model.ContactData;
import model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Generator {
    @Parameter(names = {"--type", "-t"})
    String type;

    @Parameter(names = {"--output", "-o"})
    String output;

    @Parameter(names = {"--format", "-f"})
    String format;

    @Parameter(names = {"--count", "-n"})
    int count;

    public static void main(String[] args) throws IOException {
        var generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();
    }

    private void run() throws IOException {
        var data = generate();
        save(data);
    }

    private Object generate() {
        if ("contacts".equals(type)) {
            return generateContacts();
        } else if ("groups".equals(type)) {
            return generateGroups();
        } else {
            throw new IllegalArgumentException("Unknown data type " + type);
        }
    }

    private Object generateContacts() {
        var contacts = new ArrayList<ContactData>();
        for (int i = 1; i <= count; i++) {
            contacts.add(new ContactData()
                    .withFirstName(CommonFunctions.randomString(i * 2))
                    .withLastName(CommonFunctions.randomString(i * 3))
                    .withAddress(CommonFunctions.randomString(i * 5))
                    .withMobile(CommonFunctions.randomString(i * 3))
                    .withEmail(String.format("%s@%s.ru",
                            CommonFunctions.randomString(i * 4),
                            CommonFunctions.randomString(i * 2)))
                    .withPhoto(CommonFunctions.randomFile("src/test/resources/images")));
        }
        return contacts;
    }

    private Object generateGroups() {
        var groups = new ArrayList<GroupData>();
        for (int i = 1; i <= count; i++) {
            groups.add(new GroupData()
                    .withName(CommonFunctions.randomString(i * 3))
                    .withHeader(CommonFunctions.randomString(i * 3))
                    .withFooter(CommonFunctions.randomString(i * 3)));
        }
        return groups;
    }

    private void save(Object data) throws IOException {
        if ("json".equals(format)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            var json = mapper.writeValueAsString(data);
            try (var writer = new FileWriter(output)) {
                writer.write(json);
            }
        } else if ("yaml".equals(format)) {
            var mapper = new YAMLMapper();
            mapper.writeValue(new File(output), data);
        } else if ("xml".equals(format)) {
            var mapper = new XmlMapper();
            mapper.writeValue(new File(output), data);
        } else {
            throw new IllegalArgumentException("Unknown data format " + format);
        }
    }
}
