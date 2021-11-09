/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.languages;

import fr.zelytra.histeria.Histeria;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class LangFile {
    private final HashMap<String, String> text;

    public LangFile(Lang lang) {
        this.text = new HashMap<>();
        InputStream is = Histeria.getInstance().getResource("lang/" + lang.getFileName());
        assert is != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        try {
            String line = reader.readLine();

            while (line != null) {
                if (line == null || line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                text.put(line.split("=")[0], StringUtils.substringBetween(line.split("=")[1], "\"", "\""));
                line = reader.readLine();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public HashMap<String, String> getText() {
        return text;
    }
}
