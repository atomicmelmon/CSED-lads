package group.csed.api.tracker;

import group.csed.api.ResponseTemplate;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.predictions.PeriodPrediction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/period-tracker")
@Produces(MediaType.APPLICATION_JSON)
public class TrackerResource {

    private final TrackerDao dao;
    private final SessionHelper sessionHelper;

    public TrackerResource(TrackerDao dao, SessionHelper sessionHelper) {
        this.dao = dao;
        this.sessionHelper = sessionHelper;
    }

    @POST
    @Path("/create")
    public Response insertData(PeriodData data) {
        if(dao.entryExists(data.getId())) {
            return Response.ok(new ResponseTemplate(false).build()).build();
        }
        dao.insert(data.getId(), data.getStarted(), data.getLasted(), data.getCycleLength());
        return Response.ok(new ResponseTemplate(true).build()).build();
    }

    @GET
    @Path("/prediction/{sessionID}")
    public Response getPrediction(@PathParam("sessionID") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            final PeriodData data = dao.getData(accountID);
            if(data != null) {
                final String datePrediction = PeriodPrediction.getNextPeriodDate(data.getStarted(), data.getCycleLength());
                return Response.ok(new ResponseTemplate(true).put("next", datePrediction).build()).build();
            }
            return Response.ok(new ResponseTemplate(true).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @POST
    @Path("/update")
    public Response updateData(PeriodData data) {
        dao.update(data.getId(), data.getStarted(), data.getLasted(), data.getCycleLength());
        return Response.ok(new ResponseTemplate(true).build()).build();
    }

    @GET
    @Path("/{sessionID}")
    public Response getData(@PathParam("sessionID") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            final PeriodData data = dao.getData(accountID);
            if(data != null) {
                return Response.ok(new ResponseTemplate(true).put("data", data).build()).build();
            }
            return Response.ok(new ResponseTemplate(true).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }
}