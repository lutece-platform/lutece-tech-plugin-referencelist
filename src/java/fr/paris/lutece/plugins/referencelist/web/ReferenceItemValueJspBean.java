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

import fr.paris.lutece.plugins.referencelist.business.ReferenceItemHome;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItemValue;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItemValueHome; 
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.util.AppLogService;
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
    private static final String JSP_MANAGE = "jsp/" + PLUGIN_PATH + "ManageReferenceItemValues.jsp";

    /* List View */
    private static final String VIEW_MANAGE = "manage";
    private static final String TEMPLATE_MANAGE = PLUGIN_PATH + "manage_referenceitemvalues.html";
    private static final String MARK_MANAGE = "referenceitemvalues_list";
    private static final String PROPERTY_PAGE_TITLE_MANAGE = "referencelist.manage_referenceitemvalues.pageTitle";
    
    /* Create View */
    private static final String VIEW_CREATE = "create";
    private static final String TEMPLATE_CREATE = PLUGIN_PATH + "create_referenceitemvalue.html";
    private static final String PROPERTY_PAGE_TITLE_CREATE = "referencelist.create_referenceitemvalue.pageTitle";
    private static final String ACTION_CREATE = VIEW_CREATE;
    
    /* Modify View */
    private static final String VIEW_MODIFY = "modify";
    private static final String TEMPLATE_MODIFY = PLUGIN_PATH + "modify_referenceitemvalue.html";
    private static final String MARK_MODIFY = "referenceitemvalues_list";
    private static final String PROPERTY_PAGE_TITLE_MODIFY = "referencelist.modify_referenceitemvalue.pageTitle";
    private static final String ACTION_MODIFY = VIEW_MODIFY;

    /* Removal */
    private static final String ACTION_REMOVE = "remove";
    
    /**
     * Handles the removal form of a referenceitemvalue
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage referenceitemvalues
     */
    @Action( ACTION_REMOVE )
    public String doRemoveReferenceItemValue( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( "id" ) );

        ReferenceItemValueHome.remove( nId );
        
        addInfo( "referencelist.info.referenceitemvalue.removed", getLocale( ) );
        
        return redirect( request, VIEW_MANAGE, "id", 1 );
    }
    
    /**
     * Returns the form to update info about a referenceitemvalue
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY )
    public String getModifyReferenceItemValue( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( "id" ) );

        ReferenceItemValue referenceItemValue = ReferenceItemValueHome.findByPrimaryKey( nId );
        
        Map<String, Object> model = getModel( );
        model.put( MARK_MODIFY, referenceItemValue );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY, TEMPLATE_MODIFY, model );
    }
    
    /**
     * Process the change form of a ReferenceItemValue
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY )
    public String doModifyReferenceItemValue( HttpServletRequest request )
    {
    	ReferenceItemValue itemValue = new ReferenceItemValue( );
    	
        populate( itemValue, request, request.getLocale( ) );
        
        // @TODO get the reference item or reference id
        //IdReference = itemValue.getIdreference( );
        
        // @TODO Check constraints
        /*if ( !validateBean( _referenceitem, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_REFERENCEITEM, PARAMETER_ID_REFERENCEITEM, _referenceitem.getId( ) );
        }*/

        ReferenceItemValueHome.update( itemValue );
        
        addInfo( "referencelist.info.referenceitemvalue.updated", getLocale( ) ); 
        
        // @TODO transmit the reference id as a session data
        return redirectView( request, VIEW_MANAGE);
    }
    
    /**
     * Returns the form to create a reference item value
     *
     * @param request
     *            The Http request
     * @return the html code of the reference item value form
     */
    @View( VIEW_CREATE )
    public String getCreateReferenceItemValue( HttpServletRequest request )
    {
        Map<String, Object> model = getModel( );
        
        return getPage( PROPERTY_PAGE_TITLE_CREATE, TEMPLATE_CREATE, model );
    }
    
    
    /**
     * Process the data capture form of a new referenceitem
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE )
    public String doCreateReferenceItemValue( HttpServletRequest request )
    {
    	ReferenceItemValue itemValue = new ReferenceItemValue( );
    	
    	populate( itemValue, request, request.getLocale( ) );
        
    	// @TODO get the reference item or reference id
    	// int idReferenceItem = 1;
        
        // @TODO Check constraints
  /*      if ( !validateBean( _referenceitem, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_REFERENCEITEM );
        }*/

        ReferenceItemValueHome.create( itemValue );
        
        addInfo( "referencelist.info.referenceitemvalue.created", getLocale( ) );
        
        // @TODO transmit the reference id
        return redirect( request, VIEW_MANAGE, "id", 1);
    }
    
    /**
     * Build the Default List View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE, defaultView = true )
    public String getManageReferenceItemValues( HttpServletRequest request )
    {
    	//@TODO récupérer l'identifiant du référentiel
        List<ReferenceItemValue> listReferenceItemValues = ReferenceItemValueHome.getReferenceItemValueList( 0 );
        
        // getPaginatedListModel should be added in Abstract class
        Map<String, Object> model = getPaginatedListModel( request, MARK_MANAGE, listReferenceItemValues, JSP_MANAGE );
        
        return getPage( PROPERTY_PAGE_TITLE_MANAGE, TEMPLATE_MANAGE, model );
    }
    
    
    

}
