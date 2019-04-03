package fr.paris.lutece.plugins.referencelist.service;

import java.util.List;
import java.util.Locale;

import fr.paris.lutece.plugins.referencelist.business.Reference;
import fr.paris.lutece.plugins.referencelist.business.ReferenceHome;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

public class ReferenceIdService extends ResourceIdService {
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "referencelist.rbac.referenceitem.resourceType";
    private static final String PROPERTY_LABEL_CREATE = "referencelist.rbac.referenceitem.permission.create";

    /**
     * {@inheritDoc }
     */
    @Override
    public void register() {
        ResourceType rt = new ResourceType();
        rt.setResourceIdServiceClass(ReferenceIdService.class.getName());
        rt.setPluginName("referencelist");
        rt.setResourceTypeKey(Reference.RESOURCE_TYPE);
        rt.setResourceTypeLabelKey(PROPERTY_LABEL_RESOURCE_TYPE);

        Permission p = new Permission();
        p.setPermissionKey(Reference.PERMISSION_CREATE);
        p.setPermissionTitleKey(PROPERTY_LABEL_CREATE);
        rt.registerPermission(p);

        // ... for all permissions

        ResourceTypeManager.registerResourceType(rt);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList getResourceIdList(Locale locale) {
        List<Reference> listReference = ReferenceHome.getReferencesList();

        return ReferenceList.convert(listReference, "id", "name", true);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getTitle(String strId, Locale locale) {
        Reference reference = ReferenceHome.findByPrimaryKey(Integer.parseInt(strId));

        return reference.getName();
    }

}