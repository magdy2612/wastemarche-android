package wm.wastemarche.services.http.bids;

import java.util.List;

import wm.wastemarche.model.Bid;
import wm.wastemarche.services.http.ApiProtocol;

public interface BidsApiProtocol extends ApiProtocol {

    void bidsLoaded(String page, List<Bid>bids);

    void bidLoaded(Bid bid);
}
