package fr.paris.lutece.plugins.referencelist.service;

import java.util.List;
import fr.paris.lutece.plugins.referencelist.business.CompareResult;
import fr.paris.lutece.plugins.referencelist.business.Reference;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItemHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.rbac.RBACService;

/**
 * Check & Import CSV File
 */
public final class ReferenceImport {

   public ReferenceImport() {
   }

   /**
    * CSV Import for a specific Referential.
    * 
    * @param fileItem The file item to read data from
    * @param refId    ID of Reference
    * @return a String with the import source result or null if an error occurs
    *         during the instantiation of the import source.
    */
   public static boolean doImportCSV(CompareResult compareResult, int refId, AdminUser adminUser) {

      if (!RBACService.isAuthorized(Reference.RESOURCE_TYPE, String.valueOf(refId), Reference.PERMISSION_CREATE,
            adminUser))
         return false;
      return doImportCSV(compareResult, refId);
   }

   /**
    * CSV Import for a specific Referential.
    * 
    * @param compareResult Lists to insert or update;
    * @param refId         ID of Reference
    * @return a String with the import source result or null if an error occurs
    *         during the instantiation of the import source.
    */
   private static boolean doImportCSV(CompareResult compareResult, int refId) {

      List<ReferenceItem> updateReferenceItems = compareResult.get_updateListCandidateReferenceItems();
      List<ReferenceItem> insertReferenceItems = compareResult.get_insertListCandidateReferenceItems();
      // insert
      for (ReferenceItem candidateItem : insertReferenceItems) {
         ReferenceItemHome.create(candidateItem);
      }
      // update
      for (ReferenceItem candidateItem : updateReferenceItems) {
         ReferenceItemHome.update(candidateItem);
      }

      return true;

   }

}