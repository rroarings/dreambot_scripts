package pfdriftnets.src.data;

import org.dreambot.api.methods.map.Area;

public class DriftNetsConfig {

    private static final DriftNetsConfig driftNetsConfig = new DriftNetsConfig();

    private DriftNetsConfig() {}

    public static DriftNetsConfig getDriftNetsConfig() {
        return driftNetsConfig;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        DriftNetsConfig.status = status;
    }

    public static String status = "";

    public int netsMade;
    public int juteFibreUsed;
    public int profit;

    public boolean isUseStaminas() {
        return isUseStaminas;
    }

    public void setUseStaminas(boolean useStaminas) {
        isUseStaminas = useStaminas;
    }

    public boolean isWorldHopEnabled() {
        return isWorldHopEnabled;
    }

    public void setWorldHopEnabled(boolean worldHopEnabled) {
        isWorldHopEnabled = worldHopEnabled;
    }

    public boolean isUseStaminas = false;
    public boolean isWorldHopEnabled = false;

    public int getNetsPrice() {
        return netsPrice;
    }

    public void setNetsPrice(int netsPrice) {
        this.netsPrice = netsPrice;
    }

    public int netsPrice;

    public Area getFOSSIL_LOOM() {
        return FOSSIL_LOOM;
    }

    public int getNetsMade() {
        return netsMade;
    }

    public void setNetsMade(int netsMade) {
        this.netsMade = netsMade;
    }

    public int getJuteFibreUsed() {
        return juteFibreUsed;
    }

    public void setJuteFibreUsed(int juteFibreUsed) {
        this.juteFibreUsed = juteFibreUsed;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    private Area FOSSIL_LOOM = new Area(3729, 3823, 3733, 3820);
}
