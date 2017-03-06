package controllers;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import views.html.index;

import java.util.ArrayList;

import static play.data.Form.form;

public class Application extends Controller {
    public static Result index() {
        DynamicForm bindedForm = form().bindFromRequest();
        String values = bindedForm.get("values");

        ArrayList<Tweets> tweets = getProfiles(values);

        return ok(index.render(tweets));
    }

    private static AccessToken loadAccessToken() {
        String token = "242376401-kpL0pcXdLTpgXFBeoWmAGCsUQ031nTkQ7nITOcve";
        String tokenSecret = "EBaSlL45m6OlWmSJFPsaK40DG5FXkOru7jN9wXDM3d573";
        return new AccessToken(token, tokenSecret);
    }

    public static ArrayList<Tweets> getProfiles(String values) {
        if (values == null) {
            return new ArrayList<Tweets>();
        }

        String[] valuesArray = values.split(",");

        ArrayList<Tweets> contents = new ArrayList<Tweets>();

        for (String profileOrHashTag : valuesArray) {
            profileOrHashTag = profileOrHashTag.trim();

            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();

                AccessToken accessToken = loadAccessToken();

                builder.setOAuthConsumerKey("2OdW7ld7xXARjtS5RQsTMPbWs");
                builder.setOAuthConsumerSecret("bSukzsXP2sDSQ5BjV1g28hrS3sQpB326ky8WaudmnYtQMzRqE3");

                Configuration configuration = builder.build();

                TwitterFactory factory = new TwitterFactory(configuration);
                Twitter twitter = factory.getInstance();
                twitter.setOAuthAccessToken(accessToken);

                if (profileOrHashTag.charAt(0) == '@') {
                    // Veio um profile
                    ResponseList<twitter4j.Status> tweets = twitter.getUserTimeline(profileOrHashTag);
                    for (twitter4j.Status tweet : tweets) {
                        String text = tweet.getText();

                        User user = tweet.getUser();
                        String name = user.getName();

                        contents.add(new Tweets(name, text));
                    }

                } else if (profileOrHashTag.charAt(0) == '#') {
                    // Veio uma hashtag
                    Query query = new Query(profileOrHashTag);
                    QueryResult result = twitter.search(query);

                    ArrayList<twitter4j.Status> hashTags = new ArrayList<>();
                    hashTags.addAll(result.getTweets());

                    for (twitter4j.Status hashTag : hashTags) {
                        String text = hashTag.getText();

                        User user = hashTag.getUser();
                        String name = user.getName();

                        contents.add(new Tweets(name, text));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return contents;

    }
}
