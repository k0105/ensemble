package de.rwthaachen.ensemble.persistence;

import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fp on 5/31/16.
 */
public class FileManager {

    public void writeToFile(List<List<AnswerCandidate>> listOfLists) {

        // Create folder for question request
        DateFormat df = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        String fileName = "storage/" + df.format(new Date());
        File file = new File(fileName);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        // Store each backend's response in separate file (and confidences in separately in a _conf.txt file)
        for (List<AnswerCandidate> list : listOfLists) {
            Writer output = null;
            Writer outputConf = null;
            try {
                if(list != null && list.size() > 0) {
                    output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + "/" +
                            list.get(0).getOrigin() + ".txt", true), "UTF-8"));
                    outputConf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + "/" +
                            list.get(0).getOrigin() + "_conf.txt", true), "UTF-8"));
                    for (AnswerCandidate answerCandidate : list) {
                        output.append(answerCandidate.getName());
                        output.append('\n');
                        outputConf.append(Double.toString(answerCandidate.getConfidence()));
                        outputConf.append('\n');
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                closeWriter(output);
                closeWriter(outputConf);
            }
        }

    }

    public void closeWriter(Writer writer) {
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void appendToFile(String fileName, String line) {
        Writer output = null;
        try {
            output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8"));
            output.append(line);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            closeWriter(output);
        }
    }
}


//    // List<Integer> idList = students.stream().map(Student::getId).collect(Collectors.toList());
//    List<String> nameList = list.stream().map(AnswerCandidate::getName).collect(Collectors.toList());
//    List<String> confList = list.stream().map(AnswerCandidate::getConfidence).map((Double i) -> Double.toString(i)).collect(Collectors.toList());
//
//    //  List<String> lines = Arrays.asList("The first line", "The second line");
//    try {
//      Files.write(Paths.get(fileName + "/" + list.get(0).getOrigin() + ".txt"), nameList, Charset.forName("UTF-8"));
//      Files.write(Paths.get(fileName + "/" + list.get(0).getOrigin() + "_conf.txt"), confList, Charset.forName("UTF-8"));
//    } catch (IOException ioe) {
//      ioe.printStackTrace();
//    }