package mock.date;

import api.model.Date;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.google.gson.Gson;

public class DateResponseTransformer extends ResponseTransformer {

    @Override
    public Response transform(Request req, Response resp, FileSource fs, Parameters parameters) {
        Date date = Date.builder()
                .date("01.01.1990")
                .build();
        Gson gson = new Gson();
        return Response.Builder.like(resp).but()
                .body(gson.toJson(date))
                .build();
    }

    @Override
    public String getName() {
        return "date-stub-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
