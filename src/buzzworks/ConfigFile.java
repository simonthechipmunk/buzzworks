/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Simon Junga (simonthechipmunk)
 */
public class ConfigFile {

    //read lines from file
    public static ArrayList<String> read(String path) throws IOException {

        File file = new File(path);
        ArrayList<String> values = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {

                values.add(line);
            }

        }

        return values;
    }

    //write lines to file
    public static void write(String path, ArrayList<String> values) throws IOException {

        File file = new File(path);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            
            String line;
            Iterator iter = values.iterator();
            while (iter.hasNext()) {
                line = (String) iter.next();
                bw.write(line);
                bw.newLine();
            }
        }

    }

}
