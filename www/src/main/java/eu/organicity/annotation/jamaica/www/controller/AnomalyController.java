package eu.organicity.annotation.jamaica.www.controller;

import com.amaxilatis.orion.model.subscribe.OrionEntity;
import com.amaxilatis.orion.model.subscribe.SubscriptionResponse;
import eu.organicity.annotation.jamaica.www.dto.AnomalyConfigDTO;
import eu.organicity.annotation.jamaica.www.model.AnomalyConfig;
import eu.organicity.annotation.jamaica.www.utils.RandomStringGenerator;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AnomalyController extends BaseController {

    /**
     * a log4j logger to print messages.
     */


    protected static final Logger LOGGER = Logger.getLogger(AnomalyController.class);
    final RandomStringGenerator randomStringGenerator = new RandomStringGenerator();

    /**
     * Adds a new Anomaly Detection Job to the service.
     * <p>
     *
     * @param response      the {@see HttpServletResponse} object.
     * @param anomalyConfig the {@see AnomalyConfigDTO} that describes the job to add.
     * @return the added {@see AnomalyConfigDTO}.
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/config/anomaly", method = RequestMethod.PUT, produces = "application/json")
    AnomalyConfigDTO putAnomalyConfig(final HttpServletResponse response, @RequestBody AnomalyConfigDTO anomalyConfig) {
        LOGGER.debug("[call] putAnomalyConfig");


        OrionEntity e = new OrionEntity();
        e.setId(anomalyConfig.getIdPat());
        e.setIsPattern("true");
        e.setType(anomalyConfig.getTypePat());
        String[] cond = new String[1];
        cond[0] = "TimeInstant";

        final String randUiid = randomStringGenerator.getUuid();


        try {
            // subscribe to Orion
            SubscriptionResponse r = orionService.subscribeToOrion(e, null, baseUrl + "notifyContext/" + randUiid, cond, "P1D");


            final String subscriptionId = r.getSubscribeResponse().getSubscriptionId();
            LOGGER.info("successful subscription to orion. Returned subscriptionId: " + subscriptionId);

            if (anomalyConfigRepository.count() > 0) {
                // get max jubatus port entry
                Integer maxJubatusPortEntry = anomalyConfigRepository.findMaxJubatusPort();
                if (maxJubatusPortEntry == null) {
                    maxJubatusPortEntry = 1;
                }
                // add 1 to create next port number
                basePort = maxJubatusPortEntry + 1;
            }

            // save anomaly config entry
            AnomalyConfig storedConfig = anomalyConfigRepository.save(new AnomalyConfig(anomalyConfig.getTypePat(), anomalyConfig.getIdPat(), anomalyConfig.getAttribute(), "tags", randomStringGenerator.getUuid(), randUiid, basePort, jubatusHost, subscriptionId));
            LOGGER.info("successful save new anomaly detection job. Returned id: " + storedConfig.getId());

            return new AnomalyConfigDTO(storedConfig);

        } catch (IOException er) {
            LOGGER.error(er, er);

        } catch (DataAccessException er) {
            LOGGER.error(er, er);

        }
        return null;


    }

    /**
     * Gets the information of an existing Anomaly Detection Job.
     *
     * @param response the {@see HttpServletResponse} object.
     * @param id       the id of the requested {@see AnomalyConfigDTO}.
     * @return the existing {@see AnomalyConfigDTO}.
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/config/anomaly/{id}", method = RequestMethod.GET, produces = "application/json")
    AnomalyConfigDTO getAnomalyConfig(final HttpServletResponse response, @PathVariable("id") long id) {
        LOGGER.debug("[call] getAnomalyConfig");

        AnomalyConfig config = anomalyConfigRepository.findById(id);

        return new AnomalyConfigDTO(config);
    }

    /**
     * Removes the information of an existing Anomaly Detection Job.
     *
     * @param response the {@see HttpServletResponse} object.
     * @param id       the id of the requested {@see AnomalyConfigDTO}.
     * @return the existing {@see AnomalyConfigDTO}.
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/config/anomaly/{id}", method = RequestMethod.DELETE, produces = "application/json")
    AnomalyConfigDTO deleteAnomalyConfig(final HttpServletResponse response, @PathVariable("id") long id) {
        LOGGER.debug("[call] getAnomalyConfig");

        AnomalyConfig config = anomalyConfigRepository.findById(id);
        anomalyConfigRepository.delete(id);

        return new AnomalyConfigDTO(config);
    }
}