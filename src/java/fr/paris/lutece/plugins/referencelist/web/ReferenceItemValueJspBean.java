/*
 * Copyright (c) 2002-2019, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.referencelist.web;

import fr.paris.lutece.plugins.referencelist.business.ReferenceItemValue;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItemValueHome; 
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage reference item values ( list, create, modify, remove )
 */
@Controller( controllerJsp = "ManageReferenceItemValues.jsp", controllerPath = "jsp/admin/plugins/referencelist/", right = "REFERENCELIST_MANAGEMENT" )
public class ReferenceItemValueJspBean extends AbstractReferenceListManageJspBean
{	
	// plugin relative path
	public static final String PLUGIN_PATH = "/admin/plugins/referencelist/";
	
    // JSP
    private static final String JSP_MANAGE_REFERENCE_ITEM_VALUES = "jsp/" + PLUGIN_PATH + "ManageReferenceItemValues.jsp";

    /* List View */
    private static final String VIEW_LIST_REFERENCE_ITEM_VALUES = "manageReferenceItemValues";
    private static final String TEMPLATE_LIST_REFERENCEITEMVALUES = PLUGIN_PATH + "manage_reference_item_values.html";
    private static final String MARK_LIST_REFERENCEITEMVALUES = "referenceitemvalues_list";
    private static final String PROPERTY_PAGE_TITLE_LIST_REFERENCEITEMVALUES = "referencelist.manage_reference_item_values.pageTitle";
    
    /* Create View */
    private static final String VIEW_CREATE_REFERENCE_ITEM_VALUES = "createReferenceItemValues";
    private static final String TEMPLATE_CREATE_REFERENCEITEMVALUES = PLUGIN_PATH + "/manage_reference_item_values.html";
    private static final String PROPERTY_PAGE_TITLE_CREATE_REFERENCEITEMVALUES = "referencelist.manage_reference_item_values.pageTitle";
   
    /**
     * Returns the form to create a reference item value
     *
     * @param request
     *            The Http request
     * @return the html code of the reference item value form
     */
    @View( VIEW_CREATE_REFERENCE_ITEM_VALUES )
    public String getCreateReferenceItem( HttpServletRequest request )
    {
        Map<String, Object> model = getModel( );
        
        return getPage( PROPERTY_PAGE_TITLE_CREATE_REFERENCEITEMVALUES, TEMPLATE_CREATE_REFERENCEITEMVALUES, model );
    }
    
    /**
     * Build the Default List View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_LIST_REFERENCE_ITEM_VALUES, defaultView = true )
    public String getManageReferenceItemValues( HttpServletRequest request )
    {
    	
    	//@TODO récupérer l'identifiant du référentiel
        List<ReferenceItemValue> listReferenceItemValues = ReferenceItemValueHome.getReferenceItemValueList( 0 );
        
        // getPaginatedListModel should be added in Abstract class
        Map<String, Object> model = getPaginatedListModel( request, MARK_LIST_REFERENCEITEMVALUES, listReferenceItemValues, JSP_MANAGE_REFERENCE_ITEM_VALUES );
        
        return getPage( PROPERTY_PAGE_TITLE_LIST_REFERENCEITEMVALUES, TEMPLATE_LIST_REFERENCEITEMVALUES, model );
    }
    
    
    

}
