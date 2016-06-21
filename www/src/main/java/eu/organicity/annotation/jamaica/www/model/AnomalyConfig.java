package eu.organicity.annotation.jamaica.www.model;


/**
 * Created by katdel on 30-May-16.
 */


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class AnomalyConfig {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String typePat;
    private String idPat;
    private String attribute;
    private String tags;
    private String urlExt;
    private String urlOrion;
    private int jubatusPort;
    private String jubatusConfig;
    private String subscriptionId;

    protected AnomalyConfig() {}

    public AnomalyConfig(String typePat, String idPat, String attribute, String tags, String urlExt, String urlOrion, int jubatusPort, String jubatusConfig, String subscriptionId) {
        this.typePat = typePat;
        this.idPat = idPat;
        this.attribute = attribute;
        this.tags = tags;
        this.urlExt = urlExt;
        this.urlOrion = urlOrion;
        this.jubatusPort = jubatusPort;
        this.jubatusConfig = jubatusConfig;
        this.subscriptionId = subscriptionId;

    }

    @Override
    public String toString() {
        return String.format(
                "AnomalyConfig[id=%d, typePat='%s', idPat='%s',attribute='%s', tags='%s', urlExt='%s', urlOrion='%s', jubatusPort='%d', jubatusConfig='%s', subscriptionId='%s']",
                id, typePat, idPat, attribute, tags, urlExt, urlOrion, jubatusPort, jubatusConfig, subscriptionId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJubatusConfig(){
        return jubatusConfig;
    }

    public String getTypePat(){
        return typePat;
    }

    public String getIdPat(){
        return idPat;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getTags(){
        return tags;
    }

    public String getUrlExt(){
        return urlExt;
    }

    public String getUrlOrion(){
        return urlOrion;
    }

    public int getJubatusPort(){
        return jubatusPort;
    }

    public String getSubscriptionId(){
        return subscriptionId;
    }
}
