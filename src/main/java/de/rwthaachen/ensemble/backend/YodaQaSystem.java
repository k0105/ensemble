package de.rwthaachen.ensemble.backend;

import com.eclipsesource.json.*;
import de.rwthaachen.ensemble.communication.UrlManager;
import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * Created by fp on 5/12/16.
 */
public class YodaQaSystem extends Backend {

    //private String baseURL = UrlManager.lookUpUrl(UrlManager.Backends.YODAQA.ordinal()) + "/q"; //http://qa.ailao.eu/q

    public YodaQaSystem (String backend) {
        super(backend);
    }

    class IdLabelPair {
        public IdLabelPair(String sourceID, String snippetLabel) {
            this.sourceID = sourceID;
            this.snippetLabel = snippetLabel;
        }

        public String sourceID     = "";
        public String snippetLabel = "";
    }

    Map<String, IdLabelPair> snippetMap = new HashMap<>();
    Map<String, IdLabelPair> sourceMap  = new HashMap<>();


    @Override
    public List<AnswerCandidate> askQuestion(String question) throws IOException {
        // 0. Convert question into URL encoded format
        // 1. POST question to get ID (no ID => error)
        String id = postQuestionAndGetID(urlEncodeQuestion(question));
        // 2. Validate ID format
        try {
            id = validateAndExtractID(id);
            // 3. Request answer for ID until state is finished (field finished in returned JSON true)
            return buildListFromJSON( invoke(id) );
        } catch (DataFormatException dfe) {
            dfe.printStackTrace();
            throw new IOException("Couldn't request answer, because ID validation (previous step) failed.");
        }
    }

    public String invoke(String input) {

        String reply="";

        try {
            // If we got an ID, request corresponding answer
            URL url = new URL(UrlManager.lookUpUrl(UrlManager.Backends.YODAQA.ordinal()) + "/q/" + input);
            boolean finished = false;
            int timeout = 0;

            // .:: Request until field "finished" is set (-> answer generation done) ::.
            while (!finished && timeout < 300) {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                System.out.println("Sending REST GET request to ");

                if (connection.getResponseCode() != 200) {
                    // We could throw an exception, but a REST API might e.g. return code 200 and change a DB entry
                    throw new RuntimeException("Failed: HTTP error code " + connection.getResponseCode());
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                // .:: Read answer ::.

                while ((reply = bufferedReader.readLine()) != null) {
                    //DEBUG: System.out.println(output);

                    // Example return value:
                    // {"id":"79364337","text":"\"Where was Bill Clinton born?\"","sources":{},"answers":[],"snippets":{},
                    // "finished":false,"gen_sources":0,"gen_answers":0}

                    try {
                        JsonValue value = Json.parse(reply);
                        if (value.isObject()) {
                            finished = value.asObject().getBoolean("finished", false);
                            break;
                        }
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                } // End while - read response

                if (!finished) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                } else {
                    break;
                }

                timeout++;

                connection.disconnect();

            } // End while - try until answer generation is finished or until timeout happens

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return reply;
    }


    public List<AnswerCandidate> buildListFromJSON(String jsonString) {
        System.out.println(".:: Debug Output of JSON return value");
        System.out.println(jsonString);

        JsonValue jsonValue = Json.parse(jsonString);
        if (jsonValue.isObject()){
            JsonObject jsonObject = jsonValue.asObject();

            // Prepare maps snippetMap & sourceMap
            JsonObject jsonSnippets = jsonObject.get("snippets").asObject();
            for (String valueSnippet : jsonSnippets.names()) {
                JsonObject snippet = jsonSnippets.get(valueSnippet).asObject();
                //Beware: propertyLabel or passageText is optional
                JsonValue tryPassage = snippet.get("passageText");
                JsonValue tryProperty = snippet.get("propertyLabel");
                String information = "";
                if (tryPassage != null && tryPassage.isString()) {
                    information += tryPassage.asString();
                }
                if (tryProperty != null && tryProperty.isString()) {
                    information += tryProperty.asString();
                }
                snippetMap.put(Integer.toString(snippet.get("snippetID").asInt()), new IdLabelPair(Integer.toString(snippet.get("sourceID").asInt()), information));
            }

            JsonObject jsonSources = jsonObject.get("sources").asObject();
            for (String valueSources : jsonSources.names()) {
                JsonObject source = jsonSources.get(valueSources).asObject();
                String description = source.get("type").asString() + ", " + source.get("origin").asString() + ", " + source.get("title").asString();
                sourceMap.put(Integer.toString(source.get("sourceID").asInt()), new IdLabelPair(description, source.get("URL").asString()));
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("# LATs:\n");
            for (JsonValue i: jsonObject.get("summary").asObject().get("lats").asArray() ) {
                stringBuilder.append(i.asString());
                stringBuilder.append("\n");
            }


            JsonArray answerArray = jsonObject.get("answers").asArray();
            List<AnswerCandidate> answers = new LinkedList<>();

            for (JsonValue value : answerArray) {
                if(value.isObject()) {
                    JsonObject object = value.asObject();

                    //System.out.println("#Answer: " + object.get("text").asString());
                    stringBuilder.append("#Answer: ");
                    stringBuilder.append(object.get("text").asString());
                    stringBuilder.append("\n");
                    JsonArray snippetArray = object.get("snippetIDs").asArray();
                    for (JsonValue value2 : snippetArray) {
                        //Print Snippet and source description from maps
                        if (value2.isNumber()) {
                            IdLabelPair idLabelPair1 = snippetMap.get(Integer.toString(value2.asInt()));
                            //System.out.println(" " + idLabelPair1.snippetLabel);
                            stringBuilder.append(" ");
                            stringBuilder.append(idLabelPair1.snippetLabel);
                            stringBuilder.append("\n");
                            IdLabelPair idLabelPair2 = sourceMap.get(idLabelPair1.sourceID);
                            //System.out.println(" " + idLabelPair2.sourceID + "\n " + idLabelPair2.snippetLabel);
                            stringBuilder.append(" ");
                            stringBuilder.append(idLabelPair2.sourceID);
                            stringBuilder.append("\n ");
                            stringBuilder.append(idLabelPair2.snippetLabel);
                            stringBuilder.append("\n");
                        }
                    }
                    // This explanation could (should?) also be sent to the root area for deeper inspection

                    answers.add(  new AnswerCandidate( object.get("text").asString(),
                            object.get("confidence").asDouble(), getBackend())  );
                }
            }
            System.out.println("Length of list: " + answers.size());


            return answers;

        } else {
            System.out.println("Error: Didn't receive valid JSON object.");
            return null;
        }
    }

    /**
     * Converts question from plain text into URL encoding for YodaQA REST API
     * In detail: Removes whitespaces in front of and behind string, whitespaces in between are replaced by '+'
     *            Finally, "text=" is put in the front and the entire string is surrounded with escaped double quotes
     *
     * @param questionPlain The user's question in plain text
     * @return  QuestionDemo in URL encoding, which can be submitted directly to YodaQA REST API
     */
    public String urlEncodeQuestion(String questionPlain) {
        //DEBUG: Try String question = " When   was Nietzsche  born?    ";
        questionPlain = questionPlain.trim();
        Pattern whitespaceRegExp = Pattern.compile("\\s+");
        Matcher matcher = whitespaceRegExp.matcher(questionPlain);
        while (matcher.find()) {
            questionPlain = matcher.replaceAll("+");
        }
        return "text=\"" + questionPlain + "\"";
    }

    /**
     * Posts URL encoded question to YodaQA REST API in order to get answer ID
     * @param questionURLEncoded    URL encoded user question
     * @return  Answer ID
     */
    public String postQuestionAndGetID (String questionURLEncoded) {
        try {
            //if(localBackend) {
            String baseURL = UrlManager.lookUpUrl(UrlManager.Backends.YODAQA.ordinal()) + "/q";
            //} else {
            //    baseURL = "http://qa.ailao.eu/q";
            //}

            System.out.println("Connecting to " + baseURL);

            URL url = new URL(baseURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            //DEBUG: String input = "text=\"Where+was+Bill+Clinton+born?\"";

            OutputStream os = connection.getOutputStream();
            os.write(questionURLEncoded.getBytes());
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            String result="";

            //Was ugly, is written much simpler was while
            if ((output = br.readLine()) != null) {
                //DEBUG: System.out.println(output);

                // Here we gethave ID in JSON format, e.g. {"id":"1778757153"}
                result = output;
                //break;
            }

            connection.disconnect();

            return result;

        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return "";
    }

    /**
     * Validates ID obtained by YodaQA's REST API
     * @param id    ID from YodaQA REST API
     * @throws DataFormatException  Thrown if the ID string is not valid
     */
    public String validateAndExtractID (String id) throws DataFormatException {
        String regExYodaQuestionID = "\\{\"id\":\"(\\d+)\"\\}"; // Match all chars in ID: "\\{\"id\":\"(.+?)\"\\}"
        Pattern pattern = Pattern.compile(regExYodaQuestionID);
        Matcher matcher = pattern.matcher(id);
        if(matcher.find() && matcher.group(0)==id) {
            System.out.println("Extracted ID: " + id.replaceFirst("\\{\"id\":\"", "").replaceFirst("\"\\}", ""));

            return id.replaceFirst("\\{\"id\":\"", "").replaceFirst("\"\\}", "");


            //break;
        } else {
            throw new DataFormatException("Format of ID string returned by YodaQA REST API is not valid.");
        }
    }
}
