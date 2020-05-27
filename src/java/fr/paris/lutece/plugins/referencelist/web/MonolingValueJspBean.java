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

import fr.paris.lutece.plugins.referencelist.business.MonolingValue;
import fr.paris.lutece.plugins.referencelist.business.ReferenceHome;
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
 * This class provides the user interface to manage monolingual values features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageMonolingValues.jsp", controllerPath = "jsp/admin/plugins/referencelist/", right = "REFERENCELIST_MANAGEMENT" )
public class MonolingValueJspBean extends AbstractReferenceListManageJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_MONOLING_VALUES = "/admin/plugins/referencelist/manage_monoling_values.html";
    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_REFERENCES = "referencelist.manage_monoling_values.pageTitle";
    /* Markers */
    // marker for the list in the template
    private static final String MARK_MONOLING_VALUES_LIST = "monoling_values_list";
    
    private static final String JSP_MANAGE_MONOLING_VALUES = "jsp/admin/plugins/referencelist/ManageMonolingValues.jsp";
    
    // Views
    private static final String VIEW_MANAGE_MONOLING_VALUES = "manageMonolingValues";
    // Session variable to store working values   


    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_MONOLING_VALUES, defaultView = true )
    public String getManageReferences( HttpServletRequest request )
    {
        List<MonolingValue> listMonolingValues = new ArrayList<MonolingValue>( );//ReferenceHome.getReferencesList( );
        
        MonolingValue value = new MonolingValue();
        value.setName("civilite.monsieur");
        value.setLang("fr");
        value.setValue("M.");
        listMonolingValues.add( value );
        
        value = new MonolingValue();
        value.setName("civilite.monsieur");
        value.setLang("es");
        value.setValue("Sr");
        listMonolingValues.add( value );
        
        value = new MonolingValue();
        value.setName("civilite.madame");
        value.setLang("fr");
        value.setValue("Mme");
        listMonolingValues.add( value );
        
        value = new MonolingValue();
        value.setName("civilite.madame");
        value.setLang("es");
        value.setValue("Sra");
        listMonolingValues.add( value );
        
        // getPaginatedListModel should be added in Abstract class
        Map<String, Object> model = getPaginatedListModel( request, MARK_MONOLING_VALUES_LIST, listMonolingValues, JSP_MANAGE_MONOLING_VALUES );
        
        return getPage( PROPERTY_PAGE_TITLE_MANAGE_REFERENCES, TEMPLATE_MANAGE_MONOLING_VALUES, model );
    }

}
