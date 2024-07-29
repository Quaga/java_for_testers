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

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Generator {
    @Parameter(names = {"--properties", "-p"})
    String properties_file;
    private String contact_file;
    private String group_file;
    private String format;
    private int count;

    private Properties properties;

    public static void main(String[] args) throws IOException {
        var generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();
    }

    private void run() throws IOException {
        properties = new Properties();
        properties.load(new FileReader(System.getProperty("target", properties_file)));

        contact_file = properties.getProperty("file.contacts");
        group_file = properties.getProperty("file.groups");
        format = properties.getProperty("file.format");
        count = Integer.parseInt(properties.getProperty("file.count"));

        var contactData = generateContacts();
        var contactOut = contact_file + "." + format;
        save(contactData, contactOut);

        var groupData = generateGroups();
        var groupOut = group_file + "." + format;
        save(groupData, groupOut);
    }

    private Object generateContacts() {
        var contacts = new ArrayList<ContactData>();
        for (int i = 1; i <= count; i++) {
            contacts.add(new ContactData()
                    .withRandomData(i, properties.getProperty("file.photoDir")));
        }
        return contacts;
    }

    private Object generateGroups() {
        var groups = new ArrayList<GroupData>();
        for (int i = 1; i <= count; i++) {
            groups.add(new GroupData().withRandomData(i));
        }
        return groups;
    }

    private void save(Object data, String output) throws IOException {
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
