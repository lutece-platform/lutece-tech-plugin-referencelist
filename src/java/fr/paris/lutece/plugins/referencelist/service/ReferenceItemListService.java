package fr.paris.lutece.plugins.referencelist.service;

import java.util.List;

import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItemHome;

/**
 *
 * ReferenceItemListService
 *
 */
/**
 * This class provides instances management methods for ReferenceItemList
 */
public class ReferenceItemListService {

    /* This class implements the Singleton design pattern. */
    private static ReferenceItemListService _singleton;

    /**
     * Returns the instance of ReferenceItemListService
     * 
     * @return the ReferenceItemListService instance
     */
    public static ReferenceItemListService getInstance() {
        if (_singleton == null) {
            _singleton = new ReferenceItemListService();
        }

        return _singleton;
    }

    /**
     * Returns the list of all ReferenceItems
     * 
     * @param id of Reference
     * @return the list of all ReferenceItems
     */
    public List<ReferenceItem> getReferenceItemsList(int idReference) {
        List<ReferenceItem> listReferenceItems = ReferenceItemHome.getReferenceItemsList(idReference);
        return listReferenceItems;
    }

}