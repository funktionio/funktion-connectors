/*
 * Copyright 2016 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package io.fabric8.funktion.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import io.fabric8.funktion.model.steps.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class Funktions {
    public static final String FILE_NAME = "funktion.yml";
    private static final transient Logger LOG = LoggerFactory.getLogger(Funktions.class);

    protected static String toYaml(Object dto) throws JsonProcessingException {
        ObjectMapper mapper = createObjectMapper();
        return mapper.writeValueAsString(dto);
    }

    /**
     * Tries to load the configuration file from the current directory
     */
    public static Funktion load() throws IOException {
        return findFromFolder(new File("."));
    }


    protected static Funktion loadFromFile(File file) throws IOException {
        LOG.debug("Parsing funktion configuration from: " + file.getName());
        try {
            Funktion config = Funktions.parseFunktionConfig(file);
            return validateConfig(config, file);
        } catch (IOException e) {
            throw new IOException("Failed to parse funktion config: " + file + ". " + e, e);
        }
    }


    public static Funktion loadFromString(String yaml) throws IOException {
        return parseFunktionConfig(yaml);
    }

    public static Funktion loadFromURL(URL resource) throws IOException {
        return parseFunktionConfig(resource);
    }

    protected static Funktion validateConfig(Funktion config, File file) {
        List<Flow> flows = config.getFlows();
        if (flows.isEmpty()) {
            throw new IllegalStateException("No Funktion flows defined in file: " + file.getPath());
        }
        return config;
    }

    protected static Funktion validateConfig(Funktion config, URL url) {
        List<Flow> flows = config.getFlows();
        if (flows.isEmpty()) {
            throw new IllegalStateException("No Funktion flows defined in URL: " + url);
        }
        return config;
    }


    /**
     * Tries to find the configuration from the current directory or a parent folder.
     */
    public static Funktion findFromFolder(File folder) throws IOException {
        if (folder.isDirectory()) {
            File file = new File(folder, FILE_NAME);
            if (file != null && file.exists() && file.isFile()) {
                return loadFromFile(file);
            }
            File configFolder = new File(folder, "config");
            file = new File(configFolder, FILE_NAME);
            if (file != null && file.exists() && file.isFile()) {
                return loadFromFile(file);
            }
            File parentFile = folder.getParentFile();
            if (parentFile != null) {
                return findFromFolder(parentFile);
            }
            Funktion answer = tryFindConfigOnClassPath();
            if (answer != null) {
                return answer;
            }
            throw new IOException("Funktion configuration file does not exist: " + file.getPath());
        } else if (folder.isFile()) {
            return loadFromFile(folder);
        }
        Funktion answer = tryFindConfigOnClassPath();
        if (answer != null) {
            return answer;
        }
        throw new IOException("Funktion configuration folder does not exist: " + folder.getPath());
    }

    protected static Funktion tryFindConfigOnClassPath() throws IOException {
        URL url = Funktions.class.getClassLoader().getResource(FILE_NAME);
        if (url != null) {
            try {
                Funktion config = parseFunktionConfig(url);
                return validateConfig(config, url);
            } catch (IOException e) {
                throw new IOException("Failed to parse funktion config: " + url + ". " + e, e);
            }
        }
        return null;
    }

    /**
     * Returns true if the given folder has a configuration file called {@link #FILE_NAME}
     */
    public static boolean hasConfigFile(File folder) {
        File FunktionConfigFile = new File(folder, FILE_NAME);
        return FunktionConfigFile != null && FunktionConfigFile.exists() && FunktionConfigFile.isFile();
    }

    /**
     * Creates a configured Jackson object mapper for parsing YAML
     */
    public static ObjectMapper createObjectMapper() {
        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.configure(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID, false);
        ObjectMapper om = new ObjectMapper(yamlFactory);

        for (Step step : ServiceLoader.load(Step.class, Funktions.class.getClassLoader())) {
            om.registerSubtypes(new NamedType(step.getClass(), step.getKind()));
        }

        return om;
    }

    public static Funktion parseFunktionConfig(File file) throws IOException {
        LOG.info("Loading Funktion flows from file: " + file);
        return parseYaml(file, Funktion.class);
    }

    public static Funktion parseFunktionConfig(InputStream input) throws IOException {
        return parseYaml(input, Funktion.class);
    }

    public static Funktion parseFunktionConfig(URL url) throws IOException {
        LOG.info("Loading Funktion flows from URL: " + url);
        return parseYaml(url, Funktion.class);
    }

    public static Funktion parseFunktionConfig(String yaml) throws IOException {
        return parseYaml(yaml, Funktion.class);
    }

    private static <T> T parseYaml(File file, Class<T> clazz) throws IOException {
        ObjectMapper mapper = createObjectMapper();
        return mapper.readValue(file, clazz);
    }

    private static <T> T parseYaml(URL url, Class<T> clazz) throws IOException {
        ObjectMapper mapper = createObjectMapper();
        return mapper.readValue(url, clazz);
    }

    static <T> List<T> parseYamlValues(File file, Class<T> clazz) throws IOException {
        ObjectMapper mapper = createObjectMapper();
        MappingIterator<T> iter = mapper.readerFor(clazz).readValues(file);
        List<T> answer = new ArrayList<>();
        while (iter.hasNext()) {
            answer.add(iter.next());
        }
        return answer;
    }

    private static <T> T parseYaml(InputStream inputStream, Class<T> clazz) throws IOException {
        ObjectMapper mapper = createObjectMapper();
        return mapper.readValue(inputStream, clazz);
    }

    private static <T> T parseYaml(String yaml, Class<T> clazz) throws IOException {
        ObjectMapper mapper = createObjectMapper();
        return mapper.readValue(yaml, clazz);
    }

    /**
     * Saves the funktion.yml file to the given project directory
     */
    public static boolean saveToFolder(File basedir, Funktion config, boolean overwriteIfExists) throws IOException {
        File file = new File(basedir, Funktions.FILE_NAME);
        if (file.exists()) {
            if (!overwriteIfExists) {
                LOG.warn("Not generating " + file + " as it already exists");
                return false;
            }
        }
        return saveConfig(config, file);
    }

    /**
     * Saves the configuration as YAML in the given file
     */
    public static boolean saveConfig(Funktion config, File file) throws IOException {
        createObjectMapper().writeValue(file, config);
        return true;
    }

    /**
     * Saves the configuration as JSON in the given file
     */
    public static void saveConfigJSON(Funktion funktion, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, funktion);
    }

}
