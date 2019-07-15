package wm.wastemarche.services.datacenter;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.model.Bid;
import wm.wastemarche.model.Category;
import wm.wastemarche.model.Item;
import wm.wastemarche.model.Notification;
import wm.wastemarche.model.Request;
import wm.wastemarche.model.Thread;
import wm.wastemarche.model.Transportation;
import wm.wastemarche.model.User;
import wm.wastemarche.services.http.Response;
import wm.wastemarche.services.http.bids.BidsApi;
import wm.wastemarche.services.http.bids.BidsApiProtocol;
import wm.wastemarche.services.http.categories.CategoriesApi;
import wm.wastemarche.services.http.categories.CategoriesApiProtocol;
import wm.wastemarche.services.http.items.ItemsApi;
import wm.wastemarche.services.http.items.ItemsApiProtocol;
import wm.wastemarche.services.http.notifications.NotificationApiProtocol;
import wm.wastemarche.services.http.requests.RequestApiProtocol;
import wm.wastemarche.services.http.threads.ThreadApiProtocol;
import wm.wastemarche.services.http.transportations.TransportationsApi;
import wm.wastemarche.services.http.transportations.TransportationsApiProtocol;

public class DataCenter implements ItemsApiProtocol, BidsApiProtocol, NotificationApiProtocol, RequestApiProtocol, ThreadApiProtocol, TransportationsApiProtocol, CategoriesApiProtocol {

    private DataCenterProtocol delegate = new DataCenterProtocol();

    private final static int pageSize = 10;
    public static String lang = "";
    public static String token;
    public static User user;

    private static List<Item> myItems = new ArrayList<>(0);
    private static List<Item> sellItems = new ArrayList<>(0);
    private static List<Item> buyItems = new ArrayList<>(0);
    private static List<Item> disposeItems = new ArrayList<>(0);

    private static List<Bid> bids = new ArrayList<>(0);

    private static List<Transportation> allTransportationItems = new ArrayList<>(0);
    private static List<Transportation> transportWasteItems = new ArrayList<>(0);
    private static List<Transportation> disposeWasteItems = new ArrayList<>(0);

    private static List<Category> categories = new ArrayList<>(0);

    public DataCenter(final DataCenterProtocol delegate) {
        if (delegate != null) {
            this.delegate = delegate;
        }
    }

    private static <E> List<E> getPage(final int page, final List<E> list) {
        //if( list.isEmpty() ) return list;
        //
        //if( page*pageSize <= list.size() ) {
        //    return list.subList((page-1)*pageSize, page*pageSize-1);
        //}
        //
        //if( (page-1)*pageSize < list.size() ) {
        //    return list.subList((page-1)*pageSize, list.size()-1);
        //}

        return new ArrayList<E>(0);
    }

    @Override
    public void apiError(final int errorCode) {
        Response.log("" + errorCode);
    }

    /******* Items Start ********/
    public void getMyItems(final String page) {
        final List<Item> list = getPage(Integer.parseInt(page), myItems);

        if (list.isEmpty()) {
            final ItemsApi itemsApi = new ItemsApi(this);
            itemsApi.getItems("", "1", page);
        } else {
            delegate.myItemsLoaded(page, list);
        }
    }

    public void getSellItems(final String page) {
        final List<Item> list = getPage(Integer.parseInt(page), sellItems);

        if (list.isEmpty()) {
            final ItemsApi itemsApi = new ItemsApi(this);
            itemsApi.getItems("2", "", page);
        } else {
            delegate.sellItemsLoaded(page, list);
        }
    }

    public Item getSellItem(final int id) {
        for (int i = 0, len = sellItems.size(); i < len; i++) {
            if (sellItems.get(i).id == id) return sellItems.get(i);
        }
        return null;
    }

    public void getBuyItems(final String page) {
        final List<Item> list = getPage(Integer.parseInt(page), buyItems);

        if (list.isEmpty()) {
            final ItemsApi itemsApi = new ItemsApi(this);
            itemsApi.getItems("1", "", page);
        } else {
            delegate.buyItemsLoaded(page, list);
        }
    }

    public Item getBuyItem(final int id) {
        for (int i = 0, len = buyItems.size(); i < len; i++) {
            if (buyItems.get(i).id == id) {
                return buyItems.get(i);
            }
        }
        return null;
    }

    public void getDisposeItems(final String page) {
        final ItemsApi itemsApi = new ItemsApi(this);
        itemsApi.getItems("3", "", page);
    }

    @Override
    public void itemsLoaded(final String method, final String self, final String page, final List<Item> items) {
        if (self.isEmpty()) {
            switch (method) {
                case "1":
                    sellItems.addAll(items);
                    delegate.sellItemsLoaded(page, items);
                    break;
                case "2":
                    buyItems.addAll(items);
                    delegate.buyItemsLoaded(page, items);
                    break;
                case "3":
                    disposeItems.addAll(items);
                    delegate.disposeItemsLoaded(page, items);
                    break;
                default:
            }
        } else {
            myItems.addAll(items);
            delegate.myItemsLoaded(page, items);
        }
    }

    @Override
    public void itemLoaded(final Item item) {
        Response.log("item loaded");
    }

    @Override
    public void itemCreated(Item item) {
    }
    /******** Items End ********/

    /******** Bids Start *******/
    public void getBids(final String page) {
        final List<Bid> list = getPage(Integer.parseInt(page), bids);

        if (list.isEmpty()) {
            final BidsApi bidsApi = new BidsApi(this);
            bidsApi.getBids(page);
        } else {
            delegate.bidsLoaded(page, list);
        }
    }

    @Override
    public void bidsLoaded(final String page, final List<Bid> bids) {
        bids.addAll(bids);
        delegate.bidsLoaded(page, bids);
    }

    @Override
    public void bidLoaded(final Bid bid) {
        Response.log("bid loaded");
    }
    /******** Bids End *********/

    /********* Notifications Start **********/
    @Override
    public void notificationsLoaded(final String page, final List<Notification> notifications) {
        Response.log("notifications loaded");
    }

    @Override
    public void notificationLoaded(final Notification notification) {
        Response.log("notification loaded");
    }

    /********* Notifications End ***********/

    @Override
    public void requestsLoaded(final String page, final List<Request> requests) {
        Response.log("requests loaded");
    }

    @Override
    public void requestLoaded(final Request request) {
        Response.log("request laoaded");
    }

    @Override
    public void requestExtraInfoCompleted() {
    }

    @Override
    public void sendOfferCompleted() {
    }

    @Override
    public void threadsLoaded(final String page, final List<Thread> threads) {
        Response.log("threads loaded");
    }

    @Override
    public void threadLoaded(final Thread thread) {
        Response.log("thread loaded");
    }

    /********** Transportation Start **********/
    public void getTransportWaste(final String page) {
        final List<Transportation> list = getPage(Integer.parseInt(page), transportWasteItems);

        if (list.isEmpty()) {
            final TransportationsApi transportationsApi = new TransportationsApi(this);
            transportationsApi.getTransportations(page, "4");
        } else {
            delegate.trasnportWasteLoaded(page, list);
        }
    }

    public void getDisposeWaste(final String page) {
        final List<Transportation> list = getPage(Integer.parseInt(page), disposeWasteItems);

        if (list.isEmpty()) {
            final TransportationsApi transportationsApi = new TransportationsApi(this);
            transportationsApi.getTransportations(page, "5");
        } else {
            delegate.disposeWasteLoaded(page, list);
        }
    }

    public void getAllTransport(final String page) {
        final List<Transportation> list = getPage(Integer.parseInt(page), allTransportationItems);

        if (list.isEmpty()) {
            final TransportationsApi transportationsApi = new TransportationsApi(this);
            transportationsApi.getTransportations(page, "");
        } else {
            delegate.allTransportLoaded(page, list);
        }
    }

    @Override
    public void transportationsLoaded(final String page, final String method_id, final List<Transportation> transportations) {
        switch (method_id) {
            case "4":
                transportWasteItems.addAll(transportations);
                delegate.trasnportWasteLoaded(page, transportations);
                break;
            case "5":
                disposeWasteItems.addAll(transportations);
                delegate.disposeWasteLoaded(page, transportations);
                break;
            case "":
                allTransportationItems.addAll(transportations);
                delegate.allTransportLoaded(page, transportations);
                break;
            default:
        }
    }

    @Override
    public void transportationLoaded(final Transportation transportation) {
        Response.log("transportation loaded");
    }

    @Override
    public void transportationCreated() {
    }
    /********** Transportation End ***********/

    /************ Categories Start /**************/
    public void getCategories() {
        if (categories.isEmpty()) {
            final CategoriesApi categoriesApi = new CategoriesApi(this);
            categoriesApi.getCategories();
        } else {
            delegate.categoreisLoaded(categories);
        }
    }

    public static String getCategoryIdAtIndex(final int index) {
        if (index == 0) {
            return "-1";
        }

        if (1 > categories.size()) {
            return "-1";
        }

        if (index > categories.get(0).child_category.size()) {
            return "-1";
        }

        return String.valueOf(categories.get(0).child_category.get(index - 1).id);
    }

    public static int getIndexOfCategory(final String categoryId) {
        int index = 0;
        for (int i = 0, len = categories.size(); i < len; i++) {
            if (categories.get(i).id == Integer.valueOf(categoryId).intValue()) {
                return index;
            }
            index++;
            for (int j = 0, lenn = categories.get(i).child_category.size(); j < lenn; j++) {
                if (categories.get(i).child_category.get(j).id == Integer.valueOf(categoryId).intValue()) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    @Override
    public void categoriesLoaded(final List<Category> categories) {
        DataCenter.categories = categories;
        delegate.categoreisLoaded(categories);
    }

    @Override
    public void categoriesFialed(final String error) {
    }
    /************ Categories End /****************/
}
